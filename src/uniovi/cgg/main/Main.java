package uniovi.cgg.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import uniovi.cgg.main.models.Company;
import uniovi.cgg.main.models.Options;

public class Main {

	private List<Options> objects = null;

	private Map<String, Boolean> dependeciesVariables = new HashMap<String, Boolean>();

	private Company company1 = new Company("Asesoría SL", "una asesoría",
			"Se encarga de gestionar los datos de otras emplesas que son sus clientes.");
	private Company company2 = new Company("Cambia punto ORG", "una plataforma web",
			"Se encarga de gestionar los datos de las personas que crean firmas para protestar.");
	private Company company3 = new Company("El Diablo", "un bufete de abogados",
			"Se encarga de proteger a quien sea contra lo que sea.");
	private Company company4 = new Company("Delete From", "una empresa de administración de BBDD",
			"Se encarga de administrar tus bases de datos.");
	private Company company5 = new Company("Págame más", "una compañía de seguros",
			"Se encarga de asegurar vehículos a todo riesgo.");
	private Company company6 = new Company("Matasanos", "un médico privado",
			"Médico privado que ofrece diferentes servicios de medicina.");
	private Company company7 = new Company("Sacamuelas", "un dentista", "Dentista.");
	private Company company8 = new Company("Paquete modesto", "una compañía de venta de videojuegos",
			"Servicio web que vende paquetes de videojuegos a precios reducidos.");
	private Company companies[] = { company1, company2, company3, company4, company5, company6, company7, company8 };

	private Options agreement = new Options(1, "Agreement", "Esta empresa ", "\n", new String[][] { {
			"está obligada a firmar un contrato con los clientes que garantice que se van a cumplir las mismas medidas de seguridad que los clientes aplican a los datos personales que van a tratar. ",
			"1", "true", "-" },
			{ "permite que cada empresa con la que trabaja trate los datos de sus clientes siguiendo sus propias medidas de seguridad. ",
					"1", "false", "-" } },
			new String[] { "automateSalarySystem" }, null, false, this);

	private Options salarySystem = new Options(3, "SalarySystem", "La gestión de las nóminas ", "\n",
			new String[][] { { "está automatizada en un servidor. ", "3", "-", "-" },
					{ "se lleva a mano con papel y bolígrafo. ", "1", "-", "-" } },
			null, null, true, this);

	private Options server = new Options(3, "Servers",
			"Las características del servidor de esta empresa son las siguientes: \n*", "\n",
			new String[][] { { "Sistema operativo Windows. ", "1", "-", "automateSalarySystem" },
					{ "Sistema operativo Linux. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Linux en Amazon Web Service. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Windows en Amazon Web Service. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Linux en Microsoft Azure. ", "1", "-", "automateSalarySystem" },
					{ "Máquina Virtual con Windows en Microsoft Azure. ", "1", "-", "automateSalarySystem" } },
			null, new String[] { "automateSalarySystem" }, false, this);

	private Options adminProblems = new Options(4, "AdminProblems",
			"Como solo el responsable puede cerrar las nóminas, cuando él falta, ", "\n",
			new String[][] { {
					"queda otro compañero como responsable al que se le da un usuario nuevo y cuando vuelve el responsable se le deshabilita ese usuario. ",
					"1", "-", "-" },
					{ "queda otro compañero como responsable al que se le da la clave del usuario del administrador. ",
							"1", "-", "-" },
					{ "se crea una cuenta de invitado con una contraseña conocida por el nuevo responsable. ", "1", "-",
							"-" },
					{ "se crea una cuenta invitado y se dice la contraseña a todos los que tengan que cerrar una nómina. ",
							"1", "-", "-" },
					{ "se dice la contraseña del administrador a todos para que puedan arreglar sus propios problemas. ",
							"1", "-", "-" },
					{ "se espera a que vuelva el responsable, sea el tiempo que sea. ", "1", "-", "-" } },
			null, null, false, this);

