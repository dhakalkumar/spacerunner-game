package model;

public enum SHIP {
	BLUE("view/resources/shipchooser/ships/ship_1.png", "view/resources/shipchooser/playerLife_blue.png"),
	GREEN("view/resources/shipchooser/ships/ship_2.png", "view/resources/shipchooser/playerLife_green.png"),
	ORANGE("view/resources/shipchooser/ships/ship_3.png", "view/resources/shipchooser/playerLife_orange.png"),
	RED("view/resources/shipchooser/ships/ship_4.png", "view/resources/shipchooser/playerLife_red.png");
	
	private String urlShip;
	private String urlLife;
	
	private SHIP(String urlShip, String urlLife) {
		this.urlShip = urlShip;
		this.urlLife = urlLife;
	}
	
	public String getUrl() {
		return urlShip;
	}
	
	public String getUrlLife() {
		return urlLife;
	}
}
