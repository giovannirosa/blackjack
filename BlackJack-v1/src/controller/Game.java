package controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Deck;
import model.Total;
import view.GUICustomize;
import view.GUIDecision;
import view.GUIMenu;
import view.GUIMultiplayer;
import view.GUIWin;

public class Game {

	GUIMenu MENU = new GUIMenu();
	GUIMultiplayer MULTI = new GUIMultiplayer();
	GUICustomize CUSTOM = new GUICustomize();
	GUIDecision DECISION = new GUIDecision();
	GUIWin WIN = new GUIWin();

	Actions act = new Actions();
	Total dTotal = new Total();
	Total p1Total = new Total();
	Total p2Total = new Total();
	Deck deck = new Deck();

	boolean p1Playing = true;
	boolean p1Stopped = false;
	boolean p1Busted = false;
	boolean p2Stopped = false;
	boolean p2Busted = false;
	boolean bothSaid = false;
	boolean roundEnd = false;
	boolean whosPlaying;
	boolean firstCard1 = true;
	boolean firstCard2 = true;
	boolean firstCardD = true;
	boolean secondCardD = false;
	boolean initTime = true;
	boolean lastChance = false;
	boolean dealer = true;
	boolean dealerTurn = true;

	int round = 1;
	int turn = 0; // 1 = P1; 2 = P2; 0 = D;

	// CUSTOMS
	public static int gameMode = 0;
	int aceValue = 1;
	int scoreLimit = 10;
	int reshuffle = 1;

	Image heartImg = ImageIO.read(new File("imgs/Game-hearts.png"));
	Image clubImg = ImageIO.read(new File("imgs/Game-clubs.png"));
	Image diamondImg = ImageIO.read(new File("imgs/Game-diamond.png"));
	Image spadesImg = ImageIO.read(new File("imgs/Game-spades.png"));
	Image hiddenImg = ImageIO.read(new File("imgs/hidden.png"));

	public Game() throws IOException {
		deck.setDeck(act.mountDeck());
		configImages();
		setListeners();
		MENU.setVisible(true);
	}

	private void reinitGame() throws IOException {
		MULTI = new GUIMultiplayer();
		DECISION = new GUIDecision();

		p1Playing = true;
		p1Stopped = false;
		p2Stopped = false;
		bothSaid = false;
		roundEnd = false;
		firstCard1 = true;
		firstCard2 = true;
		initTime = true;
		lastChance = false;
		dealer = true;
		dealerTurn = true;

		deck.setDeck(act.mountDeck());
		setListeners();
		resetRound();
		resetScoreboard();
	}

	private void configImages() {
		heartImg = heartImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		spadesImg = spadesImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		clubImg = clubImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		diamondImg = diamondImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		hiddenImg = hiddenImg.getScaledInstance(30, 20, Image.SCALE_SMOOTH);
	}

