package algo2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import algo.Huffmaneur;
import arbre2.Node;
import arbre2.HuffmanTree;
import bitutils.BitArray;
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
		BitArray ba;

		/* On ouvre l'inputFile */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }

		/* On ouvre l'outuptFile */
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }

		/* On crée un arbre d'huffman */
		HuffmanTree ht = new HuffmanTree();

		ba = new BitArray();

		try {
			ht = (HuffmanTree) new ObjectInputStream(bis).readObject();
		} catch (ClassNotFoundException ignore) {}
		catch (IOException ignore2){}

		System.out.println("Début du décodage\n");

		decArbre(ba, ht.getRoot(), bis, bos);

		System.out.println("Décodage terminé\n");

		try{
			bis.close();
		}catch(IOException ef){}

		try{
			bos.close();
		}catch(IOException eg){}
	}

	private void decArbre (BitArray ba, Node<FreqCode> node, BitInputStream bis, BitOutputStream bos) {

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
				ba.add(bis.readBits(1).get(0));
			}catch (ArrayIndexOutOfBoundsException i){
				try{
					bos.flush();
					System.out.println("fin\n");
				}catch(IOException g){}
			}catch (IOException t){}

			System.out.println("lu : " + ba + "\nsize : " + ba.size());
			
			if(ba.get(ba.size() - 1) == false){
				decArbre(ba, node.getRightSon(), bis, bos);
			}

			else{
				decArbre(ba, node.getLeftSon(), bis, bos);
			}
		}
	}
}
