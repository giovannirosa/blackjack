package model;

public class Game {
	boolean roundEnd = false;
	boolean initTime = true;
	boolean lastChance = false;
	boolean retMenu = false;
	int round = 1;
	
	public void setGame(boolean rd, boolean it, boolean lc, boolean rm, int ro) {
		roundEnd = rd;
		initTime = it;
		lastChance = lc;
		retMenu = rm;
		round = ro;
	}
	
	public boolean isRoundEnd() {
		return roundEnd;
	}
	public void setRoundEnd(boolean roundEnd) {
		this.roundEnd = roundEnd;
	}
	public boolean isInitTime() {
		return initTime;
	}
	public void setInitTime(boolean initTime) {
		this.initTime = initTime;
	}
	public boolean isLastChance() {
		return lastChance;
	}
	public void setLastChance(boolean lastChance) {
		this.lastChance = lastChance;
	}
	public boolean isRetMenu() {
		return retMenu;
	}
	public void setRetMenu(boolean retMenu) {
		this.retMenu = retMenu;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
}
