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

package tri;

import java.util.Random;

import tri.test.Tri;

import bitutils.BitArray;

import algo2.FreqCode;



/**
 * Class Start : main
 */

public class Start {

	public static void main(String args[]) {
		FreqCode[] tab = new FreqCode[2500000];
		
		Random r = new Random();
		int i;
		for (i=0 ; i<2500000 ; i++){
			
			tab[i] = new FreqCode(new BitArray(), r.nextInt(500));
			
		}
			
		System.out.println("Tableau non trié :");

		Tri.sort(tab);

		System.out.println("\nTableau trié :");
	}

}
