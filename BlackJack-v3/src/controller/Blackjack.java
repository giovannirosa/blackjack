package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Dealer;
import model.Deck;
import model.Game;
import model.Player;
import model.Settings;
import view.GUIGame;
import view.GUIMenu;
import view.GUISettings;
import view.GUIWin;

public class Blackjack extends Application {

	// VIEWS --------------------------------------------------------------------------------------
	GUIMenu MENU;
	GUIGame GAME;
	GUISettings SET = new GUISettings();
	GUIWin WIN = new GUIWin();
	
	// MODELS --------------------------------------------------------------------------------------
	public static Game game = new Game();
	public static Actions act = new Actions();
	public static Settings set = new Settings("Single","Normal",1);
	public static Dealer dealer = new Dealer(0,false,false,true);
	public static Player player1 = new Player("Player 1",0,false,false);
	public static Player player2 = new Player("Player 2",0,false,false);
	Deck deck = new Deck(51);

	

	public static double width;
	public static double height;

	// INITIALIZATION SECTION ---------------------------------------------------------------------

	/**
	 * Launch application.
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Starts the application, initiating all components and listeners.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		deck.setDeck(act.mountDeck());
		deck.setCardsMap(act.mountCardsMap(deck.getDeck()));
		MENU = new GUIMenu();
		setMenuListeners();
		MENU.show();
	}

	/**
	 * Exists just to handle Exception.
	 * @throws Exception
	 */
	public Blackjack() throws Exception {}

	/**
	 * Creates a new Game GUI and set related listeners.
	 */
	private void createAndCallGame() {
		try {
			GAME = new GUIGame();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setGameListeners();
		updateP1Value();
//		updateP2Value();
		GAME.show();
		MENU.hide();
		GAME.requestFocus();
	}

	// --------------------------------------------------------------------------------------------

	// LISTENERS SECTION --------------------------------------------------------------------------

	/**
	 * Set Menu and Settings listeners.
	 */
	private void setMenuListeners() {
		MENU.setOnCloseRequest(e -> windowExitAction(e));

		MENU.menuExit.setOnAction(e -> menuExitAction());

		MENU.menuRules.setOnAction(e -> {
			menuRulesAction();
			MENU.setIconified(true);
		});

		MENU.menuStrat.setOnAction(e -> {
			menuStratAction();
			MENU.setIconified(true);
		});

		MENU.spButton.setOnAction(e -> {
			set.setGameType("Single");
			createAndCallGame();
		});

		MENU.mpButton.setOnAction(e -> {
			set.setGameType("Multi");
			createAndCallGame();
		});

		MENU.customBut.setOnAction(e -> {
			if (SET.isShowing()) {
				SET.requestFocus();
			} else {
				SET.show();
			}
		});

		MENU.exitBut.setOnAction(e -> {
			System.exit(0);
		});

		SET.saveBut.setOnAction(e -> {
			set.setGameMode(SET.getGameMode());
			set.setReshuffle(SET.getReshuffle());
			SET.close();
		});

		SET.restoreBut.setOnAction(e -> {
			SET.resetAll();
		});
	}

	/**
	 * Set Game listeners.
	 */
	private void setGameListeners() {
		GAME.setOnCloseRequest(e -> windowExitAction(e));

		GAME.shuffleBut.setOnAction(e -> {
			act.shuffleDeck(deck.getDeck());
			for (String tmp:deck.getDeck()) {
				System.out.println(tmp);
			}
			Timeline timeline = act.shuffleAnimation(GAME.deck);
			timeline.play();
			timeline.setOnFinished(e1 -> {
				GAME.startBut.setDisable(false);
				GAME.messageStart.setVisible(true);
			});
		});

		GAME.startBut.setOnAction(e -> {
			GAME.initGameGUI();
		});

		GAME.startBut2.setOnAction(e -> {
			if (!checkGameFinal()) {
				resetRound();
				act.warningMessage("Please, set your bet clicking on the chips!\n\nThen Confirm!");

				activateChips();

				GAME.startBut2.setDisable(true);
				GAME.confirmBut.setDisable(false);
			}
		});

		if (!game.isRetMenu()) {
			setChipsListeners();
		}

		GAME.confirmBut.setOnAction(e -> {
			if (dealer.getChips().getRedChips()+dealer.getChips().getBlueChips()+
					dealer.getChips().getGreenChips()+dealer.getChips().getBrownChips()> 0) {
				deactivateChips();
				GAME.confirmBut.setDisable(true);
				GAME.distBut.setDisable(false);
				GAME.roundLabel.setText("DISTRIBUTE CARDS!");
			} else {
				act.warningMessage("No chips were bet!\n\nPlease bet 1 chip at least!");
			}
		});

		GAME.distBut.setOnAction(e -> {
			// distribute 2 cards each
			for (int i = 0; i < 2; i++) {
				distCards();
			}
			
			GAME.roundLabel.setText("PLAYER 1 TURN!");

			GAME.distBut.setDisable(true);
			if (!game.isRoundEnd()) {
				GAME.hit1But.setDisable(false);
				GAME.stand1But.setDisable(false);
			}
		});

		GAME.hit1But.setOnAction(e -> player1HitAction());

		GAME.hit2But.setOnAction(e -> player2HitAction());

		GAME.stand1But.setOnAction(e -> player1StopAction());

//		GAME.stand2But.setOnAction(e -> stopAction());

		GAME.menuRestart.setOnAction(e -> {
			Alert alert = act.confirmationMessage("", "Do you really want to restart game?\n\nThis will reset the chips!");

			if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
				resetChips();
				resetRound();
			} else {
			    alert.close();
			}
		});

		GAME.menuReturn.setOnAction(e -> {
			Alert alert = act.confirmationMessage("", "Do you really want to return to menu?\nYou'll lose any game progression!");

			if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
				GAME.close();
				try {
					reinitGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				MENU.show();
			} else {
			    alert.close();
			}
		});

		GAME.menuExit.setOnAction(e -> menuExitAction());

		GAME.menuRules.setOnAction(e -> {
			menuRulesAction();
			GAME.setIconified(true);
		});

		GAME.menuStrat.setOnAction(e -> {
			menuStratAction();
			GAME.setIconified(true);
		});
		
		GAME.deck.setOnMouseEntered(e -> {
			Actions.rearrangeDeck(GAME.deck,true);
		});
		
		GAME.deck.setOnMouseExited(e -> {
			Actions.rearrangeDeck(GAME.deck,false);
		});
	}

