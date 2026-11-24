# ---------- Etapa 1: build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Primero solo el pom, para reaprovechar la capa cacheada de dependencias
# cuando solo cambie el código.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Etapa 2: runtime ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Usuario sin privilegios
RUN addgroup -S app && adduser -S app -G app
COPY --from=build /app/target/app-gastos-api-1.0.0.jar app.jar
RUN chown -R app:app /app
USER app

EXPOSE 8080

# MaxRAMPercentage en vez de -Xmx fijo: se adapta al límite del contenedor.
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"
ENV CONFIG_LOCATION="/opt/app-gastos-configs/"

# Verifica que la app responde (ruta pública, no requiere token).
HEALTHCHECK --interval=30s --timeout=5s --start-period=45s --retries=3 \
    CMD wget -qO- http://localhost:8080/seg-gastos/api/v1/docs > /dev/null || exit 1

ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -Dspring.config.additional-location=optional:file:${CONFIG_LOCATION} -jar /app/app.jar"]