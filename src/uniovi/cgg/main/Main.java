package uniovi.cgg.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uniovi.cgg.main.models.Options;

public class Main {

	private List<Options> objects = null;

	private Map<String, Boolean> dependeciesVariables = new HashMap<String, Boolean>();

	private Options companies = new Options(0, "Companies", "La empresa a auditar es ", " ", new String[][] { {
			"Asesoría SL. Es una asesoría que se encarga de gestionar los datos de otras empresas que son sus clientes.",
			"1", "-", "-" },
			{ "Cambia punto ORG. Es una plataforma web que se encarga de gestionar los datos de las personas que crean firmas para protestar.",
					"1", "-", "-" },
			{ "El Diablo. Es un bufete de abogados que se encarga de proteger a quien sea contra lo que sea.", "1", "-",
					"-" },
			{ "Delete From. Es una empresa de administración de BBDD que se encarga de administrar bases de datos de diferentes clientes.",
					"1", "-", "-" },
			{ "Págame más. Es una compañía de seguros que se encarga de asegurar vehículos a todo riesgo.", "1", "-",
					"-" },
			{ "Matasanos. Es una clínica médica privada que ofrece diferentes servicios de medicina.", "1", "-", "-" },
			{ "Sacamuelas. Es una clínica odontológica.", "1", "-", "-" },
			{ "Paquete modesto. Es una compañía de venta de videojuegos online que vende paquetes de videojuegos a precios reducidos.",
					"1", "-", "-" } },
			false, this);

	private Options companySize = new Options(0, "CompanySize", "Tiene un tamaño ", " empleados.\n",
			new String[][] { { "pequeño y cuenta con 3", "1", "-", "-" }, { "pequeño y cuenta con 7", "1", "-", "-" },
					{ "pequeño y cuenta con 9", "1", "-", "-" }, { "mediano y cuenta con 13", "1", "-", "-" },
					{ "mediano y cuenta con 36", "1", "-", "-" }, { "mediano y cuenta con 45", "1", "-", "-" },
					{ "grande y cuenta con 57", "1", "-", "-" }, { "grande y cuenta con 1500", "1", "-", "-" },
					{ "grande y cuenta con 3500", "1", "-", "-" } },
			false, this);

	private Options agreement = new Options(1, "Agreement", "Esta empresa ", "\n", new String[][] { {
			"está obligada a firmar un contrato con los clientes que garantice que se van a cumplir las mismas medidas de seguridad que los clientes aplican a los datos personales que van a tratar. ",
			"3", "automateSalarySystem", "-" },
			{ "permite que cada empresa con la que trabaja trate los datos de sus clientes siguiendo sus propias medidas de seguridad. ",
					"1", "-", "-" } },
			true, this);

	private Options salarySystem = new Options(2, "SalarySystem", "La gestión de las nóminas ", "\n",
			new String[][] { { "está automatizada en un servidor. ", "3", "-", "-" },
					{ "se lleva a mano con papel y bolígrafo. ", "1", "-", "-" } },
			true, this);

	private Options operatingSystem = new Options(3, "Operating System",
			"Las características del servidor de esta empresa son las siguientes: \n*", "\n",
			new String[][] { { "Sistema operativo Windows. ", "1", "windows", "automateSalarySystem" },
					{ "Sistema operativo Linux. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Linux en Amazon Web Service. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Windows en Amazon Web Service. ", "1", "windows", "automateSalarySystem" },
					{ "Máquina Virtual con Linux en Microsoft Azure. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Windows en Microsoft Azure. ", "1", "windows", "automateSalarySystem" } },
			false, this);

