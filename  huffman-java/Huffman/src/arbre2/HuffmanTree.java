package arbre2;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import algo2.FreqCode;

public class HuffmanTree extends Tree implements Serializable{

	private static final long serialVersionUID = 1L;

	public void Build(FreqCode[] fcs){
		SortedSet<ValuedNode> ss = new TreeSet<ValuedNode>();
		
		for(FreqCode fc : fcs)
			ss.add(new ValuedNode(fc));
		
		// on fait notre truc
		
		
		setRoot(new ValuedNode(null,null)); // setroot sur le dernier de la liste
	}
	
	public void FillFreqCode(FreqCode[] fca){
		// remplissage du champ EncValue des freqcodes
	}

}