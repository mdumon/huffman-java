package arbre1;

import java.io.Serializable;
import java.util.ArrayList;

public class Arbre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Node>  frequences;
	
	public Arbre() {
		frequences = new ArrayList<Node>();
	}
	
	public ArrayList<Node> getFrequences() {
		return frequences;
	}
	
	public void setFrequences(ArrayList<Node> frequences) {
		this.frequences = frequences;
	}
	
	public String toString() {
		String res = "";
		for (Node n : this.getFrequences())
			res += n;
		return res;
	}
}
