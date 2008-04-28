package algo1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import bitutils.BitArray;
import arbre1.Arbre;
import arbre1.FreqNode;
import arbre1.LeafNode;
import arbre1.ValueNode;

public class TestSerialization {

	public static void main(String args[]) {

		Arbre arbre = new Arbre();

		Random r = new Random();
		/*byte[] tab0 = {1,1,1};
		arbre.getFrequences().add(new ValueNode(new BitArray(tab0),r.nextInt(100)));
		byte[] tab1 = {0,1,0};
		arbre.getFrequences().add(new ValueNode(new BitArray(tab1),r.nextInt(100)));
		byte[] tab2 = {1,0,1};
		arbre.getFrequences().add(new ValueNode(new BitArray(tab2),r.nextInt(100)));
		byte[] tab3 = {1,0,0};
		arbre.getFrequences().add(new ValueNode(new BitArray(tab3),r.nextInt(100)));

		arbre.getFrequences().add(new FreqNode(r.nextInt(100)));
		((FreqNode) arbre.getFrequences().get(arbre.getFrequences().size()-1)).setLeft(new LeafNode(new BitArray(tab0)));
		((FreqNode) arbre.getFrequences().get(arbre.getFrequences().size()-1)).setRight(new LeafNode(new BitArray(tab1)));
		
		System.out.println("Arbre : \n"+arbre);
		
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
		}*/
	}
}
