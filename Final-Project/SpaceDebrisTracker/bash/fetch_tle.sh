#!/bin/bash

# Directorio de trabajo
OUTPUT_DIR="./data"
mkdir -p "$OUTPUT_DIR"

# URL de CelesTrak para basura espacial general
CELESTRAK_URL="https://celestrak.org/NORAD/elements/gp.php?GROUP=debris&FORMAT=tle"

OUTPUT_FILE="$OUTPUT_DIR/debris_tle.txt"

# Usamos el comando date real de Linux
echo "[$(date +'%Y-%m-%d %H:%M:%S')] Descargando datos TLE desde CelesTrak..."

# Descargar archivo con curl
curl -s "$CELESTRAK_URL" -o "$OUTPUT_FILE"

if [ $? -eq 0 ]; then
  echo "¡Descarga exitosa! Archivo guardado en $OUTPUT_FILE"
  # Aquí luego agregaremos el comando para inyectarlo a MySQL
else
  echo "Error al descargar los datos de CelesTrak."
  exit 1
fi
