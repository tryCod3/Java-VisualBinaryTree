package bst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import system.Oval;

public class SettingBst {

	/**
	 * đối tượng của class
	 */
	private static final SettingBst BST = new SettingBst();

	public static SettingBst getBst() {
		return BST;
	}

	// insert
	public ArrayList<Integer> listLight_i = new ArrayList<Integer>();
	// dele
	public ArrayList<Integer> listLight_d = new ArrayList<Integer>();

	// leftMostChill
	public ArrayList<Integer> listMostChill = new ArrayList<Integer>();

	// NeedNode
	public ArrayList<Integer> listNeedChill = new ArrayList<Integer>();

	// traversal
	public ArrayList<Integer> listTraversal = new ArrayList<Integer>();

	// listDown
	public ArrayList<Integer> listDown = new ArrayList<Integer>();
	/**
	 * những biến bên ngoài class sẽ sử dụng
	 */
	public String F;
	public int level; // đọ cao của cây
	public String S;
	public static final int LOW = 1;
	public static final int HIGHT = 2;
	public static final int ALL = 3;
	private int pathNode[] = new int[2708];

	/**
	 * ban đầu cây không co giá trị nên node = null
	 */
	protected Node node = null;

	/**
	 * 
	 * getter && setter
	 */
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * đối tượng hỗ trợ class
	 */
	private Oval oval = Oval.getOval();

	/**
	 * @param value giá trị cần khởi tạo
	 * @return 1 node có giá trị , left = null && right = null
	 */
	Node create(int value) {
		Node node = new Node();
		node.setVal(value);
		node.setLeft(null);
		node.setRight(null);
		return node;
	}

	/**
	 * @param valueFirst : giá trị cần khởi tạo một mảng
	 * 
	 *                   mảng này sẽ là đường dẫn cho tới root
	 */
	public void settingArrayPath(int valueFirst) {
		for (int i = 0; i <= 999; i++) {
			setPath(i, valueFirst);
		}
	}

	/**
	 * @param idxValue    giá trị trong mảng
	 * @param valueChange số cần gán
	 * 
	 */
	public void setPath(int idxValue, int valueChange) {
		pathNode[idxValue] = valueChange;
	}

	/**
	 * @param value : giá trị node cần trả về
	 */
	public int getPath(int value) {
		return pathNode[value];
	}

	/**
	 * @param x giá trị cần truy vết đến root
	 * @return trả về 1 list có giá trị cần các dây nối node
	 */
	public Set<Integer> getListUpdateLine(int x) {
		Set<Integer> list = new HashSet<Integer>();
		if (getPath(x) == -1) {
			return null;
		}
		do {
			list.add(x);
			x = getPath(x);
		} while (getPath(x) != x);
		return list;
	}

	/**
	 * 
	 * @param q node bắt đầu vị trí bắt đầu cập nhập
	 */
	public void updatePath(Node q) {
		if (q != null) {

			if (q.getLeft() != null) {
				int value = q.getLeft().getVal();
				setPath(value, q.getVal());
			}

			if (q.getRight() != null) {
				int value = q.getRight().getVal();
				setPath(value, q.getVal());
			}

			updatePath(q.getLeft());
			updatePath(q.getRight());
		}
	}

	/**
	 * @param node vị trí bắt đầu
	 * @return node đó có bao nhiêu node
	 */
	public int cntChill(Node node) {
		int chill = 0;
		if (node.getLeft() != null)
			chill++;
		if (node.getRight() != null)
			chill++;
		return chill;
	}

	/**
	 * @param value giá trị cần cập nhật vị trí trên cây
	 * @param x     location.X
	 * @param y     location.Y
	 * 
	 *              thay thê vị trí x , y trên cây
	 */
	public void fixLocal(int value, int x, int y) {
		Node q = search(value);
		if (q == null) {
			return;
		}
		q.setLocalX(x);
		q.setLocalY(y);
	}

