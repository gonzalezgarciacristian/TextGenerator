package uniovi.cgg.logic.models;

import org.json.simple.JSONObject;

public class Configuration {
	
	private static final String USER_EMAIL = "USER_EMAIL";
	private static final String INTRODUCTION = "INTRODUCTION";
	private static final String SIGN = "SIGN";
	
	private String userEmail = "";
	private String introduction = "";
	private String sign = "";
	
	/**
	 * Constructor por defecto
	 * @param language
	 * @param userEmail
	 * @param introduction
	 * @param sign
	 */
	public Configuration(String userEmail, String introduction, String sign) {
		this.userEmail = userEmail;
		this.introduction = introduction;
		this.sign = sign;
	}
	
	/**
	 * Constructor pasándole un JSONObject, que es el formato en el que se almacena en ficharo
	 * @param json
	 */
	public Configuration(JSONObject json) {
		this.userEmail = (String) json.get(USER_EMAIL);
		this.introduction = (String) json.get(INTRODUCTION);
		this.sign = (String) json.get(SIGN);
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

		jsonObject.put(USER_EMAIL, this.userEmail);
		jsonObject.put(INTRODUCTION, this.introduction);
		jsonObject.put(SIGN, this.sign);

		return jsonObject;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
