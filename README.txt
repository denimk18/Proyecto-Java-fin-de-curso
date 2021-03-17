
En este archivo de texto incluyo las credenciales para iniciar sesión, las respuestas a las preguntas personales para recuperar la contraseña y cómo probar
la recepción de medicamentos.

--------------------------- USUARIOS Y CONTRASEÑAS PARA INICIAR SESIÓN ------------------------------------------------

---MÉDICOS---
Usuario: Ramon01 Contraseña: usuario1
Usuario: Ana02 Contraseña: usuario12
Usuario: Tania03 Contraseña: usuario123

---ENFERMEROS---
Usuario: Radka04 Contraseña: usuario1234
Usuario: Elizabeth05 Contraseña: usuario12345
Usuario: Pedro06 Contraseña: usuario123456

---FARMACÉUTICOS---
Usuario: Noelia07 Contraseña: usuario1234567
Usuario: Jaime08 Contraseña: usuario12345678

---ADMINISTRATIVOS---
Usuario: Sara09 Contraseña: usuario123456789
Usuario: Jaime10 Contraseña: usuario12345678910

Dichas contraseñas pueden ser cambiadas o bien desde el área personal (introduciendo la contraseña de
cada usuario que aparece aquí) o bien recuperándola contestando a una pregunta de seguridad.
Las preguntas y respuestas de cada usuario son las siguientes:

-----------------------RECUPERACIÓN DE CONTRASEÑAS OLVIDADAS---------------------------------------------------------
Usuario: Ramon01
	Pregunta: Nombre primera mascota
	Respuesta: Simba

Usuario: Ana02
	Pregunta: Nombre amigo de la infancia
	Respuesta: Mario

Usario: Tania03
	Pregunta: Marca del primer coche
	Respuesta: Renault

Usario: Radka04
	Pregunta: Escuela en la que estudiaste la ESO
	Respuesta: IES Clara Campoamor

Usario: Elizabeth05
	Pregunta: Destino del primer viaje
	Respuesta: Gandía

Usario: Pedro06
	Pregunta: Marca del primer coche
	Respuesta: BMW

Usario: Noelia07
	Pregunta: ¿Cuándo se casaron tus padres?
	Respuesta: 1990-12-12

Usario: Jaime08
	Pregunta: Nombre amigo de la infancia
	Respuesta: Ruth

Usario: Sara09
	Pregunta: Destino del primer viaje
	Respuesta: Londres

Usario: Jaime10
	Pregunta: Marca del primer coche
	Respuesta: Seat



-------------------------------------RECEPCIÓN DE MEDICAMENTOS DESDE EL ÁREA DE ADMINISTRATIVOS-------------------------------------

Para comprobar el correcto funcionamiento de este módulo, por defecto he insertado cuatro pedidos de medicación, los cuales son:

  NOMBRE        UNIDADES  FECHA PEDIDO
AMOXICILINA	100	  2020-04-05
ESPEDIFEN	100	  2020-04-05
LIZIPAINA	50	  2020-04-05
MORFINA	        2	  2020-04-05

En este caso, amoxicilina ya existe en el inventario con el código 16. Si insertamos otro código da error. Si lo insertamos con el código 16 se actualizan unidades y 
fecha de llegada cuando el farmacéutico lo recepcione.

Espedifen, lizipaina y morfina son medicamentos que se piden por 1ª vez, con lo cual no existen en el inventario. Si el usuario introduce un código ya existente
en el inventario el sistema lo notificará. Si introduce un código que NO se ha registrado se insertará correctamente. Cuando el farmacéutico lo recepcione se insertarán 
en el inventario.