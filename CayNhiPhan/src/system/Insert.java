package system;

import java.awt.Color;
import java.util.Map;

import javax.swing.JPanel;

import bst.Node;
import bst.SettingBst;

public class Insert implements Runnable {

	/**
	 * đối tượng của class Insert
	 */
	private static final Insert INSERT = new Insert();

	public static Insert getInsert() {
		return INSERT;
	}

	/***
	 * đây là các biến lưu trữ dữ liệu khi thực hiện insert
	 */
	private static String tmp_s;
	private static int tmp_v;
	private static int tmp_x;
	private static int tmp_y;

	/**
	 * đây là 2 biến giúp ra ngoài class ta có thể setting chúng , để phục vục ch
	 * việc insert
	 */
	protected JPanel box;
	protected String text;

	/**
	 * hàm khởi tạo của class
	 */
	public Insert() {
		super();
	}

	public Insert(String text) {
		super();
		this.text = text;
	}

	/**
	 * khởi tạo đối tượng cần thiết trong class
	 */
	private SettingBst bst = SettingBst.getBst();
	private Dlete dlete = Dlete.getDlete();
	private Draw draw = Draw.getDraw();
	private Gui gui = Gui.getGui();
	private Line line = Line.getLine();
	private Oval oval = Oval.getOval();
	private Question question = Question.getQuestion();
	private Search search = Search.getSearch();
	private Update update = Update.getUpdate();

	/***
	 * @param x là vị trí của node trên GUI đây là hàm điều hướng node
	 */

	private void direction(JPanel box, int x) {

		boolean op = bst.opposite();

		if (!op) {
			if (bst.F.equals("LEFT")) {
				LL(x);
			} else {
				RR(x);
			}
		} else {
			if (bst.S.equals("RIGHT")) {
				LR(box, x);
			} else {
				RL(box, x);
			}
		}

	}

	/***
	 * 
	 * @param box  : hình tròn để vẽ
	 * @param text : con số
	 * 
	 *             đây là hàm để vẽ một node trên gui
	 */

	protected void drawing(JPanel box, String text) {

		JPanel isSameLocation = question.isSameLocationX(oval.listOval, oval.X);
		oval.listOval.put(Integer.parseInt(text), (Oval) box);

		/** tạo các biến để lưu giá trị và thêm vào list để sửa chữa chúng nếu cần */
		tmp_s = bst.S;
		tmp_v = oval.before_V;
		tmp_x = box.getLocation().x;
		tmp_y = oval.Y;

		boolean isChoise = bst.opposite();

		isSame(box, isSameLocation);

		/**
		 * nếu có vị trí trùng trên Gui
		 * 
		 * di chuyển oval và line trên Gui
		 * 
		 * cập nhập lại listMapOval và listMapLine
		 */

		if (isSameLocation != null) {
			update.updateGui(update.listUpdateOval, update.listUpdateLine, Integer.parseInt(text));
		}

		/** thêm node **/
		insertBox(box);

		/** thêm dây **/

		if (bst.level > 1 && isSameLocation == null) {

			oval.before_V = tmp_v;

			Node q = search.location(oval.before_V);

			if (q != null) {
				oval.before_X = q.getLocalX();
			}

			bst.S = tmp_s;
			oval.X = tmp_x;
			oval.Y = tmp_y;

			JPanel connect = draw.Line(text, 0, 0, 0, 0);
			line.listLine.put(Integer.parseInt(text), (Line) connect);
			insertLine(connect);
		}

		update.loadGui(gui);
	}

	/**
	 * @param box : dối tượng cần vẽ hầm để thêm node trên gui
	 */
	private void insertBox(JPanel box) {
		box.setForeground(Color.GREEN);
		gui.getContentPane().add(box);
	}

	/**
	 * @param conect : đối tượng kết nối giữ 2 hộp hàm để nối 2 node lại với nhau
	 */
	private void insertLine(JPanel connect) {
		gui.getContentPane().add(connect);
	}

