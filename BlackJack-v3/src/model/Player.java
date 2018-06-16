package model;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<String> hand = new ArrayList<String>();
	private int round;
	private boolean busted;
	private boolean stopped;
	Chips chips = new Chips(10,8,6,2);
	Chips chipsBet = new Chips(0,0,0,0);

	public Player(String nm, int val, boolean bt, boolean sp) {
		setName(nm);
		setRound(val);
		setBusted(bt);
		setStopped(sp);
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

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Chips getChips() {
		return chips;
	}

	public void setChips(Chips chips) {
		this.chips = chips;
	}

	public Chips getChipsBet() {
		return chipsBet;
	}

	public void setChipsBet(Chips chipsBet) {
		this.chipsBet = chipsBet;
	}

}
