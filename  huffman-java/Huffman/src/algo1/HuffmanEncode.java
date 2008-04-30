package algo1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import bitutils.BitArray;

import tri.test.Tri;

import algo.Huffmaneur;
import arbre1.Arbre;
import arbre1.FreqNode;
import arbre1.LeafNode;
import arbre1.Node;
import arbre1.ValueNode;

public class HuffmanEncode extends Huffmaneur{
	
	public HuffmanEncode(File input, File output)throws FileNotFoundException{
		super(input, output);
	}
	
	private Node[] ouvrirFichier(String nomFichier, int tailleDico) throws FileNotFoundException{
		ArrayList<Node> noeuds = null;
		noeuds = FreqCalc.getFrequences(nomFichier, tailleDico);
		System.out.println(noeuds.size());
		
		Node[] tabNoeuds = new Node[noeuds.size()];
		noeuds.toArray(tabNoeuds);
		
		return tabNoeuds;
	}
	
	private void trierTableau(Node[] noeuds){
		Tri.sort(noeuds);
	}
	
	private Node[] fusionnerPremiers(Node[] noeuds){
		int freq1 = 0, freq2 = 0;
		BitArray value1 = null, value2 = null;
		if (noeuds[0] instanceof ValueNode){
			freq1 = ((ValueNode)noeuds[0]).getFreq();
			value1 = ((ValueNode)noeuds[0]).getValue();
		} else {
			freq1 = ((FreqNode)noeuds[0]).getFreq();
		}
		if (noeuds[1] instanceof ValueNode){
			freq2 = ((ValueNode)noeuds[1]).getFreq();
			value2 = ((ValueNode)noeuds[1]).getValue();
		} else {
			freq2 = ((FreqNode)noeuds[1]).getFreq();
		}
		
		Node left = null, right = null;
		if(value1 != null) left = new LeafNode(value1);
		else left = noeuds[0];
		if(value2 != null) right = new LeafNode(value2);
		else right = noeuds[1];
		
		FreqNode noeud = new FreqNode(freq1 + freq2, left, right);
		
		noeuds[1] = noeud;
		Node[] nTab = new Node[noeuds.length-1];
		for (int i=0; i<nTab.length; i++){
			nTab[i] = noeuds[i+1];
		}
		
		return nTab;
	}
	
	public int enregistrerArbre(Arbre a)
	{
		try
		{
			FileOutputStream fiOut = new FileOutputStream("/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTestCom");
			ObjectOutputStream fileOut = new ObjectOutputStream(fiOut);
			fileOut.writeObject(a);
			fileOut.close();
			System.out.println("L'arbre a été sauvegardé dans '/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTestCom'\n");
			return 0;
		}
		catch (Exception e)
		{
			System.out.println("Problème de sauvegarde... -- "+e);
			return 1;
		}
	}
	
	private Hashtable<BitArray, BitArray> tableCorrespondance(Arbre arbre){
		Hashtable<BitArray, BitArray> table = new Hashtable<BitArray, BitArray>();
		
		Node temp = arbre.getNode();
		
		return table;
	}
	
	@Override
	protected void huffmaner() {
		Node[] noeuds = null;
		try{
			noeuds = ouvrirFichier("/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTest", 8);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}

		trierTableau(noeuds);
		
		while(noeuds.length != 1){
			noeuds = fusionnerPremiers(noeuds);
			trierTableau(noeuds);
		}
		
		System.out.println(noeuds[0]);
		
		Arbre arbre = new Arbre();
		arbre.setNode(noeuds[0]);
		
		//enregistrerArbre(arbre);
		
	}
	
	public static void main (String args[]){
		File in = new File("/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTest");
		File out = new File("/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTestCom");
		HuffmanEncode test = null;
		try {
			test = new HuffmanEncode(in, out);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		test.huffmaner();
		
	}

}