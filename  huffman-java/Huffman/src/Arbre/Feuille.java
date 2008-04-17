/**
 * ENSICAEN
 * 6 Bd. Mar�chal Juin
 * 14050 Caen
 *
 * @class Feuille.java
 * 
 * Feuille d'un arbre, compos�e d'une cl� et d'une fr�quence
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * 
 * @version 0.0.1 - 2008-04-16
 * 
 */

package Arbre;

/**
 * Class Feuille
 */

public class Feuille implements Comparable<Feuille> {

	private int cle;
	private int freq;
	
	public Feuille(int cle, int freq) {
		this.cle = cle;
		this.freq = freq;
	}
	
	/**
	 * Accesseurs
	 */
	public int getCle() {
		return cle;
	}
	
	public void setCle(int cle) {
		this.cle = cle;
	}
	
	public int getFreq() {
		return freq;
	}
	
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	/**
	 * Surcharge de la m�thode de Comparable
	 */
	@Override
	public int compareTo(Feuille f) {
		return this.getFreq() - f.getFreq();
	}
}
