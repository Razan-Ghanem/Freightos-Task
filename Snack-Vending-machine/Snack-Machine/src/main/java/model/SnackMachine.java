package main.java.model;

import java.util.*;

import main.java.enums.Currency;
import main.java.enums.MoneySourceType;
import main.java.interfaces.SnackMachineInterface;
import main.java.utilities.Utilities;

import static main.java.enums.Currency.USD;
import static main.java.utilities.Utilities.randomInt;

public class SnackMachine implements SnackMachineInterface {
	double currentMoneyBalance = 0.0;
	List<Map<Integer, ItemQueue>> items;
	ItemQueue selectedItem;
	String currentInput = "";

	/**
	 * This function is used to check if the entered money is correctly or not
	 */
	@Override
	public Feedback acceptMoney(Money money) {
		Feedback feedback = new Feedback();
		if (selectedItem == null) {
			feedback.setStatus(false);
			feedback.setMessage("NO SNACK CHOSEN\nMONEY REJECTED");
			return feedback;
		}
		boolean hasEnoughMoney = false;
		if (money.getCurrency().equals(Currency.USD)) {
			switch (money.getType()) {
			case CARD:
				Card card = (Card) money.getExtension();
				if (Utilities.validateCard(card)) {
					double cardAvailableAmount = card.getBalance();
					if (cardAvailableAmount >= selectedItem.getPrice()) {
						currentMoneyBalance = selectedItem.getPrice();
						hasEnoughMoney = true;
					} else {
						currentMoneyBalance = cardAvailableAmount;
					}
				} else {
					feedback.setStatus(false);
					feedback.setMessage("CARD IS REJECTED \n CARD INVALID");
					return feedback;
				}
				break;
			case COIN:
				double coinAmt = money.getAmount();
				if (coinAmt == 0.10 || coinAmt == 0.20 || coinAmt == 0.50 || coinAmt == 1) {
					currentMoneyBalance += coinAmt;
					if (currentMoneyBalance >= selectedItem.getPrice()) {
						hasEnoughMoney = true;
					}
				} else {
					feedback.setStatus(false);
					feedback.setMessage("INVALID - ACCEPT ONLY\n(10c, 20c, 50c, 1$) COINS");
					return feedback;
				}
				break;
			case NOTE:
				double noteAmt = money.getAmount();
				if (noteAmt == 20 || noteAmt == 50) {
					currentMoneyBalance += noteAmt;
					if (currentMoneyBalance >= selectedItem.getPrice()) {
						hasEnoughMoney = true;
					}
				} else {
					feedback.setStatus(false);
					feedback.setMessage("INVALID - ACCEPT ONLY\n(20$, 50$) NOTES");
					return feedback;
				}
				break;

			}

			if (hasEnoughMoney) {
				dispenseItem(selectedItem);
				feedback.setStatus(true);
				double change = currentMoneyBalance - selectedItem.getPrice();
				feedback.setMessage("ENJOY SNACK \n CHANGE -> " + (change));
				if (change > 0) {
					dispenseChange(change);
				}
			} else {
				feedback.setStatus(false);
				feedback.setMessage(
						"NOT ENOUGH MONEY\nREMAINING -> " + (selectedItem.getPrice() - currentMoneyBalance));
			}
		} else {
			feedback.setStatus(false);
			feedback.setMessage("INVALID CURRENCY");
		}
		return feedback;
	}

	/**
	 * This function is used to check if the Code entered correctly or not
	 */
	@Override
	public Feedback acceptItemCode(String keyBad) {
		Feedback feedback = new Feedback();
		try {
			int rowIndex = Integer.parseInt(keyBad.substring(0, 1));
			int columnNumber = Integer.parseInt(keyBad.substring(1));
			ItemQueue queue = items.get(rowIndex).get(columnNumber);
			if (queue == null) {
				feedback.setMessage("CODE INVALID");
				feedback.setStatus(false);
			} else if (queue.getItems() == null || queue.getItems().isEmpty()) {
				feedback.setMessage("EMPTY OPTION\nNO SNACK AVAILABLE");
				feedback.setStatus(false);
			} else {
				feedback.setStatus(true);
				feedback.setMessage("SNACK AVAILABLE \nPRICE -> " + queue.getPrice() + "$");
				selectedItem = queue;
			}
		} catch (Exception e) {
			feedback.setMessage("CODE INVALID");
			feedback.setStatus(false);
		}
		return feedback;
	}

	/**
	 * This function is used to Dispense an item from the list
	 */
	@Override
	public void dispenseItem(ItemQueue itemQueue) {
		itemQueue.getItems().pop();
	}

	/**
	 * This function is used to Dispense THE Change and make the current Money
	 * Balance equals to zero.
	 */
	@Override
	public Feedback dispenseChange(double change) {
		Feedback feedback = new Feedback();
		currentMoneyBalance = 0.0;
		return feedback;
	}

	public double getCurrentMoneyBalance() {
		return currentMoneyBalance;
	}

	public void setCurrentMoneyBalance(double currentMoneyBalance) {
		this.currentMoneyBalance = currentMoneyBalance;
	}

	public List<Map<Integer, ItemQueue>> getItems() {
		return items;
	}

	public void setItems(List<Map<Integer, ItemQueue>> items) {
		this.items = items;
	}