	private Options passwordChangeSystem = new Options(5, "PasswordChangeSystem", "", "\n", new String[][] { {
			"Las contraseñas se cambian periódicamente, luego, es raro que más de una persona conozca una contraseña que no es suya. ",
			"1", "-", "-" },
			{ "El cambio de contraseña queda a decisión del propio usuario. Luego, hay trabajadores que la cambian todos los días, y otros que nunca la han cambiado y le conocen su contraseña todo el mundo.",
					"1", "-", "-" },
			{ "Las contraseñas no se cambian nunca, lo que provocó que todos los usuarios conozcan todas las contraseñas de todos. ",
					"1", "-", "-" } },
			null, null, false, this);

	private boolean automateSalarySystem;

	public static void main(String[] args) {

		Main main = new Main();

		Company company = main.companies[(main.randomNumber(0, main.companies.length - 1))];

		main.objects = new ArrayList<Options>();
		main.objects.add(main.agreement);
		main.objects.add(main.salarySystem);
		main.objects.add(main.server);
		main.objects.add(main.adminProblems);
		main.objects.add(main.passwordChangeSystem);

		String exercise = "";
		for (int i = 0, length = main.objects.size(); i < length; i++) {
			exercise += main.objects.get(i).toString();
		}

		System.out.println(exercise);
	}

	public String generateText() {
		Company company = companies[(randomNumber(0, companies.length - 1))];

		String exercise = "";

		// exercise = companyType(company) + "\n" + agreement() + "\n" + SalarySystem()
		// + "\n";

		if (automateSalarySystem) {
			// Igual sobra aquí el último, revisarlo
			exercise += server() + "\n" + passwordBBDDSystem() + "\n" + userAccounts() + "\n";
		}

		exercise += physicalPlace(company) + "\n" + network() + "\n" + backups() + "\n" + clientService() + "\n"
				+ dataService() + "\n" + ownSalarySystem() + "\n" + hoursData();

		return exercise;
	}

	/**
	 * Saca un número aleatorio que se encuentre entre el min y max, incluidos ambos
	 * 
	 * @param min número más pequeño
	 * @param max número más grande
	 * @return un número entre el min y el max, incluidos
	 */
	private int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	private String companyType(Company company) {
		int employeesNumber = randomNumber(3, 100);
		String companySize = "";
		if (employeesNumber < 10) {
			companySize = "pequeña";
		} else if (employeesNumber < 50) {
			companySize = "mediana";
		} else {
			companySize = "grande";
		}

		return "La empresa a auditar es " + company.getName() + ". Es " + company.getType() + " que tiene un tamaño "
				+ companySize + " y dispone de " + employeesNumber + " empleados. Esta empresa "
				+ company.getDescription() + ". ";
	}

	/*
	 * private String agreement() {
	 * 
	 * String text = "Esta empresa está obligada a ";
	 * 
	 * switch (randomNumber(0, 1)) { case 0: automateSalarySystem = true; text +=
	 * "firmar un contrato con los clientes que garantice que se van a cumplir las mismas medidas de seguridad que los clientes aplican a los datos personales que van a tratar. "
	 * ; break; case 1: automateSalarySystem = false; text +=
	 * "permite que cada empresa trate los datos de sus clientes siguiendo sus propias medidas de seguridad. "
	 * ; break; default: break; }
	 * 
	 * return text; }
	 */

	/*
	 * private String SalarySystem() {
	 * 
	 * String text = "La gestión de las nóminas "; text +=
	 * "está automatizada en un servidor. ";
	 * 
	 * // if(randomNumber(0, 4) < 3) { text += "está automatizada en un servidor";
	 * //}else { text = "se lleva a mano con papel y boli"; }
	 * 
	 * 
	 * return text;
	 * 
	 * }
	 */

