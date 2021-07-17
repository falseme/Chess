package main;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import gui.Assets;
import ui.Table;

public enum Piece {

	wPawn(Assets.W_PAWN, true), wRock(Assets.W_ROCK, true), wHorse(Assets.W_HORSE, true),
	wBishop(Assets.W_BISHOP, true), wQueen(Assets.W_QUEEN, true), wKing(Assets.W_KING, true),

	bPawn(Assets.B_PAWN, false), bRock(Assets.B_ROCK, false), bHorse(Assets.B_HORSE, false),
	bBishop(Assets.B_BISHOP, false), bQueen(Assets.B_QUEEN, false), bKing(Assets.B_KING, false);

	private BufferedImage image;
	private boolean team; // true = white // false = black;

	private boolean check = false;

	private Piece(BufferedImage img, boolean isWhiteTeam) {

		image = img;
		this.team = isWhiteTeam;

	}

	public LinkedList<int[]> calculateMoves(int[] pos) {

		LinkedList<int[]> moves = new LinkedList<int[]>();

		switch (this) {

		case wPawn:

			pawnMovements(moves, pos, -1);

			break;

		case bPawn:

			pawnMovements(moves, pos, 1);

			break;

		case wRock:

			rockMovements(moves, pos);

			break;

		case bRock:

			rockMovements(moves, pos);

			break;

		case wHorse:

			horseMovements(moves, pos);

			break;

		case bHorse:

			horseMovements(moves, pos);

			break;

		case wBishop:

			bishopMovements(moves, pos);

			break;

		case bBishop:

			bishopMovements(moves, pos);

			break;

		case wQueen:

			rockMovements(moves, pos);
			bishopMovements(moves, pos);

			break;

		case bQueen:

			rockMovements(moves, pos);
			bishopMovements(moves, pos);

			break;

		case wKing:

			kingMovements(moves, pos);

			break;

		case bKing:

			kingMovements(moves, pos);

			break;

		default:

			break;

		}

		return moves;

	}

	private void pawnMovements(LinkedList<int[]> moves, int[] pos, int way) {

		// moving

		if (pos[1] == 0 || pos[1] == 7)
			return;

		if (Table.getSquare(pos[0], pos[1] + 1 * way).getPiece() == null)
			moves.add(new int[] { pos[0], pos[1] + 1 * way });
		if (pos[1] == 6 && team || pos[1] == 1 && !team)
			if (Table.getSquare(pos[0], pos[1] + 2 * way).getPiece() == null)
				moves.add(new int[] { pos[0], pos[1] + 2 * way });

		// taking

		if (pos[0] > 0) {

			Piece other = Table.getSquare(pos[0] - 1, pos[1] + 1 * way).getPiece();

			if (other != null)
				if (other.team != team)
					moves.add(new int[] { pos[0] - 1, pos[1] + 1 * way });

		}

		if (pos[0] < 7) {

			Piece other = Table.getSquare(pos[0] + 1, pos[1] + 1 * way).getPiece();

			if (other != null)
				if (other.team != team)
					moves.add(new int[] { pos[0] + 1, pos[1] + 1 * way });

		}

	}

	private void rockMovements(LinkedList<int[]> moves, int[] pos) {

		// right
		for (int x = pos[0]; x <= 7; x++) {

			if (x == pos[0])
				continue;

			if (!addMove(moves, x, pos[1]))
				break;

		}
		// left
		for (int x = pos[0]; x >= 0; x--) {

			if (x == pos[0])
				continue;

			if (!addMove(moves, x, pos[1]))
				break;

		}
		// up
		for (int y = pos[1]; y >= 0; y--) {

			if (y == pos[1])
				continue;

			if (!addMove(moves, pos[0], y))
				break;

		}
		// down
		for (int y = pos[1]; y <= 7; y++) {

			if (y == pos[1])
				continue;

			if (!addMove(moves, pos[0], y))
				break;

		}

	}

