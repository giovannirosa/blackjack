package model;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	private String name = "Dealer";
	private List<String> hand = new ArrayList<String>();
	private int round;
	private boolean busted;
	private boolean active;
	private boolean firstCard;
	Chips chips = new Chips(0,0,0,0);

	public Dealer(int val, boolean bt, boolean sp, boolean fc) {
		setRound(val);
		setBusted(bt);
		setActive(sp);
		setFirstCard(fc);
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public List<String> getHand() {
		return hand;
	}

	public void setHand(List<String> hand) {
		this.hand = hand;
	}

	public boolean isBusted() {
		return busted;
	}

	public void setBusted(boolean busted) {
		this.busted = busted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Chips getChips() {
		return chips;
	}

	public void setChips(Chips chips) {
		this.chips = chips;
	}

	public boolean isFirstCard() {
		return firstCard;
	}

	public void setFirstCard(boolean firstCard) {
		this.firstCard = firstCard;
	}
}
