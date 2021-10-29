package event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import main.App;
import main.Piece;
import ui.Square;
import ui.Table;

/**
* Mouse listener class. Used to check pieces in a square and move them in the table.
* It also implements some mechanics as castilng, passant pawn capture and pawn promotion.<br>
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class SquareMouse extends MouseAdapter {

	private static boolean whiteMoving = true; // True if the white team is doing the moving or false if not.

	private Square owner; // The squre which has added the mouse listener.
	private static Square moving; // The square that has the piece which was selected to move

	private boolean in = false; // true if the mouse pointer is inside or false if it is outside

	/**
	* Creates a listener with a parent from the Square class. <br>
	* How to use: <br>
	* Square square = new Square(img, x, y); <br>
	* square.addMouseListener(new SquareMouse(square));
	*
	* @param owner The parent object which this listener will work with.
	*/
	public SquareMouse(Square owner) {

		this.owner = owner;

	}

	/**
	* Called when mouse is clicked inside the OWNER (square) object.<br>
	* It selects or unselects the square clicked so as to let the player move the piece in the square or select another.<br>
	* When moving, checks for castling movements, passant captures or promotions at last row.<br>
	* When promoting, opens a JOptionPane to select a piece.<br>
	* After moving changes the player's turn, from wite to black or from black to white, if offline.<br><br>
	*
	* Uses the method Table.enable and Table.enableAll to set which square is clickable or not.
	* @param e - object from java.awt.event.MouseEvent. necessary in the MouseListener.
	* @see Table
	*/
	public void clicked(MouseEvent e) {

		if (App.CLIENT != null) {
			if (!App.CLIENT.turn)
				return;
		}

		if (owner == moving) {

			moving = null;
			Table.enableAll(true);

			return;

		}

		if (!owner.isEnabled()) {
			System.out.println(false);
			return;
		}

		if (moving != null) {

			moving.getPiece().moved = true;

			// if it is a king => check if the movement is a castling
			if ((moving.getPiece() == Piece.wKing && (owner.getPiece() == Piece.wRock || owner.getPiece() == Piece.wRock2))
					|| (moving.getPiece() == Piece.bKing && (owner.getPiece() == Piece.bRock || owner.getPiece() == Piece.bRock2))) {

				if (owner.getPos()[0] == 0) {

					Table.getSquare(moving.getPos()[0] - 2, moving.getPos()[1]).addPiece(moving.getPiece());
					Table.getSquare(moving.getPos()[0] - 1, moving.getPos()[1]).addPiece(owner.getPiece());

				} else if (owner.getPos()[0] == 7) {

					Table.getSquare(moving.getPos()[0] + 2, moving.getPos()[1]).addPiece(moving.getPiece());
					Table.getSquare(moving.getPos()[0] + 1, moving.getPos()[1]).addPiece(owner.getPiece());

				}

				moving.addPiece(null);
				owner.addPiece(null);

			} else { // normal movement

				// check if pawn moves two squares so as to check a passant capture
				if (moving.getPiece() == Piece.wPawn || moving.getPiece() == Piece.bPawn) {

					if (Math.abs(owner.getPos()[1] - moving.getPos()[1]) == 2)
						owner.setPassant();

				}

				owner.addPiece(moving.getPiece());
				moving.addPiece(null);

				// check passant capture
				switch (owner.getPiece()) {

				case wPawn:

					if (Table.getSquare(owner.getPos()[0], owner.getPos()[1] + 1).canPassant())
						Table.getSquare(owner.getPos()[0], owner.getPos()[1] + 1).addPiece(null);

					break;
				case bPawn:

					if (Table.getSquare(owner.getPos()[0], owner.getPos()[1] - 1).canPassant())
						Table.getSquare(owner.getPos()[0], owner.getPos()[1] - 1).addPiece(null);

					break;
				default:
					// do-nothing
					break;

				}

			}

			// check if pawn is at last row
			if (owner.getPos()[1] == 0 && owner.getPiece() == Piece.wPawn
					|| owner.getPos()[1] == 7 && owner.getPiece() == Piece.bPawn) {

				String[] options = new String[] { "Dama", "Torre", "Caballo", "Alfil" };

				int selection = JOptionPane.showOptionDialog(null, "message", "title", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, null);

				switch (selection) {

				case 0:

					if (owner.getPiece().isWhiteTeam())
						owner.addPiece(Piece.wQueen);
					else
						owner.addPiece(Piece.bQueen);

					break;
				case 1:

					if (owner.getPiece().isWhiteTeam())
						owner.addPiece(Piece.wRock);
					else
						owner.addPiece(Piece.bRock);

					break;

				case 2:

					if (owner.getPiece().isWhiteTeam())
						owner.addPiece(Piece.wHorse);
					else
						owner.addPiece(Piece.bHorse);

					break;
				case 3:

					if (owner.getPiece().isWhiteTeam())
						owner.addPiece(Piece.wBishop);
					else
						owner.addPiece(Piece.bBishop);

					break;

				}

			}

			moving = null;
			Table.enableAll(true);

			if (App.CLIENT != null) {

				App.CLIENT.sendData();
				return;

			}

			whiteMoving = !whiteMoving;
			return;

		}

		if (owner.getPiece() == null)
			return;

		if (owner.getMoves() == null)
			return;
		if (owner.getMoves().isEmpty())
			return;

		if (whiteMoving != owner.getPiece().isWhiteTeam())
			return;

		moving = owner;

		Table.enable(owner.getMoves());

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		owner.inColor();
		in = true;

	}

	@Override
	public void mouseExited(MouseEvent e) {

		owner.outColor();
		in = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {

		owner.clickColor();

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (in) {
			owner.inColor();
			clicked(e);
		} else
			owner.outColor();

	}

	/**
	* Sets the statics variables to default (Any piece is selected to move and it is the white's turn to move).
	*/
	public static void reset() {

		whiteMoving = true;
		moving = null;

	}

	/**
	* Used when offline. Switches the player turn.
	*/
	public static void switchDefaultTurn() {

		whiteMoving = !whiteMoving;

	}

}
