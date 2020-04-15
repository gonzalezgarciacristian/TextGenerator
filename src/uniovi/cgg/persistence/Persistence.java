package uniovi.cgg.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uniovi.cgg.logic.models.Configuration;
import uniovi.cgg.logic.models.Options;
import uniovi.cgg.logic.models.UseCase;

public class Persistence {

	private static final String FOLDER = "../bbdd";
	private static final String JSON_EXTENSION = ".json";
	private static final String CONF_EXTENSION = ".conf";
	private static final String FILE_NAME = "bbdd"+JSON_EXTENSION;
	public static final String FILE_WITH_FOLDER = FOLDER + File.separator + FILE_NAME;
	private static final String CONFIGURATION_NAME = "configuration"+CONF_EXTENSION;
	private static final String CONFIGURATION = FOLDER + File.separator + CONFIGURATION_NAME;
	public static final String i18n_PATH = "i18n/texts";

	/**
	 * Variable que guarda la instance de la clase actual
	 */
	private static Persistence instance = null;

	/**
	 * Se usa para obtener la instancia de esta clase. Solo se permite una. Patrón
	 * Singleton.
	 * 
	 * @return Persistance
	 */
	public static Persistence getInstance() {
		if (instance == null) {
			instance = new Persistence();
		}
		return instance;
	}

	/**
	 * Privado para que no se puedan crear varias, ya que solo queremos una. Patrón
	 * Singleton.
	 */
	private Persistence() {

	}

	/**
	 * Si solo se pone /, la carpeta la creará en la raíz de la unidad, con ../ la
	 * crea en el directorio superior
	 * 
	 * @param folder
	 */
	private void createFolder(String folder) {
		new File(folder).mkdirs();
	}

	/**
	 * Solo crea el fichero y su carpeta la primera vez, es decir, en caso de que no
	 * exista. Si el fichero va dentro de una carpeta, hay que crear esa carpeta
	 * primero o fallará
	 */
	private void createFile(String nameWithFolder) {
		// To create the file you need to create the parent directories first
		createFolder(FOLDER);

		File file = new File(nameWithFolder);

		try {
			if (file.createNewFile()) {
				System.out.println(nameWithFolder + " created");
			} else {
				System.out.println(nameWithFolder + " already exists");
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * Comprueba que el fichero exista llamando a createFile, y después guarda su contenido en codificación UTF8
	 * @param file Ruta del fichero y nombre de este
	 * @param data Datos a guardar en el fichero
	 */
	public void saveFile(String file, String data) {
		createFile(file);
		
		OutputStreamWriter outputFile = null;
		
		try {
			outputFile = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
			outputFile.write(data);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				outputFile.close();
			} catch (IOException e1) {
				System.out.println(e1);
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Carga el fichero que recibe como parámetro (ruta incluída) usando UTF9 como codificación
	 * @param file ruta+fichero a leer
	 * @return String contiene todo el texto del fichero
	 */
	public String loadFileToString(String file) {
		String text = "";
		
		try {
			text = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}

		return text;
	}

	/**
	 * Convierte el texto que se le pasa a JSONObject
	 * @param text String texto a convertir
	 * @return JSONObject texto convertido
	 */
	public JSONObject stringToJSONObject(String text) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;

		try {
			jsonObject = (JSONObject) jsonParser.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	/**
	 * Convierte el texto que se le pasa a JSONArray
	 * @param text String texto a convertir
	 * @return JSONArray texto convertido
	 */
	public JSONArray stringToJSONArray(String text) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) jsonParser.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonArray;
	}
	
	/**
	 * Transforma un JSONObject a Option
	 * 
	 * @param json
	 * @return Options
	 */
	public Options loadJSONToOptions(JSONObject json, UseCase useCase) {
		// System.out.println(json);

		long id = (long) json.get(Options.ID);
		// System.out.println(id);

		String name = (String) json.get(Options.NAME);
		// System.out.println(name);

		String introduction = (String) json.get(Options.INTRODUCTION);
		// System.out.println(name);

		String conclusions = (String) json.get(Options.CONCLUSIONS);
		// System.out.println(name);

		List<String[]> optionsList = new ArrayList<String[]>();
		JSONArray optionsJSONArray = (JSONArray) json.get(Options.OPTIONS);
		JSONArray option;
		String[] option2 = null;

		for (int i = 0, length = optionsJSONArray.size(); i < length; i++) {
			option = (JSONArray) optionsJSONArray.get(i);
			option2 = new String[4];

			option2[0] = (String) option.get(0); // TEXT
			option2[1] = (String) option.get(1); // PROBABILITY
			option2[2] = (String) option.get(2); // DEPENDENCIES
			option2[3] = (String) option.get(3); // DEPENDSON

			optionsList.add(option2);

			// System.out.println(optionsJSONArray.get(i));
		}

		boolean probabilityModified = (Boolean) json.get(Options.PROBABILITY_MODIFIED);
		// System.out.println(probabilityModified);

		return new Options(id, name, introduction, conclusions, optionsList, probabilityModified, useCase);
	}
	
	public void saveConfiguration(Configuration configuration) {
		saveFile(CONFIGURATION, configuration.toJSON().toString());
	}

	public static void main(String[] args) {

		Persistence main = new Persistence();

		main.createFile(FILE_WITH_FOLDER);
		
		JSONArray json = main.stringToJSONArray(main.loadFileToString(FILE_WITH_FOLDER));

		System.out.println(json);
	}

}
