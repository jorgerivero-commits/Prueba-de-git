#!/bin/bash 

if [ $# -eq 0 ]; then 
	echo "No se ingresó parametro"
	exit 1
fi 
porcentaje_memoria() {
	total=$(grep '^MemTotal' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
	disponible=$(grep '^MemAvailable' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
	usado=$((total - disponible))
	echo $((100 * usado / total))
}

while true; do
	pct=$(porcentaje_memoria)
	
	if [ "$pct" -ge "$1" ]; then
		echo "[ALERTA] MEMORIA HA SUPERADO EL UMBRAL     UMBRAL: $1% | MEMORIA: ${pct}%"
	else
		echo "[OK] MEMORIA DEABJO DEL UMBRAL     UMBRAL: $1% | MEMORIA: ${pct}%"
	fi

	sleep 1
done
