package system;

import java.util.Map;

import javax.swing.JPanel;

public class Question {

	private static final Question QUESTION = new Question();

	public static Question getQuestion() {
		return QUESTION;
	}

	/**
	 * 
	 * @param map      : list Map oval cần kiểm tra
	 * @param location : vị trí cần kiểm tra
	 * @return : sẽ trả 1 Jpanel nếu có 1 vị trí trong @param map trùng với location
	 */

	protected JPanel isSameLocationX(Map<Integer, Oval> map, int location) {

		for (Map.Entry<Integer, Oval> entry : map.entrySet()) {

			int localX = entry.getValue().getLocation().x;

			if (localX == location) {
				return entry.getValue();
			}

		}

		return null;
	}

}
