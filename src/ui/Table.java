package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import event.SquareMouse;
import gui.Assets;
import main.Piece;
import ui.layout.TableLayout;

/**
* The table that contains the squares and the pieces. It will draw the them inside itself.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class Table extends JComponent {
	private static final long serialVersionUID = 1l;

	private static JComponent border; // The border of the table that contains the numbers and letters for rows and cols. Given by Assets.
	private static Square[][] table; // A list of every 64 squares that are in the table.

	/** Used in multiplayer. Says in which square did the player move its piece. */
	public static String lastMove;

	private static HashMap<Integer, String> numToCoord; // Hashmap used to transform numbers in coords from A to H.

	/**
		* Creates a table with a TableLayout and a border: {@link #addBorder()}.
		* @see TableLayout
		*/
	public Table() {

		numToCoord = new HashMap<Integer, String>();

		numToCoord.put(7, "A");
		numToCoord.put(6, "B");
		numToCoord.put(5, "C");
		numToCoord.put(4, "D");
		numToCoord.put(3, "E");
		numToCoord.put(2, "F");
		numToCoord.put(1, "G");
		numToCoord.put(0, "H");

		setLayout(new TableLayout());

		table = new Square[8][8];
		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				table[x][y] = new Square(Assets.TABLE[x][y], x, y);
				add(table[x][y]);

			}
		}

		addBorder();

	}

	/**
		* Adds pieces to the table in their initial position.
		*/
	public static void addPieces() {

		// Black pieces
		for (int i = 0; i < table.length; i++)
			table[i][1].addPiece(Piece.bPawn);
		table[0][0].addPiece(Piece.bRock);
		table[1][0].addPiece(Piece.bHorse);
		table[2][0].addPiece(Piece.bBishop);
		table[3][0].addPiece(Piece.bQueen);
		table[4][0].addPiece(Piece.bKing);
		table[5][0].addPiece(Piece.bBishop);
		table[6][0].addPiece(Piece.bHorse);
		table[7][0].addPiece(Piece.bRock2);

		// White pieces
		for (int i = 0; i < table.length; i++)
			table[i][6].addPiece(Piece.wPawn);
		table[0][7].addPiece(Piece.wRock);
		table[1][7].addPiece(Piece.wHorse);
		table[2][7].addPiece(Piece.wBishop);
		table[3][7].addPiece(Piece.wQueen);
		table[4][7].addPiece(Piece.wKing);
		table[5][7].addPiece(Piece.wBishop);
		table[6][7].addPiece(Piece.wHorse);
		table[7][7].addPiece(Piece.wRock2);

	}

	/**
		* Adds some pieces into a personalized position. Used by devs when testing.
		* @param hackroom the hackroom code.
		*/
	public void addPieces(long hackroom) {

		if (hackroom != 0)
			return;

		table[0][3].addPiece(Piece.bQueen);
		table[4][7].addPiece(Piece.wKing);
		table[5][6].addPiece(Piece.wBishop);

	}

	/**
		* Deletes every piece in the table and adds new ones.<br>
		* It also resets the shadow color of the squares.
		* (shadow colors are used when moving a piece or when the mouse pointer is inside the square
		* - see the class for more)
		*
		* @see Square
		*/
	public static void emptyAndReset() {

		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {
				table[x][y].addPiece(null);

				table[x][y].setEnabled(true);
				table[x][y].resetBackgroundColor();
				table[x][y].resetThreat();
			}
		}

		SquareMouse.reset();

		addPieces();

	}

	/**
		* Enables or disables the option to click every square in the table so as to select a piece to move.
		* It also recalculates every piece movements if some one has moved.
		*
		* @param b If true, the squares will be enabled, but if false, whey will be disabled. Also if true,
		* every piece will recalculate its movements in the case someone was moved.
		* @see Square
		* @see Piece
		*/
	public static void enableAll(boolean b) {

		if (b) {

			for (int y = 0; y < table.length; y++) {
				for (int x = 0; x < table[y].length; x++) {
					table[x][y].recalculateMoves();
				}
			}

			for (int y = 0; y < table.length; y++) {
				for (int x = 0; x < table[y].length; x++) {
					if (table[x][y].getPiece() == Piece.bKing || table[x][y].getPiece() == Piece.wKing)
						table[x][y].recalculateMoves();
				}
			}

		}

		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				table[x][y].setEnabled(b);
				table[x][y].resetBackgroundColor();
				table[x][y].resetThreat();

			}
		}

	}

	/**
		* Disable the option to click all squares and only enables the option to click a list of squares.<br>
		* Useful when moving a piece so as enable only the squares when it can be moved.
		*
		* @param list The list of squares to enable.
		*/
	public static void enable(List<int[]> list) {

		if (list == null)
			return;

		enableAll(false);

		while (!list.isEmpty()) {

			int[] pos = list.remove(0);
			table[pos[0]][pos[1]].setEnabled(true);
			table[pos[0]][pos[1]].switchMoveBackground();

		}

	}

	/**
		* Returns a square by the given coords.
		* @param x The row coord of the square.
		* @param y The col coord of the square.
		* @return The square in the coords given.
		*/
	public static Square getSquare(int x, int y) {
		return table[x][y];
	}

	/**
		* Returns the hole square list in the table.
		* @return A list with the 64 squares of the table.
		*/
	public static Square[][] getTable() {
		return table;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
		* Replace the pieces in the squares of this table by the pieces in the squares given.<br>
		* After that the method {@link #enableAll(boolean)} (boolean : true) is called so as to recalculate movements.
		* @param table A list with 64 squares.
		* @see Square
		*/
	public static void replaceTable(Square[][] table) {

		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				if (Table.table[x][y].getPiece() != table[x][y].getPiece()) {

					if (table[x][y].getPiece() != null)
						lastMove = numToCoord.get(y) + (x + 1);

					Table.table[x][y].addPiece(table[x][y].getPiece());

				}

			}
		}

		enableAll(true);

	}

	/**
		* Updates the graphics of the table and each square.<br>
		* Also updates the graphics of each piece.<br>
		* Every texture is given by the Assets.
		* @see Assets
		*/
	public static void updateGui() {

		border.repaint();

		Piece.updateGui();

		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				table[x][y].updateGui(Assets.TABLE[x][y]);

			}
		}

	}

	/**
		* Adds a border to the table with nums and letters written for rows and cols.
		* Given by the Assets.
		* @see Assets
		*/
	protected void addBorder() {

		border = new JComponent() {
			public static final long serialVersionUID = 1l;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(Assets.FRAME, 0, 0, getWidth(), getHeight(), null);
			}
		};

		add(border);

	}

}
