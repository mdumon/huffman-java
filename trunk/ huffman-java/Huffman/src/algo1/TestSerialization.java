package algo1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Random;

import bitutils.BitArray;
import bitutils.BitArrayBooleanArray;
import arbre1.Arbre;
import arbre1.FreqNode;
import arbre1.LeafNode;

public class TestSerialization {

	public static void main(String args[]) {

		byte[] tab0 = {0};
		BitArray b1 = new BitArrayBooleanArray(tab0);
		byte[] tab1 = {1};
		BitArray b2 = new BitArrayBooleanArray(tab1);
		byte[] tab2 = {2};
		BitArray b3 = new BitArrayBooleanArray(tab2);
		byte[] tab3 = {3};
		BitArray b4 = new BitArrayBooleanArray(tab3);
		byte[] tab4 = {4};
		BitArray b5 = new BitArrayBooleanArray(tab4);
		byte[] tab5 = {5};
		BitArray b6 = new BitArrayBooleanArray(tab5);
		byte[] tab6 = {6};
		BitArray b7 = new BitArrayBooleanArray(tab6);
		byte[] tab7 = {7};
		BitArray b8 = new BitArrayBooleanArray(tab7);
		byte[] tab8 = {8};
		BitArray b9 = new BitArrayBooleanArray(tab8);
		byte[] tab9 = {9};
		BitArray b10 = new BitArrayBooleanArray(tab9);
		byte[] tab10 = {10};
		BitArray b11 = new BitArrayBooleanArray(tab10);

		Random r = new Random();

		Arbre arbre = new Arbre();

		arbre.setNode(
			new FreqNode(r.nextInt(100),
				new FreqNode(r.nextInt(100),new LeafNode(b1),new LeafNode(b2)),
				new FreqNode(r.nextInt(100),
					new FreqNode(r.nextInt(100),new LeafNode(b3),new LeafNode(b4)),
					new FreqNode(r.nextInt(100),
							new FreqNode(r.nextInt(100),
									new FreqNode(r.nextInt(100),new LeafNode(b5),new LeafNode(b6)),
									new LeafNode(b7)),
					new FreqNode(r.nextInt(100),new LeafNode(b8),
							new FreqNode(r.nextInt(100),new LeafNode(b9),
									new FreqNode(r.nextInt(100),new LeafNode(b10),new LeafNode(b11))))))));

		BitArray testBa = new BitArrayBooleanArray();
		testBa.add(true);
		testBa.add(true);
		testBa.add(false);
		testBa.add(false);
		testBa.add(false);
		testBa.add(true);
		System.out.println("testBa : "+testBa);
		
		System.out.println("Arbre : \n"+arbre);
		
		HuffmanDecode hDecode = new HuffmanDecode();
		Hashtable<BitArray, BitArray> hTable = hDecode.createHTable(arbre);
		
		System.out.println("hTable : "+hTable);

		enregistrerArbre(arbre);
		arbre = chargerArbre();
		if (arbre != null)
			System.out.println("arbre chargé !");

		System.out.println("Arbre : \n"+arbre);

		//on tente une serialization : sauvegarde
	}

	public static Arbre chargerArbre() {

		Arbre a = null;
		//Vérification de l'existence d'un fichier de sauvegarde
		try
		{
			//tenter d'ouvrir un flux de lecture pour le fichier 'arbre.huff'
			FileInputStream fiIn = new FileInputStream("./arbre.huff");
			ObjectInputStream fileIn = new ObjectInputStream(fiIn);

			//récupérer l'arbre préalablement enregistré
			a = (Arbre) fileIn.readObject();

			//on ferme le flux de fichier
			fileIn.close();
		}

		//s'il y a une exception : le fichier n'a pas été trouvé
		catch (Exception e)
		{
			System.out.print("\nL'arbre n'existe pas");
		}

		return a;
	}

	//enregistrer l'arbre
	public static int enregistrerArbre(Arbre a)
	{
		try
		{
			FileOutputStream fiOut = new FileOutputStream("./arbre.huff");
			ObjectOutputStream fileOut = new ObjectOutputStream(fiOut);
			fileOut.writeObject(a);
			fileOut.close();
			System.out.println("L'arbre a été sauvegardé dans './arbre.huff\n");
			return 0;
		}
		catch (Exception e)
		{
			System.out.println("Problème de sauvegarde... -- "+e);
			return 1;
		}
	}
}
