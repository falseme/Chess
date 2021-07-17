package ui;

import javax.swing.JFrame;

public class Window extends JFrame {
	public static final long serialVersionUID = 1l;

	public Table table;

	public Window() {

		setTitle("nosexd");
		setSize(800, 600);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new Table();
		add(table);

		setVisible(true);

		table.addPieces();

	}

}
