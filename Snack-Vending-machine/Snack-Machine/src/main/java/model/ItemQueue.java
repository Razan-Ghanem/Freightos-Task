package main.java.model;

import java.util.Stack;

public class ItemQueue {
	Stack<Item> items;
	int row, column, columnNumber;
	double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Stack<Item> getItems() {
		return items;
	}

	public void setItems(Stack<Item> items) {
		this.items = items;
	}
}