	private Options dataBase = new Options(4, "DataBase", "*Base de datos ", "\n", new String[][] {
			{ "Oracle. ", "1", "-", "automateSalarySystem" }, { "MySQL. ", "1", "-", "automateSalarySystem" },
			{ "PostgreSQL. ", "1", "-", "automateSalarySystem" }, { "MariaDB. ", "1", "-", "automateSalarySystem" },
			{ "SQLite. ", "1", "-", "automateSalarySystem" }, { "MongoDB. ", "1", "-", "automateSalarySystem" },
			{ "Cassandra. ", "1", "-", "automateSalarySystem" },
			{ "Microsoft Access. ", "1", "-", "windows,automateSalarySystem" },
			{ "Microsoft SQL Server. ", "1", "-", "windows,automateSalarySystem" } }, false, this);

	private Options loginDataBaseSystem = new Options(5, "LoginDataBaseSystem", "", " ", new String[][] { {
			"Los empleados que trabajan con la base de datos tienen un usuario con una contraseña individual para acceder al sistema de gestión de nóminas.  ",
			"1", "passwordBBDDSystem", "-" },
			{ "Los empleados que trabajan con la base de datos usan el mismo usuario que tienen para acceder tanto a su propio ordenador como para acceder al sistema de gestión de nóminas. ",
					"1", "passwordBBDDSystem", "-" },
			{ "Los empleados que trabajan con la base de datos usan la misma contraseña, pues es compartida, tanto para acceder a su propio ordenador y para acceder al sistema de gestión de nóminas. ",
					"1", "passwordBBDDSystem", "-" },
			{ "El ordenador que usan para acceder al sistema de gestión de nóminas tiene un post-it con la contraseña escrita. ",
					"1", "-", "-" },
			{ "El ordenador que usan para acceder al sistema de gestión de nóminas no tiene contraseña y está accesible para cualquier persona. ",
					"1", "-", "-" },
			{ "", "1", "-", "" } }, false, this);

	private Options passwordDataBaseAttemptsSystem = new Options(6, "PasswordDataBaseAttemptsSystem",
			"Cada usuario tiene un número de intentos ", " para acceder al sistema. \n", new String[][] {
					{ "ilimitados", "1", "-", "passwordBBDDSystem" }, { "limitados", "1", "-", "passwordBBDDSystem" } },
			false, this);

	private Options passwordChangeSystem = new Options(7, "PasswordChangeSystem", "", "\n", new String[][] { {
			"Las contraseñas se cambian periódicamente, luego, es raro que más de una persona conozca una contraseña que no es suya. ",
			"1", "-", "passwordBBDDSystem" },
			{ "El cambio de contraseña queda a decisión del propio usuario. Luego, hay trabajadores que la cambian todos los días, y otros que nunca la han cambiado y entonces conoce su contraseña todo el mundo. ",
					"1", "-", "passwordBBDDSystem" },
			{ "Las contraseñas no se cambian nunca, lo que ha provocado que todos los empleados conozcan prácticamente todas las contraseñas de todos. ",
					"1", "-", "passwordBBDDSystem" } },
			false, this);

	private Options userAccounts = new Options(8, "UserAccounts", "", "\n", new String[][] {
			{ "No existen usuarios y todos pueden acceder directamente a cualquier ordenador. ", "2", "-", "-" },
			{ "Los usuarios están limitados y no están autorizados a realizar todas las acciones disponibles pues existen diferentes perfiles de usuario. ",
					"1", "profile", "-" }, },
			true, this);