	/**
	 * @return 1 : LR , 2 : RL , 0 : notthing
	 * 
	 *         kiểm tra tính đối xứng của F và S
	 */
	public int isSameDirection() {
		if (F.equals("LEFT") && S.equals("RIGHT")) {
			return 1;
		}
		if (F.equals("RIGHT") && S.equals("LEFT")) {
			return 2;
		}
		return 0;
	}

	/**
	 * @return true nếu đối xứng , ngược lại là false
	 */
	public boolean opposite() {
		if (level <= 1 || F.length() == 0 || S.length() == 0)
			return false;
		return ((F.equals("LEFT") && S.equals("RIGHT")) || (F.equals("RIGHT") && S.equals("LEFT")));
	}

	/**
	 * @return phần tử root của cây
	 */
	public int getFirstElement() {
		return node.getVal();
	}

	/**
	 * @param q node cần duyệt
	 * @return giá trị nhỏ nhất bắt đầu chạy từ q
	 */
	public int leftMostValue(Node q) {
		listMostChill.clear();
		while (q != null) {
			// mostchill
			listMostChill.add(q.getVal());
			q = q.getLeft();
		}
		return listMostChill.get(listMostChill.size() - 1);
	}

	/**
	 * @param q node cần duyệt
	 * @return giá trị lớn nhất bắt đầu chạy từ q
	 */
	public int rightMostValue(Node q) {
		listMostChill.clear();
		while (q != null) {
			listMostChill.add(q.getVal());
			q = q.getRight();
		}
		return listMostChill.get(listMostChill.size() - 1);
	}

	/**
	 * @param q      : node bắt đầu
	 * @param value  : giá trị cần bắt đầu để lấy những node khác
	 * @param choise : lấy những node lớn hơn hoặc bé hơn vị trí @param locaionX
	 * @param tmp    : vị trí so sánh với q
	 */

	private void setNeedNode(Node q, int value, int choise, Node tmp) {

		if (q != null) {
			if (choise == LOW) {

				if (q.getLocalX() <= tmp.getLocalX()) {
					listNeedChill.add(q.getVal());
				}

			} else if (choise == HIGHT) { // HIGHT

				if (q.getLocalX() >= tmp.getLocalX()) {
					listNeedChill.add(q.getVal());
				}

			} else if (choise == ALL) {

				listNeedChill.add(q.getVal());

			}

			setNeedNode(q.getLeft(), value, choise, tmp);
			setNeedNode(q.getRight(), value, choise, tmp);
		}

	}

	/**
	 * trả về 1 list Intẻger cần thiết
	 *
	 */
	public void getNeedNode(int value, int choise) {
		Node q = node;
		listNeedChill.clear();
		Node tmp = search(value);
		setNeedNode(q, value, choise, tmp);
	}

