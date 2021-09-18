package gui;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import ui.Table;

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

	private static String pieces; // type of pieces ("2d" or "3d"). Used to load a folder
	private static String material; // material of the board and pieces ("wood" or "stone"). Used to load a folder

	private static boolean init = false; // true if the "init" method was called almost once

	public static void init() {

		pieces = "2d";
		material = "stone";

		loadAll();

		init = true;

	}

	public static void loadStone() {
		material = "stone";
		loadAll();
	}

	public static void loadWood() {
		material = "wood";
		loadAll();
	}

	public static void load2d() {
		pieces = "2d";
		loadAll();
	}

	public static void load3d() {
		pieces = "3d";
		loadAll();
	}

	public static void loadAll() {

		W_PAWN = Loader.loadPng("/pieces/" + pieces + "/" + material + "/PawnW.png");
		W_ROCK = Loader.loadPng("/pieces/" + pieces + "/" + material + "/RookW.png");
		W_HORSE = Loader.loadPng("/pieces/" + pieces + "/" + material + "/KnightW.png");
		W_BISHOP = Loader.loadPng("/pieces/" + pieces + "/" + material + "/BishopW.png");
		W_KING = Loader.loadPng("/pieces/" + pieces + "/" + material + "/KingW.png");
		W_QUEEN = Loader.loadPng("/pieces/" + pieces + "/" + material + "/QueenW.png");

		B_PAWN = Loader.loadPng("/pieces/" + pieces + "/" + material + "/PawnB.png");
		B_ROCK = Loader.loadPng("/pieces/" + pieces + "/" + material + "/RookB.png");
		B_HORSE = Loader.loadPng("/pieces/" + pieces + "/" + material + "/KnightB.png");
		B_BISHOP = Loader.loadPng("/pieces/" + pieces + "/" + material + "/BishopB.png");
		B_KING = Loader.loadPng("/pieces/" + pieces + "/" + material + "/KingB.png");
		B_QUEEN = Loader.loadPng("/pieces/" + pieces + "/" + material + "/QueenB.png");

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
				TABLE[x][y] = Loader.loadPng("/board/" + material + "/" + numToRow.get(x) + numToCol.get(y));
			}
		}

		FRAME = Loader.loadPng("/board/" + material + "/frame.png");

		if (init)
			Table.updateGui();

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

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				TABLE[x][y] = null;
			}
		}

		FRAME = null;

	}

}
