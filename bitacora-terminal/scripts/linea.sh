#!/bin/bash

separador(){
	echo "===================="
}
fila(){
	local n=$1
	local i=$2
	local count=1
	if [ -z $i ] || [ -z $n ]; then
		echo "NO HA INTRODUCIDO VALORES!!!"
		exit 1
	fi
	if [ $1 -eq 0 ] && [ $2 -eq 0 ]; then
		echo "JA,JA,JA. Muy gracioso, pon numeros que valgan algo."
	fi
	while [ $count -le $i  ]; do
		echo "$n * $i = $(( n * count))"
		((count++))
	done
}

separador
fila "$1" "$2"
separador


