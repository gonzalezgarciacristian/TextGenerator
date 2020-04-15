package uniovi.cgg.logic.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Configuration {
	
	private static final String LANGUAGE = "LANGUAGE";
	private static final String USER_EMAIL = "USER_EMAIL";
	private static final String INTRODUCTION = "INTRODUCTION";
	private static final String SIGN = "SIGN";
	
	private String language = "";
	private String userEmail = "";
	private String introduction = "";
	private String sign = "";
	
	public Configuration(String language, String userEmail, String introduction, String sign) {
		this.language = language;
		this.userEmail = userEmail;
		this.introduction = introduction;
		this.sign = sign;
	}
	
	/**
	 * Método que introduce en un JSONObject con formato JSON el objeto inicial
	 * original entero, es decir. La configuración va dentro de un JSONObject en el
	 * mismo orden en como se tratan en el propio objeto
	 * 
	 * @return JSONObject
	 */
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(LANGUAGE, this.language);
		jsonObject.put(USER_EMAIL, this.userEmail);
		jsonObject.put(INTRODUCTION, this.introduction);
		jsonObject.put(SIGN, this.sign);

		return jsonObject;
	}
	
	

}
