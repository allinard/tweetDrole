package tweetHoover;

public enum Categories {

	// Objets directement construits
	PASDROLE("PasDrole"), DROLE("Drole"), HOMOPHOBE("Homophobe"), CONTREPETRIE("Contrepetrie"), AUTODERISION("Autoderision"), RACISTE("Raciste"), HUMOURNOIR("HumourNoir"), XENOPHOBE("Xenophobe"), VULGAIRE("Vulgaire"), MYSOGYNE("Mysogyne");

	private String name = "";

	// Constructeur
	Categories(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
