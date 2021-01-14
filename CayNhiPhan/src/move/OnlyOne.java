package move;

import java.awt.Color;

import javax.swing.JPanel;

public class OnlyOne implements Runnable {
	/**
	 * đối tượng của class
	 */
	private static final OnlyOne ONLY_ONE = new OnlyOne();

	public static OnlyOne getOnlyOne() {
		return ONLY_ONE;
	}

	/**
	 * những giá trị cần cài đặt khi ở ngoài class
	 */
	private JPanel boxLight;
	private Color color;

	/**
	 * hàm khởi tạo
	 * 
	 * @param boxLight : node phát sáng
	 * @param color    : màu sắc của node
	 */
	public OnlyOne(JPanel boxLight, Color color) {
		super();
		this.boxLight = boxLight;
		this.color = color;
	}

	public OnlyOne() {
		super();
	}

	/**
	 * thread run
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boxLight.setForeground(color);
	}

}
