package uniovi.cgg.main;

public class Company {
	
	private String name;
	private String type;
	private String description;

	public Company(String name, String type, String description) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

}
