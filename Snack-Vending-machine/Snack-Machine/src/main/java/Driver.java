package main.java;

import java.util.Scanner;

import main.java.model.Feedback;
import main.java.model.SnackMachine;

public class Driver {
	public static void main(String[] args) {
		SnackMachine sm = new SnackMachine();
		sm.initalizeMachine();
		String keyBad, moneySourceTypeInput;
		Feedback feedback = new Feedback();
		boolean moneyTypeFlag = true;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("Enter the code for your snack or cancel to exit");
			keyBad = scan.next();
			if (keyBad.equals("cancel")) {
				System.out.println("Bye Bye ... ");
				System.exit(0);
			} else {
				feedback = sm.checkInput(keyBad);
				System.out.println(feedback.getMessage());
			}
		} while (sm.isValidItem(feedback.getMessage()));
		do {
			System.out.println("Choose the money source type (coin , note or card) or cancel to exit");
			moneySourceTypeInput = scan.next();
			if ("coin".equals(moneySourceTypeInput.toLowerCase())) {
				moneyTypeFlag = false;
				sm.coinChosen(sm);
				System.out.println("Bye Bye ... ");
			} else if ("note".equals(moneySourceTypeInput.toLowerCase())) {
				moneyTypeFlag = false;
				sm.noteChosen(sm);
				System.out.println("Bye Bye ... ");
			} else if ("card".equals(moneySourceTypeInput.toLowerCase())) {
				moneyTypeFlag = false;
				sm.cardChosen(sm);
				System.out.println("Bye Bye ... ");
			} else if ("cancel".equals(moneySourceTypeInput.toLowerCase())) {
				System.out.println("Bye Bye ... ");
				System.exit(0);
			} else {
				System.out.println("wrong input ! ");
			}
		} while (moneyTypeFlag);
	}
}