	public ItemQueue getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemQueue selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * This function is used to initialize the machine and fill snacks with their
	 * prices
	 */
	public void initalizeMachine() {
		try {

			int rows = 5;
			int columns = 5;

			List<Map<Integer, ItemQueue>> items = new ArrayList<>();
			for (int i = 0; i < rows; i++) {
				Map<Integer, ItemQueue> map = new HashMap<>();
				for (int j = 0; j < columns; j++) {
					ItemQueue iq = new ItemQueue();
					Stack<Item> queueItems = new Stack<Item>();
					iq.setRow(i);
					int columnNumber = randomInt(100, 300);
					iq.setColumn(j);
					iq.setColumnNumber(columnNumber);
					for (int z = 0; z < Utilities.randomInt(0, 5); z++) {
						Item item = new Item(Utilities.snackSample[randomInt(0, 17)]);
						queueItems.push(item);
					}
					iq.setItems(queueItems);
					iq.setPrice(Utilities.priceSample[randomInt(0, 6)]);

					map.put(columnNumber, iq);
					if (iq.getItems().isEmpty()) {
						System.out.println("EMPTY   (" + i + iq.getColumnNumber() + ")");
					} else {
						System.out.println(iq.getItems().peek().getDescription() + "    " + iq.getPrice() + "$ (" + i
								+ iq.getColumnNumber() + ")");
					}
//                    System.out.println("Remaining -> " + iq.getItems().size());

				}
				items.add(map);
			}
			setItems(items);
			setCurrentMoneyBalance(0.0);
			setSelectedItem(null);
			currentInput = "";
			System.out.println("Welcome... ");
			System.out.println("Balance -> 0.0$");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	 * This function is used to check if the Code entered correctly or not
	 */
	public Feedback checkInput(String keyBad) {

		if (keyBad.length() == 4) {
			Feedback feedback = acceptItemCode(keyBad);
			if (!feedback.isStatus()) {
				feedback.setMessage("EMPTY OPTION");
			}
			return feedback;
		} else {
			Feedback feedback = new Feedback();
			feedback.setMessage("CODE INVALID");
			feedback.setStatus(false);
			return feedback;
		}
	}

	/**
	 * This function is used to check if the CODE INVALID OR EMPTY OPTION for Item
	 * and return as boolean to loop for another time or not
	 */
	public boolean isValidItem(String message) {
		if ("CODE INVALID".equals(message) || "EMPTY OPTION".equals(message)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is used to check if the Money THAT ENTERED IS NOT ENOUGH Or
	 * INVALID and return as boolean to loop for another time or not
	 */
	public boolean isValidIPrice(String message) {
		if (message.contains("NOT ENOUGH") || message.contains("INVALID - ACCEPT ONLY")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is used to check if the coin amount is correctly and valid and
	 * take the snack if did
	 */
	public void coinChosen(SnackMachine sm) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Insert Coin Amount");
		double amount = scan.nextDouble();
		Money money = new Money();
		money.setAmount(amount);
		money.setType(MoneySourceType.COIN);
		money.setCurrency(USD);
		Feedback coinFeedback = sm.acceptMoney(money);
		if (coinFeedback.isStatus()) {
			System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
			System.out.println(coinFeedback.getMessage());
		} else {
			if (coinFeedback.isStatus()) {
				System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
				System.out.println(coinFeedback.getMessage());
			} else {
				System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
				System.out.println(coinFeedback.getMessage());
			}
			if (sm.isValidIPrice(coinFeedback.getMessage())) {
				coinChosen(sm);
			}
		}
	}

	/**
	 * This function is used to check if the note amount is correctly and valid and
	 * take the snack if did
	 */
	public void noteChosen(SnackMachine sm) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Insert Note Amount");
		double noteAmount = scan.nextDouble();
		Money noteMoney = new Money();
		noteMoney.setAmount(noteAmount);
		noteMoney.setType(MoneySourceType.NOTE);
		noteMoney.setCurrency(USD);
		Feedback noteFeedback = sm.acceptMoney(noteMoney);
		if (noteFeedback.isStatus()) {
			System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
			System.out.println(noteFeedback.getMessage());
		} else {
			System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
			System.out.println(noteFeedback.getMessage());
		}
		if (sm.isValidIPrice(noteFeedback.getMessage())) {
			noteChosen(sm);
		}
	}

	/**
	 * This function is used to check if the card amount is correctly and valid and
	 * take the snack if did
	 */
	public void cardChosen(SnackMachine sm) {
		Scanner scan = new Scanner(System.in);
		Card card = new Card();
		System.out.println("Insert Card Number");
		card.setCardNumber(scan.next());
		System.out.println("Insert Card Amount");
		double cardAmount = scan.nextDouble();
		card.setBalance(cardAmount);
		Money cardMoney = new Money();
		cardMoney.setAmount(cardAmount);
		cardMoney.setType(MoneySourceType.CARD);
		cardMoney.setExtension(card);
		cardMoney.setCurrency(USD);
		Feedback cardFeedback = sm.acceptMoney(cardMoney);
		if (cardFeedback.isStatus()) {
			System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
			System.out.println(cardFeedback.getMessage());
		} else {
			System.out.println("Balance -> " + sm.getCurrentMoneyBalance() + "$");
			System.out.println(cardFeedback.getMessage());
		}
	}
}
