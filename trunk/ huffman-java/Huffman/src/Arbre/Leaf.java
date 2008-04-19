package Arbre;

import BitUtils.BitArray;

/**
 * A <code>Leaf</code> is used to store a key,
 * its frequence, (and its encoded value).
 * 
 *  
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * @author Franck Séhdéic <sehedic@ecole.ensicaen.fr>
 * 
 * @version 0.0.2 - 2008-04-19
 * 
 */
public class Leaf implements Comparable<Leaf> {
	
	/* la clef représentée par cette feuille */
	private BitArray key;
	
	/* sa fréquence d'apparition dans le fichier */ 
	private int freq;
	
	public Leaf(BitArray key,int freq){
		setKey(key);
		setFreq(0);
	}
	
	/**
	 * Accesseurs
	 */
	public BitArray getKey() {
		return key;
	}
	
	public void setKey(BitArray key) {
		this.key = key;
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
	public int compareTo(Leaf f) {
		return this.getFreq() - f.getFreq();
	}
}
