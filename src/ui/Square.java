package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JComponent;

import event.SquareMouse;
import main.Piece;

/**
* Class to create squares in the table.<br>
* A square has an image to draw and a piece to draw in front of the image.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class Square extends JComponent {
	private static final long serialVersionUID = 1l;
	public static Color BLACK = new Color(50, 50, 50);
	public static Color WHITE = new Color(200, 200, 200);

	private BufferedImage background; // The background image to be drawn. Given by the Assets.
	/** Colored shadow that is drawn every time in the square. It can take values from other shadow-colors.
		*	Used with the mouse listener to check if the mouse pointer is inside, outside or has clicked.
		* @see SquareMouse */
	protected Color hoverColor;
	private Color offColor; // Colored shadow when the mouse pointer is outside the square.
	private Color moveColor; // Colored shadow when the square is available to move the selected piece.
	private Color checkColor; // Colored shadow when a checked king is in the square.

	private boolean check = false; // True if there is a checked king in the square.
	private boolean move = false; // True if the square is available to move the selected piece.

	private boolean whiteThreat = false;
	private boolean blackThreat = false;

	private Piece piece;
	private int[] pos; // position - [0]=x - [1]=y

	private int passant = 0; // From 3 to 0. if > 0 it is possible to make a passant capture to the pawn in this square.

	/** list with every movement the piece in the square can do. If the piece is null, the list is null */
	protected LinkedList<int[]> moves;

	/**
		* Creates a square with an image and the coords in the table.
		* @param back The background texture image of the square drawn in the table. Given by the Assets.
		* @param x Row coord in the table.
		* @param y Col coord in the table.
		*/
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
	 * Adds a piece to the Square. If null, nothing is added or the current piece deleted and the list {@link #moves} becomes null.
	 *
	 * @param piece The piece to move in the square, or just to be created in it.
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
	 * Calculates possible movements and store them into {@link #moves} using methods from Piece. <br>
		* @see Piece
	 */
	public void recalculateMoves() {

		if (passant > 0)
			passant--;

		if (piece == null)
			return;
		moves = piece.calculateMoves(pos);

	}

	/**
		* Changes the {@link #hoverColor} into a color to draw when the mouse pointer is inside the square.
		*/
	public void inColor() {
		hoverColor = new Color(1f, 1f, 1f, 0.3f);
		repaint();
	}

	/**
		* Changes the {@link #hoverColor} into a color to draw when the mouse pointer is outside the square.
		*/
	public void outColor() {
		hoverColor = offColor;
		repaint();
	}

	/**
		* Changes the {@link #hoverColor} into a color to draw when the mouse pointer is clicked inside the square.
		*/
	public void clickColor() {
		hoverColor = new Color(0.5f, 0.5f, 0.5f, 0.3f);
		repaint();
	}

	/**
		* Switches the background shadow color between beeing able to let the piece move into it
		* or just letting the player choose a piece to move. <br><br>
		* In other words, if the square is selected to let the player move a piece into it from the Table methods,
		* the shadow color will be {@link #moveColor}. But if not, it will be {@link #offColor}
		* @see Table
		*/
	public void switchMoveBackground() {
		move = !move;
		repaint();
	}

	/**
		* Resets the shadow color to a rgba(0,0,0,0) unless it has a checked king.
		*/
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

	/**
		* Updates the texture of the square drawn in the table. Given by the Assets.
		* @param img The new texture that the current one will be replaced by.
		* @see gui.Assets
		*/
	public void updateGui(BufferedImage img) {

		background = img;
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

	/**
		* Gets the piece that is in the square.
		* @return The piece in the square.
		* @see Piece
		*/
	public Piece getPiece() {
		return piece;
	}

	/**
		* Get the list of moves the piece in the square can do in a specific turn.
		* @return The linkedList with the moves coords given in a two-length array.
		*/
	public LinkedList<int[]> getMoves() {
		return moves;
	}

	/**
		* Get the position in the table.
		* @return The position in the table determined by the rows and cols.
		*/
	public int[] getPos() {
		return pos;
	}

	/**
		* Sets whether the square is in the path of white piece. It is said that the square is threatened
		* @param b true if the square is in the path of a white piece or false if not.
		*/
	public void setWhiteThreat(boolean b) {
		whiteThreat = b;
	}

	/**
		* Sets whether the square is in the path of black piece. It is said that the square is threatened
		* @param b true if the square is in the path of a black piece or false if not.
		*/
	public void setBlackThreat(boolean b) {
		blackThreat = b;
	}

	/**
		* Resets the threatened state of the square. The square is not in the path of any piece.<br>
		*/
	public void resetThreat() {
		whiteThreat = false;
		blackThreat = false;
	}

	/**
		* Returns whether the square is in the path of any white piece or not.
		* @return true if the square is threatened or false if not (by a white piece).
		*/
	public boolean isWhiteThreat() {
		return whiteThreat;
	}

	/**
		* Returns whether the square is in the path of any black piece or not.
		* @return true if the square is threatened or false if not (by a black piece).
		*/
	public boolean isBlackThreat() {
		return blackThreat;
	}

	/**
		* Sets the values to their maximum expresion to be used in the algoritm, decreasing them to 0 and
		* letting pawns do a passant capture to the pawn in this square.
		*/
	public void setPassant() {
		passant = 3;
	}

	/**
		* Returns whether a pawn can passant capture the pawn in this square. <br>
		* A pawn can do a passant capture when its values are bigger than 0.<br>
		* (It does not check if the piece is a pawn or not. Classes in the "See Also" section do that).
		*
		* @return true if it is possible to passant capture the pawn in the square or false if not.
		* @see SquareMouse
		*/
	public boolean canPassant() {
		if (passant > 0)
			return true;
		return false;
	}

}