	private String bbdd() {
		String text = "Las características del servidor de esta empresa son las siguientes: \n*";
		Boolean windows = false;

		// Tipo de SSOO
		switch (randomNumber(0, 5)) {
		case 0:
			text += "Sistema operativo Windows. ";
			windows = true;
			break;
		case 1:
			text += "Sistema operativo Linux. ";
			break;
		case 2:
			text += "Máquina Virtual con Linux en Amazon Web Service. ";
			break;
		case 3:
			text += "Máquina Virtual con Windows en Amazon Web Service. ";
			windows = true;
			break;
		case 4:
			text += "Máquina Virtual con Linux en Microsoft Azure. ";
			break;
		case 5:
			text += "Máquina Virtual con Windows en Microsoft Azure. ";
			windows = true;
			break;
		default:
			break;
		}

		text += "\n*";

		// Tipo de BBDD
		int maxBBDD = 6;
		if (windows) {
			maxBBDD += 2;
		}
		switch (randomNumber(0, maxBBDD)) {
		case 0:
			text += "Base de datos Oracle. ";
			break;
		case 1:
			text += "Base de datos MySQL. ";
			break;
		case 2:
			text += "Base de datos PostgreSQL. ";
			break;
		case 3:
			text += "Base de datos MariaDB. ";
			break;
		case 4:
			text += "Base de datos SQLite. ";
			break;
		case 5:
			text += "Base de datos MongoDB. ";
			break;
		case 6:
			text += "Base de datos Cassandra. ";
			break;
		case 7:
			text += "Base de datos Microsoft Access. ";
			break;
		case 8:
			text += "Base de datos Microsoft SQL Server. ";
			break;
		default:
			break;
		}

		text += "\n";

		return text;
	}

	private String passwordBBDDSystem() {
		String text = "";
		boolean password = false;

		// ¿Contraseñas de acceso?
		switch (randomNumber(0, 4)) {
		case 0:
			text += "Los empleados que trabajan con la base de datos tienen un usuario con una contraseña individual para acceder al sistema de gestión de nóminas. ";
			password = true;
			break;
		case 1:
			text += "Los empleados que trabajan con la base de datos usan el mismo usuario que tienen para acceder a su propio ordenador para acceder al sistema de gestión de nóminas. ";
			password = true;
			break;
		case 2:
			text += "Los empleados que trabajan con la base de datos usan la misma contraseña, pues es compartida, para acceder a su propio ordenador para acceder al sistema de gestión de nóminas. ";
			password = true;
			break;
		case 3:
			text += "El ordenador que usan para acceder al sistema de gestión de nóminas tiene un post-it con la contraseña escrita. ";
			password = true;
			break;
		case 4:
			text += "El ordenador que usan para acceder al sistema de gestión de nóminas no tiene contraseña y está accesible para cualquier persona. ";
			break;
		default:
			break;
		}

		// Probabilidad de 0.5
		if (password) {
			if ((randomNumber(0, 1) == 0)) {
				text += " Cada usuario tiene un número de intentos ilimitados para acceder al sistema. ";
			} else {
				text += " Cada usuario tiene un número de intentos limitados para acceder al sistema. ";
			}
		}

		if (password) {
			text += "Sobre las contraseñas de la base de datos: " + passwordChangeSystem() + ". ";
		}

		return text;
	}

	private String userAccounts() {
		String text = "";
		Boolean profile = false;

		// Probabilidad de 0.20
		switch (randomNumber(0, 5)) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			text += "No existen usuarios y todos pueden acceder directamente a cualquier ordenador. ";
			break;
		case 5:
			text += "Los usuarios están limitados y no están autorizados a realizar todas las acciones disponibles pues existen diferentes perfiles de usuario. ";
			profile = true;
			break;
		default:
			break;
		}

		if (profile) {

			boolean userAccounts = true;

			switch (randomNumber(0, 7)) {
			case 0:
				text += "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil es el único que puede modificar y cerrar la nómina de esa empresa. ";
				text += adminProblems();
				text += "Sobre las contraseñas de las cuentas: ";
				break;
			case 1:
				text += "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede modificar y cerrar la nómina de esa empresa, pero existen otros dos usuarios que pueden modificar y cerrar la nómina para cuando el responsable no esté. ";
				text += "Se podría dar el caso de que ninguno de los 3 esté. " + adminProblems();
				break;
			case 2:
				text += "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador puede modificar la nómina de esa empresa, aunque se tiene constancia de quien hace que acción siempre por el uso de un log. ";
				break;
			case 3:
				text += "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador tiene también todos los permisos posibles, aunque se tiene constancia de quien hace que acción siempre por el uso de un log ";
				break;
			case 4:
				text += "Uno de los perfiles que existen es el del responsable de la empresa que se gestiona, este perfil puede hacer de todo, pero además cualquier trabajador tiene también todos los permisos posibles, sin dejar rastro de quien hizo qué, pues no hay log. ";
				break;
			case 5:
				text += "Cualquier trabajador tiene todos los permisos posibles, aunque se tiene constancia de quien hace que acción siempre por el uso de un log. ";
				break;
			case 6:
				text += "Cualquier trabajador tiene todos los permisos posibles, sin dejar rastro de quien hizo qué, pues no hay log. ";
				break;
			case 7:
				text += "Hay una cuenta de administrador que es el responsable de la empresa, pero el resto de los usuarios no tienen cuenta propia. ";
				userAccounts = false;
				break;
			default:
				break;
			}

			if (userAccounts) {
				text += "\n Las contraseñas de las cuentas de los usuarios: " + passwordChangeSystem();
			}
		}

