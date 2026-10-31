#!/bin/bash

OUTPUT_DIR="./data"
mkdir -p "$OUTPUT_DIR"

# URL de CelesTrak para el grupo STATIONS (o puedes cambiarlo según necesites)
CELESTRAK_URL="https://celestrak.org/NORAD/elements/gp.php?GROUP=STATIONS&FORMAT=TLE"
OUTPUT_FILE="$OUTPUT_DIR/debris_tle.txt"

echo "[$(date +'%Y-%m-%d %H:%M:%S')] Descargando datos TLE desde CelesTrak..."
curl -s "$CELESTRAK_URL" -o "$OUTPUT_FILE"

if [ $? -eq 0 ] && [ -s "$OUTPUT_FILE" ]; then
  echo "¡Descarga exitosa! Archivo guardado en $OUTPUT_FILE"
  echo "Inyectando datos en la base de datos MySQL (space_debris_db)..."

  # Credenciales de tu base de datos (ajusta usuario y contraseña si es necesario)
  DB_USER="spacedebris_user"
  DB_PASS="05012007JRJ#2" # Según se ve en tu DatabaseService.java
  DB_NAME="space_debris_db"

  # Procesar el archivo TLE de 3 líneas por satélite e insertarlo en MySQL con ON DUPLICATE KEY UPDATE
  python3 - <<EOF
import mysql.connector

db = mysql.connector.connect(
    host="localhost",
    user="$DB_USER",
    password="$DB_PASS",
    database="$DB_NAME"
)
cursor = db.cursor()

with open("$OUTPUT_FILE", "r") as f:
    lines = [line.strip() for line in f.readlines() if line.strip()]

# Agrupar de 3 en 3 (Nombre, Line1, Line2)
for i in range(0, len(lines) - 2, 3):
    name = lines[i]
    line1 = lines[i+1]
    line2 = lines[i+2]
    
    # Extraer el ID NORAD de la Línea 1 (posiciones 2 a 7)
    try:
        norad_id = int(line1[2:7].strip())
    except ValueError:
        continue

    query = """
        INSERT INTO space_debris (norad_cat_id, object_name, tle_line1, tle_line2)
        VALUES (%s, %s, %s, %s)
        ON DUPLICATE KEY UPDATE 
            object_name = VALUES(object_name),
            tle_line1 = VALUES(tle_line1),
            tle_line2 = VALUES(tle_line2)
    """
    cursor.execute(query, (norad_id, name, line1, line2))

db.commit()
cursor.close()
db.close()
print("¡Datos inyectados correctamente en MySQL desde Bash!")
EOF

else
  echo "Error al descargar los datos de CelesTrak."
  exit 1
fi