package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.SHIP;
import view.ConfirmExit;
import view.ViewManager;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewManager manager = new ViewManager();
			primaryStage = manager.getMainStage();
			primaryStage.setTitle("SpaceRunner the Game");
			primaryStage.setResizable(false);
			//primaryStage.setFullScreen(true);
			primaryStage.setOnCloseRequest(x ->{
				x.consume();
				if(ConfirmExit.askConfirmation()) {
					Platform.exit();
				}
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
