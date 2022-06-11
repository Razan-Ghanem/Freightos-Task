package main.java.interfaces;

import main.java.model.Feedback;
import main.java.model.ItemQueue;
import main.java.model.Money;

public interface SnackMachineInterface {
	Feedback acceptMoney(Money money);

	Feedback acceptItemCode(String keyBad);

	void dispenseItem(ItemQueue itemQueue);

	Feedback dispenseChange(double change);
}
