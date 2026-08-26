¿Qué guardan los directorios /home, /etc, /var y /tmp en Linux? (pueden explorarlos con ls y cat en la VM)
/home guarda los directorios personales que tenga el usuario.
/etc guarda los archivos de aplicaciones instaladas y del sistema
/var guarda variables
/tmp guarda archivos temporales 
¿Cuál es la diferencia entre una ruta absoluta y una ruta relativa? Dar 3 ejemplos de cada una desde su propia estructura de bitacora-terminal
una ruta absoluta es la ruta desde la raiz, si no estoy mal desde el mismo C:\User...
una ruta relativa es la tuya del archivo en donde estamos trabajando.

--- EJEMPLOS ---
--- EJEMPLO 1 ---
la ruta absoluta de bitacora terminal en este momento que tengo es
~/Documentos/Universidad/Programacion II/bitacora-terminal$
siendo ~ la forma de abreviar /home/... segun entendi.
--- EJEMPLO 2 --- 
una ruta relativa seria
bitacora-terminal/comandos$ 
debido a que ya no empieza con / y no tiene ~ quiere decir que es el directorio en donde estoy
--- EJEMPLO 3 ---
bitacora-terminal/evidencias$ 
lo mismo, si no tiene / o ~ entonces es relativa

¿Qué significan . y .. en una ruta? Probarlo con cd y anotar qué pasó
. significa el directorio actual
.. significa el directorio anterio o padre, por decirlo asi

cd . No hace mucho, si lo dejas asi y das enter, no pasa nada, pero si haces algo como cd ./Directorio... te moveras a ese directorio
cd .. Te devuelve al directorio padre por ejemplo: /Directorio1/Directorio2/Aum.txt
si usas cd .. estando en Aum.txt volveras Directorio2 quedando algo asi /Directorio1/Directorio2/ y si usas cd .. nuevamente volveras al anterio
