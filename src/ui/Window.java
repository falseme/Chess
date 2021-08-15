package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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

		addJMenuBar();

		setVisible(true);

		table.addPieces();

	}

	private void addJMenuBar() {

		JMenuBar bar = new JMenuBar();

		JMenu game = new JMenu("Juego");

		JMenuItem newGame = new JMenuItem("Nuevo juego");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String[] options = new String[] { "Nuevo juego", "Cancelar" };

				int option = JOptionPane.showOptionDialog(Window.this, "De verdad quieres terminar la partida actual?",
						"Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

				if (option != 0)
					return;

				Table.emptyAndReset();
				table.addPieces();

			}
		});
		game.add(newGame);

		bar.add(game);

		setJMenuBar(bar);

	}

}
