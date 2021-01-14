package system;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Oval extends JPanel {

	public int before_V;
	public int before_X;
	public final int H = 60;
	private static final Oval oval = new Oval();
	public final int W = 40;
	public int X;
	public int Y;

	public static Oval getOval() {
		return oval;
	}

	protected Map<Integer , Oval> listOval = new HashMap<Integer, Oval>();
	
	
	public Map<Integer, Oval> getListOval() {
		return listOval;
	}

	private String text;
	private int value;

	private int x;

	private int y;

	public Oval() {

	}

	public Oval(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		g2d.setStroke(bs1);
//		g2d.setColor(new Color(0, 255, 0));
		g2d.drawOval(2, 1, 40, 40);

		g2d.setFont(new Font("NewellsHand", Font.PLAIN, 15));

		if (text.length() == 3) {
			g2d.drawString(text, 8, 26);
		} else if (text.length() == 1) {
			g2d.drawString(text, 17, 25);
		} else {
			g2d.drawString(text, 13, 25);
		}

		g2d.dispose();
	}

	public void setValue(int value) {
		this.value = value;
	}

}