		return text;
	}

	private String adminProblems() {
		String text = "Como solo el responsable puede cerrar las nóminas, cuando él falta, ";

		switch (randomNumber(0, 5)) {
		case 0:
			text += "queda otro compañero como responsable al que se le da un usuario nuevo y cuando vuelve el responsable se le deshabilita ese usuario. ";
			break;
		case 1:
			text += "queda otro compañero como responsable al que se le da la clave del usuario del administrador. ";
			break;
		case 2:
			text += "se crea una cuenta de invitado con una contraseña conocida por el nuevo responsable. ";
			break;
		case 3:
			text += "se crea una cuenta invitado y se dice la contraseña a todos los que tengan que cerrar una nómina. ";
			break;
		case 4:
			text += "se dice la contraseña del administrador a todos para que puedan arreglar sus propios problemas. ";
			break;
		case 5:
			text += "se espera a que vuelva el responsable, sea el tiempo que sea. ";
			break;
		default:
			break;
		}

		return text;
	}

	private String passwordChangeSystem() {
		String text = "";

		switch (randomNumber(0, 2)) {
		case 0:
			text += "Las contraseñas se cambian periódicamente, luego, es raro que más de una persona conozca una contraseña que no es suya. ";
			break;
		case 1:
			text += "El cambio de contraseña queda a decisión del propio usuario. Luego, hay trabajadores que la cambian todos los días, y otros que nunca la han cambiado y le conocen su contraseña todo el mundo. ";
			break;
		case 2:
			text += "Las contraseñas no se cambian nunca, lo que provocó que todos los usuarios conozcan todas las contraseñas de todos. ";
			break;
		default:
			break;
		}

		return text;
	}

	private String physicalPlace(Company company) {
		String text = "Respecto al espacio físico, la " + company.getType() + " se encuentran en ";

		switch (randomNumber(0, 2)) {
		case 0:
			text += "un piso. ";
			break;
		case 1:
			text += "un edificio de oficinas. ";
			break;
		case 2:
			text += "un edificio propio. ";
			break;
		default:
			break;
		}

		text += "Los despachos ";

		switch (randomNumber(0, 4)) {
		case 0:
			text += "no existen, es una gran sala en donde cada uno tiene su mesa o una mesa compartida. ";
			break;
		case 1:
			text += "no existen, es una gran sala en donde cada uno tiene su mesa o una mesa compartida, pero cada mesa está separada por mamparas. ";
			break;
		case 2:
			text += "privados solo están para el gerente, el resto de trabajadores están en una sala en común. ";
			break;
		case 3:
			text += "son colectivos para un mismo grupo de trabajo, se usan paneles movibles para crearlos, y privados para el gerente. ";
			break;
		case 4:
			text += "son individuales y privados para todo el mundo. ";
			break;
		default:
			break;
		}

		text += "El servidor se encuentra en " + location();

		return text;
	}

	private String location() {
		String text = "";

		switch (randomNumber(0, 6)) {
		case 0:
			text += "una sala común. ";
			break;
		case 1:
			text += "la sala común guardado en un armario bajo llave que tiene el responsable actual. ";
			break;
		case 2:
			text += "la sala común guardado en un armario bajo llave a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ";
			break;
		case 3:
			text += "la sala común guardado en un armario bajo llave, la cual está colgando junto a las copias de la llave de la cerradura. ";
			break;
		case 4:
			text += "una sala privada a la que solo tiene acceso el responsable actual. ";
			break;
		case 5:
			text += "una sala privada a la que tiene acceso todo el mundo debido a que las llaves están en un cajetín compartido. ";
			break;
		case 6:
			text += "una sala privada, pero las llaves están colgadas de la cerradura en un llavero en el que se encuentran también las copias de estas. ";
			break;
		default:
			break;
		}

		return text;
	}

	private String network() {
		String text = "La red del local ";

		switch (randomNumber(0, 7)) {
		case 0:
			text += "está cableada entera y no se usa WI-FI. ";
			break;
		case 1:
			text += "está cableada entera pero también hay una red WI-FI que da acceso a todos los trabajadores. ";
			break;
		case 2:
			text += "está cableada entera pero también hay una red WI-FI que da acceso a todos los trabajadores y a los clientes. ";
			break;
		case 3:
			text += "está cableada entera pero también hay una red WI-FI pública creada por la empresa para trabajadores y visitantes. ";
			break;
		case 4:
			text += "está cableada entera pero también hay una red WI-FI pública de la ciudad. ";
			break;
		case 5:
			text += "está cableada entera pero también hay una red WI-FI pública desconocida. ";
			break;
		case 6:
			text += "es una red WI-FI de la empresa. ";
			break;
		case 7:
			text += "es una red WI-FI de la empresa, aunque hay otras redes abiertas que a veces se usan, pues suelen ir mejor que la de la empresa. ";
			break;
		default:
			break;
		}

		return text;
	}

	private String backups() {
		String text = "Las copias de seguridad se hacen ";

		boolean backups = true;

		switch (randomNumber(0, 4)) {
		case 0:
			text += "todos los viernes. ";
			break;
		case 1:
			text += "1 vez al mes. ";
			break;
		case 2:
			text += "1 vez al año. ";
			break;
		case 3:
			text += "cuando se acuerda, a veces las hacen dos días seguidos y a veces tras pasar 5 meses. ";
			break;
		case 4:
			text += "... nunca ¿Qué es una copia de seguridad? Solo ocupan espacio innecesario y desperdicia tiempo. ";
			backups = false;
			break;
		default:
			break;
		}

		if (backups) {

			text += "Para hacer las copias se utiliza ";

			switch (randomNumber(0, 5)) {
			case 0:
				text += "un sistema automatizado que las envía a un servicio en la nube. ";
				// añadir opciones: en el mismo servicio (si usan la nube arriba poner
				// variable), en otro, etc.
				break;
			case 1:
				text += "un disco duro externo ";

				switch (randomNumber(0, 6)) {
				case 0:
					text += "que se almacena en la empresa. ";
					break;
				case 1:
					text += "que recoge el jefe cuando termina de hacerse y se lo lleva para casa ";
					break;
				case 2:
					text += "que guarda el responsable bajo llave en el armario. ";
					break;
				case 3:
					text += "que guarda el último empleado en salir de la oficina en su mesa. ";
					break;
				case 4:
					text += "que recoge el último empleado en salir de la oficina y lo deja en la mesa del responsable. ";
					break;
				case 5:
					text += "que recoge el responsbale al siguiente día laboral. ";
					break;
				case 6:
					text += "que se deja enganchado al PC hasta el siguiente día. ";
					break;
				default:
					break;
				}

				break;
			case 2:
				text += "en un sistema de dos cintas que se intercambian (un día se usa una y otro día otra) en base a las explicaciones sobre el sistema que le dieron hace años al trabajador. ";
				break;
			case 3:
				text += "en el propio disco duro del servidor usando la herramienta del sistema operativo. ";
				break;
			case 4:
				text += "en un Blu-Ray que se deja sobre la mesa del responsable. ";
				break;
			case 5:
				text += "en un Blu-Ray que se deja sobre el servidor. ";
				break;
			default:
				break;
			}
		}

		return text;
	}

	private String clientService() {
		String text = "El servicio que se da a los clientes se basa en una ";

		switch (randomNumber(0, 4)) {
		case 0:
			text += "aplicación de escritorio. ";
			break;
		case 1:
			text += "aplicación web alojada en el servidor de la empresa. ";
			break;
		case 2:
			text += "aplicación web alojada en un hosting español. ";
			break;
		case 3:
			text += "aplicación web alojada en un servicio en la nube fuera de España. "; // Aquí habría que especificar
																							// igual el país, si es en
																							// USA si la empresa cumple
																							// el contrato con Europa,
																							// si es otro país si cumple
																							// normas o algo, etc. Que
																							// pregunten ellos
			break;
		case 4:
			text += "aplicación móvil subida a Google Play y que obtiene sus datos del servidor de la empresa. ";
			break;
		default:
			break;
		}

		text += " El acceso al servicio se hace mediante ";

		switch (randomNumber(0, 4)) {
		case 0:
			text += "un usuario y una contraseña creado por la empresa para el usuario. ";
			break;
		case 1:
			text += "el registro previo del usuario dando su email y contraseña, la cual no se guarda hasheada en el servidor. ";
			break;
		case 2:
			text += "el registro previo del usuario dando su email y contraseña, la cual se guarda hasheada en el servidor. ";
			break;
		case 3:
			text += "el acceso directo pues no hay usuario/contraseña. ";
			break;
		case 4:
			text += "el acceso del nombre de la empresa contratante, pues no hay contraseña. ";
			break;
		default:
			break;
		}

		text += " El acceso al servicio se hace utilizando ";

		switch (randomNumber(0, 2)) {
		case 0:
			text += "protocolos seguros como HTTPS y FTPS. ";
			break;
		case 1:
			text += "protocolos no seguros como HTTP y FTP. ";
			break;
		case 2:
			text += "protocolos no seguros como HTTP y FTP, pero encriptando todo el tráfico de manera segura con un algoritmo de seguridad de la empresa. "; // añadir
																																								// variantes
																																								// con
																																								// otros
																																								// algoritmos
																																								// reales
																																								// obsoletos
																																								// y
																																								// no
																																								// obsoletos
			break;
		default:
			break;
		}

		return text;
	}

	private String dataService() {
		String text = "El servicio que se utiliza para recibir los datos del usuario es ";

		boolean encrypted = true;

		// tg uno en ownSalarySystem para combinar
		switch (randomNumber(0, 5)) {
		case 0:
			text += "un servicio web. ";
			break;
		case 1:
			text += "un correo electrónico. ";
			break;
		case 2:
			text += "por correo postal. ";
			encrypted = false;
			break;
		case 3:
			text += "un fax. ";
			encrypted = false;
			break;
		case 4:
			text += "por teléfono. ";
			encrypted = false;
			break;
		case 5:
			text += "en persona usando hojas de papel/CD/DVD/USB. ";
			encrypted = false;
			break;
		default:
			break;
		}

		if (encrypted) {
			text += " Este servicio ";

			switch (randomNumber(0, 1)) {
			case 0:
				text += "va encriptado. ";
				break;
			case 1:
				text += "va en texto plano. ";
				break;
			default:
				break;
			}
		}

		text += " El responsable utiliza el mismo sistema para responder al cliente. "; // Añadir aquí más opciones

		return text;
	}

	private String ownSalarySystem() {
		boolean cessionData = false;

		String text = "Las nóminas internas de la empresa y de los clientes "; // separar opciones de trabajadores y
																				// clientes en 2 mejor, más juego

		switch (randomNumber(0, 4)) {
		case 0:
			text += "se hacen a mano. ";
			break;
		case 1:
			text += "se hacen manualmente a ordenador. ";
			break;
		case 2:
			text += "se generan con un programa y se guardan en un CD/DVD. ";
			break;
		case 3:
			text += "las genera una tercera empresa que se encarga de todo. ";
			cessionData = true;
			break;
		case 4:
			text += "las genera una tercera empresa con la que se firmó un contrato por el que deben de cumplir todos los criterios legales. ";
			cessionData = true;
			break;

		default:
			break;
		}

		text += "Se envían al banco usando ";

		// tg uno en dataService para combinar
		switch (randomNumber(0, 4)) {
		case 0:
			text += "un servicio web. ";
			break;
		case 1:
			text += "un correo electrónico. ";
			break;
		case 2:
			text += "por correo postal. ";
			break;
		case 3:
			text += "un fax. ";
			break;
		case 4:
			text += "en persona usando hojas de papel/CD/DVD/USB. ";
		default:
			break;
		}

		text += "Se envían al destinatario interasado usando ";

		// tg uno justo aquí arriba
		switch (randomNumber(0, 4)) {
		case 0:
			text += "un servicio web. ";
			break;
		case 1:
			text += "un correo electrónico. ";
			break;
		case 2:
			text += "por correo postal. ";
			break;
		case 3:
			text += "un fax. ";
			break;
		case 4:
			text += "en persona usando hojas de papel/CD/DVD/USB. ";
		default:
			break;
		} // añadir: se meten en un sobre y se envían a los destinatarios

		if (cessionData) {
			text += "La cesión de los datos a esta tercera empresa ";

			// tg uno justo aquí arriba
			switch (randomNumber(0, 2)) {
			case 0:
				text += "se informa al cliente y a los empleados siempre. ";
				break;
			case 1:
				text += "no se informa al cliente y a los empleados nunca. ";
				break;
			case 2:
				text += "se informa al cliente y a los empleados si preguntan. ";
				break;
			default:
				break;
			}
		}

		return text;

	}

	private String hoursData() {
		String text = "";

		// Los datos referentes a la propia gestoría se controlan en la propia
		// aplicación como si de otro cliente se tratase. Los datos de las horas
		// trabajadas están al cargo del gerente

		text += "Los datos de las horas trabajadas los lleva el gerente, el cuál "; // ¿Más opciones?

		switch (randomNumber(0, 2)) {
		case 0:
			text += "los guarda en su ordenador personal en una hoja Excel situada en su directorio personal. ";
			break;
		case 1:
			text += "los apunta en hojas de papel. ";
			break;
		case 2:
			text += "usa una aplicación web que tienen subida en su servidor y que usa el mismo sistema de usuario/password que el de las nóminas. ";
			break;
		default:
			break;
		}

		text += "Los datos son introducidos por "; // ¿Más opciones?

		switch (randomNumber(0, 2)) {
		case 0:
			text += "el gerente, que es el único que debería de tener acceso. ";
			break;
		case 1:
			text += "el propio empleado, pues él es su propio responsable. ";
			break;
		case 2:
			text += "se cambia el empleado responsable de apuntar todas las horas cada X tiempo. ";
			break;
		default:
			break;
		}

		text += "Además, esta hoja se imprime mensualmente ya que contiene los datos del mes, y se archiva "; // ¿Más
																												// opciones?

		switch (randomNumber(0, 3)) {
		case 0:
			text += "en carpetas anilladas que se guardan en un armario abierto. ";
			break;
		case 1:
			text += "en carpetas anilladas que se guardan en un armario siguiendo el mismo proceso que el acceso al servidor. ";
			break;
		case 2:
			text += "una vez se termina con ellas se tiran a reciclar, algo muy importante, pero sin triturar";
			break;
		case 3:
			text += "una vez se termina con ellas se tiran a reciclar, algo muy importante, pero después de haberlas triturado. ";
			break;
		default:
			break;
		} // más opciones en esta última

		text += "Los demás documentos ";

		switch (randomNumber(0, 2)) {
		case 0:
			text += "en carpetas anilladas que se guardan en " + location();
			break;
		case 1:
			text += "una vez se termina con ellas se tiran a reciclar, algo muy importante, pero sin triturar";
			break;
		case 2:
			text += "una vez se termina con ellas se tiran a reciclar, algo muy importante, pero después de haberlas triturado. ";
			break;
		default:
			break;
		} // más opciones en esta última

		text += "\nDurante la vida de esta empresa ";

		if (randomNumber(0, 1) == 0) {
			text += "jamás se han declarado los ficheros de datos de la LOPD, ni se lleva un registro de actividades del tratamiento. ";
		} else {
			text += "se han declarado los ficheros de datos de la LOPD, pero no se lleva un registro de actividades del tratamiento. ";
		}

		return text;
	}

	public boolean getDependeceVariable(String key) {
		return this.dependeciesVariables.get(key);
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
