package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Deck;
import model.Total;
import view.GUISettings;
import view.GUIMenu;
import view.GUIMultiplayer;
import view.GUIWin;

public class Game extends Application {

	public static List<BufferedImage> sprites = new ArrayList<BufferedImage>();

	GUIMenu MENU = new GUIMenu();
	GUIMultiplayer MULTI;
	GUISettings CUSTOM = new GUISettings();
	GUIWin WIN = new GUIWin();

	Actions act = new Actions();
	Total dTotal = new Total();
	Total p1Total = new Total();
	Total p2Total = new Total();
	Deck deck = new Deck();

	int whosPlaying = 1;
	boolean p1Stopped = false;
	boolean p1Busted = false;
	boolean p2Stopped = false;
	boolean p2Busted = false;
	boolean bothSaid = false;
	boolean roundEnd = false;
	boolean firstCardD = true;
	boolean initTime = true;
	boolean lastChance = false;
	boolean dealer = true;
	boolean retMenu = false;

	int round = 1;

	// SETTINGS ===================
	public static int gameMode = 0;
	int aceValue = 1;
	int scoreLimit = 10;
	int reshuffle = 1;
	int i = 51;
	// ============================

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
		deck.setDeck(act.mountDeck());
		sprites = act.cutDeck();
		MULTI = new GUIMultiplayer();
		setListeners();
		MENU.show();
	}

	/**
	 * Exists just to handle Exception.
	 * @throws Exception
	 */
	public Game() throws Exception {}

	/**
	 * Reinitiates the game and whole screen.
	 * @throws IOException
	 */
	private void reinitGame() throws IOException {
		MULTI = new GUIMultiplayer();
		retMenu = true;

		setListeners();
		resetRound();
		resetScoreboard();
	}

	/**
	 * Set listeners for all buttons.
	 */
	private void setListeners() {
		MULTI.getBut.setOnAction(e -> {
			play();
			if (whosPlaying != 0) {
				MULTI.getBut.setDisable(true);
				initTime = !initTime;
			}
		});

		MULTI.shuffleBut.setOnAction(e -> {
			act.shuffleDeck(deck.getDeck());
			for (String tmp:deck.getDeck()) {
				System.out.println(tmp);
			}
			Timeline timeline = act.shuffleAnimation(MULTI.deck);
			timeline.play();
			timeline.setOnFinished(e1 -> {
				MULTI.startBut.setDisable(false);
				MULTI.messageStart.setVisible(true);
			});
		});

		MULTI.startBut.setOnAction(e -> {
			MULTI.initGameGUI();
		});

		MULTI.distBut.setOnAction(e -> {
			// distribute 2 cards each
			if (gameMode == 0) {
				for (int i = 0; i < 6; i++) {
					distCards();
				}
			} else {
				for (int i = 0; i < 4; i++) {
					distCards();
				}
			}

			whosPlaying = 1;
			MULTI.roundLabel.setText("PLAYER 1 TURN");

			MULTI.restartRoundBut.setDisable(false);
			MULTI.distBut.setDisable(true);
			if (!roundEnd) {
				MULTI.continue1But.setDisable(false);
				MULTI.stop1But.setDisable(false);
			}
		});

		MULTI.restartRoundBut.setOnAction(e -> resetRound());

		MULTI.continue1But.setOnAction(e -> continueAction());

		MULTI.continue2But.setOnAction(e -> continueAction());

		MULTI.stop1But.setOnAction(e -> stopAction());

		MULTI.stop2But.setOnAction(e -> stopAction());

		MULTI.menuRestart.setOnAction(e -> {
			Alert alert = act.confirmationMessage("", "Do you really want to restart game?\nThis will reset the scoreboard!");

			if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
				resetRound();
				resetScoreboard();
			} else {
			    alert.close();
			}
		});

		MULTI.menuReturn.setOnAction(e -> {
			Alert alert = act.confirmationMessage("", "Do you really want to return to menu?\nYou'll lose any game progression!");

			if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
				MULTI.close();
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

		MULTI.menuExit.setOnAction(e -> {
			Alert alert = act.confirmationMessage("", "Do you really want to exit?");

			if (alert.showAndWait().get() == alert.getButtonTypes().get(0)) {
				System.exit(0);
			} else {
			    alert.close();
			}
		});

		MENU.spButton.setOnAction(e -> {
		});

		MENU.mpButton.setOnAction(e -> {
			MULTI.show();
			MENU.hide();
			MULTI.requestFocus();
		});

		MENU.customBut.setOnAction(e -> {
			if (CUSTOM.isShowing()) {
				CUSTOM.requestFocus();
			} else {
				CUSTOM.show();
			}
		});

		MENU.exitBut.setOnAction(e -> {
			System.exit(0);
		});

		CUSTOM.saveBut.setOnAction(e -> {
			gameMode = CUSTOM.getGameMode();
			aceValue = CUSTOM.getAceValue();
			scoreLimit = CUSTOM.getScoreLimit();
			MULTI.scoreLabel.setText("SCORE "+ scoreLimit +" POINTS TO WIN!");
			reshuffle = CUSTOM.getReshuffle();
			CUSTOM.close();
		});

		CUSTOM.restoreBut.setOnAction(e -> {
			CUSTOM.resetAll();
		});
	}

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
		return card;
	}

	/**
	 * Get a new card and verify the game rules.
	 */
	private void play() {
		int plus = 0;
		String card = getCard();
		int value = act.getValue(card);
		String suit = act.getSuit(card);

		if (gameMode == 0) {
			if (whosPlaying == 0 && dealer) { // DEALER
					dTotal.getHand().add(card);
					value = decideCard(suit,value,MULTI.cardsD);
					System.out.println("->"+card);
					plus = dTotal.getRound()+value;
					dTotal.setRound(plus);
					// stop condition for dealer
					if (dTotal.getRound() >= 17) {
						dealer = false;
						act.accurateMessage("DEALER HAS STOPPED");
					}
			} else if (whosPlaying == 1) { // P1
				p1Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards1);
				System.out.println("->"+card);
				plus = p1Total.getRound()+value;
				p1Total.setRound(plus);
			} else if (whosPlaying == 2) { // P2
				p2Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards2);
				System.out.println("->"+card);
				plus = p2Total.getRound()+value;
				p2Total.setRound(plus);
				round++;
			}
		} else {
			if (whosPlaying == 1) { // P1
				p1Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards1);
				plus = p1Total.getRound()+value;
				p1Total.setRound(plus);
			} else { // P2
				p2Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards2);
				plus = p2Total.getRound()+value;
				p2Total.setRound(plus);
				round++;
			}
		}

		MULTI.deck.getChildren().remove(deck.getCards()+1);

		if (gameMode == 1 && lastChance) {
			checkRoundFinal();
		}

		checkBusts(plus);

		checkStop();

		checkGameFinal();

		if (reshuffle == 1) {
			// Shuffle again after every play
			act.shuffleDeck(deck.getDeck());
		} else if (reshuffle == 2 && round/2 == 0) {
			// Shuffle again after 2 plays
			act.shuffleDeck(deck.getDeck());
			round = 1;
		}

		if (!roundEnd) {
			if (whosPlaying == 1 && (!p1Busted && !p1Stopped)) {
				MULTI.continue1But.setDisable(false);
				MULTI.stop1But.setDisable(false);
				MULTI.continue2But.setDisable(true);
				MULTI.stop2But.setDisable(true);
			} else if (whosPlaying == 2 && (p1Total.getHand().size() == p2Total.getHand().size()) ||
						whosPlaying == 2 && (!p2Busted && !p2Stopped)) {
				MULTI.continue1But.setDisable(true);
				MULTI.stop1But.setDisable(true);
				MULTI.continue2But.setDisable(false);
				MULTI.stop2But.setDisable(false);
			}
		}
	}

	/**
	 * Distribute initial cards only.
	 */
	private void distCards() {
		int plus = 0;
		String card = getCard();
		int value = act.getValue(card);
		String suit = act.getSuit(card);

		if (gameMode == 0) {
			if (whosPlaying == 0) { // DEALER
				if (firstCardD) {
					dTotal.getHand().add(card);
					value = decideCard(suit,value,MULTI.cardsD);
					firstCardD = false;
					plus = dTotal.getRound()+value;
					dTotal.setRound(plus);
					System.out.println("->"+card);
					whosPlaying = 1;
				} else {
					dTotal.getHand().add(card);
					value = decideCard(suit,value,MULTI.cardsD);
					System.out.println("->"+card);
					plus = dTotal.getRound()+value;
					dTotal.setRound(plus);
				}
			} else if (whosPlaying == 1) { // P1
				p1Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards1);
				System.out.println("->"+card);
				plus = p1Total.getRound()+value;
				p1Total.setRound(plus);
				whosPlaying = 2;
				act.distCardAnimation(MULTI.deck.getChildren().get(MULTI.deck.getChildren().size()-1), MULTI.cards1);
			} else if (whosPlaying == 2) { // P2
				p2Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards2);
				System.out.println("->"+card);
				plus = p2Total.getRound()+value;
				p2Total.setRound(plus);
				act.distCardAnimation(MULTI.deck.getChildren().get(MULTI.deck.getChildren().size()-1), MULTI.cards2);
				whosPlaying = 0;
			}
		} else {
			if (whosPlaying == 1) { // P1
				p1Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards1);
				plus = p1Total.getRound()+value;
				p1Total.setRound(plus);
				whosPlaying = 2;
			} else { // P2
				p2Total.getHand().add(card);
				value = decideCard(suit,value,MULTI.cards2);
				plus = p2Total.getRound()+value;
				p2Total.setRound(plus);
				whosPlaying = 1;
			}
		}

		MULTI.deck.getChildren().remove(deck.getCards()+1);
	}

	/**
	 * Check if someone or everyone have stopped.
	 */
	private void checkStop() {
		boolean p1Wins = false, p2Wins = false, p1Draws = false, p2Draws = false;
		if (gameMode == 0) {
			if (whosPlaying != 0) {
				// TODO improve this method
				if ((p1Stopped && !p1Busted) && (p2Stopped && !p2Busted)) {
					act.accurateMessage("BOTH PLAYERS STOPPED!\n\nDEALER TIME NOW!");
					wakeUpDealer();
				} else if ((p1Stopped && !p1Busted) && p2Busted) {
					act.accurateMessage("PLAYER 1 STOPPED!\nPLAYER 2 BUSTED!\n\nDEALER TIME NOW!");
					wakeUpDealer();
				} else if (p1Busted && (p2Stopped && !p2Busted)) {
					act.accurateMessage("PLAYER 1 BUSTED!\nPLAYER 2 STOPPED!\n\nDEALER TIME NOW!");
					wakeUpDealer();
				} else if (p1Busted && p2Busted) {
					act.accurateMessage("PLAYER 1 AND PLAYER 2 BUSTED!\n\nDEALER WINS!");
					act.flip();
					roundFinal();
				}
			} else if (whosPlaying == 0 && !dealer && !roundEnd) {
				if ((p1Stopped || p1Busted) && (p2Stopped || p2Busted)) {
					roundFinal();
					if (!p1Busted && p1Total.getRound() > dTotal.getRound()) {
						p1Wins = true;
					} else if (!p1Busted && p1Total.getRound() == dTotal.getRound()) {
						p1Draws = true;
					}
					if (!p2Busted && p2Total.getRound() > dTotal.getRound()) {
						p2Wins = true;
					} else if (!p2Busted && p2Total.getRound() == dTotal.getRound()) {
						p2Draws = true;
					}
					if (p1Wins && p2Wins) {
						act.winMessage("BOTH PLAYERS WIN!");
						p1p2WinRound();
					} else if (p1Wins && !p2Wins) {
						if (!p2Draws) {
							act.winMessage("ONLY P1 WINS!");
							p1WinsRound();
						} else {
							act.winMessage("P1 WINS AND P2 PUSHES!");
							p1WinsRound();
						}
					} else if (!p1Wins && p2Wins) {
						if (!p1Draws) {
							act.winMessage("ONLY P2 WINS!");
							p2WinsRound();
						} else {
							act.winMessage("P2 WINS AND P1 PUSHES!");
							p2WinsRound();
						}
					} else if (!p1Wins && !p2Wins && !p1Draws && !p2Draws) {
						act.loseMessage("DEALER WINS!");
						MULTI.roundLabel.setText("DEALER WINS!");
					} else if (!p1Wins && !p2Wins && p1Draws && p2Draws) {
						act.winMessage("IT'S A PUSH, NOBODY WINS, NOBODY LOSES!");
					} else if (p1Draws && !p2Wins) {
						act.winMessage("P1 PUSHES!");
					} else if (p2Draws && ! p1Wins) {
						act.winMessage("P2 PUSHES!");
					}
				}
			}
		} else {
			if (!roundEnd) {
				if (whosPlaying == 1 && p1Total.getRound() > p2Total.getRound() && p2Stopped) {
					try {
						act.playApplause();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					act.winMessage("PLAYER 1 WINS!");
					p1WinsRound();
					roundFinal();
				} else if (whosPlaying == 2 && p1Total.getRound() < p2Total.getRound() && p1Stopped) {
					try {
						act.playApplause();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					act.winMessage("PLAYER 2 WINS!");
					p2WinsRound();
					roundFinal();
				}
			}
		}
	}

	/**
	 * Check if someone or everyone have busted.
	 *
	 * <P><i>Busted = get more than 21 value in cards.</P></i>
	 * @param plus
	 */
	private void checkBusts(int plus) {
		if (gameMode == 0) {
			if (dTotal.getRound() > 21) {
				act.accurateMessage("DEALER GOT BUSTED! ALL PLAYERS LEFT WIN!");
				if (!p1Busted && !p2Busted) {
					p1p2WinRound();
				} else if (!p1Busted && p2Busted) {
					p1WinsRound();
				} else if (p1Busted && !p2Busted) {
					p2WinsRound();
				}
				roundFinal();
			} else {
				if (!p1Busted && p1Total.getRound() > 21) {
					act.loseMessage("PLAYER 1 GOT OVER 21 AND IS OUT!");
					p1Busted = true;
					continueAction();
				}
				if (!p2Busted && p2Total.getRound() > 21) {
					act.loseMessage("PLAYER 2 GOT OVER 21 AND IS OUT!");
					p2Busted = true;
					continueAction();
				}
			}
		} else {
			if (plus > 21) {
				try {
					act.playBoo();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (whosPlaying == 1) {
					act.loseMessage("PLAYER 1 GOT OVER 21!");
					p2WinsRound();
				} else {
					act.loseMessage("PLAYER 2 GOT OVER 21!");
					p1WinsRound();
				}
				roundFinal();
			} else if (plus == 21) {
				if (whosPlaying == 1 && !p2Stopped) {
					act.accurateMessage("PLAYER 1 GOT 21!\nPLAYER 2 HAVE ONE CHANCE TO EQUAL!");
					p1Stopped = true;
					lastChance = true;
				} else if (whosPlaying == 2 && !p1Stopped) {
					act.winMessage("PLAYER 2 GOT 21 AND WON THE GAME!");
					p2WinsRound();
					roundFinal();
				}
			}
		}
	}

	/**
	 * Check if the score limit have been reached.
	 */
	private void checkGameFinal() {
		if (p1Total.getScore() == scoreLimit) {
			try {
				act.playApplause();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WIN.winGame.setText("CONGRATULATIONS, PLAYER 1 WINS!");
			WIN.show();
		} else if (p2Total.getScore() == scoreLimit) {
			try {
				act.playApplause();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WIN.winGame.setText("CONGRATULATIONS, PLAYER 2 WINS!");
			WIN.show();
		}
	}

	/**
	 * Translates the card got to displays the correct card in screen.
	 * @param suit
	 * @param value
	 * @param cardPanel
	 * @return <b>value</b>
	 */
	private int decideCard(String suit, int value, HBox cardPanel) {
		boolean found = false;
		for (int i=0; i<52 && !found; i++) {
			if (value-1 == i) {
				found = true;
				switch (suit) {
				case "C":
					break;
				case "D":
					i+=13;
					break;
				case "H":
					i+=26;
					break;
				case "S":
					i+=39;
					break;
				default:
					break;
				}
				if (firstCardD && whosPlaying == 0) {
					cardPanel.getChildren().add(act.createFlipPane(i));
				} else {
					cardPanel.getChildren().add(new ImageView(SwingFXUtils.toFXImage(sprites.get(i), null)));
				}
			}
		}
		switch (value) {
		case 1:
			value = aceValue;
			break;
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
		return value;
	}

	/**
	 * Check if the round ends when last chance is applied.
	 *
	 * <P><i>Last Chance = when one player gets 21, the other one has 1 chance to equal.<P></i>
	 */
	private void checkRoundFinal() {
		if (p1Total.getRound() > p2Total.getRound()) {
			act.winMessage("PLAYER 1 WINS!");
			p1WinsRound();
			roundFinal();
		} else if ((p1Total.getRound() < p2Total.getRound())) {
			act.winMessage("PLAYER 2 WINS!");
			p2WinsRound();
			roundFinal();
		} else {
			act.winMessage("IT'S A PUSH, NOBODY WINS!");
			roundFinal();
		}
	}

	private void wakeUpDealer() {
		whosPlaying = 0;
		act.flip();
		if (dTotal.getRound() >= 17) {
			dealer = false;
			act.accurateMessage("DEALER HAS STOPPED");
			checkStop();
		}
	}

	/**
	 * Handles the continue action, changing player turn or not.
	 */
	private void continueAction() {
		if (whosPlaying == 1 && !p2Stopped && !p2Busted) {
			MULTI.roundLabel.setText("PLAYER 2 TURN");
			MULTI.continue1But.setDisable(true);
			MULTI.stop1But.setDisable(true);
			MULTI.continue2But.setDisable(false);
			MULTI.stop2But.setDisable(false);
		} else if (whosPlaying == 2 && !p1Stopped && !p1Busted) {
			MULTI.roundLabel.setText("PLAYER 1 TURN");
			MULTI.continue1But.setDisable(false);
			MULTI.stop1But.setDisable(false);
			MULTI.continue2But.setDisable(true);
			MULTI.stop2But.setDisable(true);
		}

		if (bothSaid) {
			initTime = false;
		} else {
			bothSaid = true;
		}

		if (!initTime && !roundEnd) {
			MULTI.getBut.setDisable(false);
			MULTI.continue1But.setDisable(true);
			MULTI.stop1But.setDisable(true);
			MULTI.continue2But.setDisable(true);
			MULTI.stop2But.setDisable(true);
			initTime = true;
		}

		changePlayer();
	}

	/**
	 * Contains the logic to change player or not.
	 */
	private void changePlayer() {
		if (whosPlaying == 1 && !p2Stopped && !p2Busted) {
			whosPlaying = 2;
		} else if (whosPlaying == 2 && !p1Stopped && !p1Busted) {
			whosPlaying = 1;
		}
	}

	/**
	 * Handles the stop action, assigning stop status to player.
	 */
	private void stopAction() {
		if (whosPlaying == 2) {
			p2Stopped = true;
			act.accurateMessage("Player 2 stopped!");
		} else {
			p1Stopped = true;
			act.accurateMessage("Player 1 stopped!");
		}

		if (gameMode == 1 && p1Stopped && p2Stopped) {
			checkRoundFinal();
		} else if (gameMode == 0 && p1Stopped && p2Stopped) {
			checkStop();
		}

		continueAction();
	}

	/**
	 * Used when both players win.
	 */
	private void p1p2WinRound() {
		MULTI.roundLabel.setText("BOTH PLAYERS WIN!");
		int score1 = p1Total.getScore()+1;
		int score2 = p2Total.getScore()+1;
		p1Total.setScore(score1);
		p2Total.setScore(score2);
		MULTI.p1ScoreValue.setText(Integer.toString(score1));
		MULTI.p2ScoreValue.setText(Integer.toString(score2));
	}

	/**
	 * Used when player 1 wins.
	 */
	private void p1WinsRound() {
		MULTI.roundLabel.setText("PLAYER 1 WIN!");
		int score = p1Total.getScore()+1;
		p1Total.setScore(score);
		MULTI.p1ScoreValue.setText(Integer.toString(score));
	}

	/**
	 * Used when player 2 wins.
	 */
	private void p2WinsRound() {
		MULTI.roundLabel.setText("PLAYER 2 WINS!");
		int score = p2Total.getScore()+1;
		p2Total.setScore(score);
		MULTI.p2ScoreValue.setText(Integer.toString(score));
	}

	/**
	 * Handles the round ending, preparing game to another round.
	 */
	private void roundFinal() {
		MULTI.getBut.setDisable(true);
		MULTI.continue1But.setDisable(true);
		MULTI.stop1But.setDisable(true);
		MULTI.continue2But.setDisable(true);
		MULTI.stop2But.setDisable(true);
		roundEnd = true;
	}

	/**
	 * Resets the scoreboard to 0.
	 */
	private void resetScoreboard() {
		p1Total.setScore(0);
		p2Total.setScore(0);
		MULTI.p1ScoreValue.setText(Integer.toString(p1Total.getScore()));
		MULTI.p2ScoreValue.setText(Integer.toString(p2Total.getScore()));
	}

	/**
	 * Resets the round and all components and logic stuff.
	 */
	private void resetRound() {
		act = new Actions();

		round = 1;

		p1Total.setRound(0);
		p1Total.setHand(new ArrayList<String>());
		p2Total.setRound(0);
		p2Total.setHand(new ArrayList<String>());
		dTotal.setRound(0);
		dTotal.setHand(new ArrayList<String>());

		deck.setDeck(act.mountDeck());
		deck.setCards(51);
		act.shuffleDeck(deck.getDeck());
		if (!retMenu) {
			MULTI.deck = act.createDeckImg(Game.sprites.get(52));
		} else {
			retMenu = false;
		}

		whosPlaying = 1;
		p1Stopped = false;
		p1Busted = false;
		p2Stopped = false;
		p2Busted = false;
		bothSaid = false;
		roundEnd = false;
		firstCardD = true;
		initTime = true;
		lastChance = false;
		dealer = true;

		MULTI.roundLabel.setText("DISTRIBUTE CARDS");

		MULTI.getBut.setDisable(true);
		MULTI.restartRoundBut.setDisable(true);
		MULTI.distBut.setDisable(false);
		MULTI.continue1But.setDisable(true);
		MULTI.stop1But.setDisable(true);
		MULTI.continue2But.setDisable(true);
		MULTI.stop2But.setDisable(true);

		MULTI.cardsD.getChildren().clear();
		MULTI.cards1.getChildren().clear();
		MULTI.cards2.getChildren().clear();
	}
}
