package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JComponent;

import event.SquareMouse;
import main.Piece;

public class Square extends JComponent {
	public static final long serialVersionUID = 1l;
	public static Color BLACK = new Color(50, 50, 50);
	public static Color WHITE = new Color(200, 200, 200);

	private BufferedImage background;
	private Color hoverColor;
	private Color offColor;
	private Color moveColor;
	private Color checkColor;

	private boolean check = false;
	private boolean move = false;

	private boolean whiteThreat = false;
	private boolean blackThreat = false;

	private Piece piece;
	private int[] pos; // 0 = x; 1 = y;

	// from 3 to 0. if > 0 then it is possible to make a passant capture to the pawn
	// in this square
	private int passant = 0;

	/**
	 * List that contains every possible move that can be made with the current
	 * piece if not null, case which let this list empty or null
	 */
	private LinkedList<int[]> moves;

	public Square(BufferedImage back, int x, int y) {

		background = back;

		offColor = new Color(0f, 0f, 0f, 0f);
		hoverColor = offColor;
		moveColor = new Color(0f, 0f, 1f, 0.18f);
		checkColor = new Color(1f, 0f, 0f, 0.35f);

		pos = new int[] { x, y };

		addMouseListener(new SquareMouse(this));

		piece = null;

	}

	/**
	 * Add a piece to the Square. If null, nothing is added or the current piece is
	 * deleted
	 * 
	 * @param piece
	 */
	public void addPiece(Piece piece) {

		this.piece = piece;
		repaint();
		if (piece == null) {
			moves = null;
			return;
		}

		recalculateMoves();

	}

	/**
	 * Calculate possible movements and store them into "moves"
	 */
	public void recalculateMoves() {

		if (passant > 0)
			passant--;

		if (piece == null)
			return;
		moves = piece.calculateMoves(pos);

	}

	public void inColor() {
		hoverColor = new Color(1f, 1f, 1f, 0.3f);
		repaint();
	}

	public void outColor() {
		hoverColor = offColor;
		repaint();
	}

	public void clickColor() {
		hoverColor = new Color(0.5f, 0.5f, 0.5f, 0.3f);
		repaint();
	}

	public void switchMoveBackground() {
		move = !move;
		repaint();
	}

	public void resetBackgroundColor() {

		hoverColor = offColor;
		move = false;

		if (piece == null) {
			check = false;
			repaint();
			return;
		}

		check = piece.isCheck();

		repaint();

	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

		if (check) {
			g.setColor(checkColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		if (move) {
			g.setColor(moveColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		g.setColor(hoverColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (piece == null)
			return;

		g.drawImage(piece.getImage(), 0, 0, getWidth(), getHeight(), null);

	}

	public Piece getPiece() {
		return piece;
	}

	public LinkedList<int[]> getMoves() {
		return moves;
	}

	public int[] getPos() {
		return pos;
	}

	public void setWhiteThreat(boolean b) {
		whiteThreat = b;
	}

	public void setBlackThreat(boolean b) {
		blackThreat = b;
	}

	public void resetThreat() {
		whiteThreat = false;
		blackThreat = false;
	}

	public boolean isWhiteThreat() {
		return whiteThreat;
	}

	public boolean isBlackThreat() {
		return blackThreat;
	}

	public void setPassant() {
		passant = 3;
	}

	public boolean canPassant() {
		if (passant > 0)
			return true;
		return false;
	}

}
