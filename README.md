# Gasto Hogar API

API REST para el control de gastos del hogar, diseñada para ser consumida por un frontend Ionic.

## Stack

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Web, Spring Data JPA, Spring Security, Bean Validation**
- **Flyway** (migraciones de base de datos)
- **MySQL** (base de datos)
- **Maven** (gestor de dependencias)
- **OpenAPI/Swagger** (documentación)
- **JUnit 5, Mockito** (pruebas)

## Estructura del proyecto

```
com.vjtech.gastoshogar/
├── adapter/
│   ├── config/          # Seguridad, CORS, JWT, Swagger
│   ├── persistence/     # Entidades JPA, Repositorios, Mappers
│   └── rest/            # Controladores, DTOs, Excepciones
├── application/
│   ├── port/in/         # Puertos de entrada (interfaces de uso)
│   ├── port/out/        # Puertos de salida
│   └── service/         # Implementaciones de casos de uso
├── domain/
│   ├── model/           # Modelos de dominio
│   ├── enums/           # Enumeraciones
│   └── port/            # Interfaces de repositorio de dominio
└── common/              # Configuración compartida, excepciones
```

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Registrar usuario |
| POST | `/api/v1/auth/login` | Iniciar sesión |
| GET | `/api/v1/auth/me` | Obtener usuario autenticado |
| GET/POST/PUT/DELETE | `/api/v1/hogares` | Gestionar hogares |
| GET/POST/PUT/DELETE | `/api/v1/categorias` | Gestionar categorías |
| GET/POST | `/api/v1/presupuestos` | Presupuestos |
| GET | `/api/v1/presupuestos/resumen` | Resumen por quincena |
| GET/POST/PUT/DELETE | `/api/v1/gastos` | Gastos |
| POST | `/api/v1/gastos/{id}/pagar` | Marcar gasto como pagado |
| GET/POST/PUT/DELETE | `/api/v1/deudas` | Deudas |
| POST | `/api/v1/deudas/{id}/pagos` | Agregar pago a deuda |
| GET/POST/PUT/DELETE | `/api/v1/tarjetas` | Tarjetas de crédito |
| GET | `/api/v1/tarjetas/{id}/movimientos` | Movimientos de tarjeta |

## Documentación

La documentación OpenAPI (Swagger) está disponible en: `http://localhost:8080/swagger-ui/index.html`
La referencia de la API está disponible en: `http://localhost:8080/v3/api-docs`

## Variables de entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `JWT_SECRET` | Clave secreta para JWT | `ChangeThisSecretKeyToAtLeast32CharactersLong!` |
| `JWT_EXPIRATION` | Expiración del token en ms | `86400000` |
| `SPRING_DATASOURCE_URL` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/gasto_hogar` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | `` |
| `APP_CORS_ALLOWED_ORIGINS` | Orígenes CORS permitidos | `http://localhost:8100` |

## Comandos

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### Compilar y ejecutar pruebas
```bash
mvn clean test
```

### Construir el JAR
```bash
mvn clean package
java -jar target/gasto-hogar-api-1.0.0.jar
```

### Ejecutar migraciones de base de datos
```bash
mvn flyway:migrate
```

### Levantar la API con Docker
```bash
docker-compose up -d
```

La API necesita tener MySQL instalado en tu máquina local con la base de datos `gasto_hogar` y el usuario `gasto_user` con contraseña `gasto_password`.

### Crear la base de datos en MySQL local
```bash
mysql -u root -p < setup-database.sql
```

## Prerrequisitos

- Java 21+ o Docker
- Maven 3.9+ (solo si corres sin Docker)
- MySQL 8.0+

## Notas de diseño

- **BigDecimal** para todos los importes monetarios con `RoundingMode.HALF_UP` y escala 2
- **Autenticación JWT stateless** con BCrypt para contraseñas
- **Aislamiento por usuario**: cada usuario solo accede a sus propios datos
- **Formato de respuesta consistente**: `ApiResponse<T>` con `timestamp`, `status`, `code`, `message`, `data`
- **Manejo de errores**: formato `timestamp`, `status`, `code`, `message`, `path`, `fieldErrors`

## Token JWT

El login devuelve un JWT en `data.data.token`. Para usarlo en peticiones posteriores:
- Guardar en `localStorage` con clave `gastos_auth_token`
- Enviar en header `Authorization: Bearer <token>`
- La app frontend (Angular/Ionic) usa este token automáticamente

## App Frontend

La app Ionic/Angular está en `/Users/joser.vazquez/Proyecto/DiseñoAppGastos/app/`:
- `useRest = true` y `restBaseUrl = 'http://localhost:8080/api/v1'` en `AuthService`
- Token guardado automáticamente en `localStorage` tras login
- `authHeaders()` y `authFetch()` disponibles en `auth.service.ts`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`