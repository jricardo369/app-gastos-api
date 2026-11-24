


# Desarrollo del módulo de control de gastos – API Java

Actúa como un desarrollador backend senior, experto en Java, Spring Boot, Spring Data JPA, Hibernate y MySQL.

Estoy desarrollando un sistema de control de gastos personales. Necesito implementar los módulos de **Ingresos, Categorías, Gastos y Dashboard**, incluyendo sus respectivas entidades, relaciones, repositorios, servicios y controladores REST.

Antes de comenzar, analiza la estructura actual del proyecto y respeta la arquitectura, convenciones, nombres de paquetes, patrones de diseño y tecnologías que ya se están utilizando. No modifiques funcionalidades existentes que no estén relacionadas con este desarrollo.

## 1. Módulo de ingresos

Crear una tabla llamada `ingresos` con los siguientes campos:

| Campo | Tipo / Descripción |
|---|---|
| id | Identificador único (PK) |
| descripcion | Descripción del ingreso |
| monto | Importe del ingreso |

Implementar los siguientes servicios REST:

- Alta de ingresos.
- Edición de ingresos.
- Eliminación de ingresos.
- Listado de ingresos.
- Consulta de ingreso por ID.

Consideraciones:
- El monto debe ser un tipo de dato adecuado para valores monetarios (`BigDecimal`).
- Implementar validaciones para evitar montos negativos o descripciones vacías.

## 2. Módulo de categorías

Crear una tabla llamada `categorias` con los siguientes campos:

| Campo | Tipo / Descripción |
|---|---|
| id | Identificador único (PK) |
| descripcion | Nombre de la categoría |

Al crear la tabla, insertar las siguientes categorías iniciales:

1. Hogar
2. Escuela
3. Carro
4. Ejercicio
5. Trj. crédito
6. La Marina
7. Adic.
8. Impv.
9. Ahorro
10. Ropa

Implementar servicios para:
- Listar categorías.
- Consultar categoría por ID.
- Dar de alta una categoría.
- Editar una categoría.
- Eliminar una categoría.

Consideraciones:
- No permitir categorías duplicadas.
- Validar que una categoría no pueda eliminarse si tiene gastos asociados, a menos que exista una estrategia definida para mantener la integridad de los datos.

## 3. Módulo de gastos

Crear una tabla llamada `gastos` con los siguientes campos:

| Campo | Tipo / Descripción |
|---|---|
| id | Identificador único (PK) |
| categoria_id | Relación con la tabla categorias |
| descripcion | Descripción del gasto |
| previsto | Importe presupuestado |
| gastado | Importe realmente gastado |

Relaciones:
- Cada gasto debe pertenecer a una categoría.
- Una categoría puede tener múltiples gastos.
- Utilizar relaciones JPA (`@ManyToOne` y `@OneToMany`) de acuerdo con la arquitectura del proyecto.

Implementar los siguientes servicios REST:
- Alta de gastos.
- Edición de gastos.
- Eliminación de gastos.
- Listado de gastos.
- Consulta de gasto por ID.
- Listado de gastos por categoría.

Consideraciones:
- Los importes deben manejarse con `BigDecimal`.
- Validar que los importes no sean negativos.
- Permitir que el campo `gastado` sea cero al momento de registrar un gasto.
- Evitar eliminar categorías que tengan gastos relacionados.

## 4. Módulo Dashboard

Crear una tabla llamada `dashboard` que permita almacenar y consultar la información financiera de cada periodo.

Campos:

| Campo | Tipo / Descripción |
|---|---|
| id | Identificador único (PK) |
| mes | Mes correspondiente al dashboard |
| tipo | Tipo de periodo: MENSUAL o QUINCENAL |
| ingreso | Total de ingresos del periodo |
| gastos_previstos | Total de gastos presupuestados |
| gastos_variables | Total de gastos variables |
| gastos_reales | Total de gastos realmente realizados |

Relaciones:
- Un registro de dashboard debe tener relacionados sus gastos correspondientes.
- Los gastos deben conservar su relación con sus respectivas categorías.
- Los ingresos deben utilizarse como fuente para calcular el total de ingresos del dashboard.

**Importante:** Diseñar correctamente las relaciones para identificar a qué periodo pertenece cada gasto y evitar que un mismo gasto se contabilice en diferentes periodos.

### 4.1 Servicio de cálculo del Dashboard

Crear un servicio que calcule y regrese la información financiera del periodo solicitado.

El cálculo debe considerar:

- **Ingreso:** Suma de los montos registrados en la tabla `ingresos` correspondientes al periodo.
- **Gastos previstos:** Suma de los importes `previsto` de los gastos relacionados con el periodo.
- **Gastos variables:** Diferencia entre el importe realmente gastado y el importe previsto, considerando únicamente los excedentes positivos.
- **Gastos reales:** Suma de los importes `gastado` de todos los gastos del periodo.

La respuesta debe incluir todos estos valores.

### 4.2 Servicio de gastos por categoría

Crear un servicio que regrese el resumen de gastos agrupados por categoría para el periodo solicitado.

La respuesta debe contener:

- ID de categoría.
- Nombre de categoría.
- Total previsto.
- Total gastado.
- Diferencia entre lo previsto y lo gastado.
- Porcentaje que representa el gasto de esa categoría respecto al total de gastos reales.

Ejemplo de respuesta:

```json
[
  {
    "categoriaId": 1,
    "categoria": "Hogar",
    "previsto": 5000.00,
    "gastado": 4500.00,
    "diferencia": 500.00,
    "porcentaje": 30.00
  }
]
```

## 5. Reglas de negocio y consideraciones técnicas

1. Utilizar `BigDecimal` para todos los cálculos monetarios.
2. Implementar DTOs para las solicitudes y respuestas de los servicios REST.
3. Separar responsabilidades entre Controller, Service y Repository.
4. Implementar validaciones mediante Jakarta Validation.
5. Manejar excepciones de forma centralizada.
6. Utilizar transacciones (`@Transactional`) cuando sea necesario.
7. Evitar consultas N+1 y problemas de serialización de relaciones bidireccionales.
8. Mantener la integridad referencial de las tablas.
9. Documentar los endpoints y sus parámetros.
10. Seguir las convenciones actuales del proyecto.

## 6. Entregables

Generar todo el código necesario para implementar estos módulos:

- Entidades JPA.
- Repositorios.
- DTOs de entrada y salida.
- Servicios e implementaciones.
- Controladores REST.
- Validaciones.
- Manejo de excepciones.
- Scripts SQL o migraciones necesarias.
- Datos iniciales para las categorías.

Antes de implementar, analiza las relaciones y define cómo se manejarán los periodos mensuales y quincenales.

No te limites a generar las entidades: implementa la lógica funcional completa y verifica que los cálculos del dashboard sean correctos.

Al finalizar, muestra:
- Estructura de archivos creados o modificados.
- Relaciones entre las tablas.
- Endpoints disponibles.
- Ejemplos de peticiones y respuestas JSON.
- Decisiones técnicas y reglas de cálculo implementadas.