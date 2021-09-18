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

public class Table extends JComponent {
	public static final long serialVersionUID = 1l;

	private static JComponent border;
	private static Square[][] table;

	public static String lastMove;

	private static HashMap<Integer, String> numToCoord;

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
		table[7][0].addPiece(Piece.bRock);

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
		table[7][7].addPiece(Piece.wRock);

	}

	public void addPieces(long hackroom) {

		if (hackroom != 0)
			return;

		table[0][3].addPiece(Piece.bQueen);
		table[4][7].addPiece(Piece.wKing);
		table[5][6].addPiece(Piece.wBishop);

	}

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

	public static Square getSquare(int x, int y) {
		return table[x][y];
	}

	public static Square[][] getTable() {
		return table;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

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

	public static void updateGui() {

		border.repaint();

		Piece.updateGui();

		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				table[x][y].updateGui(Assets.TABLE[x][y]);

			}
		}

	}

	private void addBorder() {

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
