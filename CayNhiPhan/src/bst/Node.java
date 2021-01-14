package bst;

public class Node {
	
	private static final Node NODE = new Node();
	
	public static Node getNode() {
		return NODE;
	}
	
	private Node left;
	private int localX;
	private int localY;
	private Node right;
	private int val;
	
	public Node getLeft() {
		return left;
	}
	public int getLocalX() {
		return localX;
	}
	public int getLocalY() {
		return localY;
	}
	public Node getRight() {
		return right;
	}
	public int getVal() {
		return val;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public void setLocalX(int localX) {
		this.localX = localX;
	}
	public void setLocalY(int localY) {
		this.localY = localY;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public void setVal(int val) {
		this.val = val;
	}
	
}
