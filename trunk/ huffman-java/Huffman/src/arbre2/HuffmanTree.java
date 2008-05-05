package arbre2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import algo2.FreqCode;
import bitutils.BitArrayBooleanList;

public class HuffmanTree extends Tree<FreqCode> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	transient private FreqCode[] fcs;
	
	public HuffmanTree(FreqCode[] fcs){
		this.fcs = fcs;
	}

	public void Build(){
		List<ValuedNode> vns = new ArrayList<ValuedNode>();
		
		/* On créer notre tableau mais dans le sens inverse, du moins fréquent au plus fréquent */
		for(FreqCode fc : fcs)
			vns.add(0,new ValuedNode(fc));
		
		/* Création de l'arbre */
		ValuedNode vn0,vn1,vn2;
		int vn0Freq;
		int i;
		while(vns.size() > 1){
			vn1 = vns.remove(0);
			vn2 = vns.remove(0);
			
			vn0 = new ValuedNode(vn1,vn2);
			vn0Freq = vn0.getFreq();
			
			i = 0;
			while(i < vns.size() && vn0Freq > vns.get(i).getFreq()) i++;
			vns.add(i, vn0);
		}
		
		/* On place la racine */
		setRoot(vns.remove(0));
	}
	
	public void FillEncodedValues(){
		/* On rempli le membre EncValue des FreqCodes */
		parcoursArbre(getRoot(),new BitArrayBooleanList());
		
		/* et pi là on a finit en fait :p */
	}
	
	private void parcoursArbre(Node<FreqCode> n,BitArrayBooleanList codage){
		if(n == null) return; // n'arrive jamais.. (normalement..)
		
		if(n.getValue() != null){
			n.getValue().setEncValue(new BitArrayBooleanList(codage));
			return;
		}
		
		codage.add(false);
		parcoursArbre(n.getLeftSon(),codage);
		codage.setLast(true);
		parcoursArbre(n.getRightSon(),codage);
		codage.removeLast();
	}
}