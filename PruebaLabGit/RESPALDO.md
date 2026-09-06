1. find proyecto-sensores -name "*.tmp" -delete
Limpieza de archivos temporales tmp del proyecto.
2. find proyecto-sensores -name "*~" -delete
Limpieza de archivos temporales de respaldo.
3. chmod 755 proyecto-sensores/src/analizar.sh
Asignacion de permisos de ejecucion al script analizar.sh.
4. find proyecto-sensores -name "*.log" | xargs grep "ERROR" > proyecto-sensores/reporte-errores.txt
Construccion del reporte de errores con tuberia filtrando logs.
5. wc -l proyecto-sensores/reporte-errores.txt
Verificacion de lineas generadas en el reporte de errores.
6. tar -czf respaldos/proyecto-$(date +%F).tar.gz proyecto-sensores
Creacion del archivo comprimido tar gz de respaldo con fecha.
7. tar -tzf respaldos/proyecto-*.tar.gz
Verificacion del contenido empaquetado dentro del respaldo tar.
