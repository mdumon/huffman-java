package algo2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algo.Huffmaneur;
import arbre2.HuffmanTree;
import bitutils.BitArray;
import bitutils.BitInputStream;
import bitutils.BitOutputStream;

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
		BitOutputStream bos;
		List<FreqCode> list;
		BitArray ba;
		
		/* On ouvre l'inputStream */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }
		
		/* On créer un tableau de fréquence */
		list = new ArrayList<FreqCode>();
		boolean found;
		try {
			while(true){
				ba = bis.readBits(getDicoSize());
				if(ba.size() != getDicoSize()) break;
				
				found = false;
				for(FreqCode fc: list){
					if(ba.equals(fc.getKey())){
						fc.incFreq();
						found = true;
						break;
					}	
				}
				if(!found)
					list.add(new FreqCode(ba,1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			bis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Collections.sort(list);
		
		// On construit notre arbre de huffman
		HuffmanTree ht = new HuffmanTree();
		
		try {
			new ObjectOutputStream(bos).writeObject(ht);
		} catch (IOException e) {}
		
		
		//ht.Build(list.toArray());
		
		// Il nous remplit nos valeurs encodées
		//ht.FillFreqCode(fca)

		
	}
}