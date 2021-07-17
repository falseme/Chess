package ui.layout;

import java.awt.Container;

public class TableLayout extends LayoutAdapter {

	@Override
	public void layoutContainer(Container c) {

		int W = c.getWidth();
		int H = c.getHeight();

		int w = W <= H ? W : H;
		w /= 8;

		int x = 0;
		int y = 0;

		for (int i = 0; i < c.getComponentCount(); i++) {

			c.getComponent(i).setBounds(x, y, w, w);
			x += w;
			if ((i + 1) % 8 == 0) {
				x = 0;
				y += w;
			}

		}

	}

}
