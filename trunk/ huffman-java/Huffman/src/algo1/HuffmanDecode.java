package algo1;

/**
 * <code>HuffmanDecode</code> implements the huffman
 * decoding algorithm.
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 */

import java.io.*;
import java.util.*;
import bitutils.*;
import algo.*;
import arbre1.*;

public class HuffmanDecode extends Huffmaneur {

	public HuffmanDecode() {
	}
	
	public HuffmanDecode(File inputFile,File outputFile) throws FileNotFoundException {
		super(inputFile,outputFile);
	}

	/**
	 * Decode
	 */
	@Override
	protected void huffmaner() {
		Arbre a = null;
		ArrayList<BitArrayBooleanArray> list = null;
		FileOutputStream fiOut = null;
		//Vérification de l'existence d'un fichier de sauvegarde
		try
		{
			//tenter d'ouvrir un flux de lecture pour le fichier 'arbre.huff'
			FileInputStream fiIn = new FileInputStream(this.getInputFile());
			ObjectInputStream fileIn = new ObjectInputStream(fiIn);

			//récupérer l'arbre préalablement enregistré
			a = (Arbre) fileIn.readObject();
			
			//récupérer le flux de bits
			list = (ArrayList<BitArrayBooleanArray>) fileIn.readObject();

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
		
		Hashtable<BitArray, BitArray> hTable = createHTable(a);
		decode(hTable,list,fiOut);
		
		System.out.println("hTable : "+hTable);
	}
	
	private void decode(Hashtable<BitArray, BitArray> hTable, ArrayList<BitArrayBooleanArray> list, FileOutputStream fiOut)
	{
		BitArray cle;
		
		for (BitArray b : list)
		{
			cle = hTable.get(b);
			//écrire la clé dans le fichier cible
			try {
				fiOut.write(cle.toByteArray());
			}catch (IOException e)
			{
				System.out.println("Erreur d'écriture : "+e);
			}
		}
		try {fiOut.close();} catch (IOException e) {System.out.println("Impossible de fermer le fichier cible : "+e);}
	}
	
	//parcours de l'arbre : récupération des clés -> association avec leur code binaire dans une Hashtable
	public Hashtable<BitArray, BitArray> createHTable(Arbre a)
	{
		Hashtable<BitArray, BitArray> hTable = new Hashtable<BitArray, BitArray>();
		createHTableRec(hTable, new BitArrayBooleanArray(), a.getNode(),false,true);
		return hTable;
	}
	
	private void createHTableRec(Hashtable<BitArray, BitArray> hTable, BitArrayBooleanArray code, Node node, boolean bit, boolean first)
	{
		//ajouter le bit au BitArray
		if (!first)
			code.add(bit);
		
		//un noeud qui a nécessairement 2 fils
		if (node instanceof FreqNode)
		{
			createHTableRec(hTable, new BitArrayBooleanArray(code), ((FreqNode) node).getLeft(),false,false);
			createHTableRec(hTable, new BitArrayBooleanArray(code), ((FreqNode) node).getRight(),true,false);
		}
		
		//sinon il s'agit d'une feuille
		else
			hTable.put(((LeafNode) node).getValue(), code);
	}

}
