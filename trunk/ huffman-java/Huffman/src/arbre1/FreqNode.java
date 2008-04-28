package arbre1;

import java.io.Serializable;

public class FreqNode implements Node, Serializable {

	private static final long serialVersionUID = 1L;
	
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
	
	public int getFreq() {
		return freq;
	}
	
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public Node getRight() {
		return right;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public int getValToCompare() {
		return this.getFreq();
	}

	public int compareTo(Node node) {
		return this.getValToCompare() - node.getValToCompare();
	}
	
	public String toString() {
		String res = "FreqNode : "+this.getFreq()+"\n";
		res += "FilsGauche : "+this.getLeft()+"\n";
		res += "FilsDroit : "+this.getRight()+"\n";
		return res;
	}
}
