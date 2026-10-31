#!/bin/bash

# Directorios y rutas de librerías en Ubuntu
SRC_DIR="java"
BIN_DIR="bin"
LIB_DIR="lib"
FX_LIB="/usr/share/openjfx/lib"

echo "Limpiando binarios anteriores..."
rm -rf "$BIN_DIR"
mkdir -p "$BIN_DIR"

echo "Compilando motor C++ SGP4..."
g++ -O3 cpp/sgp4_engine.cpp -o cpp/sgp4_engine

echo "Compilando clases Java con soporte JavaFX y MySQL..."
javac --module-path "$FX_LIB" \
      --add-modules javafx.controls,javafx.fxml \
      -cp "$LIB_DIR/mysql-connector-j.jar:$SRC_DIR" \
      -d "$BIN_DIR" \
      $(find "$SRC_DIR" -name "*.java")

if [ $? -ne 0 ]; then
    echo "¡Error en la compilación de Java! Revisa los errores anteriores."
    exit 1
fi

echo "¡Iniciando Space Debris Tracker..."
java --module-path "$FX_LIB" \
     --add-modules javafx.controls,javafx.fxml \
     -cp "$BIN_DIR:$LIB_DIR/mysql-connector-j.jar" \
     com.spacedebris.ui.MainApp