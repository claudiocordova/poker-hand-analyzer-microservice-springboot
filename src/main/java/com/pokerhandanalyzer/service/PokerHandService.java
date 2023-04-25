package com.pokerhandanalyzer.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.pokerhandanalyzer.model.Card;
import com.pokerhandanalyzer.model.PokerHandAnalysis;

@Service
@XRayEnabled
public class PokerHandService {

	private enum Value {
		VALUE_ACE, 
		VALUE_2, 
		VALUE_3, 
		VALUE_4, 
		VALUE_5, 
		VALUE_6, 
		VALUE_7, 
		VALUE_8, 
		VALUE_9, 
		VALUE_10, 
		VALUE_JACK,
		VALUE_QUEEN, 
		VALUE_KING
	}

	private enum Suit {
		HEARTS, 
		CLUBS, 
		DIAMONDS, 
		SPADES
	}

	/*
	 * 
	 * @param pokerHand
	 * @return
	 * 
	 * 1) Validate the poker hand
	 * 2) If valid, then figure out if it is a straight
	 * 3) else return invalid 
	 * 
	 */
	public PokerHandAnalysis submitPokerHand(List<Card> pokerHand) {

		PokerHandAnalysis pokerHandAnalysis = null;

		pokerHandAnalysis = validatePokerHand(pokerHand);
		if (pokerHandAnalysis.getStatus().equals(PokerHandAnalysis.VALID_HAND)) {
			if (isStraight(pokerHand)) {
				pokerHandAnalysis.setMessage("It is a straight, lucky you man!");
			} else {
				pokerHandAnalysis.setMessage("It is NOT a straight!");
			}
			return pokerHandAnalysis;

		} else {
			return pokerHandAnalysis;
		}

	}
	
	public String health() {
		return "ok from service";
	}

	/*
	 * 
	 * 
	 * 1) Convert the list of cards into a sorted int array
	 * 2) First loop keeps track of all possible starts (1 to 10)
	 * 3) Second loop keeps checking if a particular start value becomes a straight
	 *    by incrementing strightCounter until it becomes 5.
	 * 
	 */	
	private boolean isStraight(List<Card> pokerHand) {

		List<Integer> list = converToNumericSortedList(pokerHand);

		for (int start = 1; start <= 10; start++) {
			int straightCounter = 0;
			for (int i = 0; i < list.size(); i++) {
				int value = list.get(i).intValue();
				if (value == start + straightCounter) {
					straightCounter++;
					if (straightCounter == 5) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/*
	 * 
	 * 
	 * 1) Convert the list of cards into a sorted int array
	 * 3) Add 14 and 1 in case of ACE to take into account wrap around case
	 * 
	 */
	private List<Integer> converToNumericSortedList(List<Card> pokerHand) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < pokerHand.size(); i++) {

			Card card = pokerHand.get(i);
			if (card.getValue().equalsIgnoreCase("ACE")) {
				list.add(Integer.valueOf(1));
				list.add(Integer.valueOf(14));
			} else if (card.getValue().equalsIgnoreCase("JACK")) {
				list.add(Integer.valueOf(11));
			} else if (card.getValue().equalsIgnoreCase("QUEEN")) {
				list.add(Integer.valueOf(12));
			} else if (card.getValue().equalsIgnoreCase("KING")) {
				list.add(Integer.valueOf(13));
			} else {
				list.add(Integer.valueOf(card.getValue()));
			}
		}

		list.sort(null);

		return list;
	}

	
	
	/*
	 * 
	 * 
	 * 1) Validate for empty hand
	 * 2) Validate for missing cards
	 * 3) Validate for duplicate cards
	 * 4) Validate for invalid suit
	 * 5) Validate for invalid value
	 * 
	 */
	private PokerHandAnalysis validatePokerHand(List<Card> pokerHand) {

		HashSet<String> cardsSeen = new HashSet<String>();

		if (pokerHand == null) {
			return new PokerHandAnalysis(PokerHandAnalysis.INVALID_HAND, "No cards!");
		}

		if (pokerHand.size() != 7) {
			return new PokerHandAnalysis(PokerHandAnalysis.INVALID_HAND, "Hand needs to have 7 cards!");
		}

		for (int i = 0; i < pokerHand.size(); i++) {

			Card card = pokerHand.get(i);

			if (cardsSeen.contains(card.getValue().toUpperCase() + "_" + card.getSuit())) {
				return new PokerHandAnalysis(PokerHandAnalysis.INVALID_HAND,
						"Duplicate card " + card.getValue() + " " + card.getSuit());
			} else {
				cardsSeen.add(card.getValue().toUpperCase() + "_" + card.getSuit());
			}

			if ((findSuitByName(card.getSuit()) == null)) {
				return new PokerHandAnalysis(PokerHandAnalysis.INVALID_HAND, "Invalid Suit " + card.getSuit());
			}
			if ((findValueByName("VALUE_" + card.getValue()) == null)) {
				return new PokerHandAnalysis(PokerHandAnalysis.INVALID_HAND, "Invalid Value " + card.getValue());
			}

		}

		return new PokerHandAnalysis(PokerHandAnalysis.VALID_HAND, "");
	}

	private Suit findSuitByName(String name) {
		Suit result = null;
		for (Suit suit : Suit.values()) {
			if (suit.name().equalsIgnoreCase(name)) {
				result = suit;
				break;
			}
		}
		return result;
	}

	private Value findValueByName(String name) {
		Value result = null;
		for (Value value : Value.values()) {
			if (value.name().equalsIgnoreCase(name)) {
				result = value;
				break;
			}
		}
		return result;
	}



}
