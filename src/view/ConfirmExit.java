package view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ConfirmExit {
	public static boolean askConfirmation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Not enjoying the game?");
		alert.setContentText("Are you sure you want to quit?");
		if (alert.showAndWait() != Optional.of(ButtonType.CANCEL)) {
			return false;
		} 
		return true;
	}
}
