package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import gui.Assets;
import main.App;
import multiplayer.Client;

/**
* The window that will be open when the program starts. It contains a menu with options and the game table.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class Window extends JFrame {
	public static final long serialVersionUID = 1l;

	/** The game table
		* @see Table */
	public Table table;

	/**
		* Creates a window in the center of the screen with a (800:600) size.<br>
		* Adds a JMenuBar and a table with its pieces.
		*/
	public Window() {

		setTitle("Ajedrez");
		setSize(800, 600);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new Table();
		add(table);

		addJMenuBar();

		setVisible(true);

		Table.addPieces();

	}

	/**
		* Adds a JMenuBar with some options (new game, multiplayer, change textures)
		*/
	protected void addJMenuBar() {

		JMenuBar bar = new JMenuBar();

		// GAME
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

			}
		});
		game.add(newGame);

		// MULTIPLAYER
		JMenu multiplayer = new JMenu("Multijugador");

		JMenuItem lan = new JMenuItem("LAN");
		lan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String[] options = new String[] { "HOST", "CLIENTE" };

				int option = JOptionPane.showOptionDialog(Window.this, "Desea hostear o ser un cliente", "Advertencia",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

				if (option == 0) {
					App.CLIENT = new Client();
				} else {

					String host = JOptionPane.showInputDialog("Escriba la dirección IP del host");
					if (host == null || host.isEmpty())
						return;

					App.CLIENT = new Client(host);
				}

			}
		});
		multiplayer.add(lan);

		// OPTIONS
		JMenu options = new JMenu("Opciones");

		// LOOK - WOOD & STONE
		JMenu look = new JMenu("Aspecto");

		JRadioButtonMenuItem stone = new JRadioButtonMenuItem("Negro", true);
		stone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assets.loadStone();
			}
		});
		look.add(stone);
		JRadioButtonMenuItem wood = new JRadioButtonMenuItem("Madera");
		wood.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assets.loadWood();
			}
		});
		look.add(wood);

		ButtonGroup lookGroup = new ButtonGroup();
		lookGroup.add(stone);
		lookGroup.add(wood);

		look.addSeparator();
		// LOOK 2D & 3D

		JRadioButtonMenuItem d2 = new JRadioButtonMenuItem("2D", true);
		d2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assets.load2d();
			}
		});
		look.add(d2);

		JRadioButtonMenuItem d3 = new JRadioButtonMenuItem("3D");
		d3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assets.load3d();
			}
		});
		look.add(d3);

		ButtonGroup lookGroupD = new ButtonGroup();
		lookGroupD.add(d2);
		lookGroupD.add(d3);

		options.add(look);

		bar.add(game);
		bar.add(multiplayer);
		bar.add(options);

		setJMenuBar(bar);

	}

}
