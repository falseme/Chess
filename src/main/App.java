package main;

import gui.Assets;
import ui.Window;

public class App {

	public static void main(String[] args) {

		Assets.loadAll();

		Window window = new Window();
		window.setVisible(true);

	}

}