	private Options userProfiles = new Options(9, "UserProfiles", "", "\n", new String[][] { {
			"Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil es el único que puede modificar y cerrar la nómina de esa empresa. ",
			"1", "salaryAdmin,userAccounts,passwordChangeSystem2", "profile" },
			{ "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede modificar y cerrar la nómina de esa empresa, pero existen otros dos usuarios que pueden modificar y cerrar la nómina para cuando el responsable no esté. Se podría dar el caso de que ninguno de los 3 esté. ",
					"1", "salaryAdmin,userAccounts,passwordChangeSystem2", "profile" },
			{ "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador puede modificar la nómina de esa empresa, aunque se tiene constancia de quien hace que acción siempre por el uso de un log. ",
					"1", "userAccounts,passwordChangeSystem2", "profile" },
			{ "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador tiene también todos los permisos posibles, aunque se tiene constancia de quien hace que acción siempre por el uso de un log ",
					"1", "userAccounts,passwordChangeSystem2", "profile" },
			{ "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador tiene también todos los permisos posibles, sin dejar rastro de quien hizo qué, pues no hay log. ",
					"1", "userAccounts,passwordChangeSystem2", "profile" },
			{ "No obstante, está mal gestionado y cualquier trabajador tiene todos los permisos posibles, pero se tiene constancia de quien hace que acción siempre por el uso de un log. ",
					"1", "userAccounts,passwordChangeSystem2", "profile" },
			{ "No obstante, está mal gestionado y cualquier trabajador tiene todos los permisos posibles. Además, pueden hacer todo sin dejar rastro de quien hizo qué, pues no hay ningun sistema de log. ",
					"1", "userAccounts,passwordChangeSystem2", "profile" },
			{ "Sin embargo, solo hay una cuenta de administrador, que es del responsable de la empresa, y que está compartida por todos los usuarios, ya que no poseen cuentas propias. ",
					"1", "-", "profile" } },
			false, this);

	private Options passwordChangeSystem2 = new Options(10, "PasswordChangeSystem2", "", "\n", new String[][] { {
			"Las contraseñas se cambian periódicamente, luego, es raro que más de una persona conozca una contraseña que no es suya. ",
			"1", "-", "userAccounts" },
			{ "El cambio de contraseña queda a decisión del propio usuario. Luego, hay trabajadores que la cambian todos los días, y otros que nunca la han cambiado y entonces conoce su contraseña todo el mundo. ",
					"1", "-", "userAccounts" },
			{ "Las contraseñas no se cambian nunca, lo que ha provocado que todos los empleados conozcan prácticamente todas las contraseñas de todos. ",
					"1", "-", "userAccounts" } },
			false, this);

	private Options salaryAdmin = new Options(11, "SalaryAdmin",
			"Como solo el responsable puede cerrar las nóminas, cuando él falta, ", "\n",
			new String[][] { {
					"queda otro compañero como responsable al que se le da un usuario nuevo y cuando vuelve el responsable se le deshabilita ese usuario. ",
					"1", "-", "salaryAdmin" },
					{ "queda otro compañero como responsable al que se le da la clave del usuario del administrador. ",
							"1", "-", "salaryAdmin" },
					{ "se crea una cuenta de invitado con una contraseña conocida por el nuevo responsable. ", "1", "-",
							"salaryAdmin" },
					{ "se crea una cuenta invitado y se dice la contraseña a todos los que tengan que cerrar una nómina. ",
							"1", "-", "salaryAdmin" },
					{ "se dice la contraseña del administrador a todos para que puedan arreglar sus propios problemas. ",
							"1", "-", "salaryAdmin" },
					{ "se espera a que vuelva el responsable, sea el tiempo que sea. ", "1", "-", "salaryAdmin" } },
			false, this);

	private Options physicalPlace = new Options(12, "PhysicalPlace",
			"Respecto al espacio físico, la compañía se encuentra en ", "\n",
			new String[][] { { "un piso. ", "1", "-", "-" }, { "un edificio de oficinas. ", "1", "-", "-" },
					{ "un edificio propio. ", "1", "-", "-" } },
			false, this);

	private Options roomsPlace = new Options(13, "RoomsPlace", "Los despachos ", "\n", new String[][] {
			{ "no existen, es una gran sala en donde cada uno tiene su mesa o una mesa compartida. ", "1", "-", "-" },
			{ "no existen, es una gran sala en donde cada uno tiene su mesa o una mesa compartida, pero cada mesa está separada por mamparas. ",
					"1", "-", "-" },
			{ "no existen salvo el despacho privado del gerente, el resto de trabajadores están en una sala en común. ",
					"1", "-", "-" },
			{ "son colectivos para un mismo grupo de trabajo, se usan paneles movibles para crearlos. El gerente tiene un despacho privado. ",
					"1", "-", "-" },
			{ "son individuales y privados para todo el mundo. ", "1", "-", "-" }, }, false, this);

