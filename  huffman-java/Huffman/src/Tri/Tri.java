/**
 * ENSICAEN
 * 6 Bd. Maréchal Juin
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

package Tri;

import java.util.Arrays;

/**
 * Class Tri
 */

public class Tri {
	
 	/**
 	* Méthode de tri : les objets du tableau doivent implémenter Comparable
 	* @param Object[] tab
 	* @return void
 	*/
	public static void sort(Object[] tab) {
		Arrays.sort(tab);
	}
	
	/**
 	* Méthode de tri d'entiers
 	* @param int[] tab
 	* @return void
 	*/
	public static void sort(int[] tab) {
		Arrays.sort(tab);
	}
	
}
