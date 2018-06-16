package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.Actions;
import controller.Blackjack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GUIGame extends GUIBasic {

	public Button shuffleBut = new Button("Shuffle");
	public Button startBut = new Button("Start Game");
	public Button startBut2 = new Button("Start Round");
	public Button distBut = new Button("Distribute Cards");
	public Button confirmBut = new Button("Confirm");
	public Button hit1But = new Button("Hit");
	public Button bet1But = new Button("Bet");
	public Button stand1But = new Button("Stand");
	public Button hit2But = new Button("Hit");
	public Button bet2But = new Button("Bet");
	public Button stand2But = new Button("Stand");

	Label dLabel = new Label("DEALER");

	Label p1Label = new Label("PLAYER 1");
	public VBox redChip1 = new VBox(10);
	public VBox blueChip1 = new VBox(10);
	public VBox greenChip1 = new VBox(10);
	public VBox brownChip1 = new VBox(10);

	Label p2Label = new Label("PLAYER 2");
	public VBox redChip2 = new VBox(10);
	public VBox blueChip2 = new VBox(10);
	public VBox greenChip2 = new VBox(10);
	public VBox brownChip2 = new VBox(10);

	public Label roundLabel = new Label("BET CHIPS!");

	Label messageReady = new Label("The deck is ready and presents all 52 cards from a normal deck.");
	Label messageShuffle = new Label("Please press the button below to Shuffle the cards.");
	public Label messageStart = new Label("Great! You can Start the game now or Shuffle again if wanted.");

	public GridPane gameMainPanel = new GridPane();

	public StackPane deck = new StackPane();

	HBox buttonPanel = new HBox(5);

	public HBox zeroLine = new HBox();

	VBox firstBox = new VBox(20);
	public HBox firstLine = new HBox(10);
	public StackPane cards1 = new StackPane();
	public Tooltip cards1Value = new Tooltip();
	public HBox chips1 = new HBox(5);
	HBox act1Buttons = new HBox(10);

	VBox secondBox = new VBox(20);
	public HBox secondLine = new HBox(10);
	public StackPane cards2 = new StackPane();
	public Tooltip cards2Value = new Tooltip();
	public HBox chips2 = new HBox(5);
	HBox act2Buttons = new HBox(10);

	VBox dBox = new VBox(10);
	public HBox dLine = new HBox(10);
	public StackPane cardsD = new StackPane();
	public Tooltip cardsDValue = new Tooltip();

	VBox betBox = new VBox(10);
	public Label betValue = new Label("0");
	public HBox betLine = new HBox(10);
	public HBox chipsLine = new HBox(5);
	public VBox chipsBut = new VBox(5);
	public VBox redChip = new VBox(10);
	public VBox blueChip = new VBox(10);
	public VBox greenChip = new VBox(10);
	public VBox brownChip = new VBox(10);

	public GUIGame() throws IOException {
		super("Multiplayer Game");
		initGUI();
	}

	private void initGUI() {
		deck = Blackjack.act.createDeckImg();
		deck.setMinSize(200, 350);
		deck.setAlignment(Pos.CENTER);

		mountBetBox();
		mountChipsBox();
		Blackjack.dealer.getChips().linkComponents(chipsLine);
		Blackjack.player1.getChips().linkComponents(chips1);
		if (Blackjack.set.getGameType().equals("Multi")) {
			Blackjack.player2.getChips().linkComponents(chips2);
		}

		startBut.setDisable(true);
		messageStart.setVisible(false);
		menuRestart.setDisable(true);
		menuTools.setDisable(true);

		buttonPanel.setAlignment(Pos.CENTER);
		buttonPanel.getChildren().addAll(startBut,shuffleBut);

		zeroLine.getChildren().add(messageReady);
		zeroLine.setAlignment(Pos.CENTER);

		firstLine.getChildren().add(messageShuffle);
		firstLine.setAlignment(Pos.CENTER);

		secondLine.getChildren().add(messageStart);
		secondLine.setAlignment(Pos.CENTER);

		geralPanel.add(zeroLine, 0, 0);
		geralPanel.add(deck, 0, 1);
		geralPanel.add(firstLine, 0, 2);
		geralPanel.add(secondLine, 0, 3);
		geralPanel.add(buttonPanel, 0, 4);

//		geralPanel.setGridLinesVisible(true);

//        messageReady.getStyleClass().add("bigger");//TODO
	}

	public void initGameGUI() {
		geralPanel.getChildren().clear();
		zeroLine.getChildren().clear();
		firstLine.getChildren().clear();
		buttonPanel.getChildren().clear();
		secondLine.getChildren().clear();

		configButtons();

		buttonPanel.getChildren().addAll(startBut2,distBut);

		gameMainPanel.setAlignment(Pos.CENTER);
		gameMainPanel.setHgap(30);
		gameMainPanel.setVgap(30);
		gameMainPanel.setPadding(new Insets(25));
//		gameMainPanel.setId("Pane");

		zeroLine.getChildren().add(roundLabel);

		mountPlayersBox();

		gameMainPanel.getColumnConstraints().add(new ColumnConstraints(Blackjack.width/4 + 50));
		gameMainPanel.getRowConstraints().add(new RowConstraints(200));
		gameMainPanel.getColumnConstraints().add(new ColumnConstraints(Blackjack.width/4));
		gameMainPanel.getRowConstraints().add(new RowConstraints(280));
		gameMainPanel.getColumnConstraints().add(new ColumnConstraints(Blackjack.width/4 + 50));
//		gameMainPanel.getRowConstraints().add(new RowConstraints(280));

		gameMainPanel.add(deck, 0,0);
		if (Blackjack.set.getGameType().equals("Multi")) {
			gameMainPanel.add(betBox, 1,0);
		} else {
			gameMainPanel.add(betBox, 2,1);
		}
		if (Blackjack.set.getGameMode().equals("Normal")) {
			gameMainPanel.add(dBox, 2,0);
		}
		gameMainPanel.add(firstBox, 0,1);
		if (Blackjack.set.getGameType().equals("Multi")) {
			gameMainPanel.add(secondBox, 2,1);
		}
		gameMainPanel.add(buttonPanel, 1,2);

		geralPanel.add(gameMainPanel, 0,1, 2,2);
		geralPanel.add(zeroLine, 1,0);

//		geralPanel.setGridLinesVisible(true);
//		gameMainPanel.setGridLinesVisible(true);
	}

	private void mountBetBox() {
		betLine.getChildren().addAll(new Label("Pot Value: "),betValue);
		betLine.setAlignment(Pos.CENTER);

		List<VBox> chipsNodes = new ArrayList<>();
		chipsNodes.add(redChip);
		chipsNodes.add(blueChip);
		chipsNodes.add(greenChip);
		chipsNodes.add(brownChip);

		List<String> chipsLabels = new ArrayList<>();
		chipsLabels.add(Integer.toString(Blackjack.dealer.getChips().getRedChips()));
		chipsLabels.add(Integer.toString(Blackjack.dealer.getChips().getBlueChips()));
		chipsLabels.add(Integer.toString(Blackjack.dealer.getChips().getGreenChips()));
		chipsLabels.add(Integer.toString(Blackjack.dealer.getChips().getBrownChips()));

		mountChipsContext(chipsNodes,chipsLabels,false);

		chipsLine.getChildren().addAll(redChip,blueChip,greenChip,brownChip);
		chipsLine.setAlignment(Pos.CENTER);

		HBox cbox = new HBox(confirmBut);
		cbox.setAlignment(Pos.CENTER);

		betBox.getChildren().addAll(betLine,chipsLine,confirmBut);
		betBox.setAlignment(Pos.CENTER);
//		betBox.setId("Pane");
	}

	private void mountChipsBox() {
		List<VBox> chipsNodes = new ArrayList<>();
		chipsNodes.add(redChip1);
		chipsNodes.add(blueChip1);
		chipsNodes.add(greenChip1);
		chipsNodes.add(brownChip1);
		if (Blackjack.set.getGameType().equals("Multi")) {
			chipsNodes.add(redChip2);
			chipsNodes.add(blueChip2);
			chipsNodes.add(greenChip2);
			chipsNodes.add(brownChip2);
		}

		List<String> chipsLabels = new ArrayList<>();
		chipsLabels.add(Integer.toString(Blackjack.player1.getChips().getRedChips()));
		chipsLabels.add(Integer.toString(Blackjack.player1.getChips().getBlueChips()));
		chipsLabels.add(Integer.toString(Blackjack.player1.getChips().getGreenChips()));
		chipsLabels.add(Integer.toString(Blackjack.player1.getChips().getBrownChips()));
		if (Blackjack.set.getGameType().equals("Multi")) {
			chipsLabels.add(Integer.toString(Blackjack.player2.getChips().getRedChips()));
			chipsLabels.add(Integer.toString(Blackjack.player2.getChips().getBlueChips()));
			chipsLabels.add(Integer.toString(Blackjack.player2.getChips().getGreenChips()));
			chipsLabels.add(Integer.toString(Blackjack.player2.getChips().getBrownChips()));
		}

		mountChipsContext(chipsNodes,chipsLabels,true);

		chips1.getChildren().addAll(redChip1,blueChip1,greenChip1,brownChip1);
		chips1.setAlignment(Pos.CENTER);
		if (Blackjack.set.getGameType().equals("Multi")) {
			chips2.getChildren().addAll(redChip2,blueChip2,greenChip2,brownChip2);
			chips2.setAlignment(Pos.CENTER);
		}
	}

	private void mountChipsContext(List<VBox> chipsNodes, List<String> chipsLabels, boolean players) {
		List<Button> chipsList = Blackjack.act.createChips();
		List<Button> chipsList2 = new ArrayList<Button>();
		if (Blackjack.set.getGameType().equals("Multi")) {
			chipsList2 = Blackjack.act.createChips();
		}

		for (int i = 0; i < 4; i++) {
			HBox cl = new HBox(new Label(chipsLabels.get(i)));
			cl.setAlignment(Pos.CENTER);
			cl.setId("ChipsLabels");
			chipsNodes.get(i).getChildren().addAll(chipsList.get(i),cl);
			if (Blackjack.set.getGameType().equals("Multi") && players) {
				cl = new HBox(new Label(chipsLabels.get(i+4)));
				cl.setAlignment(Pos.CENTER);
				cl.setId("ChipsLabels");
				chipsNodes.get(i+4).getChildren().addAll(chipsList2.get(i),cl);
			}
		}
	}

	private void configButtons() {
		distBut.setDisable(true);
		hit1But.setDisable(true);
		stand1But.setDisable(true);
		hit2But.setDisable(true);
		stand2But.setDisable(true);
		confirmBut.setDisable(true);
		menuRestart.setDisable(false);
		menuTools.setDisable(false);

		Tooltip redTp = new Tooltip("10");
		Actions.hackTooltipStartTiming(redTp);
		Tooltip blueTp = new Tooltip("25");
		Actions.hackTooltipStartTiming(blueTp);
		Tooltip greenTp = new Tooltip("50");
		Actions.hackTooltipStartTiming(greenTp);
		Tooltip brownTp = new Tooltip("100");
		Actions.hackTooltipStartTiming(brownTp);

		Blackjack.dealer.getChips().getRedChipBut().setDisable(true);
		Tooltip.install(Blackjack.dealer.getChips().getRedChipBut(), redTp);
		Blackjack.dealer.getChips().getBlueChipBut().setDisable(true);
		Tooltip.install(Blackjack.dealer.getChips().getBlueChipBut(), blueTp);
		Blackjack.dealer.getChips().getGreenChipBut().setDisable(true);
		Tooltip.install(Blackjack.dealer.getChips().getGreenChipBut(), greenTp);
		Blackjack.dealer.getChips().getBrownChipBut().setDisable(true);
		Tooltip.install(Blackjack.dealer.getChips().getBrownChipBut(), brownTp);

		Blackjack.player1.getChips().getRedChipBut().setDisable(true);
		Tooltip.install(Blackjack.player1.getChips().getRedChipBut(), redTp);
		Blackjack.player1.getChips().getBlueChipBut().setDisable(true);
		Tooltip.install(Blackjack.player1.getChips().getBlueChipBut(), blueTp);
		Blackjack.player1.getChips().getGreenChipBut().setDisable(true);
		Tooltip.install(Blackjack.player1.getChips().getGreenChipBut(), greenTp);
		Blackjack.player1.getChips().getBrownChipBut().setDisable(true);
		Tooltip.install(Blackjack.player1.getChips().getBrownChipBut(), brownTp);

		if (Blackjack.set.getGameType().equals("Multi")) {
			Blackjack.player2.getChips().getRedChipBut().setDisable(true);
			Tooltip.install(Blackjack.player2.getChips().getRedChipBut(), redTp);
			Blackjack.player2.getChips().getBlueChipBut().setDisable(true);
			Tooltip.install(Blackjack.player2.getChips().getBlueChipBut(), blueTp);
			Blackjack.player2.getChips().getGreenChipBut().setDisable(true);
			Tooltip.install(Blackjack.player2.getChips().getGreenChipBut(), greenTp);
			Blackjack.player2.getChips().getBrownChipBut().setDisable(true);
			Tooltip.install(Blackjack.player2.getChips().getBrownChipBut(), brownTp);
		}
	}

	private void mountPlayersBox() {
		firstLine.getChildren().add(p1Label);
		firstLine.setAlignment(Pos.CENTER);
		secondLine.getChildren().add(p2Label);
		secondLine.setAlignment(Pos.CENTER);
		dLine.getChildren().add(dLabel);
		dLine.setAlignment(Pos.CENTER);

		act1Buttons.setAlignment(Pos.CENTER);
		act1Buttons.getChildren().add(hit1But);
		act1Buttons.getChildren().add(stand1But);
		act2Buttons.setAlignment(Pos.CENTER);
		act2Buttons.getChildren().add(hit2But);
		act2Buttons.getChildren().add(stand2But);

		setTooltips();

		dBox.setAlignment(Pos.TOP_CENTER);
		cardsD.setAlignment(Pos.CENTER);
		dBox.getChildren().add(dLine);
		dBox.getChildren().add(cardsD);
		firstBox.setAlignment(Pos.TOP_CENTER);
		cards1.setAlignment(Pos.CENTER);
		cards1.setMinHeight(123);
		firstBox.getChildren().add(firstLine);
		firstBox.getChildren().add(cards1);
		firstBox.getChildren().add(chips1);
		firstBox.getChildren().add(act1Buttons);
		secondBox.setAlignment(Pos.TOP_CENTER);
		cards2.setAlignment(Pos.CENTER);
		cards2.setMinHeight(123);
		secondBox.getChildren().add(secondLine);
		secondBox.getChildren().add(cards2);
		secondBox.getChildren().add(chips2);
		secondBox.getChildren().add(act2Buttons);
	}

	private void setTooltips() {
		Actions.hackTooltipStartTiming(cardsDValue);
		Actions.hackTooltipStartTiming(cards1Value);
		Actions.hackTooltipStartTiming(cards2Value);

		cardsD.setOnMouseEntered(e -> {
			if (!Blackjack.dealer.isActive()) {
				Actions.rearrangeCards(cardsD,true);
			}
			if (cardsDValue.getText().equals("")) {
				Tooltip.uninstall(cardsD, cardsDValue);
			} else {
				Tooltip.install(cardsD, cardsDValue);
			}
		});
		cardsD.setOnMouseExited(e -> Actions.rearrangeCards(cardsD,false));

		cards1.setOnMouseEntered(e -> {
			Actions.rearrangeCards(cards1,true);
			if (cards1Value.getText().equals("")) {
				Tooltip.uninstall(cards1, cards1Value);
			} else {
				Tooltip.install(cards1, cards1Value);
			}
		});
		cards1.setOnMouseExited(e -> Actions.rearrangeCards(cards1,false));

		cards2.setOnMouseEntered(e -> {
			Actions.rearrangeCards(cards2,true);
			if (cards2Value.getText().equals("")) {
				Tooltip.uninstall(cards2, cards2Value);
			} else {
				Tooltip.install(cards2, cards2Value);
			}
		});
		cards2.setOnMouseExited(e -> Actions.rearrangeCards(cards2,false));
	}
}
