package move;

import java.util.ArrayList;

import javax.swing.JPanel;


public interface SetUp {
	/***
	 * trả về một list cần phát sáng
	 */
	ArrayList<JPanel> listLightNode(ArrayList<Integer> list);
}
