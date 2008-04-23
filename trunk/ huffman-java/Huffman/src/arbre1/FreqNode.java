package arbre1;

public class FreqNode extends Node {
	private int freq;
	private Node left;
	private Node right;
	
	public FreqNode(int freq){
		this(freq, null, null);
	}
	
	public FreqNode(int freq, Node left, Node right){
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
}
