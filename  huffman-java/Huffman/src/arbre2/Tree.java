package arbre2;

import algo2.FreqCode;

public class Tree {
	
	public Tree(){
		root = new Node();
	}
	
	public Tree(Node root){
		setRoot(root);
	}
	
	public void Build(FreqCode[] fc){
		
	}
	
	private Node root;
	public Node getRoot(){
		return root;
	}
	private void setRoot(Node root){
		this.root = root;
	}
}