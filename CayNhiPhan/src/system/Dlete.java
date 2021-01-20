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

		int value = Integer.parseInt(text);
		Node tmp = bst.search(value);
		boolean isNullChill = (bst.cntChill(tmp) == 0 ? true : false);
		Oval ovalRemove = oval.listOval.get(value);
		Line lineRemove = line.listLine.get(value);

		// nếu nó không có con
		if (isNullChill) {
			removeOneOvalAndLine(lineRemove, ovalRemove);
			drawing(value, false);
		} else {
			whenHaveChill(tmp);
		}

		/**
		 * nếu sử dụng add_low thì event này sẽ hoạt động trở lại
		 */
		synchronized (event) {
			event.notifyAll();
		}

		update.loadGui(gui);
	}

	/**
	 * 
	 * @param value giá trị cần xóa
	 */
	private void whenHaveChill(Node q) {

		JPanel nextBoxLight = null;
		int value = q.getVal();
		int valueNext = value;
		move.setExecutorService(Executors.newSingleThreadExecutor());

		OnlyOne onlyOne = new OnlyOne(box, Color.red);
		Thread run = new Thread(onlyOne);
		run.start();

		int valueLeft = 0;

		if ((q.getLeft() != null && q.getRight() == null)) {
			valueLeft = bst.rightMostValue(q.getLeft());
		} else {
			valueLeft = bst.leftMostValue(q.getRight());
		}

//		 turn light left most chill
		LightNode lightNodeChill = new LightNode(500, bst.listMostChill, Color.YELLOW);
		move.getExecutorService().execute(lightNodeChill);

//		get node di chuyển với box có giá trị cần xóa
		nextBoxLight = search.find(oval, valueLeft);

		valueNext = valueLeft;

//		move node
		Move moveNode = new Move(nextBoxLight, box);
		move.getExecutorService().execute(moveNode);

//	  waitting đợi Move thực hiện xong rồi hàm light sẽ thực hiện

		synchronized (move) {
			try {
				move.wait();
				move.getExecutorService().shutdown();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// update
		Node down = bst.search(valueNext);
		int cntChill = bst.cntChill(down);

		if (cntChill == 0) {
			drawing(valueLeft, false);
		} else {
			drawing(valueLeft, true);
		}

	}

	/**
	 * @param value      giá trị cần xóa
	 * @param updateForY true nêý set Y , ngược lại
	 * 
	 *                   với hàm này thuật toán sẽ như sau
	 * 
	 *                   step1 : khi 1 node không con bị xóa khỏi Gui hãy lấy list
	 *                   giá trị > hơn hoặc < theo vị trí X
	 * 
	 *                   step2 : + dùng list đó di chuyển các oval qua phải hoặc qua
	 *                   trái tùy theo hướng mà thuật toán chỉ định + dịch chuyển
	 *                   theo hướng Y
	 * 
	 *                   step3 : khi oval di chuyển , dây cũng di chuyển theo cập
	 *                   nhật lại chúng
	 */
	private void drawing(int value, boolean updateForY) {

		// giá trị cần xóa
		int valueRemove = Integer.parseInt(text);
		int root = bst.getFirstElement();
		Set<Integer> listPath = new HashSet<Integer>();
		Node down = bst.search(value);
		int choise = (value > root ? bst.HIGHT : bst.LOW);

		// lấy một list số di chuyển theo chiều X
		bst.getNeedNode(value, choise);

		// isCon : có thể di chuyển biến thay thế hay không
		boolean isCon = bst.listNeedChill.contains(valueRemove);
		int valueHide = (isCon ? -1 : value);

		// id chuyển theo X
		update.moveLocationX(bst.listNeedChill, choise, valueHide);

		// nếu udapteY = true
		if (updateForY) {
			// lấy những giá trị ở dưới node thay thế
			bst.getListDown(down);
			// di chuyển node
			update.moveLocationY(bst.listDown, value);
		}

		// một list số bao gòm các sợi dậy cần update
		listPath = bst.getListUpdateLine(value);
		
		
		// xóa node
		bst.dle(valueRemove);// here
		if (bst.level > 0) {
			root = bst.getFirstElement();
			bst.setPath(root, root);
		}
		bst.updatePath(bst.getNode());
		bst.setPath(valueRemove, -1);
		
		// xóa dây của node di chuyển
		Line connectMove = line.listLine.get(value);
		if (connectMove != null) {
			removeOneOvalAndLine(connectMove, null);
		}

		// xóa valueMove trong listOval
		oval.listOval.remove(valueRemove);

		// chuyển key của line.get(valueRemove) bằng giá trị tay thế
		Line connectUpdate = line.listLine.get(valueRemove);
		line.listLine.remove(valueRemove);
		line.listLine.put(value, connectUpdate);

		// cập nhật local trên cây
		update.ovalOnTree(update.listUpdateOval);

		for (Oval oval : update.listUpdateOval) {
			listPath.add(oval.getValue());
		}
		listPath.remove(valueRemove);

		// cập nhật lại dây
		update.lineOnTree(listPath);

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

		update.loadGui(gui);

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
