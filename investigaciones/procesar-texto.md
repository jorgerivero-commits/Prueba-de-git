--- CLASE 3 ---

grep: busca y filtra texto segun lo que se ingrese en el teclado. Patron o palabra clave
cut: extrae columnas de un archivo, delimitadas por un caracter
sort: ordena las lineas de un archivo en orden alfabetico
uniq: elimina lineas repetidas dejando una unica sola, las lineas deben estar seguidas si se quieren eliminar
¿Por qué uniq suele usarse después de sort y no solo?
por que primero se necesita ordenar para luego eliminar
si dejamos el archivo sin ordenar y las lineas que queremos eliminar estan ceparadas entonces el uniq no funcionara
Qué es un archivo de log? Buscar uno en la VM (pista: /var/log) y extraer 3 líneas que contengan una palabra elegida, usando una tubería
un arhivo log es un archivo que genera el propio sistema o las apps, tiene tanto actividades como errores. sirve para tener una constancia
y ver mejor lo que hace el sistema de fondo, tal vez si hay algun problema o actividad sera registrada alli.

