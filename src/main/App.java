package main;

import gui.Assets;
import multiplayer.Client;
import ui.Window;

public class App {

	public static Client CLIENT;

	public static void main(String[] args) {

		Assets.loadAll();

		Window window = new Window();
		window.setVisible(true);

	}

}
