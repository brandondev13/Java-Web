# Java-Web

<br>
Backend Spring Boot
<br>
1. Conexion a base de datos MySQL
<br>
2. Pruebas Unitarias con Junit Y Mockito
<br>
<br>
Frontend
<br>
1. Modo Oscuro
<br>
2. Uso de JQuery
<br>
<br>

Prueba para perfil Desarrollador Java Web.
La prueba consiste del ejercicio descrito más abajo, que deberás realizar usando JDK 1.8 mínimo (Lenguaje de
programación Backend). Puedes utilizar el IDE, framework y herramientas de tu preferencia.
Ejercicio:
- Deberás construir una aplicación web completa (Backend y Frontend) que permita registrar dispositivos
electrónicos y conexiones de red y poder asignar una conexión de red a un dispositivo electrónico. En
esencia debes desarrollar el CRUD tanto de dispositivos electrónicos como de conexiones de red.
- La información a registrar de cada entidad es:
Para el Dispositivo Electrónico:
- MAC(CADENA) (Corresponde a la dirección MAC del dispositivo, el cual debe ser único)
- Tipo (CADENA) (Computador, Smartphone, Tablet)
- Conectado actualmente a la Red (BOOLEAN)
- Conexión actual (ENTERO) Llave foránea (para poder conocer a qué conexión de red se
encuentra actualmente conectado el dispositivo)
- IP asignada (CADENA)
Para Conexión de Red:
- Tipo (ENTERO)(Wifi, LAN)
- Nombre (Corresponde al nombre dado a la conexión)
- Tipo de cifrado (CADENA) (en caso de ser Wifi, WEP, WPA y WPA2)
- Usuario Conexión (CADENA)
- Contraseña de conexión(CADENA)
- Se debe construir la funcionalidad para poder asignar una conexión de red a un dispositivo electrónico. Sin
embargo, se debe tener en cuenta que no podrán haber más de 3 dispositivos electrónicos conectados a
una misma conexión de red.
- La primera pantalla de la aplicación debe mostrar dos opciones llamadas: Dispositivos Electrónicos y
Conexiones de red. Al dar clic sobre Dispositivos Electrónicos la aplicación deberá mostrar el listado de
dispositivos existentes (o vacío si aún no se ha ingresado ninguno), y permitir agregar un nuevo dispositivo,
editar uno existente o borrar algún dispositivo. Esto mismo se podrá hacer para el caso de Conexiones de
Red. Adicional debe registrarse el histórico de asignaciones de conexiones de red hechas para los diversos
dispositivos.
- Por ende se debe contar con una opción para consultar el histórico de asignaciones de conexiones hechas.
Debe ser posible filtrar este histórico por dispositivo o por conexión de red. Es decir si se consulta por
dispositivo, mostrará todas las conexiones de red históricamente asignadas a dicho dispositivo. Y si se
consulta por conexión de red, se podrán ver todos los dispositivos que se han conectado a dicha red.
- Puedes usar bien sea una base de datos relacional o NoSQL.
- Debes adicionalmente elaborar un servicio web (que puede ser consumido localmente) para consumir la
lista de dispositivos electrónicos registrados en el sistema.
