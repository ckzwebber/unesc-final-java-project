package main;

import database.ConnectionFactory;
import view.MainScreen;

public class Main {

	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			ConnectionFactory.closeConnection();
		}));

		new MainScreen();

	}
}