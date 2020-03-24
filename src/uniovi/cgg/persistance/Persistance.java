package uniovi.cgg.persistance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Persistance {

	private static final String FOLDER = "../bbdd";
	private static final String NAME = "bbdd.json";
	public static final String FILE = FOLDER + File.separator + NAME;

	/**
	 * Variable que guarda la instance de la clase actual
	 */
	private static Persistance instance = null;

	/**
	 * Se usa para obtener la instancia de esta clase. Solo se permite una. Patrón
	 * Singleton.
	 * 
	 * @return Persistance
	 */
	public static Persistance getInstance() {
		if (instance == null) {
			instance = new Persistance();
		}
		return instance;
	}

	/**
	 * Privado para que no se puedan crear varias, ya que solo queremos una. Patrón
	 * Singleton.
	 */
	private Persistance() {

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
	private void createFile() {
		// To create the file you need to create the parent directories first
		createFolder(FOLDER);

		File file = new File(FILE);

		try {
			if (file.createNewFile()) {
				System.out.println(FILE + " created");
			} else {
				System.out.println(FILE + " already exists");
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
		createFile();
		
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
	 * Carga el fichero que recibe com oparámetro (ruta incluída) usando UTF9 como codificación
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

	public static void main(String[] args) {

		Persistance main = new Persistance();

		main.createFile();
		
		JSONArray json = main.stringToJSONArray(main.loadFileToString(FILE));

		System.out.println(json);
	}

}
