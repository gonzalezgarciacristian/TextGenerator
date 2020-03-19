package uniovi.cgg.persistance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

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
	 * Se usa apra obtener la instancia de esta clase. Solo se permite una. Patrón
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

	public void saveFile(String file, String data) {
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

	private JSONObject loadFileToJSON(String file) {
		Reader inputFile = null;
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;

		try {
			inputFile = new FileReader(file);
			jsonObject = (JSONObject) jsonParser.parse(inputFile);
		} catch (ParseException e) {
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e1) {
				System.out.println(e1);
				e1.printStackTrace();
			}
		}

		return jsonObject;
	}

	public static void main(String[] args) {

		Persistance main = new Persistance();

		main.createFile();

		JSONObject obj = new JSONObject();
		obj.put("company", "UniOvi");
		obj.put("company2", "MDE Research Group");

		JSONArray list = new JSONArray();
		list.add("UniOvi");
		list.add("pequeña");
		list.add("10 empleados");
		list.add("educación");

		obj.put("messages", list);

		main.saveFile(FILE, obj.toJSONString());

		JSONObject json = main.loadFileToJSON(FILE);

		System.out.println(json);

		System.out.print(obj);

	}

}
