package view;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SoundEffects;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

public class ViewManager {

	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private static final double MENU_BUTTON_START_X = 100;
	private static final double MENU_BUTTON_START_Y = 150;
	private static final String BUTTON_SFX = "file:src/model/resources/robotSFX.wav";

	AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	private SpaceRunnerSubScene creditsSubScene;
	private SpaceRunnerSubScene helpSubScene;
	private SpaceRunnerSubScene scoreSubScene;
	private SpaceRunnerSubScene shipChooserSubScene;

	private SpaceRunnerSubScene sceneToHide;

	List<SpaceRunnerButton> menuButtons;
	List<ShipPicker> shipsList;
	private SHIP chosenShip;

	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubScenes();
		createButtons();
		createBackground();
		createLogo();

	}

	private void showSubScene(SpaceRunnerSubScene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		subScene.moveSubScene();
		sceneToHide = subScene;
	}

	private void createSubScenes() {
		creditsSubScene = new SpaceRunnerSubScene();
		helpSubScene = new SpaceRunnerSubScene();
		scoreSubScene = new SpaceRunnerSubScene();
		shipChooserSubScene = new SpaceRunnerSubScene();

		//mainPane.getChildren().addAll(creditsSubScene, helpSubScene, scoreSubScene, shipChooserSubScene);

		createShipChooserSubScene();
		createScoreSubScene();
		createHelpSubScene();
		createCreditsSubScene();

	}
	
	private void createScoreSubScene() {
		scoreSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(scoreSubScene);
		InfoLabel score = new InfoLabel("<<<< Scores >>>>");
		score.setLayoutX(115);
		score.setLayoutY(20);
		VBox scoreContainer = new VBox();
		scoreContainer.setLayoutX(150);
		scoreContainer.setLayoutY(150);
		
		Label scoreHeading = new Label("     Name			Score   ");
		scoreHeading.setUnderline(true);
		Label score1 = new Label("Astronaught 1		  100");
		Label score2 = new Label("Astronaught 2		  100");
		Label score3 = new Label("Astronaught 3		  100");
		scoreHeading.setFont(Font.font("Verdana",20));
		score1.setFont(Font.font("Verdana",20));
		score2.setFont(Font.font("Verdana",20));
		score3.setFont(Font.font("Verdana",20));
		scoreContainer.setBackground(new Background(new BackgroundFill(Color.MEDIUMAQUAMARINE, new CornerRadii(20), new Insets(-20,-20,-20,-20))));
		scoreContainer.getChildren().addAll(scoreHeading, score1, score2, score3);
		
		scoreSubScene.getPane().getChildren().addAll(score, scoreContainer);//, score1, score2, score3);		
		
	}
	private void createHelpSubScene() {
		helpSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(helpSubScene);
		InfoLabel help = new InfoLabel("   <<<< Help >>>>");
		help.setLayoutX(150);
		help.setLayoutY(20);
		GridPane helpGrid = new GridPane();
		helpGrid.setLayoutX(100);
		helpGrid.setLayoutY(100);
		helpGrid.setHgap(20);
		helpGrid.setVgap(20);
		
		ImageView ship = new ImageView(new Image(SHIP.RED.getUrl(), 80, 80, true, false));
		ImageView meteor1 = new ImageView(), meteor2 = new ImageView();
		ImageView star = new ImageView(new Image(GameViewManager.GOLD_STAR, 20, 20, true, false));
		ImageView life = new ImageView(new Image(GameViewManager.LIFE, 20, 20, true, false));
		
		meteor1.setImage(new Image(GameViewManager.METEOR_BROWN, 80, 80, true, false));
		meteor2.setImage(new Image(GameViewManager.METEOR_GREY, 80, 80, true, false));
		
		Label shipHelp 	 = new Label("This is your ship. Choose colour from the \nPlay menu. Control it with arrow keys.");
		Label meteorHelp = new Label("These are enemy meteors.\nAvoid them or shoot them.\nMore \"smart\" enemies coming soon ...");
		Label starHelp   = new Label("The stars give you points,\nIF you can grab them!");
		Label lifeHelp   = new Label("This is extra life.\nGrab it to gain an extra ship\nif you have less than three ships.");
		
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				meteor1.setRotate(90+now/10000000l);
				meteor2.setRotate(180+now/10000000l);
				ship.setRotate(-now/10000000l);
			}
		};
		timer.start();
		/* gridpane:
		 * ___0_|__1_|__2_|_3_
		 * 0|___|____|____|__
		 * 1|___|____|____|__
		 * 2|___|____|____|__
		 * 3|___|____|____|___
		 */
		
		helpGrid.add(ship, 0, 0);
		helpGrid.add(shipHelp, 1, 0);
		helpGrid.add(meteor1, 0, 1);
		helpGrid.add(meteor2, 2, 1);
		helpGrid.add(meteorHelp, 1, 1);
		helpGrid.add(life, 0, 2);
		helpGrid.add(lifeHelp, 1, 2);
		helpGrid.add(star, 0, 3);
		helpGrid.add(starHelp, 1, 3);
		helpSubScene.getPane().getChildren().addAll(help, helpGrid);
		
	}
	private void createCreditsSubScene() {
		creditsSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		InfoLabel credits = new InfoLabel("  <<< Credits >>>");
		credits.setLayoutX(120);
		credits.setLayoutY(20);
		Label credit0 = new Label("Original programming by javacraving - youtube.");
		Label credit1 = new Label("Sounds and images from ");
		Label credit2 = new Label("Heavily modified by Kumar Dhakal");
		
		String[]link    = new String[6];
				link[0] = "https://www.youtube.com/playlist?list=PL4wcbt63yAbdtY-GOeuRjIePfUsukSJZ9";
				link[1] = "https://freesound.org/";
				link[2] = "http://soundbible.com/";
				link[3] = "https://www.freesoundslibrary.com/";
				link[4] = "https://kenney.nl/";
				link[5] = "https://dhakalkumar.github.io/";
				
		Hyperlink link0, link1, link2, link3, link4, link5;
		link0 = new Hyperlink(link[0]);
		link1 = new Hyperlink(link[1]);
		link2 = new Hyperlink(link[2]);
		link3 = new Hyperlink(link[3]);
		link4 = new Hyperlink(link[4]);
		link5 = new Hyperlink(link[5]);
				
		VBox creditsBox = new VBox(10, credit0,link0, credit1, link1, link2, link3, link4, credit2, link5);
		
		creditsBox.setLayoutX(50);
		creditsBox.setLayoutY(80);
		creditsSubScene.getPane().getChildren().addAll(credits, creditsBox);
		
		Application app = new Application() {@Override public void start(Stage primaryStage) throws Exception{}};
		HostServices services = app.getHostServices();
		
		link0.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[0]);
			}
		});
		link1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[1]);
			}
		});
		link2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[2]);
			}
		});
		link3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[3]);
			}
		});
		link4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[4]);
			}
		});
		link5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				services.showDocument(link[5]);
			}
		});
	}

	private void createShipChooserSubScene() {
		//shipChooserSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserSubScene);

		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(20);
		shipChooserSubScene.getPane().getChildren().addAll(chooseShipLabel, createShipsToChoose(),
				createButtonToStart());

	}

	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipsList = new ArrayList<>();
		for (SHIP ship : SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipsList.add(shipToPick);
			box.getChildren().add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					for (ShipPicker ship : shipsList) {
						ship.setIsCircleChosen(false);
					}
					shipToPick.setIsCircleChosen(true);
					chosenShip = shipToPick.getShip();
				}
			});
		}
		box.setLayoutX(300 - 118 * 2);
		box.setLayoutY(120);
		return box;
	}

	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("Start");
		startButton.setLayoutX(350);
		startButton.setLayoutY(320);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (chosenShip != null) {
					mainStage.hide();
					GameViewManager gameViewManagger = new GameViewManager();
					gameViewManagger.createNewGame(mainStage, chosenShip);
				}
			}
		});
		return startButton;
	}

	private void createButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}

	public Stage getMainStage() {
		return mainStage;
	}

	private void addMenuButtons(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);

	}

	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("Play");
		addMenuButtons(startButton);
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showSubScene(shipChooserSubScene);
			}
		});
	}

	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("Scores");
		addMenuButtons(scoresButton);
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showSubScene(scoreSubScene);
			}
		});
	}

	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("Help");
		addMenuButtons(helpButton);
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showSubScene(helpSubScene);
			}
		});
	}

	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("Credits");
		addMenuButtons(creditsButton);

		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showSubScene(creditsSubScene);
			}
		});
	}

	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("Exit");
		addMenuButtons(exitButton);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					SoundEffects.playSound(new URI(BUTTON_SFX));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.exit();
				// mainStage.close();
			}
		});
	}

	private void createBackground() {
		Image bgImage = new Image("view/resources/menubg.jpg", 256, 256, false, true);
		BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(1024, 800, false, false, false, false));
		mainPane.setBackground(new Background(bg));
	}

	private void createLogo() {
		Image logoImage = new Image("view/resources/space_runner.jpg", 500, 100, false, false);
		ImageView logo = new ImageView(logoImage);
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				logo.setEffect(new DropShadow(100, Color.YELLOW));
			}
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				logo.setEffect(null);
			}
		});
		mainPane.getChildren().add(logo);
	}
}
