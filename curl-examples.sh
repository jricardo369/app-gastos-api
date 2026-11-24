#!/usr/bin/env bash
# Ejemplos curl para Gasto Hogar API.
# Uso:  ./curl-examples.sh
# Requiere: la API corriendo en $BASE (default http://localhost:8080) y curl + jq.

set -euo pipefail

BASE="${BASE:-http://localhost:8080}"
API="$BASE/api/v1"

# ---------------------------------------------------------------------------
# 1. Registro: crea el usuario y su membresía inicial
# ---------------------------------------------------------------------------
echo "== Registro (crea usuario + membresía MONTH) =="
curl -s -X POST "$API/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "user": "juan",
    "password": "secreto123",
    "name": "Juan Pérez",
    "address": "Av. Reforma 100",
    "phone": "5551234567",
    "email": "juan@example.com",
    "membresiaType": "MONTH"
  }' | jq .
echo

# ---------------------------------------------------------------------------
# 2. Login: devuelve token, usuario (sin password) y membresía activa
# ---------------------------------------------------------------------------
echo "== Login =="
LOGIN=$(curl -s -X POST "$API/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"user": "juan", "password": "secreto123"}')
echo "$LOGIN" | jq .

TOKEN=$(echo "$LOGIN" | jq -r '.data.token')
ID_USER=$(echo "$LOGIN" | jq -r '.data.usuario.idUser')
AUTH=(-H "Authorization: Bearer $TOKEN")
echo "token obtenido, idUser=$ID_USER"
echo

# ---------------------------------------------------------------------------
# 3. Usuario autenticado
# ---------------------------------------------------------------------------
echo "== GET /auth/me =="
curl -s "$API/auth/me" "${AUTH[@]}" | jq .
echo

# ---------------------------------------------------------------------------
# 4. CRUD de usuarios
# ---------------------------------------------------------------------------
echo "== POST /usuarios =="
curl -s -X POST "$API/usuarios" "${AUTH[@]}" \
  -H "Content-Type: application/json" \
  -d '{
    "user": "ana",
    "password": "secreto123",
    "name": "Ana Ruiz",
    "address": "Calle Falsa 123",
    "phone": "5550001111",
    "email": "ana@example.com"
  }' | jq .
echo

echo "== GET /usuarios =="
curl -s "$API/usuarios" "${AUTH[@]}" | jq .
echo

echo "== GET /usuarios/{idUser} =="
curl -s "$API/usuarios/$ID_USER" "${AUTH[@]}" | jq .
echo

echo "== PUT /usuarios/{idUser} =="
curl -s -X PUT "$API/usuarios/$ID_USER" "${AUTH[@]}" \
  -H "Content-Type: application/json" \
  -d '{"phone": "5559998888", "address": "Nueva direccion 999"}' | jq .
echo

# ---------------------------------------------------------------------------
# 5. Membresías
# ---------------------------------------------------------------------------
echo "== POST /membresias (anual para el usuario del token) =="
NUEVA=$(curl -s -X POST "$API/membresias" "${AUTH[@]}" \
  -H "Content-Type: application/json" \
  -d "{\"idUser\": \"$ID_USER\", \"type\": \"YEAR\"}")
echo "$NUEVA" | jq .
ID_MEMBRESIA=$(echo "$NUEVA" | jq -r '.data.id')
echo

echo "== GET /membresias?idUser=... =="
curl -s "$API/membresias?idUser=$ID_USER" "${AUTH[@]}" | jq .
echo

echo "== GET /membresias/{id} =="
curl -s "$API/membresias/$ID_MEMBRESIA" "${AUTH[@]}" | jq .
echo

echo "== PUT /membresias/{id} (marcar INACTIVA) =="
curl -s -X PUT "$API/membresias/$ID_MEMBRESIA" "${AUTH[@]}" \
  -H "Content-Type: application/json" \
  -d "{\"idUser\": \"$ID_USER\", \"type\": \"YEAR\", \"estado\": \"INACTIVA\"}" | jq .
echo

echo "== DELETE /membresias/{id} =="
curl -s -X DELETE "$API/membresias/$ID_MEMBRESIA" "${AUTH[@]}" | jq .
echo

# ---------------------------------------------------------------------------
# 6. DELETE de usuario (elimina también sus membresías por cascada)
# ---------------------------------------------------------------------------
echo "== DELETE /usuarios/{idUser} =="
curl -s -X DELETE "$API/usuarios/$ID_USER" "${AUTH[@]}" | jq .
echo