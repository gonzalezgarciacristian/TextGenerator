package uniovi.cgg.logic.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uniovi.cgg.exceptions.NoOptions;
import uniovi.cgg.test.Main;

public class Options {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String INTRODUCTION = "introduction";
	public static final String CONCLUSIONS = "conclusions";
	public static final String OPTIONS = "options";
	public static final String PROBABILITY_MODIFIED = "probabilityModified";

	private static final int TEXT = 0;
	private static final int PROBABILITY = 1;
	private static final int DEPENDENCIES = 2;
	private static final int DEPENDSON = 3;
	private static final String NOTHING = "-";

	private long id;

	private String name;
	private String introduction;
	private String conclusions;

	/**
	 * Options es un String[][] que se convierte a List<String[]> en el constructor
	 * para poder manipularlo. Es un String[["texto de la opci�n"]["probabilidad",
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
	 * probabilidad de aparici�n entonces false
	 */
	private boolean probabilityModified;

	private UseCase useCase;
	/*que hago con esta variable de tipo main? como idesenlazo esto? siguiente trabajo es este. Posiblemente la depednencia y la tabla tendr� que ir en el UseCase que es �nico 
			y entonces as� arreglo el main de test para romper esta depednencia y puedo arreglar el main de logic para comenzar a depende de �l y hacer a ese las llamadas desde
			el MainApp que es el gr�fico*/
	
	/**
	 * Privado para evitar que se pueda instanciar el constructor por defecto
	 */
	private Options() {}

	public Options(long id, String name, String introduction, String conclusions, String[][] optionsList,
			boolean probabilityModified, UseCase useCase) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.conclusions = conclusions;
		// Hay que crearla ya que Arrays.asList no permite operaciones:
		// https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
		this.options = new LinkedList<String[]>(Arrays.asList(optionsList));
		this.probabilityModified = probabilityModified;
		this.useCase = useCase;
	}

	public Options(long id, String name, String introduction, String conclusions, List<String[]> optionsList,
			boolean probabilityModified, UseCase useCase) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.conclusions = conclusions;
		this.options = new LinkedList<String[]>(optionsList);
		this.probabilityModified = probabilityModified;
		this.useCase = useCase;
	}

	/**
	 * Comprueba si hay opciones de texto con diferentes probabilidades, si es as�
	 * crea las probabilidades del texto que tenga mayor pondereci�n, ejecuta el
	 * aleatorio, y devuelve el texto correspondiente. En caso contrario, ejecuta el
	 * aleatorio directamente
	 * 
	 * @return String Texto aleatorio despu�s de ejecutar las probabilidades
	 * @throws NoOptions Lanza esta excepci�n si no hay opciones entre las que
	 *                   elegir
	 */
	private String getTextWithprobability() throws NoOptions {
		String text = "";
		int random = -1;
		String[] dependencies = null;
		List<String[]> optionsCopy = new LinkedList<String[]>(this.options);
		List<String[]> noPossibleOptions = new LinkedList<String[]>();

		// Elimina de las posibles opciones aquellas que dependan de una variable y
		// dicha variable est� a false
		for (int i = 0, length = optionsCopy.size(); i < length; i++) {
			dependencies = optionsCopy.get(i)[DEPENDSON].split(",");
			for (int j = 0, lengthJ = dependencies.length; j < lengthJ; j++) {
				if (!dependencies[j].contentEquals(NOTHING) && !useCase.getDependeceVariable(dependencies[j])) {
					noPossibleOptions.add(optionsCopy.get(i));
				}
			}
		}

		optionsCopy.removeAll(noPossibleOptions);

		checkRemainingOptions(optionsCopy.size(), this.name);

		// Si las probabilidad son diferentes para cada texto a generar
		if (probabilityModified) {
			List<String[]> optionsWithProbability = new ArrayList<String[]>();
			int possibilities = 0;
			// Se introducen el n�mero de probabilidad de cada texto en una nueva lista que
			// se usar� para sacar la sentencia aleatoria
			for (int i = 0, length = optionsCopy.size(); i < length; i++) {
				for (int j = 0, lengthJ = Integer.parseInt(optionsCopy.get(i)[PROBABILITY]); j < lengthJ; j++) {
					optionsWithProbability
							.add(new String[] { optionsCopy.get(i)[TEXT], optionsCopy.get(i)[DEPENDENCIES] });
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
	 * Comprueba si el n�mero que se le pasa es menor que 1, si as� es, lanza la
	 * excepci�n NoOptions. Este m�todo debe ser llamada cuando haya que comprobar
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
	 * @param option String[] Opci�n elegida en el m�todo que llama a este
	 */
	private void checkToInsertDependence(String dependence) {
		if (!dependence.equals(NOTHING)) {
			useCase.insertDependeceVariable(dependence, true);
		}
	}

	/**
	 * M�todo que devuelve un resultado de este objeto entre todas las elecciones
	 * posibles introducidas junto con su introducci�n y su conclusi�n. En el caso
	 * de que haya opciones con diferentes probabilidades, las tiene en cuenta y
	 * ejecuta el aletorio en base a esas probabilidades. Se encarga de capturar la
	 * excepci�n NoOptions
	 * 
	 * @return String Contiene la introducci�n, el resultado aleatorio y las
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

	/**
	 * M�todo que introduce en un JSONObject con formato JSON el objeto inicial
	 * original entero, es decir. Las opciones van dentro de un JSONArray en el
	 * mismo orden en como se tratan en el propio objeto
	 * 
	 * @return JSONObject
	 */
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		JSONArray optionsArray = new JSONArray();
		JSONArray optionArray = null;

		jsonObject.put(ID, this.id);
		jsonObject.put(NAME, this.name);
		jsonObject.put(INTRODUCTION, this.introduction);
		jsonObject.put(CONCLUSIONS, this.conclusions);

		// Como las opciones se parsean al sacar las probabilidades, no hace falta
		// meterlas en variables separadas ni recorrerlas si tienen diferentes
		// dependencias, solamente hace falta separarlas en arrays individuales para que
		// cuando se vuelvan a crear cada opci� nsea un JSONArray
		for (int i = 0, length = this.options.size(); i < length; i++) {
			optionArray = new JSONArray();
			optionArray.add(TEXT, options.get(i)[TEXT]);
			optionArray.add(PROBABILITY, options.get(i)[PROBABILITY]);
			optionArray.add(DEPENDENCIES, options.get(i)[DEPENDENCIES]);
			optionArray.add(DEPENDSON, options.get(i)[DEPENDSON]);
			optionsArray.add(optionArray);
		}

		jsonObject.put(OPTIONS, optionsArray);
		jsonObject.put(PROBABILITY_MODIFIED, this.probabilityModified);

		return jsonObject;
	}

	/**
	 * Saca un n�mero aleatorio que se encuentre entre el min y max, incluidos ambos
	 * 
	 * @param min n�mero m�s peque�o
	 * @param max n�mero m�s grande
	 * @return un n�mero entre el min y el max, incluidos
	 */
	private int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
