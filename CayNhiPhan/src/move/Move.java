package move;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JPanel;
import javax.swing.Timer;

import system.Oval;

public class Move implements Runnable {

	/**
	 * đối tượng của class
	 */
	private static final Move MOVE = new Move();

	public static Move getMove() {
		return MOVE;
	}

	/**
	 * những biến sưe dụng khi ra ngoài class
	 */
	private JPanel boxMove;
	private JPanel boxOld;

	/**
	 * thread pool
	 */
	protected ExecutorService executorService;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * hàm tạo
	 * 
	 * @param boxMove : box cần di chuyển
	 * @param boxOld  : vị trí boxMove cần thay thế
	 */
	public Move(JPanel boxMove, JPanel boxOld) {
		super();
		this.boxMove = boxMove;
		this.boxOld = boxOld;
	}

	public Move() {
		super();
	}

	/**
	 * @param boxMove
	 * @param boxOld  di chuyển boxMove đến vị trí boxOld
	 */

	/**
	 * chuyển động sẽ được thể hiện qua đối tượng timer.swing
	 */
	private Timer timer;

	public void moveNode(JPanel boxMove, JPanel boxOld) {

		Oval boxO = ((Oval) boxOld);
		Oval boxM = ((Oval) boxMove);

		boxMove.setForeground(Color.WHITE);

		timer = new Timer(5 , new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**
				 * Bnew.Y sẽ chạy lên phía trên khi nào đúng chính xác vị trí Bold.Y thì sẽ di
				 * chuyển tới phần location.X
				 */
			
				if (boxOld.getLocation().y < boxMove.getLocation().y) {
					boxMove.setLocation(boxMove.getLocation().x, boxMove.getLocation().y - 1);
				} else {

					if (boxOld.getLocation().x != boxMove.getLocation().x) {
						// right
						if (boxM.getValue() > boxO.getValue()) {
							boxMove.setLocation(boxMove.getLocation().x - 1, boxMove.getLocation().y);
						} else {
							boxMove.setLocation(boxMove.getLocation().x + 1, boxMove.getLocation().y);
						}
					} else {
						synchronized (MOVE) {
							MOVE.notifyAll();
						}
						timer.stop();
					}

				}

			}
		});
		timer.start();

	}

	/**
	 * Thread run
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		moveNode(boxMove, boxOld);
	}

}
