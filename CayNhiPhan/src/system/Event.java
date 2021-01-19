package system;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import bst.Node;
import bst.SettingBst;
import move.LightNode;

public class Event {

	/**
	 * lựa chọn của bạn
	 */
	private static String choise;

	/**
	 * đối tượng của class
	 */
	private static final Event EVENT = new Event();

	public static Event getEvent() {
		return EVENT;
	}

	/**
	 * đối tượng hỗ trợ trong class
	 */
	private SettingBst bst = SettingBst.getBst();
	private Control control = Control.getControl();
	private Format format = Format.getFormat();
	private Gui gui = Gui.getGui();
	private Insert insert = Insert.getInsert();
	private LightNode lightNode = LightNode.getLightNode();
	private Dlete dlete = Dlete.getDlete();
	private Search search = Search.getSearch();
	private Oval oval = Oval.getOval();

	/**
	 * hàm xây dựng tất cả các sự kiện xảy ra trong gui
	 */
	public void available() {

		/**
		 * new path -1
		 */
		bst.settingArrayPath(-1);

		/** thêm node **/
		control.getJbAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** show thông báo , nhập số **/
				gui.createBoxMessage(control.WIDTH_BM, control.HEIGHT_BM, e.getActionCommand() + " node");
				choise = e.getActionCommand();
				whenYouClick();
			}
		});

		/** delete node **/
		control.getJbDle().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** show thông báo , nhập số **/
				if (bst.level == 0) {
					Message message = new Message("error", "Cây không node");
					Thread thread = new Thread(message);
					thread.start();
					return;
				}
				if (gui != null) {
					gui.createBoxMessage(control.WIDTH_BM, control.HEIGHT_BM, e.getActionCommand() + " node");
					choise = e.getActionCommand();
					whenYouClick();
				}
			}
		});

		/** random node **/
		control.getJbRan().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ran(0, 999, 5);
			}
		});

		/** update node **/
		control.getJbFix().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (bst.level == 0) {
					Message message = new Message("error", "Cây không node");
					Thread thread = new Thread(message);
					thread.start();
					return;
				}
				if (gui != null) {
					gui.createBoxChange(control.WIDTH_BM + 250, control.HEIGHT_BM, e.getActionCommand() + " node");
				}
			}
		});

		/** for = NLR có thể cài đặt LRN , ... , nhưng mình chỉ duyệt theo NLR **/
		control.getJbFor().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				traversal("NLR");
			}
		});

		/** clear all node **/
		control.getJbCle().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (bst.level == 0) {
					Message message = new Message("error", "Cây không node");
					Thread thread = new Thread(message);
					thread.start();
					return;
				}
				clearAll();
			}
		});

	}

	/**
	 * @param text : đầu vào của ta
	 * @return true nếu nó là số , ngược lại là false nếu không phải
	 */
	protected boolean isCorrectFormat(String text) {
		return (format.isNumber(text));
	}

	protected boolean isRang(String text) {
		int number = Integer.parseInt(text);
		return number >= 0 && number <= 999;
	}

	/**
	 * khi nhấn nut ok
	 */
	private void whenYouClick() {
		/** nút ok **/

		control.getJbok().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = control.getJtfnumber().getText();

				if (isCorrectFormat(text) && isRang(text)) {
					// insert
					if (choise.equals("add")) {

						add(text);

					}
					// delete
					if (choise.equals("dle")) {
						del(text);
					}

				} else {
					control.getJtfnumber().setText("");
					Message message = new Message("error",
							"tiêu chuẩn đầu vào sai , hãy kiểm tra lại , x >= 0 && x <= 999");
					Thread thread = new Thread(message);
					thread.start();
				}

			}

		});

	}

	/**
	 * @param text : đầu vào của ta hàm dùng để thêm một node trên gui , sử dụng
	 *             luồng nếu @param text có trong cây rồi thì sẽ xuất ra thông báo
	 *             trùng node
	 */
	protected void add(String text) {
		Node q = bst.search(Integer.parseInt(text));

		if (q != null) {
			Message message = new Message("error", "cây nhị phân không được trùng số , hãy kiểm tra lại");
			Thread thread = new Thread(message);
			thread.start();
		} else {
			if (control.getJtfnumber() != null && control.getBoxMessage() != null) {
				control.getJtfnumber().setText("");
				control.getBoxMessage().dispose();
			}
			Insert insert = new Insert(text);
			Event.this.insert.text = text;
			Event.this.insert.box = insert.settingBox(text);
			lightNode.setExecutorService(Executors.newSingleThreadExecutor());
			if (bst.level > 1) {
				LightNode lightNode_light = new LightNode(500, bst.listLight_i, Color.YELLOW);
				lightNode.getExecutorService().execute(lightNode_light);
			}
			lightNode.getExecutorService().execute(insert);
			lightNode.getExecutorService().shutdown();
		}
	}

	/**
	 * @param text số đầu vào
	 * 
	 *             hàm này sẽ được kết hợp khi fix node
	 * 
	 *             xóa xong thì mới thêm node mới nào
	 */
	protected void low_add(String text) {

		synchronized (EVENT) {
			try {
				EVENT.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		add(text);

	}

	/**
	 * @param text đầu vào hàm để xóa 1 node trên cây xuất thông báo không thấy
	 *             nếu @param text không có trong cây
	 */
	protected void del(String text) {

		Node q = bst.search(Integer.parseInt(text));
		dlete.setText(text);
		lightNode.setExecutorService(Executors.newSingleThreadExecutor());
		JPanel box = search.find(Event.this.oval, Integer.parseInt(text));
		if (bst.level > 0) {
			LightNode lightNode_light = new LightNode(500, bst.listLight_d, Color.YELLOW);
			lightNode.getExecutorService().execute(lightNode_light);
		}
		if (q == null) {
			Message message = new Message("notthing", "Node " + text + " không tồn tại");
			lightNode.getExecutorService().execute(message);
		} else {
			if (control.getJtfnumber() != null && control.getBoxMessage() != null) {
				control.getJtfnumber().setText("");
				control.getBoxMessage().dispose();
			}
			Dlete dlete = new Dlete(box, text);
			lightNode.getExecutorService().execute(dlete);
		}

		lightNode.getExecutorService().shutdown();
	}

	/**
	 * @param L giá trị
	 * @param R giá trị
	 * @param N số lần thực hiện
	 * 
	 *          random số lượng node có giá trị từ L -> R mặc định sẽ là L = 0 , R =
	 *          999 6; N : tạo ra N node mặc định N = 5
	 */
	private void ran(final int L, final int R, final int N) {
		if (L < 0 || R < 0) {
			Message message = new Message("error", "L && R luôn > 0");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}
		if (L > 999 || R > 999) {
			Message message = new Message("error", "L >= 0 && R <= 999 && L <= R");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}
		if (R < L) {
			Message message = new Message("error", "R luôn >= L");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}

		if (((R - L) + 1) < N) {
			Message message = new Message("error", "số lượng node trong [L .. R] tạo ra ít hơn N");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}

		if (N > (1000 - bst.level)) {
			Message message = new Message("error", "quá 1000 node , dung lượng mặc định không đủ");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}

		Set<Integer> listRan = new HashSet<Integer>();
		Random generator = new Random();
		while (listRan.size() < N) {
			int number = generator.nextInt((R - L + 1)) + L;
			listRan.add(number);
		}

		for (Integer i : listRan) {
			Node isHaveNove = bst.search(i);
			if (isHaveNove == null) {
				insert.insertNormal(String.valueOf(i));
			}
		}
	}

	/**
	 * duyệt cây mặc định theo node-left-right
	 */
	private void traversal(String isTrav) {
		// có thể cài đặt thêm ở đây nếu bạn muốn

		if (bst.level == 0) {
			Message message = new Message("error", "Cây không node");
			Thread thread = new Thread(message);
			thread.start();
			return;
		}

		if (isTrav.toUpperCase().equals("NLR")) {
			lightNode.setExecutorService(Executors.newSingleThreadExecutor());
			bst.getNodeLeftRight();
			String text = "";
			for (Integer i : bst.listTraversal) {
				text += String.valueOf(i + " - ");
			}
			Message message = new Message("alert", text.substring(0, text.length() - 2));
			LightNode node = new LightNode(300, bst.listTraversal, Color.ORANGE);
			lightNode.getExecutorService().execute(node);
			lightNode.getExecutorService().execute(message);
		}

		lightNode.getExecutorService().shutdown();
	}

	/**
	 * reset lại Gui xóa tất cả các node trong cây
	 */
	private void clearAll() {
		bst.getNeedNode(bst.getFirstElement(), bst.ALL);
		dlete.removeList(bst.listNeedChill);
		bst.listDle(bst.listNeedChill);
	}

}
