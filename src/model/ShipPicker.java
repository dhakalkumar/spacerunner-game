package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {

	private ImageView circleImage;
	private ImageView shipImage;
	
	private String circleNotChosen = "file:model/resources/grey_cirlce.png";
	//private String circleChosen = "model/resources/yellow_boxTick.png";
	private String circleChosen = "model/resources/missile.png";
	
	private SHIP ship;
	private boolean isCircleChosen;
	
	public ShipPicker(SHIP ship) {
		circleImage = new ImageView(new Image(circleNotChosen));
		shipImage   = new ImageView(ship.getUrl());
		this.ship = ship;
		isCircleChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(shipImage);			
	}
	
	public SHIP getShip() {
		return ship;
	}
	
	public boolean isCircleChosen() {
		return isCircleChosen;
	}
	
	public void setIsCircleChosen(boolean isCircleChosen) {
		this.isCircleChosen = isCircleChosen;
		String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
		circleImage.setImage(new Image(imageToSet));
	}
}