	private Options serverLocation = new Options(14, "ServerLocation", "El servidor se encuentra en ", "\n",
			new String[][] { { "la sala común. ", "1", "-", "-" },
					{ "la sala común, guardado en un armario bajo llave que tiene el responsable actual. ", "1", "-",
							"-" },
					{ "la sala común, guardado en un armario bajo llave a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ",
							"1", "-", "-" },
					{ "la sala común guardado, en un armario bajo llave la cual está colgando de la cerradura junto a las copias de la llave . ",
							"1", "-", "-" },
					{ "una sala privada a la que solo tiene acceso el responsable actual. ", "1", "-", "-" },
					{ "una sala privada a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ",
							"1", "-", "-" },
					{ "una sala privada, pero las llaves están colgadas de la cerradura en un llavero en el que se encuentran también las copias de estas. ",
							"1", "-", "-" } },
			false, this);

	private Options network = new Options(15, "Network", "La red del local ", "\n", new String[][] {
			{ "está cableada entera y no se usa WI-FI. ", "1", "-", "-" },
			{ "está cableada entera, pero también hay una red WI-FI que da acceso a todos los trabajadores. ", "1", "-",
					"-" },
			{ "está cableada entera, pero también hay una red WI-FI que da acceso a todos los trabajadores y a los clientes.",
					"1", "-", "-" },
			{ "está cableada entera, pero también hay una red WI-FI pública creada por la empresa para trabajadores y visitantes. ",
					"1", "-", "-" },
			{ "está cableada entera, pero también hay una red WI-FI pública de la ciudad a la que se conectan cuando falla la suya o sin querer. ",
					"1", "-", "-" },
			{ "está cableada entera, pero también hay una red WI-FI pública desconocida a la que se conectan cuando falla la suya o sin querer. ",
					"1", "-", "-" },
			{ "es una red WI-FI de la empresa. ", "1", "-", "-" },
			{ "es una red WI-FI de la empresa, aunque hay otras redes abiertas que a veces se usan, pues suelen ir mejor que la de la empresa. ",
					"1", "-", "-" } },
			false, this);

	private Options backups = new Options(16, "Backups", "Las copias de seguridad se hacen ", "", new String[][] {
			{ "todos los viernes. ", "1", "backup", "-" }, { "1 vez al mes. ", "1", "backup", "-" },
			{ "1 vez al año. ", "1", "backup", "-" },
			{ "cuando se acuerdan, a veces las hacen dos días seguidos y a veces tras pasar 5 meses. ", "1", "backup",
					"-" },
			{ "... nunca ¿Qué es una copia de seguridad? Solo ocupan espacio innecesario y son un desperdicio de tiempo... y de dinero. ",
					"1", "-", "-" } },
			false, this);

	private Options backupTool = new Options(17, "BackupTool", "Para hacer las copias se utiliza ", "", new String[][] {
			{ "un sistema automatizado que las envía a un servicio en una nube pública (AWS, Azure, ...).", "1", "-",
					"backup" },
			{ "un sistema automatizado que las envía a un servicio a una nube privada a la que solo tiene acceso esta empresa.",
					"1", "-", "backup" },
			{ "un sistema de cintas automático que puede almacenar hasta 3 años de backups y se encuentra en una habitación de la empresa.",
					"1", "-", "backup" },
			{ "un disco duro externo ", "1", "physicalBackup", "backup" },
			{ "en un sistema de dos cintas que se intercambian (un día se usa una y otro día otra) en base a las explicaciones sobre el sistema que le dieron hace años al trabajador encargado. ",
					"1", "-", "backup" },
			{ "en el propio disco duro del servidor usando la herramienta del sistema operativo. ", "1", "-",
					"backup" },
			{ "en un Blu-Ray que se deja sobre la mesa del responsable. ", "1", "-", "backup" },
			{ "en un Blu-Ray que se deja sobre el servidor o incluso dentro de él. ", "1", "-", "backup" },
			{ "un programa automático que crea una imagen en un disco duro del ordenador principal y ", "1",
					"physicalBackup", "backup" } },
			false, this);

