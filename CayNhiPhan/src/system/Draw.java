package system;

import javax.swing.JPanel;

import bst.SettingBst;

public class Draw {
	/**
	 * đối tượng của class
	 */
	private static final Draw DRAW = new Draw();

	public static Draw getDraw() {
		return DRAW;
	}

	/**
	 * đối tượng hỗ trợ class
	 */
	private Oval oval = Oval.getOval();
	private SettingBst bst = SettingBst.getBst();
	private Gui gui = Gui.getGui();

	/**
	 * @param text : giá trị của node
	 * @param x1   : 0
	 * @param x2   : 0
	 * @param y1   : 0
	 * @param y2   : 0
	 * @return trả về mộ đối tượng để kết nối 2 node
	 */
	public JPanel Line(String text, int x1, int x2, int y1, int y2) {
		int x = 0, y = 0;
		int w = 0, h = 0;
		y = (oval.Y - 60) + 41;
		h = oval.Y - y;
		if (bst.S.equals("LEFT")) {
			x = oval.X + 22; // 45 >> 1
//			System.out.println("L = " + oval.before_X +  " " + oval.X);
			w = (oval.before_X - oval.X);
			x1 = 0;
			x2 = w;
			y1 = h;
			y2 = 0;
		} else {
			x = oval.before_X + 22;
//			System.out.println("R = " + oval.before_X +  " " + oval.X);
			w = (oval.X - oval.before_X);
			x1 = 0;
			x2 = w;
			y1 = 0;
			y2 = h;
		}
		Line line = new Line(x1, x2, y1, y2);
		line.setSize(w, h);
		line.setLocation(x, y);
		line.setBackground(gui.getContentPane().getBackground());
		line.setValue(Integer.parseInt(text));
//		System.out.println("text = " + text + " x = " + x + " y =  " + y + " w = " + w + " h = " + h);
		return line;
	}

	/**
	 * 
	 * @param text : giá trị của node
	 * @param x    : vị trí của location.X của node
	 * @param y    : vị trí của location.Y của node
	 * @return trả về một đối tượng là node
	 */
	public JPanel Oval(String text, int x, int y) {
		Oval oval = new Oval(text, x, y);
		oval.setBackground(gui.getContentPane().getBackground());
		oval.setSize(45, 42); // 45 here
		oval.setLocation(x, y);
		oval.setValue(Integer.parseInt(text));
		return oval;
	}

}
