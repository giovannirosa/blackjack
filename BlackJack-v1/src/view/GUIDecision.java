package view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIDecision extends JOptionPane {

	public JButton stopBut = new JButton("Stop");
	public JButton continueBut = new JButton("Continue");
	
	public JLabel newValue = new JLabel();
	public JLabel newSuit = new JLabel();
	JLabel messageDec = new JLabel("Do you want to Continue or Stop?");
	public JLabel whoSays = new JLabel();
	public JLabel messageCard = new JLabel("You got: ");
	public JLabel amountLabel = new JLabel();
	
	public JPanel geralPanel = new JPanel();
	
//	JPanel buttonPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	JPanel whoSaysPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardLine2 = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardLineD = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel cardLineGeral = new JPanel (new FlowLayout (FlowLayout.CENTER));
	JPanel decLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	public JPanel amtLine = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public GUIDecision() {
		initGUI();
	}
	
	public void initGUI() {
//		this.setTitle("Decision");
//		this.setLayout(new BorderLayout());
//		this.setSize(300,200);
//		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.setLocationRelativeTo(null);
		
		geralPanel.setLayout(new BoxLayout (geralPanel, BoxLayout.PAGE_AXIS));
		
//		buttonPanel.add(continueBut);
//		buttonPanel.add(stopBut);
		
		whoSaysPanel.add(whoSays);
		cardLineGeral.add(messageCard);
		cardLineGeral.add(cardLine);
		decLine.add(messageDec);
		amtLine.add(amountLabel);
		
		geralPanel.add(whoSaysPanel);
		geralPanel.add(cardLineGeral);
		geralPanel.add(decLine);
		geralPanel.add(amtLine);
//		geralPanel.add(buttonPanel);
		
//		this.add(geralPanel);
//		this.setAutoRequestFocus(true);
	}
	
	public int decisionMessage() {
		String[] options = new String[2];
		options[0] = new String("Continue");
		options[1] = new String("Stop");
		return JOptionPane.showOptionDialog(null,geralPanel,"Decision", 0,JOptionPane.PLAIN_MESSAGE,null,options,null);
	}
	
}