	/**
	 * @param box   : đối tượng để vẽ
	 * @param panel : là đối tượng trùng location.X với box
	 * 
	 *              cập nhật lại vị trí của box
	 */
	private void isSame(JPanel box, JPanel panel) {
		if (panel == null) {
			return;
		}

		/** loại bỏ tất cả các phần tử trong mảng **/
		update.listUpdateOval.clear();
		update.listUpdateLine.clear();

		/**
		 * lr : 1 , rl : 2 , nothing : 0
		 */
		int dir = bst.isSameDirection();

		direction(box, oval.X);
	}

	/**
	 * @pram x : location.X của node cần thêm lấy những phần tử nhỏ hơn <= x trừ
	 *       chính nó và tất cả b�?n nó sẽ trừ oval.W
	 **/
	private void LL(int x) {
		int value = Integer.parseInt(text);
		bst.getNeedNode(value, bst.LOW);
		for (Integer i : bst.listNeedChill) {
			if (i == value) {
				continue;
			}
			int xx = oval.listOval.get(i).getLocation().x;
			int yy = oval.listOval.get(i).getLocation().y;
			oval.listOval.get(i).setLocation(xx - oval.W, yy);
			update.listUpdateOval.add(oval.listOval.get(i));
		}
	}

	/**
	 * @pram x : location.X của node cần thêm lấy những phần tử nh�? hơn x và chính
	 *       nó và tất cả b�?n nó sẽ trừ location_x
	 **/
	private void LR(JPanel box, int x) {
		int value = Integer.parseInt(text);
		bst.getNeedNode(value, bst.LOW);
		for (Integer i : bst.listNeedChill) {
			int xx = oval.listOval.get(i).getLocation().x;
			int yy = oval.listOval.get(i).getLocation().y;
			if (xx == box.getLocation().x) {
				if (value != i) {
					continue;
				}
			}
			oval.listOval.get(i).setLocation(xx - oval.W, yy);
			update.listUpdateOval.add(oval.listOval.get(i));
		}
	}

	/**
	 * @pram x : location.X của node cần thêm lấy những phần tử lớn hơn nó , và
	 *       chính nó và location + oval.W
	 *
	 */
	private void RL(JPanel box, int x) {
		int value = Integer.parseInt(text);
		bst.getNeedNode(value, bst.HIGHT);

		for (Integer i : bst.listNeedChill) {
			int xx = oval.listOval.get(i).getLocation().x;
			int yy = oval.listOval.get(i).getLocation().y;
			if (xx == box.getLocation().x) {
				if (value != i) {
					continue;
				}
			}
			oval.listOval.get(i).setLocation(xx + oval.W, yy);
			update.listUpdateOval.add(oval.listOval.get(i));
		}
	}

	/**
	 * @pram x : location.X của node cần thêm lấy những phần tử nhỏ hơn >= x trừ
	 *       chính nó và tất cả các phần tử nó sẽ + location_x
	 **/
	private void RR(int x) {
		int value = Integer.parseInt(text);
		bst.getNeedNode(value, bst.HIGHT);
		for (Integer i : bst.listNeedChill) {
			if (i == value) {
				continue;
			}
			int xx = oval.listOval.get(i).getLocation().x;
			int yy = oval.listOval.get(i).getLocation().y;
			oval.listOval.get(i).setLocation(xx + oval.W, yy);
			update.listUpdateOval.add(oval.listOval.get(i));
		}
	}

	/**
	 * @param text : số cần thêm vào node đây là hàm thêm 1 node trên GUI bình
	 *             thường , không dùng luồng
	 */
	protected void insertNormal(String text) {
		this.text = text;
		this.box = settingBox(text);
		drawing(this.box, this.text);
	}

	/**
	 * luồng chạy
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		drawing(INSERT.box, INSERT.text);
	}

	/**
	 * @param text : giá trị của node cần vẽ
	 * @return 1 đối tượng mà ta sẽ dùng hàm drawing để vẽ
	 */
	protected JPanel settingBox(String text) {

		int value = Integer.parseInt(text);

		/** insert node **/
		bst.addNode(value);

		/** set path **/

		/**
		 * a[root] = root
		 */
		if (bst.level == 1) {
			bst.setPath(bst.getFirstElement(), bst.getFirstElement());
		} else {
			bst.setPath(value, oval.before_V);
		}

		/** lấy vị trí x , y **/

		int x = oval.X;
		int y = oval.Y;

		JPanel box = draw.Oval(text, x, y);

		return box;
	}

}
