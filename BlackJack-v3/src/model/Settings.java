package model;

public class Settings {

	private String gameType; // Singleplayer, Multiplayer
	private String gameMode; // Normal, Versus
	private int reshuffle; // Rounds to reshuffle deck
	
	public Settings(String gt, String gm, int rs) {
		setGameType(gt);
		setGameMode(gm);
		setReshuffle(rs);
	}
	
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getGameMode() {
		return gameMode;
	}
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
	public int getReshuffle() {
		return reshuffle;
	}
	public void setReshuffle(int reshuffle) {
		this.reshuffle = reshuffle;
	}
	
}
