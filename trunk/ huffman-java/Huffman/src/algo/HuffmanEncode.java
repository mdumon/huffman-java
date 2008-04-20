package algo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import arbre.Leaf;
import bitutils.BitArray;
import bitutils.BitInputStream;

/**
 * <code>HuffmanEncode</code> implements the huffman
 * encoding algorithm.
 * 
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public class HuffmanEncode extends Huffmaneur {

	public HuffmanEncode(){
		super();
	}
	
	public HuffmanEncode(File inputFile,File outputFile) throws FileNotFoundException{
		super(inputFile,outputFile);
	}
	public HuffmanEncode(File inputFile,File outputFile,int dicoSize) throws FileNotFoundException {
		super(inputFile,outputFile);
		setDicoSize(dicoSize);
	}
	
	private int dicoSize;
	public int getDicoSize(){
		return dicoSize;
	}
	public void setDicoSize(int dicoSize){
		this.dicoSize = dicoSize;
	}
	
	@Override
	protected void huffmaner() {
		BitInputStream bis;
		List<Leaf> list;
		BitArray ba;
		
		/* On ouvre l'inputStream */
		try {
			bis = new bitutils.BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		/* On créer un tableau de fréquence */
		list = new ArrayList<Leaf>();
		boolean found;
		try {
			while(bis.availableBits() > 0){
				ba = bis.readBits(Math.min(getDicoSize(),bis.availableBits()));
				
				found = false;
				for(Leaf leaf: list){
					if(ba.equals(leaf.getKey())){
						leaf.incFreq();
						found = true;
						break;
					}	
				}
				if(!found)
					list.add(new Leaf(ba,1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(list);
	}
}
