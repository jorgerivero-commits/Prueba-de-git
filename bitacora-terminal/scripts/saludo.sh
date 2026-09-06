#!/bin/bash

if [ -z "$1" ]; then
	echo "Nombre Vacío."
	exit 1
fi
echo "Hola, $1."
