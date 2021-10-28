package multiplayer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
* Used to show information about the connection status.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class ServerWindow extends JDialog {
	private static final long serialVersionUID = 1l;

	private JTextArea textArea; // a text area where to write information

	/**
	* Creates a new window with a ScrollPane and a JTextArea to show information.
	*/
	public ServerWindow() {

		setTitle("Información del juego");
		setSize(300, 600);

		textArea = new JTextArea();

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(scroll);

		setVisible(true);

	}

	/**
	* Wirtes a given String in the TextArea.
	*
	* @param line The String that is wanted to be written.
	*/
	public void writeLine(String line) {

		textArea.append(line + "\n");

	}

}
