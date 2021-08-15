package event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

		if (!owner.isEnabled()) {
			System.out.println(false);
			return;
		}

		if (moving != null) {

			owner.addPiece(moving.getPiece());
			moving.addPiece(null);
			moving = null;
			Table.enableAll(true);

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

}
