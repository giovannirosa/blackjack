package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Blackjack;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUISettings extends Stage {

	Label gameMode = new Label("Game Mode:");
	Label aceValue = new Label("Ace Value:");
	Label scoreLimit = new Label("Score Limit:");
	Label reshuffleMode = new Label("Re-shuffle Mode:");

	RadioButton agDealer = new RadioButton("Against Dealer");
	RadioButton agPlayer = new RadioButton("Against Player");

	RadioButton ace1 = new RadioButton("1");
	RadioButton ace11 = new RadioButton("11");

	RadioButton everyHand = new RadioButton("Every Hand");
	RadioButton every2Hands = new RadioButton("Every 2 Hands");
	RadioButton never = new RadioButton("Never");

	Slider scoreSlid = new Slider(5,15,10);

	public Button saveBut = new Button("Save Changes");
	public Button restoreBut = new Button("Restore Default");

	GridPane geralPanel = new GridPane();

	HBox buttonPanel = new HBox(5);

	public VBox gamePanel = new VBox(10);
	public VBox acePanel = new VBox(10);
	public VBox scorePanel = new VBox(10);
	public VBox reshufflePanel = new VBox(10);

	public HBox gameLine = new HBox(10);
	public HBox gameOpt = new HBox(10);

	public HBox aceLine = new HBox(10);
	public HBox aceOpt = new HBox(10);

	public HBox scoreLine = new HBox(10);
	public HBox scoreOpt = new HBox(10);

	public HBox reshuffleLine = new HBox(10);
	public HBox reshuffleOpt = new HBox(10);


	public GUISettings() throws IOException {
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Customize");
		this.setResizable(false);

		geralPanel.setAlignment(Pos.CENTER);
		geralPanel.setHgap(10);
		geralPanel.setVgap(30);
		geralPanel.setPadding(new Insets(25, 25, 25, 25));
		geralPanel.setId("Pane");
		gamePanel.setAlignment(Pos.CENTER);
		gamePanel.setId("Pane");
		gamePanel.setPadding(new Insets(10));
		gameLine.setAlignment(Pos.CENTER);
		gameOpt.setAlignment(Pos.CENTER);
		acePanel.setAlignment(Pos.CENTER);
		acePanel.setId("Pane");
		acePanel.setPadding(new Insets(10));
		aceLine.setAlignment(Pos.CENTER);
		aceOpt.setAlignment(Pos.CENTER);
		scorePanel.setAlignment(Pos.CENTER);
		scorePanel.setId("Pane");
		scorePanel.setPadding(new Insets(10));
		scoreLine.setAlignment(Pos.CENTER);
		scoreOpt.setAlignment(Pos.CENTER);
		reshufflePanel.setAlignment(Pos.CENTER);
		reshufflePanel.setId("Pane");
		reshufflePanel.setPadding(new Insets(10));
		reshuffleLine.setAlignment(Pos.CENTER);
		reshuffleOpt.setAlignment(Pos.CENTER);
		buttonPanel.setAlignment(Pos.CENTER);

		ToggleGroup gameGroup = new ToggleGroup();
		agDealer.setToggleGroup(gameGroup);
		agPlayer.setToggleGroup(gameGroup);
		agDealer.setSelected(true);

		ToggleGroup aceGroup = new ToggleGroup();
		ace1.setToggleGroup(aceGroup);
		ace11.setToggleGroup(aceGroup);
		ace1.setSelected(true);

		ToggleGroup reshuffleGroup = new ToggleGroup();
		everyHand.setToggleGroup(reshuffleGroup);
		every2Hands.setToggleGroup(reshuffleGroup);
		never.setToggleGroup(reshuffleGroup);
		everyHand.setSelected(true);

		scoreSlid.setMinorTickCount(0);
		scoreSlid.setMajorTickUnit(5);
		scoreSlid.setShowTickMarks(true);
		scoreSlid.setShowTickLabels(true);
		scoreSlid.setSnapToTicks(true);
		scoreSlid.setBlockIncrement(1);

		gameLine.getChildren().add(gameMode);
		aceLine.getChildren().add(aceValue);
		scoreLine.getChildren().add(scoreLimit);
		reshuffleLine.getChildren().add(reshuffleMode);

		gameOpt.getChildren().add(agDealer);
		gameOpt.getChildren().add(agPlayer);

		aceOpt.getChildren().add(ace1);
		aceOpt.getChildren().add(ace11);

		scoreOpt.getChildren().add(scoreSlid);

		reshuffleOpt.getChildren().add(everyHand);
		reshuffleOpt.getChildren().add(every2Hands);
		reshuffleOpt.getChildren().add(never);

		gamePanel.getChildren().add(gameLine);
		gamePanel.getChildren().add(gameOpt);

		acePanel.getChildren().add(aceLine);
		acePanel.getChildren().add(aceOpt);

		scorePanel.getChildren().add(scoreLine);
		scorePanel.getChildren().add(scoreOpt);

		reshufflePanel.getChildren().add(reshuffleLine);
		reshufflePanel.getChildren().add(reshuffleOpt);

		buttonPanel.getChildren().add(saveBut);
		buttonPanel.getChildren().add(restoreBut);

		geralPanel.add(gamePanel, 0, 0);
		geralPanel.add(acePanel, 0, 1);
		geralPanel.add(scorePanel, 0, 2);
		geralPanel.add(reshufflePanel, 0, 3);
		geralPanel.add(buttonPanel, 0, 4);

//		geralPanel.setGridLinesVisible(true);
		
		Scene scene = new Scene(geralPanel, 400, Blackjack.height-90);
        this.setScene(scene);

        scene.getStylesheets().add
        (GUIMenu.class.getResource("GUISettings.css").toExternalForm());

        BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("imgs/Config.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.getIcons().add(SwingFXUtils.toFXImage(icon, null));
	}

	public void resetAll() {
		agDealer.setSelected(true);
		ace1.setSelected(true);
		everyHand.setSelected(true);
		scoreSlid.setValue(10);
	}

	public String getGameMode() {
		if (agDealer.isSelected()) {
			return "Normal";
		} else {
			return "Versus";
		}
	}

	public int getAceValue() {
		if (ace1.isSelected()) {
			return 1;
		} else {
			return 11;
		}
	}

	public int getScoreLimit() {
		return (int) scoreSlid.getValue();
	}

	public int getReshuffle() {
		if (everyHand.isSelected()) {
			return 1;
		} else if (every2Hands.isSelected()) {
			return 2;
		} else {
			return 0;
		}
	}
}
