package model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

	private List<String> deck = new ArrayList<String>();
	private int cards = 51;
	
	public void CardModel (List<String> dk, int cd) {
		deck = dk;
		cards = cd;
	}

	public List<String> getDeck() {
		return deck;
	}

	public void setDeck(List<String> deck) {
		this.deck = deck;
	}

	public int getCards() {
		return cards;
	}

	public void setCards(int cards) {
		this.cards = cards;
	}
}
