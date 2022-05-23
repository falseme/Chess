package multiplayer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	
	private Client client; // the player client.

	/**
	* Creates a new window with a ScrollPane and a JTextArea to show information.
	* 
	* @param client The client object that represents the player who opened the window.
	*/
	public ServerWindow(Client client) {

		this.client = client;
		
		setTitle("Información del juego");
		setSize(300, 600);

		textArea = new JTextArea();

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(scroll);
		
		this.addWindowListener(new CloseListener());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	
	private class CloseListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent e) {
			
			client.disconnect();
			
		}
		
	}

}