	private Options physicalBackup = new Options(18, "PhysicalBackup", "", "\n",
			new String[][] { { "que se almacena en la empresa. ", "1", "-", "physicalBackup" },
					{ "que recoge el jefe cuando termina de hacerse y se lo lleva para casa ", "1", "-",
							"physicalBackup" },
					{ "que guarda el responsable bajo llave en el armario. ", "1", "-", "physicalBackup" },
					{ "que guarda el último empleado en salir de la oficina en su mesa. ", "1", "-", "physicalBackup" },
					{ "que recoge el último empleado en salir de la oficina y lo deja en la mesa del responsable. ",
							"1", "-", "physicalBackup" },
					{ "que recoge el responsable al siguiente día laboral. ", "1", "-", "physicalBackup" },
					{ "que se deja en el PC hasta el siguiente día. ", "1", "-", "physicalBackup" } },
			false, this);

	private Options clientService = new Options(19, "ClientService",
			"El servicio que se da a los clientes y se utiliza para responderles se basa en una ", " ",
			new String[][] { { "aplicación de escritorio.", "1", "-", "-" },
					{ "aplicación web alojada en el servidor de la empresa. ", "1", "-", "-" },
					{ "aplicación web alojada en un hosting español.", "1", "-", "-" },
					{ "aplicación web alojada en un servicio en la nube fuera de España, pero dentro de Europa.", "1",
							"-", "-" },
					{ "aplicación web alojada en un servicio en la nube fuera de Europa.", "1", "-", "-" },
					{ "aplicación web alojada en un hosting de Estados Unidos.", "1", "-", "-" },
					{ "aplicación móvil subida a Google Play y que obtiene sus datos del servidor de la empresa.", "1",
							"-", "-" } },
			false, this);

	private Options clientServiceAccess = new Options(20, "ClientServiceAccess",
			"El acceso al servicio se hace mediante ", "\n",
			new String[][] { { "", "1", "-", "-" },
					{ "un usuario y una contraseña desarrollado por la empresa para el usuario. ", "1", "-", "-" },
					{ "el registro previo del usuario dando su email y contraseña, la cual no se guarda hasheada en el servidor. ",
							"1", "-", "-" },
					{ "el registro previo del usuario dando su email y contraseña, la cual no se guarda hasheada en el servidor y se envía al usuario en un correo en texto plano. ",
							"1", "-", "-" },
					{ "el registro previo del usuario dando su email y contraseña, la cual se guarda hasheada en el servidor. ",
							"1", "-", "-" },
					{ "el acceso directo pues no hay usuario/contraseña. ", "1", "-", "-" },
					{ "el acceso del nombre de la empresa contratante, pues no hay contraseña. ", "1", "-", "-" } },
			false, this);

	private Options clientServiceAccessSystem = new Options(21, "ClientServiceAccessSystem",
			"El acceso al servicio se hace utilizando ", "\n",
			new String[][] { { "protocolos seguros como HTTPS y FTPS. ", "1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP. ", "1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico de manera segura con un algoritmo de seguridad de la empresa. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando criptografía de clase privada. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando criptografía de clase pública. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando criptografía de clase híbrida. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo AES. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo DES. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo Pretty Good Privacy (PGP). ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo OpenPGP. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo SHA-1. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo SHA-3. ",
							"1", "-", "-" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo MD6. ",
							"1", "-", "-" } },
			false, this);

