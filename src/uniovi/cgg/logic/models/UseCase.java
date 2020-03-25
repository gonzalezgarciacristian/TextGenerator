package uniovi.cgg.logic.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

/**
 * Encapsula todas las diferentes optiones creadas bajo un mismo objeto, de modo
 * que se pueda iterar por ellas, guardarlas todas juntas, generar el ejercicio,
 * y salvar todas las opciones juntas
 * 
 * @author Cristian
 *
 */
public class UseCase {

	private List<Options> options;
	
	private Map<String, Boolean> dependeciesVariables = new HashMap<String, Boolean>();

	/**
	 * Utilizar solo cuando se vaya a generar uno nuevo y se vayan cargando de una
	 * en una la sopciones
	 */
	public UseCase() {
	}

	/**
	 * Constructor utilizado para cuando ya se tienen todas las opciones y se quiere
	 * crear un caso de uso nuevo pasandole la lista d eopciones
	 * 
	 * @param objects
	 */
	public UseCase(List<Options> objects) {
		this.options = new ArrayList<Options>(objects);
	}

	/**
	 * Encapsula todas las opciones que estan separadas bajo un JSONArray que es el
	 * que contiene todas las misma sopciones de un objeto, que es el utilizado en
	 * el programa y que se llama UseCase
	 * 
	 * @return JSONArray
	 */
	private JSONArray toJSON() {
		JSONArray optionsArray = new JSONArray();

		for (int i = 0, length = this.options.size(); i < length; i++) {
			optionsArray.add(i, this.options.get(i).toJSON());
		}

		return optionsArray;
	}

	@Override
	public String toString() {
		return toJSON().toJSONString();
	}

	/**
	 * Recorre todos los objectos Options introducidos y llama al toString de estos,
	 * con lo cuál saca ya una opción con probabilidad de entre todas las que posee
	 * cada Options y realiza así el enunciado para el ejercicio
	 * 
	 * @return
	 */
	public String generateExercise() {
		String exercise = "";

		for (int i = 0, length = this.options.size(); i < length; i++) {
			exercise += this.options.get(i).toString();
		}

		return exercise;
	}

	public List<Options> getOptions() {
		return options;
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
