package algo;

import java.io.File;
import java.io.FileNotFoundException;

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
		
	}
}
