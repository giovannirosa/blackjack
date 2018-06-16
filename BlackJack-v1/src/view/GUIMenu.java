package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIMenu extends JFrame {
	
	public JButton spButton = new JButton("SINGLEPLAYER");
	public JButton mpButton = new JButton("MULTIPLAYER");
	public JButton customBut = new JButton("CUSTOMIZE");
	public JButton exitBut = new JButton("EXIT");
	
	Image menuImg = ImageIO.read(new File("imgs/menu.png"));
	Image iconImg = ImageIO.read(new File("imgs/Nut.png"));

	public GUIMenu() throws IOException {
		initGUI();
	}

	private void initGUI() throws IOException {
		this.setTitle("BlackJack gVersion 1.0");
		this.setLayout(new BorderLayout());
		this.setSize(800,600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(iconImg);

		JLayeredPane geralPanel = new JLayeredPane();
		JPanel spPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
		spPanel.setOpaque(false);
		JPanel mpPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
		mpPanel.setOpaque(false);
		JPanel customPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
		customPanel.setOpaque(false);
		JPanel exitPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
		exitPanel.setOpaque(false);

		menuImg = menuImg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		JLabel background = new JLabel(new ImageIcon(menuImg));

		spPanel.add(spButton);
		mpPanel.add(mpButton);
		customPanel.add(customBut);
		exitPanel.add(exitBut);

		geralPanel.add(background, 1);
		geralPanel.add(spPanel, 0);
		geralPanel.add(mpPanel, 0);
		geralPanel.add(customPanel, 0);
		geralPanel.add(exitPanel, 0);

		background.setSize(background.getPreferredSize());
		spPanel.setBounds(320, 120, 150, 100);
		mpPanel.setBounds(320, 160, 150, 100);
		customPanel.setBounds(320, 200, 150, 100);
		exitPanel.setBounds(320, 240, 150, 100);

		this.add(geralPanel);
	}
}