	private void menuExitAction() {
		Alert alert = act.confirmationMessage("", "Do you really want to exit?");

		if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
			System.exit(0);
		} else {
		    alert.hide();
		}
	}

	private void windowExitAction(WindowEvent e) {
		Alert alert = act.confirmationMessage("", "Do you really want to exit?");

		if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
			System.exit(0);
		} else {
		    alert.hide();
		    e.consume();
		}
	}

	private void menuRulesAction() {
		getHostServices().showDocument("http://blackjack.org/blackjack-rules/");
	}

	private void menuStratAction() {
		getHostServices().showDocument("http://blackjack.org/blackjack-strategy/");
	}

	private void noChipsMessage() {
		act.warningMessage("There's no chips!");
	}

	private void updatePotValue() {
		GAME.betValue.setText(Integer.toString((dealer.getChips().getRedChips()*10)+(dealer.getChips().getBlueChips()*25)+
				(dealer.getChips().getGreenChips()*50)+(dealer.getChips().getBrownChips()*100)));
	}

	private void updateP1Value() {
		player1.getChips().getRedChipLab().setText(Integer.toString(player1.getChips().getRedChips()));
		player1.getChips().getBlueChipLab().setText(Integer.toString(player1.getChips().getBlueChips()));
		player1.getChips().getGreenChipLab().setText(Integer.toString(player1.getChips().getGreenChips()));
		player1.getChips().getBrownChipLab().setText(Integer.toString(player1.getChips().getBrownChips()));
	}

	private void updateP2Value() {
		player2.getChips().getRedChipLab().setText(Integer.toString(player2.getChips().getRedChips()));
		player2.getChips().getBlueChipLab().setText(Integer.toString(player2.getChips().getBlueChips()));
		player2.getChips().getGreenChipLab().setText(Integer.toString(player2.getChips().getGreenChips()));
		player2.getChips().getBrownChipLab().setText(Integer.toString(player2.getChips().getBrownChips()));
	}

	private void activateChips() {
		dealer.getChips().getRedChipBut().setDisable(false);
		dealer.getChips().getBlueChipBut().setDisable(false);
		dealer.getChips().getGreenChipBut().setDisable(false);
		dealer.getChips().getBrownChipBut().setDisable(false);

		player1.getChips().getRedChipBut().setDisable(false);
		player1.getChips().getBlueChipBut().setDisable(false);
		player1.getChips().getGreenChipBut().setDisable(false);
		player1.getChips().getBrownChipBut().setDisable(false);

		if (set.getGameType().equals("Multi")) {
			player2.getChips().getRedChipBut().setDisable(false);
			player2.getChips().getBlueChipBut().setDisable(false);
			player2.getChips().getGreenChipBut().setDisable(false);
			player2.getChips().getBrownChipBut().setDisable(false);
		}
	}

	private void deactivateChips() {
		dealer.getChips().getRedChipBut().setDisable(true);
		dealer.getChips().getBlueChipBut().setDisable(true);
		dealer.getChips().getGreenChipBut().setDisable(true);
		dealer.getChips().getBrownChipBut().setDisable(true);

		player1.getChips().getRedChipBut().setDisable(true);
		player1.getChips().getBlueChipBut().setDisable(true);
		player1.getChips().getGreenChipBut().setDisable(true);
		player1.getChips().getBrownChipBut().setDisable(true);

		if (set.getGameType().equals("Multi")) {
			player2.getChips().getRedChipBut().setDisable(true);
			player2.getChips().getBlueChipBut().setDisable(true);
			player2.getChips().getGreenChipBut().setDisable(true);
			player2.getChips().getBrownChipBut().setDisable(true);
		}
	}

	/**
	 * Set Chips listeners.
	 */
	public void setChipsListeners() {
		dealer.getChips().getRedChipBut().setOnAction(e -> {
			if (dealer.getChips().getRedChips() == 0) {
				noChipsMessage();
			} else {
				dealer.getChips().setRedChips(dealer.getChips().getRedChips()-1);
				dealer.getChips().getRedChipLab().setText(Integer.toString(dealer.getChips().getRedChips()));
				player1.getChips().setRedChips(player1.getChips().getRedChips()+1);
				updateP1Value();
				player1.getChipsBet().setRedChips(player1.getChipsBet().getRedChips()-1);
				updatePotValue();
			}
		});

		player1.getChips().getRedChipBut().setOnAction(e -> {
			if (player1.getChips().getRedChips() == 0) {
				noChipsMessage();
			} else {
				player1.getChips().setRedChips(player1.getChips().getRedChips()-1);
				updateP1Value();
				dealer.getChips().setRedChips(dealer.getChips().getRedChips()+1);
				dealer.getChips().getRedChipLab().setText(Integer.toString(dealer.getChips().getRedChips()));
				player1.getChipsBet().setRedChips(player1.getChipsBet().getRedChips()+1);
				updatePotValue();
			}
		});

		dealer.getChips().getBlueChipBut().setOnAction(e -> {
			if (dealer.getChips().getBlueChips() == 0) {
				noChipsMessage();
			} else {
				dealer.getChips().setBlueChips(dealer.getChips().getBlueChips()-1);
				dealer.getChips().getBlueChipLab().setText(Integer.toString(dealer.getChips().getBlueChips()));
				player1.getChips().setBlueChips(player1.getChips().getBlueChips()+1);
				updateP1Value();
				player1.getChipsBet().setBlueChips(player1.getChipsBet().getBlueChips()-1);
				updatePotValue();
			}
		});

		player1.getChips().getBlueChipBut().setOnAction(e -> {
			if (player1.getChips().getBlueChips() == 0) {
				noChipsMessage();
			} else {
				player1.getChips().setBlueChips(player1.getChips().getBlueChips()-1);
				updateP1Value();
				dealer.getChips().setBlueChips(dealer.getChips().getBlueChips()+1);
				dealer.getChips().getBlueChipLab().setText(Integer.toString(dealer.getChips().getBlueChips()));
				player1.getChipsBet().setBlueChips(player1.getChipsBet().getBlueChips()+1);
				updatePotValue();
			}
		});

		dealer.getChips().getGreenChipBut().setOnAction(e -> {
			if (dealer.getChips().getGreenChips() == 0) {
				noChipsMessage();
			} else {
				dealer.getChips().setGreenChips(dealer.getChips().getGreenChips()-1);
				dealer.getChips().getGreenChipLab().setText(Integer.toString(dealer.getChips().getGreenChips()));
				player1.getChips().setGreenChips(player1.getChips().getGreenChips()+1);
				updateP1Value();
				player1.getChipsBet().setGreenChips(player1.getChipsBet().getGreenChips()-1);
				updatePotValue();
			}
		});

		player1.getChips().getGreenChipBut().setOnAction(e -> {
			if (player1.getChips().getGreenChips() == 0) {
				noChipsMessage();
			} else {
				player1.getChips().setGreenChips(player1.getChips().getGreenChips()-1);
				updateP1Value();
				dealer.getChips().setGreenChips(dealer.getChips().getGreenChips()+1);
				dealer.getChips().getGreenChipLab().setText(Integer.toString(dealer.getChips().getGreenChips()));
				player1.getChipsBet().setGreenChips(player1.getChipsBet().getGreenChips()+1);
				updatePotValue();
			}
		});

		dealer.getChips().getBrownChipBut().setOnAction(e -> {
			if (dealer.getChips().getBrownChips() == 0) {
				noChipsMessage();
			} else {
				dealer.getChips().setBrownChips(dealer.getChips().getBrownChips()-1);
				dealer.getChips().getBrownChipLab().setText(Integer.toString(dealer.getChips().getBrownChips()));
				player1.getChips().setBrownChips(player1.getChips().getBrownChips()+1);
				updateP1Value();
				player1.getChipsBet().setBrownChips(player1.getChipsBet().getBrownChips()-1);
				updatePotValue();
			}
		});

		player1.getChips().getBrownChipBut().setOnAction(e -> {
			if (player1.getChips().getBrownChips() == 0) {
				noChipsMessage();
			} else {
				player1.getChips().setBrownChips(player1.getChips().getBrownChips()-1);
				updateP1Value();
				dealer.getChips().setBrownChips(dealer.getChips().getBrownChips()+1);
				dealer.getChips().getBrownChipLab().setText(Integer.toString(dealer.getChips().getBrownChips()));
				player1.getChipsBet().setBrownChips(player1.getChipsBet().getBrownChips()+1);
				updatePotValue();
			}
		});

		if (set.getGameType().equals("Multi")) {
			player2.getChips().getRedChipBut().setOnAction(e -> {
				if (player2.getChips().getRedChips() == 0) {
					noChipsMessage();
				} else {
					player2.getChips().setRedChips(player2.getChips().getRedChips()-1);
					updateP2Value();
					dealer.getChips().setRedChips(dealer.getChips().getRedChips()+1);
					dealer.getChips().getRedChipLab().setText(Integer.toString(dealer.getChips().getRedChips()));
					player2.getChipsBet().setRedChips(player2.getChipsBet().getRedChips()+1);
					updatePotValue();
				}
			});

			player2.getChips().getBlueChipBut().setOnAction(e -> {
				if (player2.getChips().getBlueChips() == 0) {
					noChipsMessage();
				} else {
					player2.getChips().setBlueChips(player2.getChips().getBlueChips()-1);
					player2.getChips().getBlueChipLab().setText(Integer.toString(player2.getChips().getBlueChips()));
					dealer.getChips().setBlueChips(dealer.getChips().getBlueChips()+1);
					dealer.getChips().getBlueChipLab().setText(Integer.toString(dealer.getChips().getBlueChips()));
					player2.getChipsBet().setBlueChips(player2.getChipsBet().getBlueChips()+1);
					updatePotValue();
				}
			});

			player2.getChips().getGreenChipBut().setOnAction(e -> {
				if (player2.getChips().getGreenChips() == 0) {
					noChipsMessage();
				} else {
					player2.getChips().setGreenChips(player2.getChips().getGreenChips()-1);
					player2.getChips().getGreenChipLab().setText(Integer.toString(player2.getChips().getGreenChips()));
					dealer.getChips().setGreenChips(dealer.getChips().getGreenChips()+1);
					dealer.getChips().getGreenChipLab().setText(Integer.toString(dealer.getChips().getGreenChips()));
					player2.getChipsBet().setGreenChips(player2.getChipsBet().getGreenChips()+1);
					updatePotValue();
				}
			});

			player2.getChips().getBrownChipBut().setOnAction(e -> {
				if (player2.getChips().getBrownChips() == 0) {
					noChipsMessage();
				} else {
					player2.getChips().setBrownChips(player2.getChips().getBrownChips()-1);
					player2.getChips().getBrownChipLab().setText(Integer.toString(player2.getChips().getBrownChips()));
					dealer.getChips().setBrownChips(dealer.getChips().getBrownChips()+1);
					dealer.getChips().getBrownChipLab().setText(Integer.toString(dealer.getChips().getBrownChips()));
					player2.getChipsBet().setBrownChips(player2.getChipsBet().getBrownChips()+1);
					updatePotValue();
				}
			});
		}
	}

	//---------------------------------------------------------------------------------------------

	// GAMING LOGIC SECTION -----------------------------------------------------------------------

	/**
	 * Take a random card from the deck.
	 * @return <b>card</b>
	 */
	private String getCard() {
		int numberCards = deck.getCards();
		int index = act.getRandom(numberCards);
		String card = deck.getDeck().get(index);
		deck.getDeck().remove(index);
		deck.setCards(numberCards-1);
		GAME.deck.getChildren().remove(GAME.deck.getChildren().size()-1);
		return card;
	}

	private void wakeUpDealer() {
		GAME.roundLabel.setText("DEALER TIME!");
		dealer.setActive(true);
		act.flip();
		GAME.cardsDValue.setText(Integer.toString(dealer.getRound()));
		dealerStop();
		while (dealer.isActive()) {
			String card = getCard();
			giveDealerCard(card);
			dealerStop();
			checkDBust();
		}
		checkRoundEnd();
	}

	/**
	 * Get a new card and verify the game rules for Player 1.
	 */
	private void player1HitAction() {
		String card = getCard();
		giveP1Card(card);

		checkP1Bust();
	}

	/**
	 * Get a new card and verify the game rules for Player 2.
	 */
	private void player2HitAction() {
		String card = getCard();
		giveP2Card(card);

		checkP2Bust();
	}

	private void giveDealerCard(String card) {
		if (dealer.isFirstCard()) {
			dealer.getHand().add(card);
			dealer.setActive(true);
			int value = decideCard(card,GAME.cardsD,dealer.getRound(),dealer.getHand());
			dealer.setActive(false);
			dealer.setFirstCard(false);
			dealer.setRound(value);
			System.out.println("->"+card);
		} else {
			dealer.getHand().add(card);
			int value = decideCard(card,GAME.cardsD,dealer.getRound(),dealer.getHand());
			System.out.println("->"+card);
			dealer.setRound(value);
		}

		if (dealer.isActive()) {
			GAME.cardsDValue.setText(Integer.toString(dealer.getRound()));
		}
	}

	private void giveP1Card(String card) {
		int value = decideCard(card,GAME.cards1,player1.getRound(),player1.getHand());
		System.out.println("->"+card);
		player1.setRound(value);
		GAME.cards1Value.setText(Integer.toString(player1.getRound()));
		checkBlackjack();
	}

	private void giveP2Card(String card) {
		int value = decideCard(card,GAME.cards2,player2.getRound(),player2.getHand());
		player2.getHand().add(card);
		System.out.println("->"+card);
		player2.setRound(value);
		GAME.cards2Value.setText(Integer.toString(player2.getRound()));
	}

	/**
	 * Distribute initial cards only.
	 */
	private void distCards() {
		String card;

		if (set.getGameType().equals("Single")) {
			card = getCard();
			giveP1Card(card);
			if (!game.isRoundEnd()) {
				card = getCard();
				giveDealerCard(card);
			}
			game.setRound(game.getRound()+1);
		} else {
			card = getCard();
			giveP1Card(card);
			card = getCard();
			giveP2Card(card);
			card = getCard();
			giveDealerCard(card);
			game.setRound(game.getRound()+1);
		}
		
		if (dealer.getHand().size() == 2 && player1.getHand().size() == 2) {
			game.setInitTime(false);
		}
	}

	/**
	 * Translates the card got to displays the correct card in screen.
	 * @param card
	 * @param cardPanel
	 * @return <b>value</b>
	 */
	private int decideCard(String card, StackPane cardPanel, int round, List<String> hand) {
		if (dealer.isActive() && dealer.isFirstCard()) {
			cardPanel.getChildren().add(0,act.createFlipPane(deck.getCardsMap().get(card)));
		} else {
			cardPanel.getChildren().add(0,deck.getCardsMap().get(card));
		}
		Actions.rearrangeCards(cardPanel,false);

		int value = act.getValue(card);
		
		switch (value) {
		case 11:
			value = 10;
			break;
		case 12:
			value = 10;
			break;
		case 13:
			value = 10;
			break;
		default:
			break;
		}
		
		int sum = round + value;
		
		if (value == 1) {
			sum = round + 11;
			if (sum > 21) {
				value = 1;
				sum = round + 1;
			} else {
				value = 11;
				card = act.getSuit(card)+"14";
			}
		}
		
		if (sum > 21) {
			for (int i = 0; i < hand.size(); i++) {
				if (act.getValue(hand.get(i)) == 14) {
					sum-=10;
					hand.set(i, act.getSuit(hand.get(i))+"1");
				}
			}
		}
		
		player1.getHand().add(card);
		
		return round+value;
	}

	private void dealerStop() {
		if (dealer.getRound() >= 17) {
			dealer.setActive(false);
		}
	}

	/**
	 * Handles the stop action, assigning stop status to player.
	 */
	private void player1StopAction() {
		player1.setStopped(true);
		if (set.getGameType().equals("Single")) {
			act.accurateMessage("PLAYER 1 STOPPED!\n\nDEALER TIME NOW!");
			wakeUpDealer();
		} else {
			if (player1.isStopped() && player2.isStopped()) {
				act.accurateMessage("PLAYER 1 STOPPED!\n\nDEALER TIME NOW!");
				wakeUpDealer();
			} else {
				act.accurateMessage("PLAYER 1 STOPPED!"); //TODO
			}
		}
	}

	//---------------------------------------------------------------------------------------------

	// CHECKERS LOGIC SECTION ---------------------------------------------------------------------

	private void checkBlackjack() {
		if (player1.getRound() == 21) {
			act.accurateMessage("PLAYER 1 GOT A BLACKJACK!");
			player1.setStopped(true);
			GAME.hit1But.setDisable(true);
			GAME.stand1But.setDisable(true);
			wakeUpDealer();
		}
	}
	
	/**
	 * Check if someone or everyone have stopped.
	 */
	private void checkRoundEnd() {
		boolean p1Wins = false, p2Wins = false, p1Draws = false, p2Draws = false;
		if (set.getGameMode().equals("Normal")) {
			if (set.getGameType().equals("Single")) {
				if (!dealer.isActive() && !game.isRoundEnd()) {
					if (player1.getRound() > dealer.getRound()) {
						p1WinsRound();
					} else if (player1.getRound() == dealer.getRound()) {
						push();
					} else if (!dealer.isBusted()) {
						act.loseMessage("DEALER WIN!");
					}
					finalizeRound();
				}
			} else {
				if (!dealer.isActive() && !game.isRoundEnd()) {
					// TODO improve this method
					if ((player1.isStopped() && !player1.isBusted()) && (player2.isStopped() && !player2.isBusted())) {
						act.accurateMessage("BOTH PLAYERS STOPPED!\n\nDEALER TIME NOW!");
						wakeUpDealer();
					} else if ((player1.isStopped() && !player1.isBusted()) && player2.isBusted()) {
						act.accurateMessage("PLAYER 1 STOPPED!\nPLAYER 2 BUSTED!\n\nDEALER TIME NOW!");
						wakeUpDealer();
					} else if (player1.isBusted() && (player2.isStopped() && !player2.isBusted())) {
						act.accurateMessage("PLAYER 1 BUSTED!\nPLAYER 2 STOPPED!\n\nDEALER TIME NOW!");
						wakeUpDealer();
					} else if (player1.isBusted() && player2.isBusted()) {
						act.accurateMessage("PLAYER 1 AND PLAYER 2 BUSTED!\n\nDEALER WINS!");
						GAME.roundLabel.setText("DEALER WIN!");
						act.flip();
						finalizeRound();
					}
				} else if (!dealer.isActive() && !game.isRoundEnd()) {
					if ((player1.isStopped() || player1.isBusted()) && (player2.isStopped() || player2.isBusted())) {
						if (!player1.isBusted() && player1.getRound() > dealer.getRound()) {
							p1Wins = true;
						} else if (!player1.isBusted() && player1.getRound() == dealer.getRound()) {
							p1Draws = true;
						}
						if (!player2.isBusted() && player2.getRound() > dealer.getRound()) {
							p2Wins = true;
						} else if (!player2.isBusted() && player2.getRound() == dealer.getRound()) {
							p2Draws = true;
						}
						if (p1Wins && p2Wins) {
							act.winMessage("BOTH PLAYERS WIN!");
							p1p2WinRound();
						} else if (p1Wins && !p2Wins) {
							if (!p2Draws) {
								act.winMessage("ONLY P1 WIN!");
								p1WinsRound();
							} else {
								act.winMessage("P1 WINS AND P2 PUSHES!");
								p1WinsRound();
							}
						} else if (!p1Wins && p2Wins) {
							if (!p1Draws) {
								act.winMessage("ONLY P2 WIN!");
								p2WinsRound();
							} else {
								act.winMessage("P2 WINS AND P1 PUSHES!");
								p2WinsRound();
							}
						} else if (!p1Wins && !p2Wins && !p1Draws && !p2Draws) {
							act.loseMessage("DEALER WIN!");
							GAME.roundLabel.setText("DEALER WIN!");
						} else if (!p1Wins && !p2Wins && p1Draws && p2Draws) {
							act.winMessage("IT'S A PUSH, NOBODY WINS, NOBODY LOSES!");
						} else if (p1Draws && !p2Wins) {
							act.winMessage("P1 PUSHES!");
						} else if (p2Draws && ! p1Wins) {
							act.winMessage("P2 PUSHES!");
						}
						finalizeRound();
					}
				}
			}
		} else {
			if (!game.isRoundEnd()) {
				if (player1.getRound() > player2.getRound() && player2.isStopped()) {
					try {
						act.playApplause();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					act.winMessage("PLAYER 1 WINS!");
					p1WinsRound();
					finalizeRound();
				} else if (player1.getRound() < player2.getRound() && player1.isStopped()) {
					try {
						act.playApplause();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					act.winMessage("PLAYER 2 WINS!");
					p2WinsRound();
					finalizeRound();
				}
			}
		}
	}

	private void checkDBust() {
		if (dealer.getRound() > 21) {
			act.accurateMessage("DEALER GOT BUSTED!");
			dealer.setBusted(true);
			dealer.setActive(false);
			p1WinsRound();
			finalizeRound();
		}
	}

	private void checkP1Bust() {
		if (!player1.isBusted() && player1.getRound() > 21) {
			act.loseMessage("PLAYER 1 GOT BUSTED! DEALER WIN!");
			GAME.roundLabel.setText("DEALER WIN!");
			act.flip();
			GAME.cardsDValue.setText(Integer.toString(dealer.getRound()));
			player1.setBusted(true);
			finalizeRound();
		}
	}

	private void checkP2Bust() {
		if (!player2.isBusted() && player2.getRound() > 21) {
			act.loseMessage("PLAYER 1 GOT BUSTED! DEALER WIN!");
			GAME.roundLabel.setText("DEALER WIN!");
			act.flip();
			GAME.cardsDValue.setText(Integer.toString(dealer.getRound()));
			player2.setBusted(true);
			finalizeRound();
		}
	}

	private boolean isP1OutOfChips() {
		if (player1.getChips().getRedChips()+player1.getChips().getBlueChips()
			+player1.getChips().getGreenChips()+player1.getChips().getBrownChips() == 0) {
			return true;
		}
		return false;
	}

	private boolean isP2OutOfChips() {
		if (player2.getChips().getRedChips()+player2.getChips().getBlueChips()
			+player2.getChips().getGreenChips()+player2.getChips().getBrownChips() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the score limit have been reached.
	 */
	private boolean checkGameFinal() {
		if (isP1OutOfChips()) {
			act.loseMessage("PLAYER 1 IS OUT OF CHIPS!\n\nGAME OVER!");
			return true;
		}
		if (set.getGameType().equals("Multi")) { // TODO
			if (isP2OutOfChips()) {
				act.loseMessage("PLAYER 1 IS OUT OF CHIPS!\n\nGAME OVER!");
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the round ends when last chance is applied.
	 *
	 * <P><i>Last Chance = when one player gets 21, the other one has 1 chance to equal.<P></i>
	 */
//	private void checkRoundFinal() {
//		if (player1.getRound() > player2.getRound()) {
//			p1WinsRound();
//			finalizeRound();
//		} else if ((player1.getRound() < player2.getRound())) {
//			act.winMessage("PLAYER 2 WINS!");
//			p2WinsRound();
//			finalizeRound();
//		} else {
//			act.winMessage("IT'S A PUSH, NOBODY WINS!");
//			finalizeRound();
//		}
//	}

	//---------------------------------------------------------------------------------------------

	// FINALIZATION SECTION -----------------------------------------------------------------------

	/**
	 * Used when both players win.
	 */
	private void p1p2WinRound() {
		GAME.roundLabel.setText("BOTH PLAYERS WIN!");

	}

	/**
	 * Used when player 1 wins.
	 */
	private void p1WinsRound() {
		act.winMessage("PLAYER 1 WINS!");
		GAME.roundLabel.setText("PLAYER 1 WIN!");
		player1WinChips();
		try {
			act.playApplause();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Used when player 2 wins.
	 */
	private void p2WinsRound() {
		act.winMessage("PLAYER 2 WINS!");
		GAME.roundLabel.setText("PLAYER 2 WINS!");
		player2WinChips();
		try {
			act.playApplause();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void push() {
		act.accurateMessage("It's a push!\n\nChips bet are returned!");
		player1.getChips().setRedChips(player1.getChips().getRedChips()+player1.getChipsBet().getRedChips());
		player1.getChips().getRedChipLab().setText(Integer.toString(player1.getChips().getRedChips()));
		player1.getChips().setBlueChips(player1.getChips().getBlueChips()+player1.getChipsBet().getBlueChips());
		player1.getChips().getBlueChipLab().setText(Integer.toString(player1.getChips().getBlueChips()));
		player1.getChips().setGreenChips(player1.getChips().getGreenChips()+player1.getChipsBet().getGreenChips());
		player1.getChips().getGreenChipLab().setText(Integer.toString(player1.getChips().getGreenChips()));
		player1.getChips().setBrownChips(player1.getChips().getBrownChips()+player1.getChipsBet().getBrownChips());
		player1.getChips().getBrownChipLab().setText(Integer.toString(player1.getChips().getBrownChips()));
	}

	private void clearPot() {
		if (dealer != null) {
			dealer.getChips().setRedChips(0);
			dealer.getChips().getRedChipLab().setText("0");
			dealer.getChips().setBlueChips(0);
			dealer.getChips().getBlueChipLab().setText("0");
			dealer.getChips().setGreenChips(0);
			dealer.getChips().getGreenChipLab().setText("0");
			dealer.getChips().setBrownChips(0);
			dealer.getChips().getBrownChipLab().setText("0");
		}
		GAME.betValue.setText("0");
	}

	private void player1WinChips() {
		player1.getChips().setRedChips(player1.getChips().getRedChips()+player1.getChipsBet().getRedChips()*2);
		player1.getChips().getRedChipLab().setText(Integer.toString(player1.getChips().getRedChips()));
		player1.getChips().setBlueChips(player1.getChips().getBlueChips()+player1.getChipsBet().getBlueChips()*2);
		player1.getChips().getBlueChipLab().setText(Integer.toString(player1.getChips().getBlueChips()));
		player1.getChips().setGreenChips(player1.getChips().getGreenChips()+player1.getChipsBet().getGreenChips()*2);
		player1.getChips().getGreenChipLab().setText(Integer.toString(player1.getChips().getGreenChips()));
		player1.getChips().setBrownChips(player1.getChips().getBrownChips()+player1.getChipsBet().getBrownChips()*2);
		player1.getChips().getBrownChipLab().setText(Integer.toString(player1.getChips().getBrownChips()));
	}
	
	private void player2WinChips() {
		player2.getChips().setRedChips(player2.getChips().getRedChips()+player2.getChipsBet().getRedChips()*2);
		player2.getChips().getRedChipLab().setText(Integer.toString(player2.getChips().getRedChips()));
		player2.getChips().setBlueChips(player2.getChips().getBlueChips()+player2.getChipsBet().getBlueChips()*2);
		player2.getChips().getBlueChipLab().setText(Integer.toString(player2.getChips().getBlueChips()));
		player2.getChips().setGreenChips(player2.getChips().getGreenChips()+player2.getChipsBet().getGreenChips()*2);
		player2.getChips().getGreenChipLab().setText(Integer.toString(player2.getChips().getGreenChips()));
		player2.getChips().setBrownChips(player2.getChips().getBrownChips()+player2.getChipsBet().getBrownChips()*2);
		player2.getChips().getBrownChipLab().setText(Integer.toString(player2.getChips().getBrownChips()));
	}

	//---------------------------------------------------------------------------------------------

	// RESETERS SECTION ---------------------------------------------------------------------------

	/**
	 * Reinitiates the game and whole screen.
	 * @throws IOException
	 */
	private void reinitGame() throws IOException {
		dealer = new Dealer(0,false,false,false);
		player1 = new Player("Player 1",0,false,false);
		player2 = new Player("Player 2",0,false,false);
		GAME = new GUIGame();
		game.setRetMenu(true);

		setGameListeners();
		resetRound();
	}

	private void finalizeRound() {
		GAME.startBut2.setDisable(false);
		GAME.distBut.setDisable(true);
		GAME.hit1But.setDisable(true);
		GAME.stand1But.setDisable(true);
		GAME.hit2But.setDisable(true);
		GAME.stand2But.setDisable(true);
		game.setRoundEnd(true);
	}

	private void resetChips() {
		player1.getChips().resetChips(10,8,6,2);
		updateP1Value();
		player2.getChips().resetChips(10,8,6,2);
		updateP2Value();
	}

	/**
	 * Resets the round and all components and logic stuff.
	 */
	private void resetRound() {
		act = new Actions();

		game.setRound(1);

		clearPot();

		player1.setRound(0);
		player1.setHand(new ArrayList<String>());
		player2.setRound(0);
		player2.setHand(new ArrayList<String>());
		dealer.setRound(0);
		dealer.setHand(new ArrayList<String>());
		dealer.setBusted(false);
		dealer.setActive(false);
		dealer.setFirstCard(true);
		GAME.cardsDValue.setText("");

		deck.setDeck(act.mountDeck());
		deck.setCards(51);
		act.shuffleDeck(deck.getDeck());
		if (!game.isRetMenu()) {
			act.replaceDeck(GAME.deck);
		} else {
			game.setRetMenu(false);
		}

		GAME.roundLabel.setText("BET CHIPS!");

		GAME.startBut2.setDisable(false);
		GAME.distBut.setDisable(true);
		GAME.hit1But.setDisable(true);
		GAME.stand1But.setDisable(true);
		GAME.hit2But.setDisable(true);
		GAME.stand2But.setDisable(true);

		player1.setStopped(false);
		player1.setBusted(false);
		player2.setStopped(false);
		player2.setBusted(false);
		
		game.setRoundEnd(false);
		game.setInitTime(true);
		game.setLastChance(false);

		GAME.cardsD.getChildren().clear();
		GAME.cards1.getChildren().clear();
		GAME.cards2.getChildren().clear();

		player1.getChipsBet().resetChips(0,0,0,0);
		GAME.cards1Value.setText("");

		player2.getChipsBet().resetChips(0,0,0,0);
		GAME.cards2Value.setText("");

	}

	//---------------------------------------------------------------------------------------------
}
