# Gasto Hogar API

API REST de apoyo para la app web de control de gastos. Gestiona usuarios, membresías y el respaldo del estado completo de la app. Diseñada para ser consumida por un frontend Ionic/Angular.

## Stack

- Java 21, Spring Boot 3.3.4 (Web, Data JPA, Security, Validation)
- MySQL 8, Flyway (migración `V2__limpieza_y_respaldos.sql`; `init.sql` es el script manual inicial)
- JWT (jjwt), OpenAPI/Swagger, Maven, JUnit 5 + AssertJ

## Estructura

```
com.vjtech.gastoshogar/
├── adapter/
│   ├── config/          # Seguridad, CORS, JWT, Swagger, ModelMapper
│   ├── persistence/     # Entidades JPA y repositorios
│   └── rest/            # Controladores, DTOs y manejo de excepciones
├── application/
│   ├── port/in/         # Puertos de entrada
│   └── service/         # Implementaciones
├── domain/enums/        # TipoMembresia, EstadoMembresia
└── common/exception/    # Errores compartidos
```

## Base de datos

Solo tres tablas:

| Tabla | Uso |
|-------|-----|
| `usuarios` | Credenciales y datos del usuario (`user` es el identificador de login) |
| `membresias` | Plan `MONTH`/`YEAR`, `estado` `ACTIVA`/`INACTIVA` y vigencia |
| `respaldos` | **Una fila por usuario** con `payload_json LONGTEXT`, `schema_version` y `updated_at` |

Membresía activa = `estado = 'ACTIVA'` **y** `fecha_inicio <= hoy <= fecha_fin` (`MembresiaServiceImpl.findActiva`). Registrar un usuario crea automáticamente una membresía activa.

El script `src/main/resources/db/migration/init.sql` crea la BD desde cero (borra y recrea las tablas). La migración `V2__limpieza_y_respaldos.sql` elimina las tablas antiguas (`categorias`, `dashboard`, `ingresos`, `gastos`) y crea `respaldos` sobre una BD ya existente.

## Endpoints

Base real en EC2: `http://<host>/seg-gastos/api/v1` (proxy nginx en el puerto 80; el contenedor sigue escuchando en `8080`). En local: `http://localhost:8080/seg-gastos/api/v1`. El `context-path` es `/seg-gastos`.

### Auth — público

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registra usuario + membresía inicial. Requiere `user`, `password` (6-100), `name`, `email`, `membresiaType` (`MONTH`/`YEAR`) |
| POST | `/auth/login` | `{"user":"...","password":"..."}` → token JWT |

### Requieren JWT

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/auth/me` | Usuario autenticado + `membresiaActiva` |
| GET/POST/PUT/DELETE | `/usuarios` | CRUD de usuarios |
| GET/POST/PUT/DELETE | `/membresias` | CRUD de membresías |
| POST | `/respaldos` | Guarda el JSON completo de la app (`schemaVersion`, `payload`) |
| GET | `/respaldos` | Recupera el último respaldo del usuario |

`/respaldos` devuelve **403** si el usuario no tiene membresía activa. El payload es opaco para la API: se guarda tal cual para poder recuperarlo o generar reportes después.

Las respuestas exitosas vienen en `ApiResponse<T>` (`data` contiene el objeto). Los errores usan `ApiErrorResponse` (`code`, `message`, `fieldErrors`).

## Ejemplos

```bash
# Registro
curl -X POST http://localhost:8080/seg-gastos/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"user":"juan","password":"secreto123","name":"Juan","email":"juan@example.com","membresiaType":"MONTH"}'

# Login
curl -X POST http://localhost:8080/seg-gastos/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"user":"juan","password":"secreto123"}'

# Subir respaldo
curl -X POST http://localhost:8080/seg-gastos/api/v1/respaldos \
  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" \
  -d '{"schemaVersion":1,"payload":"{\"gastos\":[],\"deudas\":[]}"}'
```

## Configuración

La configuración **no está en el repo**: se carga desde un `application.properties` externo mediante `spring.config.additional-location`, que tiene prioridad sobre el `application.properties` del classpath.

| Entorno | Ruta | Cómo se carga |
|---------|------|---------------|
| Local (`mvn spring-boot:run`) | `C:\config-apps\app-gastos-configs\application.properties` | `pom.xml` (propiedad `app.config.location`, se puede sobrescribir con `-Dapp.config.location=...`) |
| EC2 (Docker) | `/opt/app-gastos-configs/application.properties` | `docker-compose.yml` monta el directorio readonly y el `Dockerfile` pasa `CONFIG_LOCATION` |

Contenido del archivo (claves nativas de Spring):

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/seguimiento_gastos?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_LOCAL
jwt.secret=CAMBIAR_POR_UNA_CLAVE_DE_AL_MENOS_32_CARACTERES
jwt.expiration=86400000
app.cors.allowed-origins=http://localhost:4200,http://localhost:8100,http://127.0.0.1:4200,http://127.0.0.1:8100
```

En EC2 usa `host.docker.internal:3306` en la URL (MySQL en la misma máquina) y los valores de JWT/CORS de producción. Si el archivo no existe, la app arranca con los defaults del classpath (la location es `optional:`).

Swagger en EC2: `http://<host>/seg-gastos/swagger-ui.html` y `http://<host>/seg-gastos/api/v1/docs`. En local usa `http://localhost:8080/seg-gastos/swagger-ui.html`. Usa **Authorize** con el token JWT (sin `Bearer`).

## Ejecutar y probar

```bash
mvn spring-boot:run
mvn clean test
mvn clean package
```

Docker: `docker compose up -d --build` (ya no requiere `.env`, pero necesita `/opt/app-gastos-configs/application.properties`). El deploy a EC2 se hace con `.github/workflows/deploy.yml`.
