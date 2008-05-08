package algo1;

/**
 * <code>HuffmanDecode</code> implements the huffman
 * decoding algorithm.
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import algo.Huffmaneur;
import arbre1.Arbre;
import arbre1.FreqNode;
import arbre1.LeafNode;
import arbre1.Node;
import bitutils.BitArrayBooleanArray;
import bitutils.BitInputStream;
import bitutils.BitOutputStream;

public class HuffmanDecodeBis extends Huffmaneur {

	public HuffmanDecodeBis() {
	}

	public HuffmanDecodeBis(File inputFile,File outputFile) throws FileNotFoundException {
		super(inputFile,outputFile);
	}

	/**
	 * Decode
	 */
	@Override
	protected void huffmaner() {
		Arbre a = null;
		FileOutputStream fiOut = null;
		BitArrayBooleanArray ba = new BitArrayBooleanArray();
		BitInputStream bis;
		BitOutputStream bos;

		//Vérification de l'existence d'un fichier de sauvegarde
		try
		{
			//tenter d'ouvrir un flux de lecture pour le fichier 'arbre.huff'
			FileInputStream fiIn = new FileInputStream(this.getInputFile());
			ObjectInputStream fileIn = new ObjectInputStream(fiIn);

			//récupérer l'arbre préalablement enregistré
			a =(Arbre) fileIn.readObject();
			
			//récupérer le nombre d'éléments
			int nbElements = fileIn.read();
			
			bis = new BitInputStream(new BufferedInputStream(fileIn));
			bos = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(this.getOutputFile())));

			//lire bit à bit en parcourant l'arbre : si on arrive sur une feuille,
			//on a la clé, on écrit sa valeur correspondante dans le fichier cible
			int retval;
			for(int i = 1; i <= nbElements ; i++){
				retval = decArbre(ba, a.getNode(), bis, bos);
				setAdvance(i*100/nbElements);
				if(retval == -1) break; /* fin du fichier, on sort */
			}
			
			//on ferme le flux de fichier
			fileIn.close();
		}

		//s'il y a une exception : le fichier n'a pas été trouvé
		catch (Exception e)
		{
			System.out.print("\nL'arbre n'existe pas");
		}

		//ouverture du fichier cible
		try
		{
			fiOut = new FileOutputStream(this.getOutputFile());
		}
		catch (Exception e) {
			System.out.println("Impossible d'ouvrir le fichier cible : "+e);
			return;
		}
	}

	private int decArbre (BitArrayBooleanArray ba, Node node, BitInputStream bis, BitOutputStream bos) {

		byte bitlu = 0;
		boolean b = false;

		if(node instanceof LeafNode){
			try {
				/*
				System.out.println("Valeur trouv�e : " + node.getValue().getKey());
				System.out.println("Valeur encod�e lue dans le fichier : " + ba);
				System.out.println("Caract�re � �crire : " + node.getValue().getKey().toByteArray()[0]);
				 */
				bos.writeBits(((LeafNode) node).getValue());
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
				return decArbre(ba, (b)?((FreqNode) node).getRight():((FreqNode) node).getLeft() , bis, bos);
			}catch(ArrayIndexOutOfBoundsException m){}
		}
		return 0;
	}
}