	private Options dataService = new Options(22, "DataService",
			"Otro servicio que se utiliza para recibir los datos del usuario y que usa el trabajador para responder al cliente es ",
			" ",
			new String[][] { { "", "1", "-", "-" }, { "un servicio web de la propia empresa. ", "1", "encrypted", "-" },
					{ "un servicio web de otra empresa. ", "1", "encrypted", "-" },
					{ "una aplicación móvil. ", "1", "encrypted", "-" },
					{ "un correo electrónico. ", "1", "encrypted", "-" }, { "por correo postal.\n", "1", "-", "-" },
					{ "un fax.\n", "1", "-", "-" }, { "por teléfono.\n", "1", "-", "-" },
					{ "en persona utilizando hojas de papel/CD/DVD/USB.\n", "1", "-", "-" }, },
			false, this);

	private Options dataServiceEncrypted = new Options(23, "dataServiceEncrypted", "Este servicio ", "\n",
			new String[][] { { "va encriptado. ", "1", "-", "encrypted" },
					{ "va en texto plano. ", "1", "-", "encrypted" },
					{ "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico usando el algoritmo AES. ",
							"1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo DES. ", "1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo Pretty Good Privacy (PGP). ", "1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo OpenPGP. ", "1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo SHA-1. ", "1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo SHA-3. ", "1", "-", "encrypted" },
					{ "va encriptado usando el algoritmo MD6. ", "1", "-", "encrypted" },
					{ "usa el mismo método que el servicio anterior", "3", "-", "encrypted" } },
			true, this);

	// separar opciones de trabajadores y clientes en 2 mejor, más juego
	private Options ownSalarySystem = new Options(24, "OwnSalarySystem",
			"Las nóminas internas de la empresa y de los clientes ", "",
			new String[][] { { "se hacen a mano. ", "1", "-", "-" },
					{ "se hacen manualmente a ordenador. ", "1", "-", "-" },
					{ "se generan con un programa y se guardan en un CD/DVD. ", "1", "-", "-" },
					{ "las genera una tercera empresa que se encarga de todo. ", "1", "cessionData", "-" },
					{ "las genera una tercera empresa con la que se firmó un contrato por el que deben de cumplir todos los criterios legales. ",
							"1", "cessionData", "-" } },
			false, this);

	private Options sendToBank = new Options(25, "SendToBank", "Se envían al banco usando ", "",
			new String[][] { { "", "1", "-", "-" }, { "un servicio web. ", "1", "-", "-" },
					{ "un correo electrónico. ", "1", "-", "-" }, { "correo postal.", "1", "-", "-" },
					{ "un fax. ", "1", "-", "-" }, { "en persona usando hojas de papel/CD/DVD/USB. ", "1", "-", "-" } },
			false, this);

	private Options sendToRecipient = new Options(26, "SendToRecipient", "Se envían al destinatario interasado usando ",
			"",
			new String[][] { { "un servicio web. ", "1", "-", "-" }, { "un correo electrónico. ", "1", "-", "-" },
					{ "correo postal. ", "1", "-", "-" }, { "un fax. ", "1", "-", "-" },
					{ "correo electrónico y/o correo postal y/o aplicación móvil y/o aplicación web y/o fax y/o en persona usando hojas de papel/CD/DVD/USB. El cliente puede elegir cualquier combinación",
							"1", "-", "-" },
					{ "en persona usando hojas de papel/CD/DVD/USB. ", "1", "-", "-" } },
			false, this);

	private Options cessionData = new Options(27, "CessionData", "La cesión de los datos a esta tercera empresa ", "\n",
			new String[][] { { "se informa al cliente y a los empleados siempre. ", "1", "-", "cessionData" },
					{ "no se informa ni a los clientes y ni a los empleados nunca. ", "1", "-", "cessionData" },
					{ "se informa al cliente y a los empleados si preguntan. ", "1", "-", "cessionData" },
					{ "se informa al cliente y a los empleados dependiendo dle trabajador que toque. ", "1", "-",
							"cessionData" } },
			false, this);

