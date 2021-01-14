package system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import bst.Node;
import bst.SettingBst;

public class Search {

	/**
	 * đối tượng của class
	 */
	private static final Search SEARCH = new Search();

	public static Search getSearch() {
		return SEARCH;
	}

	/**
	 * đối tượng hỗ trợ của class
	 */
	private SettingBst bst = SettingBst.getBst();
	private Oval oval = Oval.getOval();
	private Line line = Line.getLine();

	/**
	 * @param value : giá trị của cây tìm vị trí ở trên gui của cây
	 */
	public Node location(int value) {
		return bst.search(value);
	}

	/**
	 * 
	 * @param cls   đối tượng cần tìm
	 * @param value giá trị trong list đối tượng cần tìm
	 * @return nếu có giá trị cần tìm sẽ return chính giá trị ấy , ngược lại sẽ là
	 *         null
	 */
	public JPanel find(Object cls, int value) {
		if (cls instanceof Oval) {
			return oval.listOval.get(value);
		} else if (cls instanceof Line) { // line
			return line.listLine.get(value);
		}
		return null;
	}

	/**
	 * @param list : là list số
	 * @return một list Oval có các giá trị cần lấy , và các giá trị này đều có giá
	 *         trj trong list
	 */
	public ArrayList<Oval> getListOval(ArrayList<Integer> list) {
		ArrayList<Oval> listOval = new ArrayList<Oval>();
		for (Integer i : list) {
			listOval.add(oval.listOval.get(i));
		}
		return listOval;
	}

	/**
	 * @param list : là list số
	 * @return một list Line có các giá trị cần lấy , và các giá trị này đều có giá
	 *         trj trong list
	 */
	public ArrayList<Line> getListLine(ArrayList<Integer> list) {
		ArrayList<Line> listLine = new ArrayList<Line>();
		for (Integer i : list) {
			listLine.add(line.listLine.get(i));
		}
		return listLine;
	}


}
