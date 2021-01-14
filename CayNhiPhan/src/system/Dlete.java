package system;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import bst.Node;
import bst.SettingBst;
import move.LightNode;
import move.Move;
import move.OnlyOne;

public class Dlete implements Runnable {

	/**
	 * đối tượng của class
	 */
	private static final Dlete DLETE = new Dlete();
	private Event event = Event.getEvent();

	public static Dlete getDlete() {
		return DLETE;
	}

	/**
	 * những biến cần lấy dữ liệu khi ra ngoài class
	 */
	private String text;
	private JPanel box;

	/**
	 * đây là hàm khởi tọa của class
	 * 
	 * @param box  : node cần xóa
	 * @param text : dữ liệu đầu vào
	 */
	public Dlete(JPanel box, String text) {
		super();
		this.text = text;
		this.box = box;
	}

	public Dlete() {
		super();
	}

	/**
	 * getter && setter
	 */
	public JPanel getBox() {
		return box;
	}

	public void setBox(JPanel box) {
		this.box = box;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * đối tượng hỗ trợ cho class
	 */
	private Gui gui = Gui.getGui();
	private Line line = Line.getLine();
	private Update update = Update.getUpdate();
	private Oval oval = Oval.getOval();
	private SettingBst bst = SettingBst.getBst();
	private Search search = Search.getSearch();
	private LightNode lightNode = LightNode.getLightNode();
	private Move move = Move.getMove();

	/**
	 * @param text : giá trị cần xóa trên cây hàm dùng để xóa 1 node trên gui , dùng
	 *             thread
	 */
	private void dlete(String text) {

		Node q = bst.search(Integer.parseInt(text));
		int value = q.getVal();
		int valueNext = value;

		JPanel line = search.find(Dlete.this.line, value);
		boolean isNull = false;

		// nếu node cần xóa không có con
		if (q.getLeft() == null && q.getRight() == null) {
			isNull = true;
		}
		// nếu node có 1 hoặc 2 con
		else {

			JPanel nextBoxLight = null;
			move.setExecutorService(Executors.newSingleThreadExecutor());

			if ((q.getLeft() != null && q.getRight() == null)) {

				Node nextLeft = q.getLeft();
				valueNext = nextLeft.getVal();
				nextBoxLight = search.find(oval, nextLeft.getVal());

			} else {

				OnlyOne onlyOne = new OnlyOne(box, Color.red);
				Thread run = new Thread(onlyOne);
				run.start();

				/** làm sáng các node trên listmostchill bắt đầu từ q.right() **/
//				 get listLight
				int valueLeft = bst.leftMostValue(q.getRight());

//				 turn light left most chill
				LightNode lightNodeChill = new LightNode(500, bst.listMostChill, Color.YELLOW);
				move.getExecutorService().execute(lightNodeChill);

//				get node di chuyển với box có giá trị cần xóa
				nextBoxLight = search.find(oval, valueLeft);

				valueNext = valueLeft;
			}

//			move node
			Move moveNode = new Move(nextBoxLight, box);
			move.getExecutorService().execute(moveNode);

		}

//		  waitting đợi Move thực hiện xong rồi hàm light sẽ thực hiện

		if (!isNull) {
			synchronized (move) {
				try {
					move.wait();
					move.getExecutorService().shutdown();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// update
		int root = bst.getFirstElement();
		bst.getNeedNode(root, bst.ALL);
		removeList(bst.listNeedChill);
		ArrayList<Integer> listUpdate = new ArrayList<Integer>();
		bst.dle(value);
		bst.getNeedNode(root, bst.ALL);
		listUpdate.addAll(new ArrayList<Integer>(bst.listNeedChill));
		bst.listDle(bst.listNeedChill);
		oval.listOval.clear();
		Dlete.this.line.listLine.clear();
		update.drawAgain(listUpdate);

		/**
		 * nếu sử dụng add_low thì event này sẽ hoạt động trở lại
		 */
		synchronized (event) {
			event.notifyAll();
		}
	}

	/**
	 * 
	 * @param line : đối tượng cần xóa
	 * @param oval : đối tượng cần xóa
	 * 
	 *             xóa 1 đối tượng trong 2 đối tượng trên ra khởi GUI và list của
	 *             chúng
	 */

	protected void removeOneOvalAndLine(Line line, Oval oval) {

		if (oval != null) {
			int valueOval = oval.getValue();
			this.oval.listOval.remove(valueOval);
			gui.getContentPane().remove(oval);
		}

		if (line != null) {
			int valueLine = line.getValue();
			this.line.listLine.remove(valueLine);
			gui.getContentPane().remove(line);
		}

		gui.getContentPane().revalidate();
		gui.getContentPane().repaint();

	}

	/**
	 * 
	 * @param listOval : list oval cần xóa
	 * @param listLine : list line cần xóa
	 * 
	 *                 xóa tất các các thứ có trong listOval && listLine trên GUI
	 */
	protected void remove(ArrayList<Oval> listOval, ArrayList<Line> listLine) {

		for (int i = 0; i < listOval.size(); i++) {
			removeOneOvalAndLine(null, listOval.get(i));
		}

		for (int i = 0; i < listLine.size(); i++) {
			removeOneOvalAndLine(listLine.get(i), null);
		}

	}

	/**
	 * @param list : một lít giá trị cần xóa khỏi gui hàm này sẽ xóa các node và dây
	 *             mang giá trị có trong @param list
	 */
	public void removeList(ArrayList<Integer> list) {
		remove(search.getListOval(list), search.getListLine(list));
	}

	/**
	 * thread run
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		dlete(text);
	}

}