	private void setListeners() {
		MULTI.getBut.addActionListener(e -> {
			play();
		});

		MULTI.shuffleBut.addActionListener(e -> {
			act.shuffleDeck(deck.getDeck());
			for (String tmp:deck.getDeck()) {
				System.out.println(tmp);
			}
			MULTI.startBut.setEnabled(true);
			MULTI.messageStart.setVisible(true);
		});

		MULTI.startBut.addActionListener(e -> {
			MULTI.initGameGUI();
		});

		MULTI.distBut.addActionListener(e -> {
			// distribute 2 cards each
			if (gameMode == 0) {
				for (int i = 0; i < 6; i++) {
					play();
					if (i != 0 && i != 3) {
						p1Playing = !p1Playing;
					}
				}
			} else {
				for (int i = 0; i < 4; i++) {
					play();
					p1Playing = !p1Playing;
				}
			}
			DECISION.whoSays.setText("Player 1 says!");

			MULTI.getBut.setEnabled(true);
			MULTI.restartRoundBut.setEnabled(true);
			MULTI.distBut.setEnabled(false);
			if (!roundEnd) {
				if (DECISION.decisionMessage() == 0) {
					DECISION.continueBut.doClick();
				} else {
					DECISION.stopBut.doClick();
				}
			} else {
				MULTI.getBut.setEnabled(false);
			}
		});

		MULTI.restartRoundBut.addActionListener(e -> resetRound());

		MULTI.menuRestart.addActionListener(e -> {
			int dialogResult = act.confirmMessage("Do you really want to restart the game?\nThis will restart the scoreboard!");
			if (dialogResult == JOptionPane.YES_OPTION) {
				resetRound();
				resetScoreboard();
			}
		});

		MULTI.menuReturn.addActionListener(e -> {
			int dialogResult = act.confirmMessage("Do you really want to return to menu?\nYou'll lose any game progression!");
			if (dialogResult == JOptionPane.YES_OPTION) {
				MULTI.dispose();
				try {
					MULTI = new GUIMultiplayer();
					reinitGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				MENU.setVisible(true);
			}
		});

		MULTI.menuExit.addActionListener(e -> {
			int dialogResult = act.confirmMessage("Do you really want to exit?");
			if (dialogResult == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});

		DECISION.stopBut.addActionListener(e -> {
			whosPlaying = whoPlays();
			if (!whosPlaying) {
				p2Stopped = true;
				JOptionPane.showMessageDialog(null,"Player 2 stopped!","",JOptionPane.INFORMATION_MESSAGE);
			} else {
				p1Stopped = true;
				JOptionPane.showMessageDialog(null,"Player 1 stopped!","",JOptionPane.INFORMATION_MESSAGE);
			}

			if (gameMode == 1 && p1Stopped && p2Stopped) {
				checkRoundFinal();
			} else if (gameMode == 0 && p1Stopped && p2Stopped) {
				checkStop();
			}

			decisionLogic();
		});

		DECISION.continueBut.addActionListener(e -> {
			whosPlaying = whoPlays();
			if (whosPlaying && !dealerTurn) {
				MULTI.roundLabel.setText("PLAYER 2 TURN");
			} else if (!whosPlaying && !dealerTurn) {
				MULTI.roundLabel.setText("PLAYER 1 TURN");
			} else if (turn == 1) {
				MULTI.roundLabel.setText("DEALER TURN");
			}
			decisionLogic();
		});

		MENU.spButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "Not working! Sorry :(");
		});

		MENU.mpButton.addActionListener(e -> {
			MULTI.setVisible(true);
			MENU.dispose();
		});

		MENU.customBut.addActionListener(e -> {
			CUSTOM.setVisible(true);
		});

		MENU.exitBut.addActionListener(e -> {
			MENU.dispose();
			System.exit(0);
		});

		CUSTOM.saveBut.addActionListener(e -> {
			gameMode = CUSTOM.getGameMode();
			aceValue = CUSTOM.getAceValue();
			scoreLimit = CUSTOM.getScoreLimit();
			MULTI.scoreLabel.setText("SCORE "+ scoreLimit +" POINTS TO WIN!");
			reshuffle = CUSTOM.getReshuffle();
			CUSTOM.dispose();
		});

