package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIWin extends JFrame {
	
	JLabel winGif = new JLabel();
	public JLabel winGame = new JLabel();
	
	Icon winImg = new ImageIcon("imgs/win.gif");
	
	JPanel winningPanel = new JPanel();
	JPanel w0Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	JPanel w1Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
	
	public GUIWin() {
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Congratulations");
		this.setLayout(new BorderLayout());
		this.setSize(800,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		winningPanel.setLayout(new BoxLayout (winningPanel, BoxLayout.Y_AXIS));
		
		winGif.setIcon(winImg);
		w0Panel.add(winGame);
		w1Panel.add(winGif);
		winningPanel.add(new JPanel());
		winningPanel.add(w0Panel);
		winningPanel.add(w1Panel);
		
		this.add(winningPanel);
		this.pack();
	}
}