	private void horseMovements(LinkedList<int[]> moves, int[] pos) {

		// # 2 # 3 # //
		// 1 # # # 4 //
		// # # C # # //
		// 8 # # # 5 //
		// # 7 # 6 # //

		// 1
		if (pos[0] - 2 >= 0 && pos[1] - 1 >= 0)
			addMove(moves, pos[0] - 2, pos[1] - 1);

		// 2
		if (pos[0] - 1 >= 0 && pos[1] - 2 >= 0)
			addMove(moves, pos[0] - 1, pos[1] - 2);

		// 3
		if (pos[0] + 1 <= 7 && pos[1] - 2 >= 0)
			addMove(moves, pos[0] + 1, pos[1] - 2);

		// 4
		if (pos[0] + 2 <= 7 && pos[1] - 1 >= 0)
			addMove(moves, pos[0] + 2, pos[1] - 1);

		// 5
		if (pos[0] + 2 <= 7 && pos[1] + 1 <= 7)
			addMove(moves, pos[0] + 2, pos[1] + 1);

		// 6
		if (pos[0] + 1 <= 7 && pos[1] + 2 <= 7)
			addMove(moves, pos[0] + 1, pos[1] + 2);

		// 7
		if (pos[0] - 1 >= 0 && pos[1] + 2 <= 7)
			addMove(moves, pos[0] - 1, pos[1] + 2);

		// 8
		if (pos[0] - 2 >= 0 && pos[1] + 1 <= 7)
			addMove(moves, pos[0] - 2, pos[1] + 1);

	}

	private void bishopMovements(LinkedList<int[]> moves, int[] pos) {

		int x, y;

		// down-right
		x = pos[0];
		y = pos[1];
		while (true) {

			x++;
			y++;
			if (x > 7 || y > 7)
				break;

			if (!addMove(moves, x, y))
				break;

		}
		// down-left
		x = pos[0];
		y = pos[1];
		while (true) {

			x--;
			y++;
			if (x < 0 || y > 7)
				break;

			if (!addMove(moves, x, y))
				break;

		}
		// up-right
		x = pos[0];
		y = pos[1];
		while (true) {

			x++;
			y--;
			if (x > 7 || y < 0)
				break;

			if (!addMove(moves, x, y))
				break;

		}
		// up-left
		x = pos[0];
		y = pos[1];
		while (true) {

			x--;
			y--;
			if (x < 0 || y < 0)
				break;

			if (!addMove(moves, x, y))
				break;

		}

	}

	private void kingMovements(LinkedList<int[]> moves, int[] pos) {

		// all left
		if (pos[0] - 1 >= 0) {

			addMove(moves, pos[0] - 1, pos[1]);
			if (pos[1] - 1 >= 0)
				addMove(moves, pos[0] - 1, pos[1] - 1);
			if (pos[1] + 1 <= 7)
				addMove(moves, pos[0] - 1, pos[1] + 1);

		}
		// all right
		if (pos[0] + 1 <= 7) {

			addMove(moves, pos[0] + 1, pos[1]);
			if (pos[1] - 1 >= 0)
				addMove(moves, pos[0] + 1, pos[1] - 1);
			if (pos[1] + 1 <= 7)
				addMove(moves, pos[0] + 1, pos[1] + 1);

		}
		// up
		if (pos[1] - 1 >= 0)
			addMove(moves, pos[0], pos[1] - 1);

		// down
		if (pos[1] + 1 <= 7)
			addMove(moves, pos[0], pos[1] + 1);

		// test if checkmate

		check = false;

		if (team) {
			if (Table.getSquare(pos[0], pos[1]).isWhiteThreat())
				check = true;
		} else {
			if (Table.getSquare(pos[0], pos[1]).isBlackThreat())
				check = true;
		}

		if (check) {
			if (moves.isEmpty())
				System.out.println("CHECKMATE");
			else
				System.out.println("CHECK");
		}

	}

	/**
	 * Add coords to the movements list of the currect piece and check if there are
	 * more posibilities on the same line.
	 * 
	 * Does not work with pawns
	 * 
	 * @param moves
	 * @param x
	 * @param y
	 * @return true if it has more posibilities on the same line
	 */
	private boolean addMove(LinkedList<int[]> moves, int x, int y) {

		boolean threat = false;

		if (team) {
			if (Table.getSquare(x, y).isWhiteThreat())
				threat = true;
		} else {
			if (Table.getSquare(x, y).isBlackThreat())
				threat = true;
		}

		Piece other = Table.getSquare(x, y).getPiece();
		if (other == null) {

			if (this == wKing || this == bKing) {
				if (!threat)
					moves.add(new int[] { x, y });
			} else {
				moves.add(new int[] { x, y });
			}

			if (team)
				Table.getSquare(x, y).setBlackThreat(true);
			else
				Table.getSquare(x, y).setWhiteThreat(true);

		} else if (other.team != team) {

			if (this == wKing || this == bKing) {
				if (!threat)
					moves.add(new int[] { x, y });
			} else {
				moves.add(new int[] { x, y });
			}

			if (team)
				Table.getSquare(x, y).setBlackThreat(true);
			else
				Table.getSquare(x, y).setWhiteThreat(true);

			return false;

		} else
			return false;

		return true;

	}

	public BufferedImage getImage() {
		return image;
	}

	public boolean isWhiteTeam() {
		return team;
	}

}
