# Colección de ejemplos curl para Gasto Hogar API

## Registro de usuario
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "nombre": "Usuario Test",
    "password": "password123"
  }'
```

## Inicio de sesión
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "password123"
  }'
```

## Obtener usuario autenticado (reemplazar TOKEN)
```bash
curl -X GET http://localhost:8080/api/v1/auth/me \
  -H "Authorization: Bearer TOKEN"
```

## Crear hogar
```bash
curl -X POST http://localhost:8080/api/v1/hogares \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Mi Hogar",
    "moneda": "MXN",
    "zonaHoraria": "America/Mexico_City"
  }'
```

## Obtener hogares
```bash
curl -X GET http://localhost:8080/api/v1/hogares \
  -H "Authorization: Bearer TOKEN"
```

## Crear categoría
```bash
curl -X POST http://localhost:8080/api/v1/categorias \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Hogar",
    "color": "#a8a4ff",
    "icono": "house",
    "estado": "ACTIVO"
  }'
```

## Crear presupuesto
```bash
curl -X POST http://localhost:8080/api/v1/presupuestos \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "anio": 2026,
    "mes": 9,
    "efectivoQ1": 13500,
    "valesQ1": 1345,
    "nominaQ1": 0,
    "efectivoQ2": 13500,
    "valesQ2": 1345,
    "nominaQ2": 0
  }'
```

## Obtener resumen del presupuesto
```bash
curl -X GET "http://localhost:8080/api/v1/presupuestos/resumen?anio=2026&mes=9" \
  -H "Authorization: Bearer TOKEN"
```

## Crear ingreso
```bash
curl -X POST http://localhost:8080/api/v1/presupuestos/ingresos \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "quincena": "Q1",
    "concepto": "Nomina",
    "monto": 13500
  }'
```

## Crear gasto
```bash
curl -X POST http://localhost:8080/api/v1/gastos \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Gasolina",
    "importe": 700,
    "categoriaId": "UUID-CATEGORIA",
    "fecha": "2026-09-01",
    "tipo": "FIJO",
    "observaciones": "Gasolina del mes"
  }'
```

## Obtener gastos con filtros
```bash
curl -X GET "http://localhost:8080/api/v1/gastos?quincena=Q1&tipo=FIJO&estadoPago=PENDIENTE&page=0&size=10&sort=fecha,desc" \
  -H "Authorization: Bearer TOKEN"
```

## Marcar gasto como pagado
```bash
curl -X POST http://localhost:8080/api/v1/gastos/GASTO_ID/pagar \
  -H "Authorization: Bearer TOKEN"
```

## Crear deuda
```bash
curl -X POST http://localhost:8080/api/v1/deudas \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "deudor": "Miguel Angel",
    "descripcion": "Prestamo personal",
    "importeTotal": 1000,
    "fechaVencimiento": "2026-12-31"
  }'
```

## Agregar pago parcial a deuda
```bash
curl -X POST http://localhost:8080/api/v1/deudas/DEUDA_ID/pagos \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "importe": 300,
    "fecha": "2026-09-03"
  }'
```

## Obtener resumen de deudas
```bash
curl -X GET http://localhost:8080/api/v1/deudas/resumen \
  -H "Authorization: Bearer TOKEN"
```

## Crear tarjeta de crédito
```bash
curl -X POST http://localhost:8080/api/v1/tarjetas \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titular": "Juan Perez",
    "numero": "4111-1111-1111-1111",
    "banco": "Banorte"
  }'
```

## Crear movimiento de tarjeta
```bash
curl -X POST http://localhost:8080/api/v1/tarjetas/TARJETA_ID/movimientos \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Compra Supermercado",
    "fechaCompra": "2026-09-01",
    "importe": 500,
    "tipo": "COMPRA",
    "cuotas": 1
  }'
```

## Obtener movimientos de tarjeta
```bash
curl -X GET "http://localhost:8080/api/v1/tarjetas/TARJETA_ID/movimientos?estado=ACTIVO&tipo=COMPRA&page=0&size=10" \
  -H "Authorization: Bearer TOKEN"
```

## Endpoints de catálogo
```bash
# Obtener estados permitidos
curl -X GET http://localhost:8080/api/v1/catalogos/estados \
  -H "Authorization: Bearer TOKEN"

# Obtener tipos de gasto
curl -X GET http://localhost:8080/api/v1/catalogos/tipos-gasto \
  -H "Authorization: Bearer TOKEN"

# Obtener tipos de movimiento de tarjeta
curl -X GET http://localhost:8080/api/v1/catalogos/tipos-movimiento-tarjeta \
  -H "Authorization: Bearer TOKEN"
```