	/**
	 * @param list cần xóa khởi cây xóa những giá trị khỏi cây
	 */
	public void listDle(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			setPath(list.get(i), -1);
			dle(list.get(i));
			if(level == 1) {
				setPath(node.getVal() , node.getVal());
			}else {
				updatePath(node);
			}
		}
	}

	/**
	 * @param value giá trị cần thêm
	 * 
	 *              thêm một giá trị vào cây
	 */
	public void addNode(int value) {
		Node point = create(value);
		level++; // số nút có trong cây
		listLight_i.clear();
		if (node == null) {
			node = point;
			oval.X = 650;
			oval.Y = 10;
			node.setLocalX(650);
			node.setLocalY(10);
			setPath(value, value);
		} else {
			Node q = node;
			Node w = node;

			if (value > node.getVal()) {
				F = "RIGHT";
			} else {
				F = "LEFT";
			}

			while (true) {
				int data = q.getVal();
				int x = q.getLocalX();
				int y = q.getLocalY();
				w = q;
				oval.before_X = w.getLocalX();
				oval.before_V = w.getVal();

				setPath(value, data);

				/** node will light */
				BST.listLight_i.add(data);

				if (value > data) { // right
					q = q.getRight();
					if (q == null) {
						data = w.getVal();
						S = "RIGHT";
						w.setRight(point);
						w = w.getRight();
						w.setLocalX(x + oval.W);
						w.setLocalY(y + oval.H);
						oval.X = w.getLocalX();
						oval.Y = w.getLocalY();
						break;
					}
				} else { // left
					q = q.getLeft();
					if (q == null) {
						data = w.getVal();
						S = "LEFT";
						w.setLeft(point);
						w = w.getLeft();
						w.setLocalX(x - oval.W);
						w.setLocalY(y + oval.H);
						oval.X = w.getLocalX();
						oval.Y = w.getLocalY();
						break;
					}
				}
			}
		}
	}

	/**
	 * hàm xóa
	 */
	public void dle(int value) {
		level--;
		node = dleNode(node, value);
	}

	/**
	 * @param node  vị trí bắt đầu
	 * @param value giá trị cần xóa
	 * @return xóa giá value trên cât
	 */
	private Node dleNode(Node node, int value) {

		if (node == null)
			return node;

		if (node.getVal() > value) {
			node.setLeft(dleNode(node.getLeft(), value));
		} else if (node.getVal() < value) {
			node.setRight(dleNode(node.getRight(), value));
		} else {

			if (node.getLeft() == null && node.getRight() == null) {
				node = null;
			}

			else {

				Node next = (node.getRight() != null ? node.getRight() : node.getLeft());

				boolean isRight = next.getVal() > node.getVal();

				if (isRight) {
					int val = leftMostValue(next);
					int valPre = getPath(val);
					Node pre = search(valPre);
					Node now = null;
					if (val == next.getVal()) {
						now = pre.getRight();
						if (now.getRight() != null) {
							pre.setRight(now.getRight());
						} else {
							pre.setRight(null);
						}
						now = null;
					} else {
						now = pre.getLeft();
						if (now.getRight() != null) {
							pre.setLeft(now.getRight());
						} else {
							pre.setLeft(null);
						}
					}
					now = null;
					node.setVal(val);
				} else {
					int val = rightMostValue(next);
					int valPre = getPath(val);
					Node pre = search(valPre);
					Node now = null;
					if (val == next.getVal()) {
						now = pre.getLeft();
						if (now.getLeft() != null) {
							pre.setLeft(now.getLeft());
						} else {
							pre.setLeft(null);
						}
					} else {
						now = pre.getRight();
						if (now.getLeft() != null) {
							pre.setRight(now.getLeft());
						} else {
							pre.setRight(null);
						}
					}
					now = null;
					node.setVal(val);

				}

			}

		}
		return node;
	}

	/**
	 * 
	 * @param tmp vị trí bắt đầu
	 * 
	 *            lấy một list số bắt đầu từ node tmp
	 */
	private void setListDown(Node tmp) {
		if (tmp != null) {
			listDown.add(tmp.getVal());
			setListDown(tmp.getLeft());
			setListDown(tmp.getRight());
		}
	}

	/**
	 * 
	 * @param tmp vị trí bắt đầu
	 */
	public void getListDown(Node tmp) {
		listDown.clear();
		setListDown(tmp);
	}

	/**
	 * duyệt cây NLR
	 */
	public void getNodeLeftRight() {
		Node q = node;
		listTraversal.clear();
		NLR(q);
	}

	// duyệt cây
	private void NLR(Node node) {
		if (node != null) {
			listTraversal.add(node.getVal());
			NLR(node.getLeft());
			NLR(node.getRight());
		}
	}

	/**
	 * tìm kiếm node
	 */
	public Node search(int value) {
		Node q = node;
		listLight_d.clear();
		return searchNode(q, value);
	}

	/**
	 * @param node  vị trí bắt đầu trên cây
	 * @param value cần tìm
	 * @return node cần tìm , nếu không có giá trị thì trả về null
	 */
	private Node searchNode(Node node, int value) {
		while (node != null) {
			// dele
			listLight_d.add(node.getVal());

			if (value == node.getVal()) {
				oval.X = node.getLocalX();
				oval.Y = node.getLocalY();
				return node;
			}
			oval.before_V = node.getVal();
			oval.before_X = node.getLocalX();
			if (value > node.getVal()) {
				node = node.getRight();
			} else {
				node = node.getLeft();
			}
		}
		return null;
	}

}
