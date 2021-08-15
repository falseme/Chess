package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import event.SquareMouse;
import main.Piece;
import ui.layout.TableLayout;

public class Table extends JComponent {
	public static final long serialVersionUID = 1l;

	private static Square[][] table;

	public Table() {

		setLayout(new TableLayout());

		boolean white = true;

		table = new Square[8][8];
		for (int y = 0; y < table.length; y++) {
			for (int x = 0; x < table[y].length; x++) {

				table[x][y] = new Square(white ? Square.WHITE : Square.BLACK, x, y);
				add(table[x][y]);
				white = !white;

			}
			white = !white;
		}

	}

	public void addPieces() {

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
			table[pos[0]][pos[1]].switchBackground();

		}

	}

	public static Square getSquare(int x, int y) {
		return table[x][y];
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
