#!/bin/bash

num=$1
for(( i=1; i<=10; i++));	
do
	resultado=$(($num * $i))
	echo "$num * $i = $resultado"
done	
