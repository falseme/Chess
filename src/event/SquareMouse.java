package event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import main.App;
import main.Piece;
import ui.Square;
import ui.Table;

public class SquareMouse extends MouseAdapter {

	private static boolean whiteMoving = true;

	private Square owner;
	private static Square moving; // contains the square with the piece which is moving;

	private boolean in = false;

	public SquareMouse(Square owner) {

		this.owner = owner;

	}

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

			// if it is a king > check if the movement is a castling
			if (moving.getPiece() == Piece.wKing && owner.getPiece() == Piece.wRock
					|| moving.getPiece() == Piece.bKing && owner.getPiece() == Piece.bRock) {

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

				owner.addPiece(moving.getPiece());
				moving.addPiece(null);

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

		owner.brightBackground();
		in = true;

	}

	@Override
	public void mouseExited(MouseEvent e) {

		owner.resetBackground();
		in = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {

		owner.darkBackground();

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (in) {
			owner.brightBackground();
			clicked(e);
		} else
			owner.resetBackground();

	}

	public static void reset() {

		whiteMoving = true;
		moving = null;

	}

	public static void switchDefaultTurn() {

		whiteMoving = !whiteMoving;

	}

}
