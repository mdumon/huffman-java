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

/**
 * <code>HuffmanEncode</code> implements the Huffman encoding algorithm
 * 
 * @author Quentin Douillet
 *
 */

public class HuffmanEncode extends Huffmaneur{

	private int tailleDico;

	/**
	 * Set the size of the dictionnary
	 * 
	 * @param tailleDico
	 */
	public void setTailleDico(int tailleDico){
		this.tailleDico = tailleDico;
	}

	/**
	 * Get the size of the dictionnary
	 * 
	 * @return The size of the dictionnary
	 */
	public int getTailleDico(){
		return this.tailleDico;
	}

	/**
	 * 
	 * @param input The file to be read
	 * @param output The file to be wrote
	 * @throws FileNotFoundException If the file to be read is not found
	 */
	public HuffmanEncode(File input, File output)throws FileNotFoundException{
		this(input, output, 8);
	}

	/**
	 * 
	 * @param input The file to be read
	 * @param output The file to be wrote
	 * @param tailleDico The size of the dictionnary
	 * @throws FileNotFoundException If the file to be read is not found
	 */
	public HuffmanEncode(File input, File output, int tailleDico)throws FileNotFoundException{
		super(input, output);
		setTailleDico(tailleDico);
	}

	/**
	 * 
	 * @param fichier The file to be read
	 * @return An array of nodes representing each BitArray with their frequence in the file
	 * @throws FileNotFoundException If the file to be read is not found
	 */
	private Node[] ouvrirFichier(File fichier) throws FileNotFoundException{
		ArrayList<Node> noeuds = null;
		noeuds = FreqCalc.getFrequences(fichier, getTailleDico());

		Node[] tabNoeuds = new Node[noeuds.size()];
		noeuds.toArray(tabNoeuds);

		return tabNoeuds;
	}

	/**
	 * Sort an array of nodes
	 * 
	 * @param noeuds The array to be sorted
	 */
	private void trierTableau(Node[] noeuds){
		Arrays.sort(noeuds);
	}

	/**
	 * 
	 * @param noeuds The array to be marged
	 * @return An array with the two firsts elements of the given array are merged in a FreqNode
	 */
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

	/**
	 * Save the compressed file
	 * 
	 * @param arbre The tree of the Huffman algorithm
	 * @param contenu An ArrayList containing the compressed bit of the file
	 * @return True if the file is saved or false if is not
	 */
	public boolean enregistrerFichier(Arbre arbre, ArrayList<BitArrayBooleanArray> contenu)
	{
		try
		{
			FileOutputStream fichierOut = new FileOutputStream(getOutputFile());
			ObjectOutputStream fileOut = new ObjectOutputStream(fichierOut);
			fileOut.writeObject(arbre);
			fileOut.writeObject(contenu);
			fileOut.close();
			System.out.println("Enregistrement terminé !\n");
			return false;
		}
		catch (Exception e)
		{
			System.out.println("Problème de sauvegarde... -- "+e);
			return true;
		}
	}

	/**
	 * Make an array of Code (to know the compressed representation of a given BitArray)
	 * 
	 * @param arbre The Huffman tree
	 * @return An array of Code
	 */
	private ArrayList<Code> tableCorrespondance(Arbre arbre){
		ArrayList<Code> table = new ArrayList<Code>();

		parcourirArbre(arbre.getNode(), table, new BitArrayBooleanArray());

		return table;
	}

	/**
	 * 
	 * @param courant The current node
	 * @param tableCodage The Code array
	 * @param debut The begginning of the compressed BitArray
	 */
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

	/**
	 * Encode the file
	 * 
	 * @param fichier The file to be read
	 * @param tableCodage The Code array to encode the file
	 * @return An array containing the encoded BitArray
	 */
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
}
