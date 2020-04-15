package uniovi.cgg.logic.models;

import org.json.simple.JSONObject;

public class Configuration {
	
	private static final String FROM = "FROM";
	private static final String USER_EMAIL = "USER_EMAIL";
	private static final String SMTP_SERVER = "SMTP_SERVER";	
	private static final String TITLE = "TITLE";
	private static final String INTRODUCTION = "INTRODUCTION";
	private static final String SIGN = "SIGN";
	
	private String from = "";
	private String userEmail = "";
	private String SMTPServer = "";
	private String title = "";
	private String introduction = "";
	private String sign = "";
	
	/**
	 * Constructor por defecto
	 * @param language
	 * @param userEmail
	 * @param introduction
	 * @param sign
	 */
	public Configuration(String from, String userEmail, String SMTPServer, String title, String introduction, String sign) {
		this.from = from;
		this.userEmail = userEmail;
		this.SMTPServer = SMTPServer;
		this.title = title;
		this.introduction = introduction;
		this.sign = sign;
	}
	
	/**
	 * Constructor pasándole un JSONObject, que es el formato en el que se almacena en ficharo
	 * @param json
	 */
	public Configuration(JSONObject json) {
		this.from = (String) json.get(FROM);
		this.userEmail = (String) json.get(USER_EMAIL);
		this.SMTPServer = (String) json.get(SMTP_SERVER);
		this.title = (String) json.get(TITLE);
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

		jsonObject.put(FROM, this.from);
		jsonObject.put(USER_EMAIL, this.userEmail);
		jsonObject.put(SMTP_SERVER, this.SMTPServer);
		jsonObject.put(TITLE, this.title);
		jsonObject.put(INTRODUCTION, this.introduction);
		jsonObject.put(SIGN, this.sign);

		return jsonObject;
	}

	public String getFrom() {
		return from;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public String getSMTPServer() {
		return SMTPServer;
	}

	public String getTitle() {
		return title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public String getSign() {
		return sign;
	}
	
}
