package webservices;

public class Damage {
	String position;
	String type;
	Double confidence;
	String closeup;
	
	public Damage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Damage(String position, String type, Double confidence, String closeup) {
		super();
		this.position = position;
		this.type = type;
		this.confidence = confidence;
		this.closeup = closeup;
	}
}
