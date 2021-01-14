package system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import bst.Node;
import bst.SettingBst;

public class Update {
	/**
	 * đối tượng của class
	 */
	private static final Update UPDATE = new Update();

	public static Update getUpdate() {
		return UPDATE;
	}

	/**
	 * đối tượng hôx trợ của class
	 */
	private SettingBst bst = SettingBst.getBst();
	private Draw draw = Draw.getDraw();
	private Gui gui = Gui.getGui();
	private Line line = Line.getLine();
	private Oval oval = Oval.getOval();

	/**
	 * listLocal : khi có việc trùng location.X bên class insert , ta sẽ có dữ liệu
	 * của cả cây
	 */

	protected ArrayList<Oval> listUpdateOval = new ArrayList<Oval>();
	protected Set<Integer> listUpdateLine = new HashSet<Integer>();

	/**
	 * @param listMove : list oval này sẽ có các giá trị tương ứng trong listLine
	 */

	protected void lineOnTree(Set<Integer> listMove) {
		for (Integer i : listMove) {
			int value = i;
			if (bst.getFirstElement() != value) {
				Line locationLine = line.listLine.get(value);
				new Dlete().removeOneOvalAndLine(locationLine, null);
				Node tmp = bst.search(value);
				if (oval.X > oval.before_X) {
					bst.S = "RIGHT";
				} else {
					bst.S = "LEFT";
				}
				JPanel connect = draw.Line(String.valueOf(value), 0, 0, 0, 0);
				line.listLine.put(value, (Line) connect);
				gui.getContentPane().add(connect);
			}
		}
		gui.getContentPane().revalidate();
		gui.getContentPane().repaint();
	}

	/**
	 * hàm sẽ di chuyển vị trí những node trên cây
	 * 
	 * @param listMove : những node sẽ di chuyển location trên cây
	 */
	protected void ovalOnTree(ArrayList<Oval> listMove) {
		for (int i = 0; i < listMove.size(); i++) {
			int value = listMove.get(i).getValue();
			int x = listMove.get(i).getLocation().x;
			int y = listMove.get(i).getLocation().y;
			bst.fixLocal(value, x, y);
		}
	}

	/**
	 * @param listUpdateOval : listOval cần update
	 * @param listUpdateLine : listLine cần update
	 * @param begin          : giá trị bắt đầu đường dẫn
	 * 
	 *                       updateGui khi thêm hoặc xóa node
	 */
	protected void updateGui(ArrayList<Oval> listUpdateOval, Set<Integer> listUpdateLine, int begin) {
		ovalOnTree(listUpdateOval);

		/**
		 * get những giá trị của listUpdateOval
		 */
		for (int i = 0; i < listUpdateOval.size(); i++) {
			int value = listUpdateOval.get(i).getValue();
			listUpdateLine.add(value);
		}

		/**
		 * lấy những giá trong pathNode
		 */
		
		Set<Integer> listTmp = bst.getListUpdateLine(begin);

		if (listTmp != null) {
			listUpdateLine.addAll(listTmp);
		}

		/**
		 * just update Line
		 */
		lineOnTree(listUpdateLine);
	}

	/**
	 * @param list : list số để insert , hàm sẽ vẽ lại các node có trong list
	 */
	protected void drawAgain(ArrayList<Integer> list) {
		
		for(Integer i : list) {
			new Insert().insertNormal(String.valueOf(i));
		}

	}

}
