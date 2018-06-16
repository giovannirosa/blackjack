package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import controller.Actions;
import controller.Game;

@SuppressWarnings("serial")
public class GUIMultiplayer extends JFrame {
	
	public JButton shuffleBut = new JButton("Shuffle");
	public JButton startBut = new JButton("Start Game");
	public JButton getBut = new JButton("Get Card");
	public JButton distBut = new JButton("Distribute Cards");
	public JButton restartRoundBut = new JButton("Restart Round");
	
	JLabel dealerLabel = new JLabel("DEALER : ");
	JLabel p1Label = new JLabel("PLAYER 1:");
	JLabel p2Label = new JLabel("PLAYER 2:");
	public JLabel roundLabel = new JLabel("PLAYER 1 ROUND");
	
	public JLabel scoreLabel = new JLabel("SCORE 10 POINTS TO WIN!");
	JLabel p1ScoreLabel = new JLabel("P1:");
	public JLabel p1ScoreValue = new JLabel("0");
	JLabel p2ScoreLabel = new JLabel("P2:");
	public JLabel p2ScoreValue = new JLabel("0");
	
	JLabel equalLabel = new JLabel("=");
	JLabel equalLabel2 = new JLabel("=");
	JLabel equalLabelD = new JLabel("=");
	
	JLabel messageReady = new JLabel("The deck is ready and presents all cards from a normal deck.");
	JLabel messageShuffle = new JLabel("Please click on button below to Shuffle the cards.");
	public JLabel messageStart = new JLabel("Great! You can Start the game now or Shuffle again if wanted.");
	
	public JTextField finalField = new JTextField(5);
	public JTextField finalField2 = new JTextField(5);
	public JTextField finalFieldD = new JTextField(5);
	
	JMenuBar menuBar = new JMenuBar();
	public JMenuItem menuRestart = new JMenuItem("Restart Game");
	public JMenuItem menuReturn = new JMenuItem("Return Menu");
	public JMenuItem menuExit = new JMenuItem("Exit");
	JMenu menuOptions = new JMenu("Options");
	
	Actions act = new Actions();
	
	Image iconImg = ImageIO.read(new File("imgs/Nut.png"));
	
	JPanel geralPanel = new JPanel();

	public JPanel scoreMainPanel = new JPanel (new GridLayout (1, 1));
	public JPanel gameMainPanel = new JPanel ();
	
	JPanel buttonPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	JPanel scorePanel = new JPanel();
	JPanel messageScorePanel = new JPanel(new FlowLayout (FlowLayout.CENTER));
	JPanel p1ScorePanel = new JPanel(new FlowLayout (FlowLayout.CENTER));
	JPanel p2ScorePanel = new JPanel(new FlowLayout (FlowLayout.CENTER));
	
	public JPanel cardPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardPanel2 = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardPanelD = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	JPanel line0 = new JPanel (new GridLayout (1, 1));
	JPanel zeroLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	JPanel organize = new JPanel(new FlowLayout (FlowLayout.CENTER));
	JPanel line1 = new JPanel (new GridLayout (1, 1));
	JPanel firstLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	JPanel organize2 = new JPanel(new FlowLayout (FlowLayout.CENTER));
	JPanel line2 = new JPanel (new GridLayout (1, 1));
	JPanel secondLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public JPanel organizeD = new JPanel(new FlowLayout (FlowLayout.CENTER));
	public JPanel lineD = new JPanel (new GridLayout (1, 1));
	
	public GUIMultiplayer() throws IOException {
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Multiplayer Game");
		this.setLayout(new BorderLayout());
		this.setSize(1000,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(iconImg);

		startBut.setEnabled(false);
		getBut.setEnabled(false);
		restartRoundBut.setEnabled(false);

		messageStart.setVisible(false);

		Border borda = BorderFactory.createTitledBorder("GAME");

		finalField.setEditable(false);
		finalField.setHorizontalAlignment(JTextField.CENTER);
		finalField2.setEditable(false);
		finalField2.setHorizontalAlignment(JTextField.CENTER);
		finalFieldD.setEditable(false);
		finalFieldD.setHorizontalAlignment(JTextField.CENTER);

		geralPanel.setLayout(new BoxLayout (geralPanel, BoxLayout.Y_AXIS));
		scorePanel.setLayout(new BoxLayout (scorePanel, BoxLayout.Y_AXIS));
		gameMainPanel.setLayout(new BoxLayout (gameMainPanel, BoxLayout.Y_AXIS));
		
		buttonPanel.add(startBut);
		buttonPanel.add(shuffleBut);

		zeroLine.add(messageReady);
		line0.add(zeroLine);

		firstLine.add(messageShuffle);
		line1.add(firstLine);

		secondLine.add(messageStart);
		line2.add(secondLine);

		geralPanel.add(new JPanel());
		geralPanel.add(line0);
		geralPanel.add(line1);
		geralPanel.add(line2);
		geralPanel.add(buttonPanel);
		geralPanel.setBorder(borda);

		menuOptions.add(menuRestart);
		menuOptions.add(menuReturn);
		menuOptions.add(menuExit);
		menuBar.add(menuOptions);
		
		this.setJMenuBar(menuBar);
		this.add(geralPanel);
	}
	
	public void initGameGUI() {
		geralPanel.removeAll();
		geralPanel.setLayout(null);
		zeroLine.removeAll();
		firstLine.removeAll();
		organize.removeAll();
		buttonPanel.removeAll();
		line1.removeAll();
		secondLine.removeAll();
		organize2.removeAll();
		line2.removeAll();
		
		buttonPanel.add(distBut);
		buttonPanel.add(getBut);
		buttonPanel.add(restartRoundBut);
		
		messageScorePanel.add(scoreLabel);
		p1ScorePanel.add(p1ScoreLabel);
		p1ScorePanel.add(p1ScoreValue);
		p2ScorePanel.add(p2ScoreLabel);
		p2ScorePanel.add(p2ScoreValue);
		scorePanel.add(messageScorePanel);
		scorePanel.add(new JPanel());
		scorePanel.add(p1ScorePanel);
		scorePanel.add(p2ScorePanel);
		scorePanel.setBorder(BorderFactory.createTitledBorder("SCOREBOARD"));
		
		zeroLine.add(roundLabel);
		
		organizeD.add(dealerLabel);
		organizeD.add(cardPanelD);
		organizeD.add(equalLabelD);
		organizeD.add(finalFieldD);
		lineD.add(organizeD);
		
		
		organize.add(p1Label);
		organize.add(cardPanel);
		organize.add(equalLabel);
		organize.add(finalField);
		line1.add(organize);
		
		organize2.add(p2Label);
		organize2.add(cardPanel2);
		organize2.add(equalLabel2);
		organize2.add(finalField2);
		line2.add(organize2);
		
		scoreMainPanel.add(scorePanel);
		gameMainPanel.add(zeroLine);
		if (Game.gameMode == 0) {
			gameMainPanel.add(lineD);
		} else {
			gameMainPanel.add(new JPanel());
		}
		gameMainPanel.add(line1);
		gameMainPanel.add(line2);
		gameMainPanel.add(buttonPanel);
		
		geralPanel.setLayout(new BoxLayout (geralPanel, BoxLayout.X_AXIS));
		geralPanel.add(scoreMainPanel);
		geralPanel.add(gameMainPanel);
		geralPanel.revalidate();
		geralPanel.repaint();
	}
}
