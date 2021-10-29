package main;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import gui.Assets;
import ui.Table;

/**
* Enumerator used to add pieces to the squares in the table. <br><br>
* Syntaxis:<br>
* wPawn  -  w: it is in the white team.  -  Pawn: says what piece is.<br><br>
* Declared form:<br>
* wPawn(Assets.W_PAWN, true); using the constructor {@link #Piece(BufferedImage, boolean)}.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public enum Piece {

	wPawn(Assets.W_PAWN, true), wRock(Assets.W_ROCK, true), wHorse(Assets.W_HORSE, true),
	wBishop(Assets.W_BISHOP, true), wQueen(Assets.W_QUEEN, true), wKing(Assets.W_KING, true),

	// Pieces added to solved a bug with the castilng function.
	// when castilg, the var "moved" is used to check the rocks and it is imposible to have a same piece with different vars.
	wRock2(Assets.W_ROCK, true),
	bRock2(Assets.B_ROCK, false),

	bPawn(Assets.B_PAWN, false), bRock(Assets.B_ROCK, false), bHorse(Assets.B_HORSE, false),
	bBishop(Assets.B_BISHOP, false), bQueen(Assets.B_QUEEN, false), bKing(Assets.B_KING, false);

	private BufferedImage image; // the image to draw in the square in the table
	private boolean team; // true = white // false = black;

	private boolean check = false; // used by kings. true if chekc or checkmate, false if not.
	public boolean moved = false; // true if the piece was moved at least once.

	/**
	 * Used when checking movements - If a king is in the path, is necessary
		* to check one more movement to prevent the king from doing an ilegal movement. <br>
		*
		* Used in methods {@link #rockMovements(LinkedList, int[])} and {@link #bishopMovements(LinkedList, int[])}
	 */
	protected boolean oneMoreMovement = false;

	/**
	* Creates a piece with an image to show in the table. Also the team is especified by a boolean.
	* @param img The image to represent the piece. Given by the Assets.
	* @param isWhiteTeam true to create a white piece and false to create a black piece.
	* @see Assets
	*/
	private Piece(BufferedImage img, boolean isWhiteTeam) {

		image = img;
		this.team = isWhiteTeam;

	}

	/**
	* Calculate the moves the piece can move given its position in the table and returns them in a LinkedList. <br>
	* Every move is stored in the Square object that has this piece.
	*
	* @param pos A two-length array with the x and y coords of the piece.
	* @return LinkedList: A list with coords of the squares that it is possible to move the piece.
	* @see ui.Square
	*/
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

		case wRock2:

			rockMovements(moves, pos);

			break;

		case bRock2:

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

	/**
	* Calculates the movements a pawn can do using pathfinding.
	* @param moves List which will be loaded with every move the pawn can do.
	* @param pos The piece's coords in the table given by its square.
	* @param way The way in which the pawn is moving.
	* @see ui.Square
	*/
	protected void pawnMovements(LinkedList<int[]> moves, int[] pos, int way) {

		// moving

		if (pos[1] == 0 || pos[1] == 7)
			return;

		if (Table.getSquare(pos[0], pos[1] + 1 * way).getPiece() == null){

			moves.add(new int[] { pos[0], pos[1] + 1 * way });
			if (pos[1] == 6 && team || pos[1] == 1 && !team)
				if (Table.getSquare(pos[0], pos[1] + 2 * way).getPiece() == null)
					moves.add(new int[] { pos[0], pos[1] + 2 * way });

		}

		// taking

		if (pos[0] > 0) {

			threatSquare(pos[0] - 1, pos[1] + 1 * way);

			Piece other = Table.getSquare(pos[0] - 1, pos[1] + 1 * way).getPiece();

			if (other != null)
				if (other.team != team)
					moves.add(new int[] { pos[0] - 1, pos[1] + 1 * way });

			// passant
			Piece passant = Table.getSquare(pos[0] - 1, pos[1]).getPiece();
			if ((passant == Piece.bPawn && team || passant == Piece.wPawn && !team)
					&& Table.getSquare(pos[0] - 1, pos[1]).canPassant())
				moves.add(new int[] { pos[0] - 1, pos[1] + way });

		}

		if (pos[0] < 7) {

			threatSquare(pos[0] + 1, pos[1] + 1 * way);

			Piece other = Table.getSquare(pos[0] + 1, pos[1] + 1 * way).getPiece();

			if (other != null)
				if (other.team != team)
					moves.add(new int[] { pos[0] + 1, pos[1] + 1 * way });

			// passant
			Piece passant = Table.getSquare(pos[0] + 1, pos[1]).getPiece();
			if ((passant == Piece.bPawn && team || passant == Piece.wPawn && !team)
					&& Table.getSquare(pos[0] + 1, pos[1]).canPassant())
				moves.add(new int[] { pos[0] + 1, pos[1] + way });

		}

	}

	/**
	* Calculates the movements a rock can do using pathfinding.
	* @param moves List which will be loaded with every move the rock can do.
	* @param pos The piece's coords in the table given by its square.
	*/
	protected void rockMovements(LinkedList<int[]> moves, int[] pos) {

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

	/**
	* Calculates the movements a horse (knight) can do using pathfinding.
	* @param moves List which will be loaded with every move the horse (knight) can do.
	* @param pos The piece's coords in the table given by its square.
	*/
	protected void horseMovements(LinkedList<int[]> moves, int[] pos) {

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

	/**
	* Calculates the movements a bishop can do using pathfinding.
	* @param moves List which will be loaded with every move the bishop can do.
	* @param pos The piece's coords in the table given by its square.
	*/
	protected void bishopMovements(LinkedList<int[]> moves, int[] pos) {

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

	/**
	* Calculates the movements a king can do using pathfinding.
	* @param moves List which will be loaded with every move the king can do.
	* @param pos The piece's coords in the table given by its square.
	*/
	protected void kingMovements(LinkedList<int[]> moves, int[] pos) {

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

		// castling

		if (!moved) {

			Piece leftRock = Table.getSquare(0, pos[1]).getPiece();
			Piece rightRock = Table.getSquare(7, pos[1]).getPiece();

			if (leftRock != null) {
				if (Table.getSquare(pos[0] - 1, pos[1]).getPiece() == null
						&& Table.getSquare(pos[0] - 2, pos[1]).getPiece() == null
						&& Table.getSquare(pos[0] - 3, pos[1]).getPiece() == null
						&& ((leftRock == Piece.wRock || leftRock == Piece.bRock || leftRock == Piece.wRock2 || leftRock == Piece.bRock2)
						&& leftRock.team == team) && !leftRock.moved) {
					if (!Table.getSquare(pos[0] - 2, pos[1]).isWhiteThreat() && team
							|| !Table.getSquare(pos[0] - 2, pos[1]).isBlackThreat() && !team)
						moves.add(new int[] { 0, pos[1] });
				}

			}

			if (rightRock != null) {
				if (Table.getSquare(pos[0] + 1, pos[1]).getPiece() == null
						&& Table.getSquare(pos[0] + 2, pos[1]).getPiece() == null
						&& ((rightRock == Piece.wRock || rightRock == Piece.bRock || rightRock == Piece.wRock2 || rightRock == Piece.bRock2)
						&& rightRock.team == team) && !rightRock.moved) {
					if (!Table.getSquare(pos[0] - 2, pos[1]).isWhiteThreat() && team
							|| !Table.getSquare(pos[0] - 2, pos[1]).isBlackThreat() && !team)
						moves.add(new int[] { 7, pos[1] });
				}
			}

		}

		// test if checkmate

		check = checkThreat(pos[0], pos[1]);

		if (check) {
			if (moves.isEmpty())
				System.out.println("CHECKMATE");
			else
				System.out.println("CHECK");
		}

	}

	/**
	 * Add coords to the movements list of the current piece and check if there are
	 * more posibilities on the same line.<br>
	 * Does not work with pawns
	 *
	 * @param moves The movements list of the current piece.
	 * @param x The X coord in the table.
	 * @param y The Y coord in the table
	 * @return true if it has more posibilities on the same line or false if not.
	 */
	protected boolean addMove(LinkedList<int[]> moves, int x, int y) {

		threatSquare(x, y);

		if (oneMoreMovement) {
			oneMoreMovement = false;
			return false;
		}

		boolean threat = checkThreat(x, y);

		Piece other = Table.getSquare(x, y).getPiece();

		if (other == null) {

			if (this == wKing || this == bKing) {
				if (!threat)
					moves.add(new int[] { x, y });
			} else {
				moves.add(new int[] { x, y });
			}

		} else if (other.team != team) {

			if (this == wKing || this == bKing) {
				if (!threat)
					moves.add(new int[] { x, y });
			} else {
				moves.add(new int[] { x, y });
			}

			if (other == wKing || other == bKing) {
				oneMoreMovement = true;
				return true;
			}

			return false;

		} else
			return false;

		return true;

	}

	/**
	 * Given the coords x and y, checks if the square is threatened by any piece.<br>
		* In other words, checks if the LinkedList given by the method {@link #calculateMoves(int[])}
		* contains the coords especified in the parameters.
	 *
	 * @param x The x coords in the table (row)
	 * @param y The y coords in the table (col)
	 * @return true if the square is threatened or false if not
	 */
	protected boolean checkThreat(int x, int y) {

		if (team) {
			if (Table.getSquare(x, y).isWhiteThreat())
				return true;
		} else {
			if (Table.getSquare(x, y).isBlackThreat())
				return true;
		}

		return false;

	}

	/**
	 * Given the coords, especifies that square is threatened by a team.<br>
	 *
	 * @param x The x coords in the table (row)
	 * @param y The y coords in the table (col)
	 */
	protected void threatSquare(int x, int y) {

		if (team)
			Table.getSquare(x, y).setBlackThreat(true);
		else
			Table.getSquare(x, y).setWhiteThreat(true);

	}

	/**
	* Called after reload assets.<br>
	* Set the piece's graphics to the new ones, selected by the user.
	*
	* @see Assets
	*/
	public static void updateGui() {

		wPawn.image = Assets.W_PAWN;
		wRock.image = Assets.W_ROCK;
		wHorse.image = Assets.W_HORSE;
		wBishop.image = Assets.W_BISHOP;
		wQueen.image = Assets.W_QUEEN;
		wKing.image = Assets.W_KING;
		bPawn.image = Assets.B_PAWN;
		bRock.image = Assets.B_ROCK;
		bHorse.image = Assets.B_HORSE;
		bBishop.image = Assets.B_BISHOP;
		bQueen.image = Assets.B_QUEEN;
		bKing.image = Assets.B_KING;
		wRock2.image = Assets.W_ROCK;
		bRock2.image = Assets.B_ROCK;

	}

	/**
	* Returns the image of the piece.
	* @return The image of the current piece.
	*/
	public BufferedImage getImage() {
		return image;
	}

	/**
	* Checks if the piece is in the white team.
	* @return true if it is a white piece or false if it is black
	*/
	public boolean isWhiteTeam() {
		return team;
	}

	/**
	* Used for kings. returns if there exists a check or checkmate.
	*
	* @return true if there exists a check or false if not.
	*/
	public boolean isCheck() {
		return check;
	}

}
