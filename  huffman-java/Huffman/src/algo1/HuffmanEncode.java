package algo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import bitutils.BitArrayBooleanArray;
import bitutils.BitInputStream;

import algo.Huffmaneur;
import arbre1.Arbre;
import arbre1.Code;
import arbre1.FreqNode;
import arbre1.LeafNode;
import arbre1.Node;
import arbre1.ValueNode;

public class HuffmanEncode extends Huffmaneur{
	
	private int tailleDico;
	
	public void setTailleDico(int tailleDico){
		this.tailleDico = tailleDico;
	}
	
	public int getTailleDico(){
		return this.tailleDico;
	}
	
	public HuffmanEncode(File input, File output)throws FileNotFoundException{
		this(input, output, 8);
	}
	
	public HuffmanEncode(File input, File output, int tailleDico)throws FileNotFoundException{
		super(input, output);
		setTailleDico(tailleDico);
	}
	
	private Node[] ouvrirFichier(File fichier) throws FileNotFoundException{
		ArrayList<Node> noeuds = null;
		noeuds = FreqCalc.getFrequences(fichier, getTailleDico());
		
		Node[] tabNoeuds = new Node[noeuds.size()];
		noeuds.toArray(tabNoeuds);
		
		return tabNoeuds;
	}
	
	private void trierTableau(Node[] noeuds){
		Arrays.sort(noeuds);
	}
	
	private Node[] fusionnerPremiers(Node[] noeuds){
		int freq1 = 0, freq2 = 0;
		BitArrayBooleanArray value1 = null, value2 = null;
		if (noeuds[0] instanceof ValueNode){
			freq1 = ((ValueNode)noeuds[0]).getFreq();
			value1 = (BitArrayBooleanArray)((ValueNode)noeuds[0]).getValue();
		} else {
			freq1 = ((FreqNode)noeuds[0]).getFreq();
		}
		if (noeuds[1] instanceof ValueNode){
			freq2 = ((ValueNode)noeuds[1]).getFreq();
			value2 = (BitArrayBooleanArray)((ValueNode)noeuds[1]).getValue();
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
		Node[] tabNoeuds = new Node[noeuds.length-1];
		for(int i=0; i<tabNoeuds.length; i++){
			tabNoeuds[i] = noeuds[i+1];
		}
		return tabNoeuds;
	}
	
	public int enregistrerFichier(Arbre arbre, ArrayList<BitArrayBooleanArray> contenu)
	{
		try
		{
			FileOutputStream fichierOut = new FileOutputStream(getOutputFile());
			ObjectOutputStream fileOut = new ObjectOutputStream(fichierOut);
			fileOut.writeObject(arbre);
			fileOut.writeObject(contenu);
			fileOut.close();
			System.out.println("Enregistrement terminé !\n");
			return 0;
		}
		catch (Exception e)
		{
			System.out.println("Problème de sauvegarde... -- "+e);
			return 1;
		}
	}
	
	private ArrayList<Code> tableCorrespondance(Arbre arbre){
		ArrayList<Code> table = new ArrayList<Code>();
		
		parcourirArbre(arbre.getNode(), table, new BitArrayBooleanArray());
		
		return table;
	}
	
	private void parcourirArbre(Node courant, ArrayList<Code> tableCodage, BitArrayBooleanArray debut){
		if (courant != null){
			if (courant instanceof LeafNode){
				tableCodage.add(new Code(((LeafNode)courant).getValue(), new BitArrayBooleanArray(debut)));
			} else {
				debut.add(false);
				parcourirArbre(((FreqNode)courant).getLeft(), tableCodage, debut);
				debut.setLast(true);
				parcourirArbre(((FreqNode)courant).getRight(), tableCodage, debut);
				debut.removeLast();
			}
		}
	}
	
	private ArrayList<BitArrayBooleanArray> encode(BitInputStream fichier, ArrayList<Code> tableCodage){
		ArrayList<BitArrayBooleanArray> fichierEncode = new ArrayList<BitArrayBooleanArray>();
		
		while(true)
		{
			BitArrayBooleanArray bitarray = null;
			try {
				BitArrayBooleanArray temp = (BitArrayBooleanArray)fichier.readBits(getTailleDico());
				if(tableCodage.contains(new Code(temp, new BitArrayBooleanArray()))){
					bitarray = ((BitArrayBooleanArray)((Code)tableCodage.get(tableCodage.indexOf(new Code(temp, new BitArrayBooleanArray())))).getValue());
				}
			} catch (IOException e) {
				return fichierEncode;
			}
			if(bitarray == null) return fichierEncode;
			fichierEncode.add(bitarray);
		}
	}
	
	@Override
	protected void huffmaner() {
		Node[] noeuds = null;
		try{
			noeuds = ouvrirFichier(getInputFile());
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}

		System.out.println("Table fréquences pas triée ("+noeuds.length+"):");
		
		for(int i=0; i<noeuds.length; i++){
			System.out.print(noeuds[i]);
		}
		System.out.println("");
		
		trierTableau(noeuds);
		
		System.out.println("Table fréquences :");
		
		for(int i=0; i<noeuds.length; i++){
			System.out.print(noeuds[i]);
		}
		System.out.println("");
		
		while(noeuds.length != 1){
			noeuds = fusionnerPremiers(noeuds);
			trierTableau(noeuds);
		}
		
		Arbre arbre = new Arbre();
		arbre.setNode(noeuds[0]);
		
		System.out.println(arbre);
		
		//Hashtable<BitArrayBooleanArray, BitArrayBooleanArray> correspondances = tableCorrespondance(arbre);
		ArrayList<Code> correspondances= tableCorrespondance(arbre);
		ArrayList<BitArrayBooleanArray> fichierEncode = null;
		try{
			FileInputStream fp = new FileInputStream(getInputFile());
			BitInputStream bitInputStream = new BitInputStream((InputStream)fp);
			fichierEncode = encode(bitInputStream, correspondances);
		} catch (FileNotFoundException e){
			System.err.println("Fichier introuvable !");
		}
		
		System.out.println(fichierEncode);
		
		if (fichierEncode != null) enregistrerFichier(arbre, fichierEncode);
		
	}
	
	public static void main (String args[]){
		File in = new File("/home/quentin/Documents/workspace/Projet Huffman/src/algo1/test/fichierTest");
		File out = new File("/home/quentin/Documents/workspace/Projet Huffman/src/algo1/test/fichierTestCom");
		HuffmanEncode test = null;
		try {
			test = new HuffmanEncode(in, out);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		test.huffmaner();
		
	}

}