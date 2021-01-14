package system;

import javax.swing.JOptionPane;

public class Message implements Runnable {

	private static final Message MESSAGE = new Message();

	public static Message getMessage() {
		return MESSAGE;
	}

	private String choise;
	private String text;
	
	
	public Message(String choise , String text) {
		super();
		this.choise = choise;
		this.text = text;
	}
	
	public Message() {
		// TODO Auto-generated constructor stub
	}

	/* hiển thị thông báo */
	public void error(String error) {
		JOptionPane.showMessageDialog(null, error, "alert", JOptionPane.ERROR_MESSAGE);
	}

	public void notthing(String not) {
		JOptionPane.showMessageDialog(null, not, "alert", JOptionPane.WARNING_MESSAGE);
	}
	
	public void alert(String alert) {
		JOptionPane.showMessageDialog(null, alert , "alert", JOptionPane.OK_OPTION);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(choise.equals("error")) {
			error(text);
		}else if(choise.equals("notthing")) {
			notthing(text);
		}else if(choise.equals("alert")) {
			alert(text);
		}
		
	}

}
