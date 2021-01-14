package system;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Control {

	/* box Control */
	private static final Control CONTROL = new Control();
	public int HEIGHT = 40;
	public int HEIGHT_BM = 100;

	public final int WIDTH = 350;
	public final int WIDTH_BM = 280;

	public static Control getControl() {
		return CONTROL;
	}

	private JLabel boxControl;
	private JFrame boxMessage;
	private JFrame boxChange;
	private JButton jbAdd;
	private JButton jbDle;
	private JButton jbFix;
	private JButton jbFor;
	private JButton jbok;
	private JButton jbRan;
	private JButton jbCle;
	private JTextField jtfnumber;
	private JLabel jlValOld;
	private JLabel jlValNew;
	private JTextField jtfValOld;
	private JTextField jtfValNew;
	private JButton jbexit;

	public JLabel getBoxControl() {
		return boxControl;
	}

	public JFrame getBoxMessage() {
		return boxMessage;
	}

	public JButton getJbAdd() {
		return jbAdd;
	}

	public JButton getJbDle() {
		return jbDle;
	}

	public JButton getJbFix() {
		return jbFix;
	}

	public JButton getJbFor() {
		return jbFor;
	}

	public JButton getJbok() {
		return jbok;
	}

	/* box Message */

	public JButton getJbRan() {
		return jbRan;
	}

	public JButton getJbCle() {
		return jbCle;
	}

	public void setJbCle(JButton jbCle) {
		this.jbCle = jbCle;
	}

	public JTextField getJtfnumber() {
		return jtfnumber;
	}

	public void setBoxControl(JLabel boxControl) {
		this.boxControl = boxControl;
	}

	public void setBoxMessage(JFrame boxMessage) {
		this.boxMessage = boxMessage;
	}

	public void setJbAdd(JButton jbAdd) {
		this.jbAdd = jbAdd;
	}

	public void setJbDle(JButton jbDle) {
		this.jbDle = jbDle;
	}

	public void setJbFix(JButton jbFix) {
		this.jbFix = jbFix;
	}

	public void setJbFor(JButton jbFor) {
		this.jbFor = jbFor;
	}

	public void setJbok(JButton jbok) {
		this.jbok = jbok;
	}

	public void setJbRan(JButton jbRan) {
		this.jbRan = jbRan;
	}

	public void setJtfnumber(JTextField jtfnumber) {
		this.jtfnumber = jtfnumber;
	}

	public JFrame getBoxChange() {
		return boxChange;
	}

	public void setBoxChange(JFrame boxChange) {
		this.boxChange = boxChange;
	}

	public JLabel getJlValOld() {
		return jlValOld;
	}

	public void setJlValOld(JLabel jlValOld) {
		this.jlValOld = jlValOld;
	}

	public JLabel getJlValNew() {
		return jlValNew;
	}

	public void setJlValNew(JLabel jlValNew) {
		this.jlValNew = jlValNew;
	}

	public JTextField getJtfValOld() {
		return jtfValOld;
	}

	public void setJtfValOld(JTextField jtfValOld) {
		this.jtfValOld = jtfValOld;
	}

	public JTextField getJtfValNew() {
		return jtfValNew;
	}

	public JButton getJbexit() {
		return jbexit;
	}

	public void setJbexit(JButton jbexit) {
		this.jbexit = jbexit;
	}

	public void setJtfValNew(JTextField jtfValNew) {
		this.jtfValNew = jtfValNew;
	}

	

}
