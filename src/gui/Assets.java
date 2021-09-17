package gui;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets {

	public static BufferedImage W_PAWN;
	public static BufferedImage W_ROCK;
	public static BufferedImage W_HORSE;
	public static BufferedImage W_BISHOP;
	public static BufferedImage W_KING;
	public static BufferedImage W_QUEEN;

	public static BufferedImage B_PAWN;
	public static BufferedImage B_ROCK;
	public static BufferedImage B_HORSE;
	public static BufferedImage B_BISHOP;
	public static BufferedImage B_KING;
	public static BufferedImage B_QUEEN;

	public static BufferedImage[][] TABLE;

	public static BufferedImage FRAME;

	public static BufferedImage T_BORDER_LEFT;
	public static BufferedImage T_BORDER_RIGHT;
	public static BufferedImage T_BORDER_TOP;
	public static BufferedImage T_BORDER_BOTTOM;

	public static void loadAll() {

		W_PAWN = Loader.loadPng("/pieces/2d/stone/PawnW.png");
		W_ROCK = Loader.loadPng("/pieces/2d/stone/RookW.png");
		W_HORSE = Loader.loadPng("/pieces/2d/stone/KnightW.png");
		W_BISHOP = Loader.loadPng("/pieces/2d/stone/BishopW.png");
		W_KING = Loader.loadPng("/pieces/2d/stone/KingW.png");
		W_QUEEN = Loader.loadPng("/pieces/2d/stone/QueenW.png");

		B_PAWN = Loader.loadPng("/pieces/2d/stone/PawnB.png");
		B_ROCK = Loader.loadPng("/pieces/2d/stone/RookB.png");
		B_HORSE = Loader.loadPng("/pieces/2d/stone/KnightB.png");
		B_BISHOP = Loader.loadPng("/pieces/2d/stone/BishopB.png");
		B_KING = Loader.loadPng("/pieces/2d/stone/KingB.png");
		B_QUEEN = Loader.loadPng("/pieces/2d/stone/QueenB.png");

		HashMap<Integer, Character> numToRow = new HashMap<Integer, Character>();
		numToRow.put(0, 'a');
		numToRow.put(1, 'b');
		numToRow.put(2, 'c');
		numToRow.put(3, 'd');
		numToRow.put(4, 'e');
		numToRow.put(5, 'f');
		numToRow.put(6, 'g');
		numToRow.put(7, 'h');

		HashMap<Integer, Character> numToCol = new HashMap<Integer, Character>();
		numToCol.put(0, '8');
		numToCol.put(1, '7');
		numToCol.put(2, '6');
		numToCol.put(3, '5');
		numToCol.put(4, '4');
		numToCol.put(5, '3');
		numToCol.put(6, '2');
		numToCol.put(7, '1');

		// load table

		TABLE = new BufferedImage[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {

				TABLE[x][y] = Loader.loadPng("/board/black/" + numToRow.get(x) + numToCol.get(y));

			}
		}

		FRAME = Loader.loadPng("/board/black/frame.png");

		T_BORDER_LEFT = Loader.loadPng("/board/black/border_left_legend");
		T_BORDER_RIGHT = Loader.loadPng("/board/black/border_right");
		T_BORDER_TOP = Loader.loadPng("/board/black/border_top");
		T_BORDER_BOTTOM = Loader.loadPng("/board/black/border_bottom_legend");

	}

	public static void emptyAll() {

		W_PAWN = null;
		W_ROCK = null;
		W_HORSE = null;
		W_BISHOP = null;
		W_KING = null;
		W_QUEEN = null;

		B_PAWN = null;
		B_ROCK = null;
		B_HORSE = null;
		B_BISHOP = null;
		B_KING = null;
		B_QUEEN = null;

	}

}
