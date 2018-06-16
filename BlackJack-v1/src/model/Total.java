package model;

public class Total {
	
	private int round = 0;
	private int score = 0;
	private int cards = 0;
	
	public void TotalModel(int val, int scr, int cd) {
		setRound(val);
		setScore(scr);
		setCards(cd);
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

	public int getCards() {
		return cards;
	}

	public void setCards(int cards) {
		this.cards = cards;
	}

}
