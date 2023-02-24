package interaction;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInteraction {
	private Scanner reader;
	private boolean isConsoleInput;

	public UserInteraction() {
		reader = new Scanner(System.in);
		isConsoleInput = true;
	}

	public void print(String text) {
		System.out.print(text);
	}

	public void printLine(String text) {
		System.out.println(text);
	}

	public void printLine(Number num) {
		System.out.println(num);
	}

	public boolean isConsoleInput() {
		return isConsoleInput;
	}

	public boolean uiChooseInputStream() {
		printLine("Откуда берем входные данные ?\n1 - Клавиатура\n2 - Файл");
		String input = read();
		if (!input.equals("1") && !input.equals("2")) {
			return false;
		}
		if (input.equals("2")) {
			printLine("Введите название файла с данными\nВернуться назад: back");
			do {
				input = read();
				if (input.equalsIgnoreCase("back")) {
					return false;
				}
				try {
					InputStream is = new FileInputStream(input);
					changeInputStream(is);
				} catch (FileNotFoundException e) {
					printLine("Файл не найден. Введите название еще раз!");
					continue;
				}
				return true;
			} while (true);
		}
		return true;
	}

	public String read() {
		try {
			return reader.next();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String readLine() {
		try {
			return reader.nextLine();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public void changeInputStream(InputStream stream) {
		reader = new Scanner(stream);
		isConsoleInput = false;
	}
}
