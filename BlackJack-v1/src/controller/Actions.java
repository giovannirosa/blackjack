package controller;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Actions {

	Icon loseImg = new ImageIcon("imgs/skull.png");
	Icon congrats = new ImageIcon("imgs/congrats.png");
	Icon TwoOneImg = new ImageIcon("imgs/21.png");

	/**
	 * Simply mount a deck of cards from 1 to 13:
	 * A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K.
	 * <P>Also applies the suits (S,H,D,C):
	 * spades, hearts, diamonds, clubs.<P>
	 */
	public List<String> mountDeck() {
		List<String> dk = new ArrayList<String>();
		String[] suits = {"S","H","D","C"}; // spades, hearts, diamonds, clubs
		for (int i = 1; i < 14; i++) {
			for (int j = 0; j < 4; j++) {
				dk.add(suits[j]+Integer.toString(i));
			}
		}
		return dk;
	}

	/**
	 * Randomly shuffle the cards using collections.
	 * @param deck
	 */
	public void shuffleDeck(List<String> deck) {
		Collections.shuffle(deck);
	}

	/**
	 * Retrieve a random number based on max value.
	 * @param max
	 * @return <b>int random</b>
	 */
	public int getRandom(int max) {
		return (int) (Math.random()*max) + 1;
	}

	/**
	 * Retrieve the numberic value of the given card.
	 * @param card
	 * @return <b>int number</b>
	 */
	public int getValue(String card) {
		return Integer.parseInt(card.substring(1));
	}

	public String getSuit(String card) {
		return card.substring(0, 1);
	}

	/**
	 * Set Nimbus Look and Feel if available.
	 * <P>Otherwise it will set System Look and Feel.
	 * <P>
	 */
	public void setNimbusLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Serif", Font.PLAIN, 15));
		UIManager.put("OptionPane.cancelButtonText", "nope");
	}

	/**
	 * Plays applause sound.
	 * @throws IOException
	 */
	public void playApplause() throws IOException {
		InputStream app = new FileInputStream("sounds/Applause.wav");
		AudioStream applauseStream = new AudioStream(app);
		AudioPlayer.player.start(applauseStream);
	}

	/**
	 * Plays boo sound.
	 * @throws IOException
	 */
	public void playBoo() throws IOException {
		InputStream boo = new FileInputStream("sounds/Boo.wav");
		AudioStream booStream = new AudioStream(boo);
		AudioPlayer.player.start(booStream);
	}

	/**
	 * Displays win message.
	 * @param message
	 */
	public void winMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE, congrats);
	}

	/**
	 * Displays lose message for player who marks more than 21.
	 * @param message
	 */
	public void loseMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE, loseImg);
	}

	/**
	 * Displays accurate message for player who marks 21.
	 * @param message
	 */
	public void accurateMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE, TwoOneImg);
	}

	/**
	 * Displays confirm dialog.
	 * @param message
	 */
	public int confirmMessage(String message) {
		String[] options = new String[2];
		options[0] = new String("Yes");
		options[1] = new String("No");
		return JOptionPane.showOptionDialog(null, message, "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,null);
	}
}
