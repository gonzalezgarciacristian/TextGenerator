package uniovi.cgg.main.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import uniovi.cgg.exceptions.NoOptions;
import uniovi.cgg.main.Main;

public class Options {

	private long id;

	private String name;
	private String introduction;
	private String conclusions;

	/**
	 * Options es un String[][] que se convierte a List<String[]> en el constructor
	 * para poder manipularlo. Es un String[["texto de la opción"]["probabilidad",
	 * "-/true/false separados por comas tantas veces como dependencias haya"],
	 * "-/true/false separados por comas tantas veces como dependencias haya"]].
	 * Luego: [0: [0: texto],[1: probabilidad],[2: estado al que pone la dependencia
	 * si la tiene (depencencies == true)],[3: variable de la que dependen, si hay +
	 * de 1, se separan por comas (dependsOn == true)], 1: [0: texto],[1:
	 * probabilidad],[2: estado al que pone la dependencia si la tiene (depencencies
	 * == true)],[3: variable de la que dependen, si hay + de 1, se separan por
	 * comas (dependsOn == true)],...]
	 */
	private List<String[]> options;
	
	/**
	 * True si los textos tienen diferente probabilidad, si todos tienen la misma
	 * probabilidad de aparición entonces false
	 */
	private boolean probabilityModified;

	private static final int TEXT = 0;
	private static final int PROBABILITY = 1;
	private static final int DEPENDENCIES = 2;
	private static final int DEPENDSON = 3;
	private static final String NOTHING = "-";	

	private Main main;

	public Options(long id, String name, String introduction, String conclusions, String[][] options,
			boolean probabilityModified, Main main) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.conclusions = conclusions;
		// Hay que crearla ya que Arrays.asList no permite operaciones:
		// https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
		this.options = new LinkedList<String[]>(Arrays.asList(options));
		this.probabilityModified = probabilityModified;
		this.main = main;
	}

	/**
	 * Comprueba si hay opciones de texto con diferentes probabilidades, si es así
	 * crea las probabilidades del texto que tenga mayor pondereción, ejecuta el
	 * aleatorio, y devuelve el texto correspondiente. En caso contrario, ejecuta el
	 * aleatorio directamente
	 * 
	 * @return String Texto aleatorio después de ejecutar las probabilidades
	 * @throws NoOptions Lanza esta excepción si no hay opciones entre las que
	 *                   elegir
	 */
	private String getTextWithprobability() throws NoOptions {
		String text = "";
		int random = -1;
		String[] dependencies = null;
		List<String[]> optionsCopy = new LinkedList<String[]>(this.options);
		List<String[]> noPossibleOptions = new LinkedList<String[]>();		

		// Elimina de las posibles opciones aquellas que dependan de una variable y
		// dicha variable esté a false
		for (int i = 0, length = optionsCopy.size(); i < length; i++) {
			dependencies = optionsCopy.get(i)[DEPENDSON].split(",");			
			for (int j = 0, lengthJ = dependencies.length; j < lengthJ; j++) {
				if (!dependencies[j].contentEquals(NOTHING) && !main.getDependeceVariable(dependencies[j])) {
					noPossibleOptions.add(optionsCopy.get(i));
				}
			}
		}
		
		//System.out.println("posibles: " + options.size());
		//System.out.println("no posibles: " + noPossibleOptions.size());
		optionsCopy.removeAll(noPossibleOptions);
		//System.out.println("restantes: " + options.size());

		checkRemainingOptions(optionsCopy.size(), this.name);

		// Si las probabilidad son diferentes para cada texto a generar
		if (probabilityModified) {
			List<String[]> optionsWithProbability = new ArrayList<String[]>();
			int possibilities = 0;
			// Se introducen el número de probabilidad de cada texto en una nueva lista que
			// se usará para sacar la sentencia aleatoria
			for (int i = 0, length = optionsCopy.size(); i < length; i++) {
				for (int j = 0, lengthJ = Integer.parseInt(optionsCopy.get(i)[PROBABILITY]); j < lengthJ; j++) {
					optionsWithProbability.add(new String[] { optionsCopy.get(i)[TEXT], optionsCopy.get(i)[DEPENDENCIES] });
					possibilities++;
				}
			}

			random = randomNumber(0, possibilities - 1);
			text += optionsWithProbability.get(random)[TEXT];

			checkToInsertDependence(optionsWithProbability.get(random)[1]);

			return text;
		} else {
			int max = optionsCopy.size();
			checkRemainingOptions(max, this.name);
			random = randomNumber(0, max - 1);
			checkToInsertDependence(optionsCopy.get(random)[DEPENDENCIES]);
			return optionsCopy.get(random)[TEXT];
		}

	}

	/**
	 * Comprueba si el número que se le pasa es menor que 1, si así es, lanza la
	 * excepción NoOptions. Este método debe ser llamada cuando haya que comprobar
	 * si quedan opciones entre las que elegir
	 * 
	 * @param options int
	 * @throws NoOptions
	 */
	private void checkRemainingOptions(int options, String name) throws NoOptions {
		if (options < 1) {
			throw new NoOptions(name);
		}
	}

	/**
	 * Comprueba si tiene variable de dependencia, y si tiene, la pone a true
	 * 
	 * @param option String[] Opción elegida en el método que llama a este
	 */
	private void checkToInsertDependence(String dependence) {
		if (!dependence.equals(NOTHING)) {
			main.insertDependeceVariable(dependence, true);
		}
	}

	/**
	 * Método que devuelve un resultado de este objeto entre todas las elecciones
	 * posibles introducidas junto con su introducción y su conclusión. En el caso
	 * de que haya opciones con diferentes probabilidades, las tiene en cuenta y
	 * ejecuta el aletorio en base a esas probabilidades. Se encarga de capturar la
	 * excepción NoOptions
	 * 
	 * @return String Contiene la introducción, el resultado aleatorio y las
	 *         conclusiones
	 */
	@Override
	public String toString() {
		try {
			return introduction + getTextWithprobability() + conclusions;
		} catch (NoOptions e) {
			e.printStackTrace();			
		}
		return "";
	}
	
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		JSONArray optionsArray = new JSONArray();
		
		jsonObject.put("id", this.id);
		jsonObject.put("name", this.name);
		jsonObject.put("introduction", this.introduction);
		jsonObject.put("conclusions", this.conclusions);
	
		JSONObject jsonObjectOptions = new JSONObject();		
		JSONObject jsonObjectText = new JSONObject();
		
		for(int i = 0, length = this.options.size(); i < length; i++) {
			optionsArray.add(TEXT, options.get(i)[TEXT]);
			optionsArray.add(PROBABILITY, options.get(i)[PROBABILITY]);
			optionsArray.add(DEPENDENCIES, options.get(i)[DEPENDENCIES]);
			optionsArray.add(DEPENDSON, options.get(i)[DEPENDSON]);
		}
		
		jsonObjectOptions.put("options", optionsArray);
				
		jsonObject.put("options", optionsArray);
		jsonObject.put("probabilityModified", this.probabilityModified);	

		return jsonObject;
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

	public String getName() {
		return name;
	}

}
