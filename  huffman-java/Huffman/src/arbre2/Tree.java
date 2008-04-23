package arbre2;


public class Tree {
	
	public Tree(){
		root = new Node();
	}
	
	public Tree(Node root){
		setRoot(root);
	}
	
	private Node root;
	public Node getRoot(){
		return root;
	}
	protected void setRoot(Node root){
		this.root = root;
	}
}