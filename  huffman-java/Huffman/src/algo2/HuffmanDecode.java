package algo2;

import groupe2.TimedLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import algo.Huffmaneur;
import arbre2.HuffmanTree;
import arbre2.Node;
import bitutils.BitArrayBooleanArray;
import bitutils.BitInputStream;
import bitutils.BitOutputStream;

/**
 * <code>HuffmanDecode</code> implements the huffman
 * decoding algorithm.
 * 
 * @author Maxime Dumon <dumon@ecole.ensicaen.fr>
 * @version 1.0, 18/04/08
 * @see Huffmaneur
 */
public class HuffmanDecode extends Huffmaneur {

	public HuffmanDecode(){
		super();
	}
	
	/**
	 * Creates a <code>HuffmanDecode</code>
	 * from an inputFile and an outputFile
	 * @param the <code>inputFile</code> to decode and the <code>outputFile</code>
	 * which will be the decoded file
	 */
	public HuffmanDecode(File inputFile, File outputFile) throws FileNotFoundException {
		super(inputFile,outputFile);
	}
	
	/**
	 * Decode the inputFile in the outputFile
	 * @param void
	 * @return void
	 */
	@Override
	protected void huffmaner() {
		BitInputStream bis;
		BitOutputStream bos;
		BitArrayBooleanArray ba;
		int nbElements = 0;
		
		/* On lance le logger */
		TimedLog tl = new TimedLog();
		tl.start();

		/* On ouvre l'inputFile */
		tl.log("On ouvre l'inputFile");
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }

		/* On ouvre l'outuptFile */
		tl.log("On ouvre l'outuptFile");
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }

		/* On cr�e un arbre d'huffman */
		tl.log("On crée un arbre d'huffman");
		ba = new BitArrayBooleanArray();
		HuffmanTree ht = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			ht = (HuffmanTree) ois.readObject();
		} catch (ClassNotFoundException ignore) {
			System.out.println("ClassNotFoundException : " + ignore.getMessage());
		}
		catch (IOException ignore2){
			System.out.println("IOException : " + ignore2.getMessage());
		}
		
		tl.log("arbre récupéré OK");
		
		try {
			Integer nbElem = (Integer) ois.readObject();
			nbElements = nbElem.intValue();
		}
		catch (IOException ignore2){
			System.out.println("IOException : " + ignore2.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException : " + e.getMessage());
		}

		/* On lance le décodage */
		tl.log("D�but du d�codage (nbElements : " + nbElements + ")");
		
		int retval;
		for(int i = 1; i <= nbElements ; i++){
			retval = decArbre(ba, ht.getRoot(), bis, bos);
			setAdvance(i*100/nbElements);
			if(retval == -1) break; /* fin du fichier, on sort */
		}

		tl.log("D�codage termin�\n");

		try{
			bis.close();
		}catch(IOException ef){}

		try{
			bos.close();
		}catch(IOException eg){}
	}
	
	/**
	 * Find in an <code>HuffmanTree</code>
	 * the decoded value of a bitArray from an inputStream
	 * @param the <code>BitArrayBooleanArray</code> to decode
	 * the <code>Node</code> of the tree, the <code>BitInputstream</code> and <code>BitOutputStream</code>
	 * used to read and write the values.
	 */
	private int decArbre (BitArrayBooleanArray ba, Node<FreqCode> node, BitInputStream bis, BitOutputStream bos) {

		byte bitlu = 0;
		boolean b = false;

		if(node.getValue() != null){
			try {
				/*
				System.out.println("Valeur trouv�e : " + node.getValue().getKey());
				System.out.println("Valeur encod�e lue dans le fichier : " + ba);
				System.out.println("Caract�re � �crire : " + node.getValue().getKey().toByteArray()[0]);
				*/
				bos.writeBits(node.getValue().getKey());
				ba.clear();
			}catch (IOException ignore4){}
		}
		else{

			try {
				bitlu = bis.readBit();
				if(bitlu == -1) return bitlu;

				b = (bitlu == 1);

				ba.add(b);
			}catch (ArrayIndexOutOfBoundsException i){
			}catch (IOException t){}

			//System.out.println("lu : " + ba + "\nsize : " + ba.size());

			try{
				return decArbre(ba, (b)?node.getRightSon():node.getLeftSon() , bis, bos);
			}catch(ArrayIndexOutOfBoundsException m){}
		}
		return 0;
	}
}
