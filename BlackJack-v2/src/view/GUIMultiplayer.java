package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Actions;
import controller.Game;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIMultiplayer extends Stage {

	public Button shuffleBut = new Button("Shuffle");
	public Button startBut = new Button("Start Game");
	public Button getBut = new Button("Get Card");
	public Button distBut = new Button("Distribute Cards");
	public Button restartRoundBut = new Button("Restart Round");
	public Button continue1But = new Button("Continue");
	public Button stop1But = new Button("Stop");
	public Button continue2But = new Button("Continue");
	public Button stop2But = new Button("Stop");

	Label dLabel = new Label("DEALER");
	Label p1Label = new Label("PLAYER 1");
	Label p2Label = new Label("PLAYER 2");
	public Label roundLabel = new Label("DISTRIBUTE CARDS");

	public Label scoreLabel = new Label("SCORE 10 POINTS TO WIN!");
	Label p1ScoreLabel = new Label("Player 1:");
	public Label p1ScoreValue = new Label("0");
	Label p2ScoreLabel = new Label("Player 2:");
	public Label p2ScoreValue = new Label("0");

	Label messageReady = new Label("The deck is ready and presents all 52 cards from a normal deck.");
	Label messageShuffle = new Label("Please press the button below to Shuffle the cards.");
	public Label messageStart = new Label("Great! You can Start the game now or Shuffle again if wanted.");

	MenuBar menuBar = new MenuBar();
	public MenuItem menuRestart = new MenuItem("Restart Game");
	public MenuItem menuReturn = new MenuItem("Return Menu");
	public MenuItem menuExit = new MenuItem("Exit");
	Menu menuOptions = new Menu("Options");

	Actions act = new Actions();

	GridPane geralPanel = new GridPane();

	public GridPane gameMainPanel = new GridPane();

	public StackPane deck = new StackPane();
	public HBox deckPanel = new HBox();

	HBox buttonPanel = new HBox(5);

	VBox scorePanel = new VBox(5);
	HBox messageScorePanel = new HBox();
	HBox p1ScorePanel = new HBox(10);
	HBox p2ScorePanel = new HBox(10);

	public HBox zeroLine = new HBox();

	VBox firstBox = new VBox(10);
	public HBox firstLine = new HBox(10);
	public HBox cards1 = new HBox(10);
	HBox act1Buttons = new HBox(10);

	VBox secondBox = new VBox(10);
	public HBox secondLine = new HBox(10);
	public HBox cards2 = new HBox(10);
	HBox act2Buttons = new HBox(10);

	VBox dBox = new VBox(10);
	public HBox dLine = new HBox(10);
	public HBox cardsD = new HBox(10);

	public GUIMultiplayer() throws IOException {
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Multiplayer Game");
		this.setOnCloseRequest(e -> System.exit(0));
		this.setMaximized(true);

		geralPanel.setAlignment(Pos.CENTER);
		geralPanel.setHgap(10);
		geralPanel.setVgap(30);
		geralPanel.setPadding(new Insets(25));
		geralPanel.setId("Pane");

		deck = act.createDeckImg(Game.sprites.get(52));
		deck.setMinSize(200, 350);
		deckPanel.getChildren().add(deck);
		deckPanel.setAlignment(Pos.CENTER);

		menuOptions.setId("Menu");
		menuOptions.getItems().add(menuRestart);
		menuOptions.getItems().add(menuReturn);
		menuOptions.getItems().add(menuExit);
		menuBar.getMenus().add(menuOptions);

		startBut.setDisable(true);

		messageStart.setVisible(false);

		buttonPanel.setAlignment(Pos.CENTER);
		buttonPanel.getChildren().add(startBut);
		buttonPanel.getChildren().add(shuffleBut);

		zeroLine.getChildren().add(messageReady);
		zeroLine.setAlignment(Pos.CENTER);

		firstLine.getChildren().add(messageShuffle);
		firstLine.setAlignment(Pos.CENTER);

		secondLine.getChildren().add(messageStart);
		secondLine.setAlignment(Pos.CENTER);

		geralPanel.add(zeroLine, 0, 0);
		geralPanel.add(deckPanel, 0, 1);
		geralPanel.add(firstLine, 0, 2);
		geralPanel.add(secondLine, 0, 3);
		geralPanel.add(buttonPanel, 0, 4);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight()-90;

		geralPanel.setMinSize(width,height);
//		geralPanel.setGridLinesVisible(true);

		Scene scene = new Scene(new VBox());
		((VBox)scene.getRoot()).getChildren().add(menuBar);
		((VBox)scene.getRoot()).getChildren().add(geralPanel);
        this.setScene(scene);

        scene.getStylesheets().add
        (GUIMenu.class.getResource("GUIMultiplayer.css").toExternalForm());

        BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("imgs/Nut.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.getIcons().add(SwingFXUtils.toFXImage(icon, null));
	}

	public void initGameGUI() {
		geralPanel.getChildren().clear();
		zeroLine.getChildren().clear();
		firstLine.getChildren().clear();
		buttonPanel.getChildren().clear();
		secondLine.getChildren().clear();

		scorePanel.setAlignment(Pos.CENTER);
		scorePanel.setId("Pane");
		scorePanel.setPadding(new Insets(25));
		messageScorePanel.setAlignment(Pos.CENTER);
		p1ScorePanel.setAlignment(Pos.CENTER);
		p2ScorePanel.setAlignment(Pos.CENTER);

		getBut.setDisable(true);
		restartRoundBut.setDisable(true);
		continue1But.setDisable(true);
		stop1But.setDisable(true);
		continue2But.setDisable(true);
		stop2But.setDisable(true);

		buttonPanel.getChildren().add(distBut);
		buttonPanel.getChildren().add(getBut);
		buttonPanel.getChildren().add(restartRoundBut);

		messageScorePanel.getChildren().add(scoreLabel);
		p1ScorePanel.getChildren().add(p1ScoreLabel);
		p1ScorePanel.getChildren().add(p1ScoreValue);
		p2ScorePanel.getChildren().add(p2ScoreLabel);
		p2ScorePanel.getChildren().add(p2ScoreValue);
		scorePanel.getChildren().add(messageScorePanel);
		scorePanel.getChildren().add(p1ScorePanel);
		scorePanel.getChildren().add(p2ScorePanel);

		gameMainPanel.setAlignment(Pos.CENTER);
		gameMainPanel.setHgap(30);
		gameMainPanel.setVgap(30);
		gameMainPanel.setPadding(new Insets(25));
		gameMainPanel.setId("Pane");

		zeroLine.getChildren().add(roundLabel);

		firstLine.getChildren().add(p1Label);
		firstLine.setAlignment(Pos.CENTER);
		secondLine.getChildren().add(p2Label);
		secondLine.setAlignment(Pos.CENTER);
		dLine.getChildren().add(dLabel);
		dLine.setAlignment(Pos.CENTER);

		act1Buttons.setAlignment(Pos.CENTER);
		act1Buttons.getChildren().add(continue1But);
		act1Buttons.getChildren().add(stop1But);
		act2Buttons.setAlignment(Pos.CENTER);
		act2Buttons.getChildren().add(continue2But);
		act2Buttons.getChildren().add(stop2But);

		dBox.setAlignment(Pos.TOP_CENTER);
		cardsD.setAlignment(Pos.CENTER);
		dBox.getChildren().add(dLine);
		dBox.getChildren().add(cardsD);
		firstBox.setAlignment(Pos.TOP_CENTER);
		cards1.setAlignment(Pos.CENTER);
		cards1.setMinHeight(123);
		firstBox.getChildren().add(firstLine);
		firstBox.getChildren().add(cards1);
		firstBox.getChildren().add(act1Buttons);
		secondBox.setAlignment(Pos.TOP_CENTER);
		cards2.setAlignment(Pos.CENTER);
		cards2.setMinHeight(123);
		secondBox.getChildren().add(secondLine);
		secondBox.getChildren().add(cards2);
		secondBox.getChildren().add(act2Buttons);

		gameMainPanel.getColumnConstraints().add(new ColumnConstraints(700));
		gameMainPanel.getRowConstraints().add(new RowConstraints(170));
		gameMainPanel.getColumnConstraints().add(new ColumnConstraints(700));
		gameMainPanel.getRowConstraints().add(new RowConstraints(170));

		gameMainPanel.add(deckPanel, 0,0);
		if (Game.gameMode == 0) {
			gameMainPanel.add(dBox, 1,0);
		}
		gameMainPanel.add(firstBox, 0,1);
		gameMainPanel.add(secondBox, 1,1);
		gameMainPanel.add(buttonPanel, 0,2, 2,1);

		geralPanel.add(scorePanel, 0,0);
		geralPanel.add(gameMainPanel, 0,1, 2,2);
		geralPanel.add(zeroLine, 1,0);

//		geralPanel.setGridLinesVisible(true);
//		gameMainPanel.setGridLinesVisible(true);

	}
}
