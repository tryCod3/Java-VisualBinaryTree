package system;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Line extends JPanel {

	private static final Line LINE = new Line();

	public static Line getLine() {
		return LINE;
	}

	protected Map<Integer, Line> listLine = new HashMap<Integer, Line>();

	private int value;
	private int x1;
	private int x2;
	private int y1;
	private int y2;

	public Line() {

	}

	public Line(int x1, int x2, int y1, int y2) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int val) {
		this.value = val;
	}

	public Map<Integer, Line> getListLine() {
		return listLine;
	}

	public void setListLine(Map<Integer, Line> listLine) {
		this.listLine = listLine;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		g2d.setStroke(bs1);

		g2d.drawLine(x1, y1, x2, y2);

		g2d.dispose();
	}

}
