package arbre1;

import bitutils.BitArray;

public class Code {

	private BitArray clef;
	private BitArray valeur;
	
	public Code(BitArray key, BitArray value){
		this.clef = key;
		this.valeur = value;
	}
	
	public BitArray getValue(){
		return valeur;
	}
	
	public BitArray getKey(){
		return clef;
	}
	
	public boolean equals(Object o){
		boolean resultat = true;
		
		if (o instanceof Code){
			resultat = ((Code)o).getKey().equals(clef);
		} else resultat = false;
		
		return resultat;
	}
	
	public String toString(){
		String res = "";
		res += clef.toString();
		res += " : ";
		res += valeur.toString();
		
		return res;
	}
}
