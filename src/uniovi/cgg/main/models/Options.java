package uniovi.cgg.main.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uniovi.cgg.main.Main;

public class Options {

	private long id;

	private String name;
	private String introduction;
	private String conclusions;

	/**
	 * Options es String[["texto de la opción"]["probabilidad", "-/true/false
	 * separados por comas tantas veces como dependencias haya"]]. Luego: [0: [0:
	 * texto],[1: probabilidad],[2: estado al que pone la dependencia], 1: [0:
	 * texto],[1: probabilidad],[2: estado al que pone la dependencia]]
	 */
	private String[][] options;

	/**
	 * Nombre de la dependencia que sirve para visualizarla en el formulario. Si hay
	 * más de 1, se meten separadas por comas
	 */
	private String[] dependencies;

	/**
	 * Nombre de la dependencia de la que depende y que tiene que buscar para saber
	 * si se ejecutará. Si hay más de 1, se meten separadas por comas
	 */
	private String[] dependsOn;

	/**
	 * True si los textos tienen diferente probabilidad, si todos tienen la misma
	 * probabilidad de parición entonces false
	 */
	private boolean probabilityModified;

	private Main main;

	public Options(long id, String name, String introduction, String conclusions, String[][] options,
			String[] dependencies, String[] dependsOn, boolean probabilityModified, Main main) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.conclusions = conclusions;
		this.options = options;
		this.dependencies = dependencies;
		this.probabilityModified = probabilityModified;
		this.main = main;

		// Metemos las dependencias en la HashMap de variables, si las hay
		if(this.dependencies != null) {
			for (int i = 0, length = this.dependencies.length; i < length; i++) {		
				main.insertDependeceVariable(this.dependencies[i], false);
			}
		}
	}

	/**
	 * Comprueba si hay opciones de texto con diferentes probabilidades, si es así
	 * crea las probabilidades del texto que tenga mayor pondereción, ejecuta el
	 * aleatorio, y devuelve el texto correspondiente. En caso contrario, ejecuta el
	 * aleatorio directamente
	 * 
	 * @return String Texto aleatorio después de ejecutar las probabilidades
	 */
	private String getTextWithprobability() {
		String text = "";
		int random = -1;

		if (probabilityModified) {
			List<String[]> optionsWithProbability = new ArrayList<String[]>();
			int possibilities = 0;

			for (int i = 0, length = options.length; i < length; i++) {
				for (int j = 0, lengthJ = Integer.parseInt(options[i][1]); j < lengthJ; j++) {
					optionsWithProbability.add(new String[] { options[i][0], options[i][2] });
					possibilities++;
				}
			}

			random = randomNumber(0, possibilities - 1);
			text += optionsWithProbability.get(random)[0];

			if (optionsWithProbability.get(random)[1].equals("true")) {
				main.insertDependeceVariable(optionsWithProbability.get(random)[1], true);
			}

			return text;
		} else {
			random = randomNumber(0, options.length - 1);
			
			if (options[random][1].equals("true")) {
				main.insertDependeceVariable(options[random][1], true);
			}
			return options[random][0];
		}
		
	}
	
	/**
	 * Método que devuelve 1 resultado de este objeto entre todas las elecciones
	 * posibles introducidas junto con su introducción y su conclusión. En el caso
	 * de que haya opciones con diferentes probabilidades, las tiene en cuenta y
	 * ejecuta e laletorio en base a esas probabilidades
	 * 
	 * @return String Contiene la introducción, el resultado aleatorio y las
	 *         conclusiones
	 */
	@Override
	public String toString() {
		String text = introduction;
		
		if (this.dependsOn == null) {
			text += getTextWithprobability();
		} else {			
			for(int i = 0, length = this.dependsOn.length; i < length; i++) {
				if(main.getDependeceVariable(this.dependsOn[i])) {
					text+= getTextWithprobability();
				}
			}
		}
		text += conclusions;
		return text;
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

	public String[] getDependencies() {
		return dependencies;
	}

}
