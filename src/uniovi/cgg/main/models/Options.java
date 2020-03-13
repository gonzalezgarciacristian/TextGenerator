package uniovi.cgg.main.models;

public class Options {
	
	private long id;
	
	private String name;
	private String introduction;
	private String conclusions;
	
	private double probability;
	
	private Options[] options;
	
	private long[] dependencies;

	public Options(long id, String name, String introduction, String conclusions, double probability, Options[] options,
			long[] dependencies) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.conclusions = conclusions;
		this.probability = probability;
		this.options = options;
		this.dependencies = dependencies;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getConclusions() {
		return conclusions;
	}

	public void setConclusions(String conclusions) {
		this.conclusions = conclusions;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public Options[] getOptions() {
		return options;
	}

	public void setOptions(Options[] options) {
		this.options = options;
	}

	public long[] getDependencies() {
		return dependencies;
	}

	public void setDependencies(long[] dependencies) {
		this.dependencies = dependencies;
	}

}
