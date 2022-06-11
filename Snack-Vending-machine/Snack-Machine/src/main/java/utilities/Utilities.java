package main.java.utilities;

import java.util.Random;

import main.java.model.Card;

public class Utilities {
	public static String[] snackSample = new String[] { "M&Ms", "Snickers", "Pringles", "Laviva", "Ferrero", "Cheetos",
			"Ali BaBa", "Penuts", "Kinder", "Hershey's", "Lays Chips", "7 days", "Lion", "Mars", "Kit Kat", "Ruffles",
			"Bounty", "Oreo" };

	public static double[] priceSample = new double[] { 0.25, 0.50, 1.00, 2.00, 2.50, 3.00 };

	public static int randomInt(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	public static boolean validateCard(Card card) {
		if (card.getCardNumber().length() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
