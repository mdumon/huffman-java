/**
 * ENSICAEN
 * 6 Bd. Maréchal Juin
 * 14050 Caen
 *
 * 
 * 
 * Feuille d'un arbre, composée d'une clé et d'une fréquence
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

		System.out.println("Tableau non trié :");
		for (Feuille f : tab)
			System.out.print(f.getFreq()+", ");

		Tri.sort(tab);

		System.out.println("\nTableau trié :");
		for (Feuille f : tab)
			System.out.print(f.getFreq()+", ");
	}

}
