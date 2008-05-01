package algo2;

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
import bitutils.BitArray;
import bitutils.BitArrayBooleanArray;
import bitutils.BitInputStream;
import bitutils.BitOutputStream;

/**
 * <code>HuffmanDecode</code> implements the huffman
 * decoding algorithm.
 * 
 * @author Maxime Dumon <dumon@ecole.ensicaen.fr>
 */

public class HuffmanDecode extends Huffmaneur {

	public HuffmanDecode(){
		super();
	}

	public HuffmanDecode(File inputFile, File outputFile) throws FileNotFoundException {
		super(inputFile,outputFile);
	}

	@Override
	protected void huffmaner() {
		BitInputStream bis;
		BitOutputStream bos;
		BitArrayBooleanArray ba;

		/* On ouvre l'inputFile */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }

		/* On ouvre l'outuptFile */
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }

		/* On cr�e un arbre d'huffman */
		HuffmanTree ht = new HuffmanTree();

		ba = new BitArrayBooleanArray();

		try {
			ht = (HuffmanTree) new ObjectInputStream(bis).readObject();
		} catch (ClassNotFoundException ignore) {}
		catch (IOException ignore2){}

		System.out.println("D�but du d�codage\n");

		while(decArbre(ba, ht.getRoot(), bis, bos) == 0);

		System.out.println("D�codage termin�\n");

		try{
			bis.close();
		}catch(IOException ef){}

		try{
			bos.close();
		}catch(IOException eg){}
	}

	private int decArbre (BitArrayBooleanArray ba, Node<FreqCode> node, BitInputStream bis, BitOutputStream bos) {

		int sortie = 0;

		if(node.getValue() != null){
			try {
				System.out.println("Valeur trouv�e : " + node.getValue().getKey());
				System.out.println("Valeur encod�e lue dans le fichier : " + ba);
				System.out.println("Caract�re � �crire : " + node.getValue().getKey().toByteArray()[0]);
				bos.writeBits(node.getValue().getKey());
				ba.clear();
			}catch (IOException ignore4){}
		}
		else{

			try {
				ba.add(bis.readBits(1).get(0));
			}catch (ArrayIndexOutOfBoundsException i){
			}catch (IOException t){}

			System.out.println("lu : " + ba + "\nsize : " + ba.size());
			try{
				if(ba.get(ba.size() - 1) == true){
					return decArbre(ba, node.getRightSon(), bis, bos);
				}

				else{
					return decArbre(ba, node.getLeftSon(), bis, bos);
				}
			}catch (ArrayIndexOutOfBoundsException k){
				sortie = -1;
			}
		}
		return sortie;
	}
}
