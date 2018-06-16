package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class GUICustomize extends JFrame {

	JLabel gameMode = new JLabel("Game Mode:");
	JLabel aceValue = new JLabel("Ace Value:");
	JLabel scoreLimit = new JLabel("Score Limit:");
	JLabel reshuffleMode = new JLabel("Re-shuffle Mode:");
	
	JRadioButton agDealer = new JRadioButton("Against Dealer");
	JRadioButton agPlayer = new JRadioButton("Against Player");
	
	JRadioButton ace1 = new JRadioButton("1");
	JRadioButton ace11 = new JRadioButton("11");
	
	JRadioButton everyHand = new JRadioButton("Every Hand");
	JRadioButton every2Hands = new JRadioButton("Every 2 Hands");
	JRadioButton never = new JRadioButton("Never");
	
	JSlider scoreSlid = new JSlider(JSlider.HORIZONTAL,5,15,10);
	
	public JButton saveBut = new JButton("Save Changes");
	public JButton restoreBut = new JButton("Restore Default");
	
	Image iconImg = ImageIO.read(new File("imgs/config.png"));
	
	JPanel geralPanel = new JPanel();
	
	JPanel buttonPanel = new JPanel(new FlowLayout (FlowLayout.CENTER));
	
	public JPanel gamePanel = new JPanel();
	public JPanel acePanel = new JPanel();
	public JPanel scorePanel = new JPanel();
	public JPanel reshufflePanel = new JPanel();
	
	public JPanel gameLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel gameOpt = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public JPanel aceLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel aceOpt = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public JPanel scoreLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel scoreOpt = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public JPanel reshuffleLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel reshuffleOpt = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	
	public GUICustomize() throws IOException {
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Customize");
		this.setLayout(new BorderLayout());
		this.setSize(400,600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(iconImg);
		
		geralPanel.setLayout(new BoxLayout (geralPanel, BoxLayout.Y_AXIS));
		gamePanel.setLayout(new BoxLayout (gamePanel, BoxLayout.Y_AXIS));
		gamePanel.setBorder(BorderFactory.createTitledBorder("GAME MODE"));
		acePanel.setLayout(new BoxLayout (acePanel, BoxLayout.Y_AXIS));
		acePanel.setBorder(BorderFactory.createTitledBorder("ACE VALUE"));
		scorePanel.setLayout(new BoxLayout (scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createTitledBorder("SCORE LIMIT"));
		reshufflePanel.setLayout(new BoxLayout (reshufflePanel, BoxLayout.Y_AXIS));
		reshufflePanel.setBorder(BorderFactory.createTitledBorder("RE-SHUFFLE"));
		
		ButtonGroup gameGroup = new ButtonGroup();
		gameGroup.add(agDealer);
		gameGroup.add(agPlayer);
		agDealer.setSelected(true);
		
		ButtonGroup aceGroup = new ButtonGroup();
		aceGroup.add(ace1);
		aceGroup.add(ace11);
		ace1.setSelected(true);
		
		ButtonGroup reshuffleGroup = new ButtonGroup();
		reshuffleGroup.add(everyHand);
		reshuffleGroup.add(every2Hands);
		reshuffleGroup.add(never);
		everyHand.setSelected(true);
		
		scoreSlid.setMinorTickSpacing(1);
		scoreSlid.setMajorTickSpacing(5);
		scoreSlid.setPaintTicks(true);
		scoreSlid.setPaintLabels(true);
		
		gameLine.add(gameMode);
		aceLine.add(aceValue);
		scoreLine.add(scoreLimit);
		reshuffleLine.add(reshuffleMode);
		
		gameOpt.add(agDealer);
		gameOpt.add(agPlayer);
		
		aceOpt.add(ace1);
		aceOpt.add(ace11);
		
		scoreOpt.add(scoreSlid);
		
		reshuffleOpt.add(everyHand);
		reshuffleOpt.add(every2Hands);
		reshuffleOpt.add(never);
		
		gamePanel.add(gameLine);
		gamePanel.add(gameOpt);
		
		acePanel.add(aceLine);
		acePanel.add(aceOpt);
		
		scorePanel.add(scoreLine);
		scorePanel.add(scoreOpt);
		
		reshufflePanel.add(reshuffleLine);
		reshufflePanel.add(reshuffleOpt);
		
		buttonPanel.add(saveBut);
		buttonPanel.add(restoreBut);
		
		geralPanel.add(gamePanel);
		geralPanel.add(acePanel);
		geralPanel.add(scorePanel);
		geralPanel.add(reshufflePanel);
		geralPanel.add(buttonPanel);		
		
		this.add(geralPanel);
		
	}
	
	public void resetAll() {
		agDealer.setSelected(true);
		ace1.setSelected(true);
		everyHand.setSelected(true);
		scoreSlid.setValue(10);
	}
	
	public int getGameMode() {
		if (agDealer.isSelected()) {
			return 0;
		} else {
			return 1;
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
		return scoreSlid.getValue();
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