	// Los datos referentes a la propia gestoría se controlan en la propia
	// aplicación como si de otro cliente se tratase. Los datos de las horas
	// trabajadas están al cargo del gerente
	private Options hoursData = new Options(28, "HoursData",
			"Los datos de las horas trabajadas los lleva el gerente, que ", "",
			new String[][] {
					{ "las guarda en su ordenador personal en una hoja Excel situada en su directorio personal. ", "1",
							"", "-" },
					{ "los apunta en hojas de papel. ", "1", "", "-" },
					{ "usa una aplicación web que tienen subida en su servidor y que usa el mismo sistema de usuario/password que el de las nóminas. ",
							"1", "", "-" },
					{ "usa una aplicación móvil que tienen subida en su servidor y que usa el mismo sistema de usuario/password que el de las nóminas. ",
							"1", "", "-" } },
			false, this);

	private Options hoursDataManagement = new Options(29, "HoursDataManagement", "Los datos son introducidos por ", "",
			new String[][] { { "el gerente, que es el único que debería de tener acceso. ", "1", "", "-" },
					{ "el propio empleado, pues él es su propio responsable. ", "1", "-", "-" },
					{ "se cambia el empleado responsable de apuntar todas las horas cada X tiempo. ", "1", "-", "-" },
					{ "cada empleado cuando entra a trabajar. ", "1", "-", "" },
					{ "cada empleado cuando entra y sale de trabajar. ", "1", "-", "" },
					{ "cada empleado, no obstante, no se acuerdan todos los días, cosa que suele ocurrir muy amenudo, y a veces las firman cuando cuadra, es decir, un dia cada semana o cada dos todos los que le faltan y el siguiente. ",
							"1", "-", "" } },
			false, this);

	private Options printedSheet = new Options(30, "ServerLocation",
			"Además, esta hoja se imprime mensualmente ya que contiene los datos del mes, y se archiva ", "",
			new String[][] { { "en carpetas anilladas que se guardan en un armario abierto. ", "1", "-", "-" }, {
					"en carpetas anilladas que se guardan en un armario siguiendo el mismo proceso que el acceso al servidor. ",
					"1", "-", "-" },
					{ "y una vez se termina con ellas se tiran a reciclar, algo muy importante, pero sin triturar. ",
							"1", "-", "-" },
					{ "y una vez se termina con ellas se tiran a reciclar, algo muy importante, después de pasar por la trituradora. ",
							"1", "-", "-" },
					{ "cuando el gerente se acuerda y hay tinta en al impresora. A veces pasa semanas sin hacerlo. ",
							"1", "-", "-" },
					{ "cuando el gerente se acuerda. A veces pasan semanas o meses sobre la pila de papeles de su mesa. ",
							"1", "-", "-" } },
			false, this);

	private Options documentLocation = new Options(31, "ServerLocation", "Los demás documentos ", "", new String[][] {
			{ "se meten en carpetas anilladas que se almacenan en ", "3", "location", "-" },
			{ "una vez se termina con ellos se tiran a reciclar, algo muy importante, pero sin triturar. ", "1", "-",
					"-" },
			{ "una vez se termina con ellas se tiran a reciclar, algo muy importante, pero después de haberlas triturado. ",
					"1", "-", "-" } },
			true, this);

