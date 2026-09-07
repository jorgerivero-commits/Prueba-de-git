#!/bin/bash

i=1
n=$1
if [ -z $n ]; then
	echo "Parametro Vacio"
	exit 1
fi
echo "INICIO"
while [ $i -le $n ]; do
	echo "Latido $i $(date +%T)" 
	sleep 1
	i=$(( i + 1))
done
echo "FIN"
