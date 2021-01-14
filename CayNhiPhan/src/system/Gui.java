package system;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import bst.SettingBst;
import move.LightNode;

public class Gui extends JFrame {

	private static final Gui GUI = new Gui();
	private Control control = Control.getControl();

	public static final int WIDTH = 1300;
	public static final int HEIGHT = 800;

	public static Gui getGui() {
		return GUI;
	}

	/*
	 * tạo một Gui có kích thước chi�?u rộng là width , và chi�?u cao là height , và
	 * màu n�?n là color
	 */
	public void createBackGround(int width, int height, Color color) {

		/** cài đặt cho JFrame **/
		setSize(width, height);
		setLocationRelativeTo(null);
		setTitle("BackGround");
		setLayout(null);
		getContentPane().setBackground(color);
		setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);

		/** cài đặt hộp đi�?u khiển **/
		control.setBoxControl(new JLabel());
		control.getBoxControl().setSize(control.WIDTH, control.HEIGHT);
		control.getBoxControl().setBackground(new Color(218, 165, 32));
		control.getBoxControl().setOpaque(true);
		control.getBoxControl().setLayout(new FlowLayout(0, 5, 5));

		control.setJbAdd(new JButton("add"));
		control.setJbDle(new JButton("dle"));
		control.setJbRan(new JButton("ran"));
		control.setJbFix(new JButton("fix"));
		control.setJbFor(new JButton("for"));
		control.setJbCle(new JButton("cle"));

		/** thêm vào hộp đi�?u khiển **/
		control.getBoxControl().add(control.getJbAdd());
		control.getBoxControl().add(control.getJbDle());
		control.getBoxControl().add(control.getJbRan());
		control.getBoxControl().add(control.getJbFix());
		control.getBoxControl().add(control.getJbFor());
		control.getBoxControl().add(control.getJbCle());

		/** thêm vào GUI **/
		getContentPane().add(control.getBoxControl());
	}

	/**
	 * @param width  chiều rộng Jframe
	 * @param height chiều cao Jframe
	 * @param title
	 */

	/* khi nhấn nào các nut trên hộp đi�?u khiển thì Box message hiện ra */
	protected void createBoxMessage(int width, int height, String title) {

		/** cài đặt hộp hiển thị cho thêm hoặc xóa **/
		control.setBoxMessage(new JFrame(title));
		control.getBoxMessage().setSize(width, height);
		control.getBoxMessage().getContentPane().setBackground(new Color(128, 128, 128));
		control.getBoxMessage().setLayout(new FlowLayout(0, 10, 10));
		control.getBoxMessage().setDefaultCloseOperation(control.getBoxMessage().DISPOSE_ON_CLOSE);
		control.getBoxMessage().setLocationRelativeTo(null);

		control.setJtfnumber(new JTextField(10));
		control.setJbok(new JButton("ok"));

		/** thêm vào BoxMessage **/
		control.getBoxMessage().add(control.getJtfnumber());
		control.getBoxMessage().add(control.getJbok());

		control.getJtfnumber().setFont(new Font("Monaco", Font.PLAIN, 15));

		control.getBoxMessage().setVisible(true);
		control.getBoxMessage().revalidate();
	}

	/* khi nhấn nào các nut trên hộp đi�?u khiển thì Box Change hiện ra */
	protected void createBoxChange(int width, int height, String title) {

		/** cài đặt hộp hiển thị cho sửa đổi giá trị trên cây **/
		control.setBoxChange(new JFrame(title));
		control.getBoxChange().setSize(width, height);
		control.getBoxChange().setLayout(new FlowLayout(0, 10, 10));
		control.getBoxChange().setDefaultCloseOperation(control.getBoxMessage().DISPOSE_ON_CLOSE);
		control.getBoxChange().setLocationRelativeTo(null);

		control.setJlValNew(new JLabel("node new"));
		control.setJlValOld(new JLabel("node old"));
		control.setJtfValNew(new JTextField(10));
		control.setJtfValOld(new JTextField(10));
		control.setJbok(new JButton("change"));
		control.setJbexit(new JButton("exit"));

		control.getBoxChange().add(control.getJlValOld());
		control.getBoxChange().add(control.getJtfValOld());
		control.getBoxChange().add(control.getJlValNew());
		control.getBoxChange().add(control.getJtfValNew());
		control.getBoxChange().add(control.getJbok());
		control.getBoxChange().add(control.getJbexit());

		control.getBoxChange().setVisible(true);
		control.getBoxChange().revalidate();

		/** exit **/
		control.getJbexit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				control.getJtfValNew().setText("");
				control.getJtfValOld().setText("");
				control.getBoxChange().dispose();
			}
		});

		/** change **/
		control.getJbok().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String valOld = control.getJtfValOld().getText();
				String valNew = control.getJtfValNew().getText();

				Event event = new Event();
				SettingBst bst = SettingBst.getBst();
				LightNode lightNode = LightNode.getLightNode();

				// kiểm tra format
				if (event.isCorrectFormat(valOld) && event.isCorrectFormat(valNew) && event.isRang(valOld)
						&& event.isRang(valNew)) {

					// kiểm tra node cũ có tồn tại trong cây không
					boolean isHaveOld = (bst.search(Integer.parseInt(valOld)) != null ? true : false);

					// kiểm tra node mới có tồn tại trong cây không
					boolean isHaveNew = (bst.search(Integer.parseInt(valNew)) != null ? true : false);

					if (isHaveOld) {

						if (!isHaveNew) {
							
							lightNode.setExecutorService(Executors.newSingleThreadExecutor());

							Thread dle = new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									event.del(valOld);
								}
							});

							Thread add = new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									event.low_add(valNew);
								}
							});

							control.getJtfValNew().setText("");
							control.getJtfValOld().setText("");
							control.getBoxChange().dispose();

							lightNode.getExecutorService().execute(dle);
							lightNode.getExecutorService().execute(add);
							lightNode.getExecutorService().shutdown();
							
						} else {

							Message message = new Message("error", "Node new đã tồn tại");
							Thread thread = new Thread(message);
							thread.start();

						}

					} else {

						Message message = new Message("error", "Node old không tồn tại");
						Thread thread = new Thread(message);
						thread.start();

					}

				} else {
					control.getJtfValNew().setText("");
					control.getJtfValOld().setText("");
					Message message = new Message("error",
							"tiêu chuẩn đầu vào sai , hãy kiểm tra lại , x >= 0 && x <= 999");
					Thread thread = new Thread(message);
					thread.start();
				}

			}
		});
	}

}
