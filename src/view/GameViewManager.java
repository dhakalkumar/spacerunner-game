package view;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;
import model.SoundEffects;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private ImageView ship;

	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private boolean isSpacePressed;
	private boolean isBulletFired = false;
	private int angle;

	private GridPane gridPane1, gridPane2;
	private static final String BG_IMAGE = "view/resources/purple.png";
	public static final String METEOR_BROWN = "view/resources/index1.png";
	public static final String METEOR_GREY = "view/resources/index2.png";
	private static final String BULLET = "view/resources/missile.png";
	private AnimationTimer gameTimer;

	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 700;

	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	Random randomPosGen;

	private ImageView star;
	private ImageView life;
	private ImageView bullet;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife = 3;
	private int points;
	public static final String GOLD_STAR = "view/resources/star_gold.png";
	public static final String LIFE = "view/resources/heart.png";
	private static final String BUTTON_SFX = "file:src/model/resources/robotSFX.wav";
	private static final String LIFE_LOST = "file:src/model/resources/life-lost.wav";
	private static final String LIFE_GAIN = "file:src/model/resources/life-gain.wav";

	private static final int STAR_RADIUS = 12;
	private static final int SHIP_RADIUS = 27;
	private static final int METEOR_RADIUS = 40;

	public GameViewManager() {
		randomPosGen = new Random();
		initializeStage();
		createKeysListeners();
	}

	private void createKeysListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
				if (event.getCode() == KeyCode.SPACE) {
					isSpacePressed = true;
					isBulletFired = true;
				}
			}
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
				if (event.getCode() == KeyCode.SPACE) {
					isSpacePressed = false;
					isBulletFired = false;
				}

			}
		});

	}

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setTitle("Space Runner");
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		gameStage.setOnCloseRequest(x -> {
			x.consume();
			// if(ConfirmExit.askConfirmation()) {
			// Platform.exit();
			gameStage.close();
			menuStage.show();
			// }
		});
	}

	public void createNewGame(Stage menuStage, SHIP chosenShip) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBG();
		createShip(chosenShip);
		createGameElements(chosenShip);
		createGameLoop();
		gameStage.show();

	}

	private void createGameElements(SHIP chosenShip) {
		// playerLife = 2;
		star = new ImageView(GOLD_STAR);
		setNewElementPos(star);

		life = new ImageView(LIFE);
		setNewElementPos(life);

		pointsLabel = new SmallInfoLabel("Points:  00");
		pointsLabel.setLayoutX(460);
		// pointsLabel.setLayoutY(10);
		gamePane.getChildren().addAll(star, life, pointsLabel);

		playerLifes = new ImageView[playerLife];
		for (int i = 0; i < playerLife; i++) {
			playerLifes[i] = new ImageView(chosenShip.getUrlLife());
			playerLifes[i].setLayoutX(455 + (i * 40));
			playerLifes[i].setLayoutY(50);
			gamePane.getChildren().add(playerLifes[i]);
		}

		brownMeteors = new ImageView[3];
		for (int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i] = new ImageView(new Image(METEOR_BROWN, 60*i, 200-i*10, true, false));
			setNewElementPos(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		greyMeteors = new ImageView[3];
		for (int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i] = new ImageView(new Image(METEOR_GREY, 50+i*20, 100+i*10, true, false));
			setNewElementPos(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		}
	}

	private void setNewElementPos(ImageView image) {
		image.setLayoutX(randomPosGen.nextInt(GAME_WIDTH));
		image.setLayoutY(-(randomPosGen.nextInt(3200) + 600));
	}

	private void moveElements() {
		star.setLayoutY(star.getLayoutY() + 5);
		life.setLayoutY(life.getLayoutY() + 5);
//		if (isBulletFired) {
//			//System.out.println("isBulletFired: "+isBulletFired);
//			System.out.println("before moving bullet: " + bullet.getLayoutY());
//			bullet.setLayoutY(bullet.getLayoutY() - 5);
//			System.out.println("After moving bullet: " + bullet.getLayoutY());
//			//bullet.relocate(bullet.getLayoutX(), bullet.getLayoutY() - 5);
//			isBulletFired = false;
//			//System.out.println("isBulletFired: "+isBulletFired);
//		}

		for (int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 7);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4 * i);
		}
		for (int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 7);
			greyMeteors[i].setRotate(greyMeteors[i].getRotate() + 5 + i);

		}
	}

	private void checkElementsPos() {
		if (star.getLayoutY() > GAME_HEIGHT) {
			setNewElementPos(star);
		}
		if (life.getLayoutY() > GAME_HEIGHT) {
			setNewElementPos(life);
		}

		for (int i = 0; i < brownMeteors.length; i++) {
			if (brownMeteors[i].getLayoutY() > GAME_HEIGHT) {
				setNewElementPos(brownMeteors[i]);
			}
		}
		for (int i = 0; i < greyMeteors.length; i++) {
			if (greyMeteors[i].getLayoutY() > GAME_HEIGHT) {
				setNewElementPos(greyMeteors[i]);
			}
		}
	}

	private void createShip(SHIP chosenShip) {
		ship = new ImageView(chosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH / 2 - 50);
		ship.setLayoutY(GAME_HEIGHT - 200);
		gamePane.getChildren().add(ship);
	}

	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				moveBG();
				moveElements();
				fireBullet();
				moveBullet();
				checkElementsPos();
				collisionDetection();
				moveShip();
			}
		};
		gameTimer.start();
	}

	private void fireBullet() {
		if (isSpacePressed) {
			bullet = new ImageView(BULLET);
			bullet.setLayoutX(ship.getLayoutX() + 38);
			bullet.setLayoutY(ship.getLayoutY() - 45);

			gamePane.getChildren().add(bullet);
			isBulletFired = true;
		}
	}
	private void moveBullet() {
		if (isBulletFired) {
			System.out.println("bullet fired at " + bullet.getY() + " now moving it");
			//bullet.setLayoutY(bullet.getLayoutY() - 5);
			bullet.setY(bullet.getY() - 5);
			System.out.println("bullet is at: " + bullet.getLayoutY());
			//isBulletFired = false;
			//bullet = new ImageView(BULLET);
		}
	}

	private void moveShip() {
		if (isLeftKeyPressed && !isRightKeyPressed) {
			if (angle > -30) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if (ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX() - 3);
			}
		}
		if (!isLeftKeyPressed && isRightKeyPressed) {
			if (angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if (ship.getLayoutX() < 522) {
				ship.setLayoutX(ship.getLayoutX() + 3);
			}
		}
		if (isLeftKeyPressed && isRightKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
		}
		if (!isLeftKeyPressed && !isRightKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
		}
	}

	private void createBG() {
//		Image bgImage =  new Image("view/resources/game_bg.jpg", 256, 256, false, true);
//		BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(1024, 800, false, false, false, false));
//		gamePane.setBackground(new Background(bg));
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
		for (int i = 0; i < 12; i++) {
			ImageView bgImage1 = new ImageView(BG_IMAGE);
			ImageView bgImage2 = new ImageView(BG_IMAGE);
			GridPane.setConstraints(bgImage1, i % 3, i / 3);
			GridPane.setConstraints(bgImage2, i % 3, i / 3);
			gridPane1.getChildren().add(bgImage1);
			gridPane2.getChildren().add(bgImage2);
		}
		gridPane2.setLayoutY(-1024);

		gamePane.getChildren().addAll(gridPane1, gridPane2);

	}

	private void moveBG() {
		gridPane1.setLayoutY(gridPane1.getLayoutY() + 5);
		gridPane2.setLayoutY(gridPane2.getLayoutY() + 5);
		if (gridPane1.getLayoutY() >= 1024) {
			gridPane1.setLayoutY(-1024);
		}
		if (gridPane2.getLayoutY() >= 1024) {
			gridPane2.setLayoutY(-1024);
		}
	}

	private void collisionDetection() {
		//collision detection with star
		if (SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX() + 49, ship.getLayoutY() + 49,
				star.getLayoutX() + 37, star.getLayoutY() + 15)) {
			setNewElementPos(star);
			points += 2;
			String textToSet = "Points: ";
			if (points < 10) {
				textToSet += "0";
			}
			pointsLabel.setText(textToSet + points);
		}
		// collision detection with life
		if (SHIP_RADIUS + 12 > calculateDistance(ship.getLayoutX() + 49, ship.getLayoutY() + 49, life.getLayoutX() + 37,
				life.getLayoutY() + 15)) {
			setNewElementPos(life);
			points += 5;
			String textToSet = "Points: ";
			if (points < 10) {
				textToSet += "0";
			}
			pointsLabel.setText(textToSet + points);
			addLife();

		}
		// collision detection with meteors
		for (int i = 0; i < brownMeteors.length; i++) {
			if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX() + 49, ship.getLayoutY() + 37,
					brownMeteors[i].getLayoutX() + 20, brownMeteors[i].getLayoutY() + 20)) {

				removeLife();
				setNewElementPos(brownMeteors[i]);
			}
		}
		for (int i = 0; i < brownMeteors.length; i++) {
			if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX() + 49, ship.getLayoutY() + 37,
					greyMeteors[i].getLayoutX() + 20, greyMeteors[i].getLayoutY() + 20)) {

				removeLife();
				setNewElementPos(greyMeteors[i]);
			}
		}
	}

	private void removeLife() {
		
		gamePane.getChildren().remove(playerLifes[playerLife-1]);
		try {
			SoundEffects.playSound(new URI(LIFE_LOST));
		} catch (URISyntaxException e) {
			System.out.println("Error: Life lost sound could not be played!");
			e.printStackTrace();
		}
		playerLife--;
		if (playerLife <= 0) {
			gameTimer.stop();
			// TODO show a message that says the game is over
			gameStage.close();
			menuStage.show();

		}
	}

	private void addLife() {
		if (playerLife < 3) {
			gamePane.getChildren().add(playerLifes[playerLife]);
			playerLife++;
		}
		try {
			SoundEffects.playSound(new URI(LIFE_GAIN));
		} catch (URISyntaxException e) {
			System.out.println("Error: Life gain sound could not be played!");
			e.printStackTrace();
		}

	}

	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
