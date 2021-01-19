package move;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import javax.swing.JPanel;

import system.Dlete;
import system.Gui;
import system.Line;
import system.Oval;

public class LightNode implements SetUp, Runnable {
	/**
	 * đối tượng của class
	 */
	private static final LightNode A_INSERT = new LightNode();

	public static LightNode getLightNode() {
		return A_INSERT;
	}

	/**
	 * những biến dùng ngoài class
	 */
	private int sleep;
	private ArrayList<Integer> list;
	private Color color;

	/**
	 * hàm tạo
	 * 
	 * @param sleep ngủ
	 * @param list  số
	 * @param color màu
	 */
	public LightNode(int sleep, ArrayList<Integer> list, Color color) {
		super();
		this.sleep = sleep;
		this.list = list;
		this.color = color;
	}

	public LightNode() {
		super();
	}

	/**
	 * thread pool
	 */
	private ExecutorService executorService;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * đối tượng hỗ trợ
	 */
	private Oval oval = Oval.getOval();
	private Line line = Line.getLine();

	@Override
	public ArrayList<JPanel> listLightNode(ArrayList<Integer> list) {
		ArrayList<JPanel> listLight = new ArrayList<JPanel>();
		for (int i = 0; i < list.size(); i++) {
			int value = list.get(i);
			listLight.add(oval.getListOval().get(value));
		}
		return listLight;
	}

	/**
	 * @param sleep thời gian dừng
	 * @param list  dùng nó để lấy những đối tượng cần phát sáng
	 * @param color màu sắc phát sáng
	 * 
	 *              sẽ làm phát sáng những node có trong listOval
	 */
	public synchronized void lightNode(int sleep, ArrayList<Integer> list, Color color) {
		ArrayList<JPanel> light = listLightNode(list);

		for (int i = 0; i < light.size(); i++) {

			light.get(i).setForeground(color);
			
			Line connect = line.getListLine().get(list.get(i));
			if (connect != null) {
				connect.setForeground(color);
			}

			try {
				wait(sleep);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			light.get(i).setForeground(Color.GREEN);
			if (connect != null) {
				line.getListLine().get(list.get(i)).setForeground(Color.white);
			}
		}

	}

	/**
	 * thread run
	 */
	@Override
	public void run() {
		lightNode(sleep, list, color);
	}

}
