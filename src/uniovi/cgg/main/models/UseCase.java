package uniovi.cgg.main.models;

import java.util.ArrayList;
import java.util.List;

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
	public String exercise() {
		String exercise = "";

		for (int i = 0, length = this.options.size(); i < length; i++) {
			exercise += this.options.get(i).toString();
		}

		return exercise;
	}

	public List<Options> getOptions() {
		return options;
	}
	
}
