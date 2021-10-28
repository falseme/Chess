package ui.layout;

import java.awt.Container;

/**
* A layout for the game table. Used to draw the squares and borders.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class TableLayout extends LayoutAdapter {

	@Override
	public void layoutContainer(Container c) {

		int n = c.getComponentCount() - 1;

		int W = c.getWidth();// screen width
		int H = c.getHeight();// screen height
		int w = W <= H ? W : H; // table size (width = height)
		w *= 0.9;// table size without border

		int x0 = W / 2 - (w / 2); // initial x-pos of the squares
		int y0 = H / 2 - (w / 2);// initial y-pos of the squares
		w /= 8; // squares size

		//squares locations
		int x = x0;
		int y = y0;
		for (int i = 0; i < n; i++) {

			c.getComponent(i).setBounds(x, y, w, w);
			x += w;
			if ((i + 1) % 8 == 0) {
				x = x0;
				y += w;
			}

		}

		// border

		int w4 = w / 4; // side-border's width = 20 & squares' width = 80 &-> 80/4=20
		int s = w4 * 2 + w * 8; // total border size -> the two border's size + 8 * the squares' size (width = height)
		c.getComponent(n).setBounds(x0 - w4, y0 - w4 - w4 / 9, s, s);

	}

}