	private Options documentStorage = new Options(32, "DocumentStorage", "", "", new String[][] {
			{ "la sala común. ", "1", "-", "location" },
			{ "la sala común, guardados en un armario bajo llave que tiene el responsable actual. ", "1", "-",
					"location" },
			{ "la sala común, guardados en un armario bajo llave a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ",
					"1", "-", "location" },
			{ "la sala común guardados, en un armario bajo llave la cual está colgando de la cerradura junto a las copias de la llave . ",
					"1", "-", "location" },
			{ "una sala privada a la que solo tiene acceso el responsable actual. ", "1", "-", "location" },
			{ "una sala privada a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ",
					"1", "-", "location" },
			{ "una sala privada, pero las llaves están colgadas de la cerradura en un llavero en el que se encuentran también las copias de estas. ",
					"1", "-", "location" } },
			false, this);

	private Options fileLOPD = new Options(33, "FileLOPD", "\nDurante la vida de esta empresa ", "\n", new String[][] {
			{ "jamás se han declarado los ficheros de datos de la LOPD, ni se lleva un registro de actividades del tratamiento. ",
					"2", "-", "-" },
			{ "se han declarado los ficheros de datos de la LOPD, pero no se lleva un registro de actividades del tratamiento. ",
					"2", "-", "-" },
			{ "se han declarado los ficheros de la LOPD y se lleva el registro de actividades del tratatamiento. ", "1",
					"-", "-" } },
			true, this);

	private static Main main;
	
	public static void main(String[] args) {

		main = new Main();

		main.objects = new ArrayList<Options>();

		main.objects.add(main.companies);
		main.objects.add(main.companySize);
		main.objects.add(main.agreement);
		main.objects.add(main.salarySystem);
		main.objects.add(main.operatingSystem);
		main.objects.add(main.dataBase);
		main.objects.add(main.loginDataBaseSystem);
		main.objects.add(main.passwordDataBaseAttemptsSystem);
		main.objects.add(main.passwordChangeSystem);
		main.objects.add(main.userAccounts);
		main.objects.add(main.userProfiles);
		main.objects.add(main.passwordChangeSystem2);
		main.objects.add(main.salaryAdmin);
		main.objects.add(main.physicalPlace);
		main.objects.add(main.roomsPlace);
		main.objects.add(main.serverLocation);
		main.objects.add(main.network);
		main.objects.add(main.backups);
		main.objects.add(main.backupTool);
		main.objects.add(main.physicalBackup);
		main.objects.add(main.clientService);
		main.objects.add(main.clientServiceAccess);
		main.objects.add(main.clientServiceAccessSystem);
		main.objects.add(main.dataService);
		main.objects.add(main.dataServiceEncrypted);
		main.objects.add(main.ownSalarySystem);
		main.objects.add(main.sendToBank);
		main.objects.add(main.sendToRecipient);
		main.objects.add(main.cessionData);
		main.objects.add(main.hoursData);
		main.objects.add(main.hoursDataManagement);
		main.objects.add(main.printedSheet);
		main.objects.add(main.documentLocation);
		main.objects.add(main.documentStorage);
		main.objects.add(main.fileLOPD);

		String exercise = "";
		for (int i = 0, length = main.objects.size(); i < length; i++) {
			exercise += main.objects.get(i).toString();
		}

		System.out.println(exercise);
	}
	
	public String generateText() {
		String exercise = "";
		for (int i = 0, length = main.objects.size(); i < length; i++) {
			exercise += main.objects.get(i).toString();
		}

		return exercise;
	}

	/**
	 * Comprueba que la key exista y, en caso positivo, devuelve su valor. Si no,
	 * false.
	 * 
	 * @param key String clave a buscar
	 * @return boolean valor de la key, si no existe false
	 */
	public boolean getDependeceVariable(String key) {
		if (this.dependeciesVariables.containsKey(key)) {
			return this.dependeciesVariables.get(key);
		}
		return false;
	}

	/**
	 * Inserta un par clave-valor en la tabla hash. Si ya existía la clave,
	 * sobrescribe el valor
	 * 
	 * @param key   String
	 * @param value boolean
	 */
	public void insertDependeceVariable(String key, boolean value) {
		this.dependeciesVariables.put(key, value);
	}

}
