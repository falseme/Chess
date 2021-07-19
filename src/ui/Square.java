package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JComponent;

import event.SquareMouse;
import main.Piece;

public class Square extends JComponent {
	public static final long serialVersionUID = 1l;
	public static Color BLACK = new Color(50, 50, 50);
	public static Color WHITE = new Color(200, 200, 200);

	private Color backgroundColor;
	private Color normalColor;
	private Color moveColor;
	private Color checkColor;

	private boolean whiteThreat = false;
	private boolean blackThreat = false;

	private Piece piece;
	private int[] pos; // 0 = x; 1 = y;

	/**
	 * List that contains every possible move that can be made with the current
	 * piece if not null, case which let this list empty or null
	 */
	private LinkedList<int[]> moves;

	public Square(Color color, int x, int y) {

		backgroundColor = color;
		normalColor = color;
		moveColor = normalColor == WHITE ? new Color(120, 120, 250) : new Color(70, 70, 250);
		checkColor = normalColor == WHITE ? new Color(250, 120, 120) : new Color(250, 40, 40);
		color = checkColor;
		setBackground(color);

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

		if (piece == null)
			return;
		moves = piece.calculateMoves(pos);

	}

	public void darkBackground() {
		setBackground(backgroundColor.darker());
	}

	public void brightBackground() {
		setBackground(backgroundColor.brighter());
	}

	public void resetBackground() {
		setBackground(backgroundColor);
	}

	public void switchBackground() {

		if (backgroundColor == normalColor)
			backgroundColor = moveColor;
		else
			backgroundColor = normalColor;

		setBackground(backgroundColor);

	}

	public void resetBackgroundColor() {

		backgroundColor = normalColor;
		setBackground(backgroundColor);

		if (piece == null)
			return;

		if (piece.isCheck()) {
			backgroundColor = checkColor;
			setBackground(backgroundColor);
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(getBackground());
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

}
