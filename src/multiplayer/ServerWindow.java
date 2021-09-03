package multiplayer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerWindow extends JDialog {
	public static final long serialVersionUID = 1l;

	private JTextArea textArea;

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

	public void writeLine(String line) {

		textArea.append(line + "\n");

	}

}
