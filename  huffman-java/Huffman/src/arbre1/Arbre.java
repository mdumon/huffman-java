package arbre1;

import java.io.Serializable;

public class Arbre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Node  node;
	
	public Arbre() {
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public String toString() {;
		return this.getNode().toString();
	}
}
