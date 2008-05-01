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

		/* On crï¿½e un arbre d'huffman */
		ba = new BitArrayBooleanArray();

		HuffmanTree ht = new HuffmanTree();

		try {
			ht = (HuffmanTree) new ObjectInputStream(bis).readObject();
		} catch (ClassNotFoundException ignore) {}
		catch (IOException ignore2){}

		System.out.println("Dï¿½but du dï¿½codage\n");

		while(decArbre(ba, ht.getRoot(), bis, bos) != -1);

		System.out.println("Dï¿½codage terminï¿½\n");

		try{
			bis.close();
		}catch(IOException ef){}

		try{
			bos.close();
		}catch(IOException eg){}
	}

	private int decArbre (BitArrayBooleanArray ba, Node<FreqCode> node, BitInputStream bis, BitOutputStream bos) {

		byte bitlu = 0;
		boolean b = false;

		if(node.getValue() != null){
			try {
				System.out.println("Valeur trouvée : " + node.getValue().getKey());
				System.out.println("Valeur encodée lue dans le fichier : " + ba);
				System.out.println("Caractère à écrire : " + node.getValue().getKey().toByteArray()[0]);
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

			System.out.println("lu : " + ba + "\nsize : " + ba.size());

			try{
				return decArbre(ba, (b)?node.getRightSon():node.getLeftSon() , bis, bos);
			}catch(ArrayIndexOutOfBoundsException m){}
		}
		return 0;
	}
}
