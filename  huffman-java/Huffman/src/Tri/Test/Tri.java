/**
 * ENSICAEN
 * 6 Bd. Mar�chal Juin
 * 14050 Caen
 *
 * @class Tri.java
 * 
 * Trier en ordre croissant
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * 
 * @version 0.0.1 - 2008-04-16
 * 
 */

package Tri.Test;

import java.util.Arrays;

/**
 * Class Tri
 */

public class Tri {
	
 	/**
 	* M�thode de tri : les objets du tableau doivent impl�menter Comparable
 	* @param Object[] tab
 	* @return void
 	*/
	public static void sort(Object[] tab) {
		Arrays.sort(tab);
	}
	
	/**
 	* M�thode de tri d'entiers
 	* @param int[] tab
 	* @return void
 	*/
	public static void sort(int[] tab) {
		Arrays.sort(tab);
	}
	
}
