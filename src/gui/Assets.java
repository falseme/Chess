package gui;

import java.awt.image.BufferedImage;

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

	public static void loadAll() {

		W_PAWN = Loader.loadPng("/w_pawn");
		W_ROCK = Loader.loadPng("/w_rock");
		W_HORSE = Loader.loadPng("/w_horse");
		W_BISHOP = Loader.loadPng("/w_bishop");
		W_KING = Loader.loadPng("/w_king");
		W_QUEEN = Loader.loadPng("/w_queen");

		B_PAWN = Loader.loadPng("/b_pawn");
		B_ROCK = Loader.loadPng("/b_rock");
		B_HORSE = Loader.loadPng("/b_horse");
		B_BISHOP = Loader.loadPng("/b_bishop");
		B_KING = Loader.loadPng("/b_king");
		B_QUEEN = Loader.loadPng("/b_queen");

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
