package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;

public class Deck {

	private Map<String, ImageView> cardsMap = new HashMap<String, ImageView>();
	private List<String> deck = new ArrayList<String>();
	private int cards;

	public Deck(int cd) {
		cards = cd;
	}

	public int getCards() {
		return cards;
	}

	public void setCards(int cards) {
		this.cards = cards;
	}

	public Map<String, ImageView> getCardsMap() {
		return cardsMap;
	}

	public void setCardsMap(Map<String, ImageView> cardsMap) {
		this.cardsMap = cardsMap;
	}

	public List<String> getDeck() {
		return deck;
	}

	public void setDeck(List<String> deck) {
		this.deck = deck;
	}
}
