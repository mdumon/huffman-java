package algo2;

import bitutils.BitArray;

/**
 * A <code>FreqCode</code> is used to store a key,
 * its frequence, and its encoded value.
 * 
 *  
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * @author Franck Séhdéic <sehedic@ecole.ensicaen.fr>
 * 
 * @version 0.0.3 - 2008-04-21
 * 
 */
public class FreqCode implements Comparable<FreqCode> {
	
	/* la clef représentée par cette feuille */
	private BitArray key;
	
	/* sa fréquence d'apparition */ 
	private int freq;
	
	/* sa valeur encodée */
	private BitArray encValue;
	
	public FreqCode(BitArray key,int freq){
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
	public void incFreq(){
		this.freq++;
	}
	
	public BitArray getEncValue(){
		return encValue;
	}
	public void setEncValue(BitArray encValue){
		this.encValue = encValue;
	}
	
	/**
	 * Surcharge de la m�thode de Comparable
	 */
	@Override
	public int compareTo(FreqCode l) {
		return this.getFreq() - l.getFreq();
	}
}