		CUSTOM.restoreBut.addActionListener(e -> {
			CUSTOM.resetAll();
		});
	}

	private void decisionLogic() {
		p1Playing = !p1Playing;
//		DECISION.dispose();

		if (bothSaid) {
			initTime = false;
		} else {
			bothSaid = true;
		}

		whosPlaying = whoPlays();
		if (whosPlaying) {
			DECISION.whoSays.setText("Player 1 says!");
		} else {
			DECISION.whoSays.setText("Player 2 says!");
		}

		if (initTime) {
			DECISION.cardLineGeral.removeAll();
			DECISION.cardLineGeral.add(DECISION.messageCard);
			DECISION.cardLineGeral.add(DECISION.cardLine2);
			DECISION.cardLineGeral.revalidate();
			DECISION.cardLineGeral.repaint();

			if (DECISION.decisionMessage() == 0) {
				DECISION.continueBut.doClick();
			} else {
				DECISION.stopBut.doClick();
			}
		} else {
			DECISION.cardLineGeral.removeAll();
			DECISION.cardLineGeral.add(DECISION.messageCard);
			DECISION.cardLineGeral.add(DECISION.cardLine);
			DECISION.cardLineGeral.revalidate();
			DECISION.cardLineGeral.repaint();
		}
	}

	private boolean whoPlays() {
		return (p1Playing && !p1Stopped) || p2Stopped; // true for P1 and false for P2
	}

	private String getCard() {
		int numberCards = deck.getCards();
		int index = act.getRandom(numberCards);
		String card = deck.getDeck().get(index);
		deck.getDeck().remove(index);
		deck.setCards(numberCards-1);
		return card;
	}

	private void play() {
		int plus = 0;
		String card = getCard();
		int value = act.getValue(card);
		String suit = act.getSuit(card);
		whosPlaying = whoPlays(); // true for P1 and false for P2
		if (whosPlaying) {
			DECISION.whoSays.setText("Player 1 says!");
		} else {
			DECISION.whoSays.setText("Player 2 says!");
		}
		if (!initTime) {
			DECISION.cardLine.removeAll();
			DECISION.cardLine.add(DECISION.messageCard);
			DECISION.cardLine.add(DECISION.newSuit);
			DECISION.cardLine.add(DECISION.newValue);
			DECISION.cardLine.revalidate();
			DECISION.cardLine.repaint();
		}
		if (gameMode == 0) {
			if (dealer && dealerTurn) { // DEALER
				if (!firstCardD) {
					MULTI.cardPanelD.add(new JLabel("+"));
				} else {
					firstCardD = false;
				}
				value = decideSuitandValue(suit,value,MULTI.cardPanelD,DECISION.cardLineD);
				plus = dTotal.getRound()+value;
				MULTI.finalFieldD.setText(Integer.toString(plus));
				dTotal.setRound(plus);
				dealerTurn = false;
				// stop condition for dealer
				if (dTotal.getRound() >= 17) {
					dealer = false;
				}
			} else if (whosPlaying) { // P1
				if (!firstCard1) {
					MULTI.cardPanel.add(new JLabel("+"));
				} else {
					firstCard1 = false;
				}
				value = decideSuitandValue(suit,value,MULTI.cardPanel,DECISION.cardLine);
				plus = p1Total.getRound()+value;
				MULTI.finalField.setText(Integer.toString(plus));
				p1Total.setRound(plus);
			} else if (!whosPlaying) { // P2
				if (!firstCard2) {
					MULTI.cardPanel2.add(new JLabel("+"));
				} else {
					firstCard2 = false;
				}
				value = decideSuitandValue(suit,value,MULTI.cardPanel2,DECISION.cardLine2);
				plus = p2Total.getRound()+value;
				MULTI.finalField2.setText(Integer.toString(plus));
				p2Total.setRound(plus);
				round++;
				if (dealer) {
					dealerTurn = true;
				}
			}
		} else {
			if (whosPlaying) { // P1
				if (!firstCard1) {
					MULTI.cardPanel.add(new JLabel("+"));
				} else {
					firstCard1 = false;
				}
				value = decideSuitandValue(suit,value,MULTI.cardPanel,DECISION.cardLine);
				plus = p1Total.getRound()+value;
				MULTI.finalField.setText(Integer.toString(plus));
				p1Total.setRound(plus);
			} else { // P2
				if (!firstCard2) {
					MULTI.cardPanel2.add(new JLabel("+"));
				} else {
					firstCard2 = false;
				}
				value = decideSuitandValue(suit,value,MULTI.cardPanel2,DECISION.cardLine2);
				plus = p2Total.getRound()+value;
				MULTI.finalField2.setText(Integer.toString(plus));
				p2Total.setRound(plus);
				round++;
			}
		}

		MULTI.revalidate();
		MULTI.repaint();

		if (gameMode == 1 && lastChance) {
			checkRoundFinal();
		}

		checkBusts(plus);

		checkStop();

		showDecision(plus);

		MULTI.revalidate();
		MULTI.repaint();

		checkGameFinal();

		if (reshuffle == 1) {
			// Shuffle again after every play
			act.shuffleDeck(deck.getDeck());
		} else if (reshuffle == 2 && round/2 == 0) {
			// Shuffle again after 2 plays
			act.shuffleDeck(deck.getDeck());
			round = 1;
		}

		turn++;

		if (turn == 3 && dealer) {
			turn = 0;
		} else if (turn == 3 && !dealer) {
			turn = 1;
		}

	}

	private void checkStop() {
		boolean p1Wins = false, p2Wins = false, p1Draws = false, p2Draws = false;
		if (gameMode == 0) {
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
					act.loseMessage("NOBODY WINS!");
					MULTI.roundLabel.setText("NOBODY WINS!");
				} else if (!p1Wins && !p2Wins && p1Draws && p2Draws) {
					act.winMessage("IT'S A PUSH, NOBODY WINS, NOBODY LOSES!");
				}
			}
		} else {
			if (!roundEnd) {
				if (whosPlaying && p1Total.getRound() > p2Total.getRound() && p2Stopped) {
					try {
						act.playApplause();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					act.winMessage("PLAYER 1 WINS!");
					p1WinsRound();
					roundFinal();
				} else if (!whosPlaying && p1Total.getRound() < p2Total.getRound() && p1Stopped) {
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

	private void showDecision(int plus) {
		if (!roundEnd && !initTime && turn != 0) {
			DECISION.amountLabel.setText("Your amount is "+plus+"!");
			DECISION.amtLine.revalidate();
			DECISION.amtLine.repaint();
			if (whosPlaying && !p1Stopped) {
				if (DECISION.decisionMessage() == 0) {
					DECISION.continueBut.doClick();
				} else {
					DECISION.stopBut.doClick();
				}
			} else if (!whosPlaying && !p2Stopped) {
				if (DECISION.decisionMessage() == 0) {
					DECISION.continueBut.doClick();
				} else {
					DECISION.stopBut.doClick();
				}
			}
		}
	}

	private void checkBusts(int plus) {
		// check lose/win
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
				if (p1Busted && p2Busted) {
					act.loseMessage("BOTH PLAYERS GOT BUSTED! DEALER WINS!");
				}

				if (!p1Busted && p1Total.getRound() > 21) {
					act.loseMessage("PLAYER 1 GOT OVER 21 AND IS OUT!");
					p1Busted = true;
					p1Stopped = true;
				}
				if (!p2Busted && p2Total.getRound() > 21) {
					act.loseMessage("PLAYER 2 GOT OVER 21 AND IS OUT!");
					p2Busted = true;
					p2Stopped = true;
				}
			}
		} else {
			if (plus > 21) {
				try {
					act.playBoo();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (whosPlaying) {
					act.loseMessage("PLAYER 1 GOT OVER 21!");
					p2WinsRound();
				} else {
					act.loseMessage("PLAYER 2 GOT OVER 21!");
					p1WinsRound();
				}
				roundFinal();
			} else if (plus == 21) {
				if (whosPlaying && !p2Stopped) {
					act.accurateMessage("PLAYER 1 GOT 21!\nPLAYER 2 HAVE ONE CHANCE TO EQUAL!");
					p1Stopped = true;
					lastChance = true;
				} else if (!whosPlaying && !p1Stopped) {
					act.winMessage("PLAYER 2 GOT 21 AND WON THE GAME!");
					p2WinsRound();
					roundFinal();
				}
			}
		}
	}

	private void checkGameFinal() {
		if (p1Total.getScore() == scoreLimit) {
			try {
				act.playApplause();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WIN.winGame.setText("CONGRATULATIONS, PLAYER 1 WINS!");
			WIN.setVisible(true);
		} else if (p2Total.getScore() == scoreLimit) {
			try {
				act.playApplause();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WIN.winGame.setText("CONGRATULATIONS, PLAYER 2 WINS!");
			WIN.setVisible(true);
		}
	}

	private int decideSuitandValue(String suit, int value, JPanel cardPanel, JPanel cardLine) {
		switch (suit) {
		case "S":
			cardPanel.add(new JLabel(new ImageIcon(spadesImg)));
			if (initTime) {
				cardLine.add(new JLabel(new ImageIcon(spadesImg)));
			} else {
				DECISION.newSuit.setIcon(new ImageIcon(spadesImg));
			}
			break;
		case "H":
			cardPanel.add(new JLabel(new ImageIcon(heartImg)));
			if (initTime) {
				cardLine.add(new JLabel(new ImageIcon(heartImg)));
			} else {
				DECISION.newSuit.setIcon(new ImageIcon(heartImg));
			}
			break;
		case "D":
			cardPanel.add(new JLabel(new ImageIcon(diamondImg)));
			if (initTime) {
				cardLine.add(new JLabel(new ImageIcon(diamondImg)));
			} else {
				DECISION.newSuit.setIcon(new ImageIcon(diamondImg));
			}
			break;
		case "C":
			cardPanel.add(new JLabel(new ImageIcon(clubImg)));
			if (initTime) {
				cardLine.add(new JLabel(new ImageIcon(clubImg)));
			} else {
				DECISION.newSuit.setIcon(new ImageIcon(clubImg));
			}
			break;
		default:
			break;
		}
		switch (value) {
		case 1:
			cardPanel.add(new JLabel("A"));
			value = aceValue;
			if (initTime) {
				cardLine.add(new JLabel("A"));
			} else {
				DECISION.newValue.setText("A");
			}
			break;
		case 11:
			cardPanel.add(new JLabel("J"));
			value = 10;
			if (initTime) {
				cardLine.add(new JLabel("J"));
			} else {
				DECISION.newValue.setText("J");
			}
			break;
		case 12:
			cardPanel.add(new JLabel("Q"));
			value = 10;
			if (initTime) {
				cardLine.add(new JLabel("Q"));
			} else {
				DECISION.newValue.setText("Q");
			}
			break;
		case 13:
			cardPanel.add(new JLabel("K"));
			value = 10;
			if (initTime) {
				cardLine.add(new JLabel("K"));
			} else {
				DECISION.newValue.setText("K");
			}
			break;
		default:
			cardPanel.add(new JLabel(Integer.toString(value)));
			if (initTime) {
				cardLine.add(new JLabel(Integer.toString(value)));
			} else {
				DECISION.newValue.setText(Integer.toString(value));
			}
		}
		return value;
	}

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

	private void p1p2WinRound() {
		MULTI.roundLabel.setText("BOTH PLAYERS WIN!");
		int score1 = p1Total.getScore()+1;
		int score2 = p2Total.getScore()+1;
		p1Total.setScore(score1);
		p2Total.setScore(score2);
		MULTI.p1ScoreValue.setText(Integer.toString(score1));
		MULTI.p2ScoreValue.setText(Integer.toString(score2));
		MULTI.scoreMainPanel.revalidate();
		MULTI.scoreMainPanel.repaint();
	}

	private void p1WinsRound() {
		MULTI.roundLabel.setText("PLAYER 1 WIN!");
		int score = p1Total.getScore()+1;
		p1Total.setScore(score);
		MULTI.p1ScoreValue.setText(Integer.toString(score));
		MULTI.scoreMainPanel.revalidate();
		MULTI.scoreMainPanel.repaint();
	}

	private void p2WinsRound() {
		MULTI.roundLabel.setText("PLAYER 2 WINS!");
		int score = p2Total.getScore()+1;
		p2Total.setScore(score);
		MULTI.p2ScoreValue.setText(Integer.toString(score));
		MULTI.scoreMainPanel.revalidate();
		MULTI.scoreMainPanel.repaint();
	}

	private void roundFinal() {
		MULTI.getBut.setEnabled(false);
		DECISION.stopBut.setEnabled(false);
		roundEnd = true;
	}

	private void resetScoreboard() {
		p1Total.setScore(0);
		p2Total.setScore(0);
		MULTI.p1ScoreValue.setText(Integer.toString(p1Total.getScore()));
		MULTI.p2ScoreValue.setText(Integer.toString(p2Total.getScore()));
		MULTI.scoreMainPanel.revalidate();
		MULTI.scoreMainPanel.repaint();
	}

	private void resetRound() {
		DECISION.newSuit.setIcon(null);
		DECISION.newValue.setText("");
		MULTI.finalField.setText("");
		MULTI.finalField2.setText("");
		MULTI.finalFieldD.setText("");
		DECISION.amountLabel.setText("");
		turn = 0;
		round = 1;
		p1Total.setRound(0);
		p2Total.setRound(0);
		dTotal.setRound(0);
		deck.setDeck(act.mountDeck());
		deck.setCards(51);
		act.shuffleDeck(deck.getDeck());
		MULTI.getBut.setEnabled(true);
		DECISION.stopBut.setEnabled(true);
		MULTI.roundLabel.setText("PLAYER 1 ROUND");
		p1Playing = true;
		p1Stopped = false;
		p1Busted = false;
		p2Stopped = false;
		p2Busted = false;
		roundEnd = false;
		firstCard1 = true;
		firstCard2 = true;
		firstCardD = true;
		initTime = true;
		bothSaid = false;
		lastChance = false;
		dealer = true;
		dealerTurn = true;
		MULTI.getBut.setEnabled(false);
		MULTI.restartRoundBut.setEnabled(false);
		MULTI.distBut.setEnabled(true);
		MULTI.cardPanelD.removeAll();
		MULTI.cardPanel.removeAll();
		MULTI.cardPanel2.removeAll();
		DECISION.cardLineD.removeAll();
		DECISION.cardLine.removeAll();
		DECISION.cardLine2.removeAll();
		MULTI.gameMainPanel.revalidate();
		MULTI.gameMainPanel.repaint();
		DECISION.cardLineGeral.add(DECISION.messageCard);
		DECISION.revalidate();
		DECISION.repaint();
	}
}
