/**
 * ENSICAEN
 * 6 Bd. Mar�chal Juin
 * 14050 Caen
 *
 * 
 * 
 * Feuille d'un arbre, compos�e d'une cl� et d'une fr�quence
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * 
 * @version 0.0.1 - 2008-04-16
 * 
 */

package Algo;

import java.util.Random;
import Tri.*;

import Arbre.*;

/**
 * Class Start : main
 */

public class Start {

	public static void main(String args[]) {
		Feuille[] tab = new Feuille[20];
		Random r = new Random();
		int i;
		for (i=0 ; i<20 ; i++)
			tab[i] = new Feuille(r.nextInt(20), r.nextInt(50));

		System.out.println("Tableau non tri� :");
		for (Feuille f : tab)
			System.out.print(f.getFreq()+", ");

		Tri.sort(tab);

		System.out.println("\nTableau tri� :");
		for (Feuille f : tab)
			System.out.print(f.getFreq()+", ");
	}

}
