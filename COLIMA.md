# 🐳 Guía de Docker con Colima

Colima es una forma ligera de ejecutar Docker en macOS sin Docker Desktop.
Consume mucho menos recursos y se controla desde terminal.

## Instalación

```bash
# 1. Instalar Homebrew (si no lo tienes)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# 2. Instalar herramientas de desarrollador de Apple
xcode-select --install

# 3. Aceptar licencia de Xcode
xcodebuild -license accept

# 4. Instalar Colima, Docker y Docker Compose
brew install colima docker docker-compose
```

## Uso diario

### Arrancar Colima (necesario antes de usar docker-compose)
```bash
colima start --memory 2 --cpu 2
```
> Limita a 2GB RAM y 2 CPUs. Ajusta según tu computadora.

### Verificar que Docker funciona
```bash
docker ps
docker-compose version
```

### Levantar la base de datos MySQL para la API
```bash
cd /Users/joser.vazquez/Proyecto/DiseñoAppGastos/api
docker-compose up -d
```

### Verificar que MySQL está corriendo
```bash
docker-compose ps
docker exec -it gasto-hogar-mysql mysql -u gasto_user -pgasto_password gasto_hogar -e "SHOW TABLES;"
```

### Parar todo (libera todos los recursos)
```bash
docker-compose down
colima stop
```

### Arranque completo de la API
```bash
# 1. Arrancar Colima
colima start --memory 2 --cpu 2

# 2. Arrancar MySQL
cd /Users/joser.vazquez/Proyecto/DiseñoAppGastos/api
docker-compose up -d

# 3. Arrancar la API
mvn spring-boot:run
```

### Parar todo
```bash
# 1. Parar la API (Ctrl+C en la terminal donde corre)
# 2. Parar MySQL
docker-compose down
# 3. Parar Colima
colima stop
```

## Estado de Colima
```bash
colima status    # Ver si está corriendo
colima stop      # Detener (0 recursos)
colima start     # Iniciar
colima rm        # Eliminar la VM
```

## Recursos consumidos
| Estado | RAM | CPU | Disco |
|--------|-----|-----|-------|
| colima stop | 0 MB | 0 | ~500MB |
| colima start (2GB) | ~2GB | 2 cores | ~500MB |
| docker-compose up | ~2.5GB | 2 cores | ~500MB |

## Ventajas vs Docker Desktop
- ✅ Consume 0 recursos cuando está parado
- ✅ Sin proceso en segundo plano
- ✅ 100% compatible con `docker` y `docker-compose`
- ✅ Más rápido de instalar
- ❌ Requiere instalar herramientas de desarrollador de Apple