package model;

import java.util.ArrayList;
import java.util.List;

public class Total {

	private List<String> hand = new ArrayList<String>();
	private int round = 0;
	private int score = 0;

	public void TotalModel(List<String> hd, int val, int scr) {
		setHand(hd);
		setRound(val);
		setScore(scr);
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getHand() {
		return hand;
	}

	public void setHand(List<String> hand) {
		this.hand = hand;
	}

}
