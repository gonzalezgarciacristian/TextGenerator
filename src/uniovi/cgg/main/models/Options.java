package uniovi.cgg.main.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import uniovi.cgg.exceptions.NoOptions;
import uniovi.cgg.main.Main;

public class Options {

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

	private static final int TEXT = 0;
	private static final int PROBABILITY = 1;
	private static final int DEPENDENCIES = 2;
	private static final int DEPENDSON = 3;
	private static final String NOTHING = "-";

	/*
	 * private int[] probability; private String[] dependencies; private String[]
	 * dependsOn;
	 */

	/**
	 * True si los textos tienen diferente probabilidad, si todos tienen la misma
	 * probabilidad de parici�n entonces false
	 */
	private boolean probabilityModified;

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
		List<String[]> noPossibleOptions = new LinkedList<String[]>();

		// Elimina de las posibles opciones aquellas que dependan de una variable y
		// dicha variable est� a false
		for (int i = 0, length = options.size(); i < length; i++) {
			dependencies = options.get(i)[DEPENDSON].split(",");			
			for (int j = 0, lengthJ = dependencies.length; j < lengthJ; j++) {
				if (!dependencies[j].contentEquals(NOTHING) && !main.getDependeceVariable(dependencies[j])) {
					noPossibleOptions.add(options.get(i));
				}
			}
		}
		
		//System.out.println("posibles: " + options.size());
		//System.out.println("no posibles: " + noPossibleOptions.size());
		options.removeAll(noPossibleOptions);
		//System.out.println("restantes: " + options.size());

		checkRemainingOptions(options.size(), this.name);

		// Si las probabilidad son diferentes para cada texto a generar
		if (probabilityModified) {
			List<String[]> optionsWithProbability = new ArrayList<String[]>();
			int possibilities = 0;
			// Se introducen el n�mero de probabilidad de cada texto en una nueva lista que
			// se usar� para sacar la sentencia aleatoria
			for (int i = 0, length = options.size(); i < length; i++) {
				for (int j = 0, lengthJ = Integer.parseInt(options.get(i)[PROBABILITY]); j < lengthJ; j++) {
					optionsWithProbability.add(new String[] { options.get(i)[TEXT], options.get(i)[DEPENDENCIES] });
					possibilities++;
				}
			}

			random = randomNumber(0, possibilities - 1);
			text += optionsWithProbability.get(random)[TEXT];

			checkToInsertDependence(optionsWithProbability.get(random)[1]);

			return text;
		} else {
			int max = options.size();
			checkRemainingOptions(max, this.name);
			random = randomNumber(0, max - 1);
			checkToInsertDependence(options.get(random)[DEPENDENCIES]);
			return options.get(random)[TEXT];
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
			main.insertDependeceVariable(dependence, true);
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
	 * Saca un n�mero aleatorio que se encuentre entre el min y max, incluidos ambos
	 * 
	 * @param min n�mero m�s peque�o
	 * @param max n�mero m�s grande
	 * @return un n�mero entre el min y el max, incluidos
	 */
	private int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public String getName() {
		return name;
	}

}
