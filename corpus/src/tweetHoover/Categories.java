package tweetHoover;

public enum Categories {

	// Objets directement construits
	HOMOPHOBE("Homophobe"), PASDROLE("PasDrole"), CONTREPETRIE("Contrepetrie"), DROLE("Drole");

	private String name = "";

	// Constructeur
	Categories(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
