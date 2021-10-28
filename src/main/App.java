package main;

import gui.Assets;
import multiplayer.Client;
import ui.Window;

/**
* Main application class. Contains the main method and the client for a multiplayer (LAN) match.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class App {

	/**
	* The player client. Used for a multiplayer (Lan) connection.
	*
	* @see Client
	*/
	public static Client CLIENT;

	/**
	 * Main method - Initialize the game assets and opens a Window.
		*
		* @param args Idk what is this for.
		* @see ui.Window
		* @see gui.Assets
	 **/
	public static void main(String[] args) {

		Assets.init();

		Window window = new Window();
		window.setVisible(true);

	}

}
