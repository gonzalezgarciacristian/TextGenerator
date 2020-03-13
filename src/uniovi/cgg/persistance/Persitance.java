package uniovi.cgg.persistance;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Persitance {

	private static final String FOLDER = "../bbdd";
	private static final String NAME = "bbdd.json";
	private static final String FILE = FOLDER + File.separator + NAME;

	/**
	 * Si solo se pone /, la carpeta la creará en la raíz de la unidad, con ../ la
	 * crea en el directorio superior
	 * 
	 * @param folder
	 */
	private void createFolder(String folder) {
		// To create the file you need to create the parent directories first
		new File(folder).mkdirs();
	}

	/**
	 * Sol ocrea el fichero y su carpeta la primera vez, es decir, en caso de que n
	 * oexista
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

	private void saveFile(String file, String data) {

		FileWriter outputFile = null;

		try {
			outputFile = new FileWriter(FILE);
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

	public static void main(String[] args) {

		Persitance main = new Persitance();

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

		System.out.print(obj);

	}

}
