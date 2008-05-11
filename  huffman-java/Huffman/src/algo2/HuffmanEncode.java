package algo2;

import groupe2.Logger;
import groupe2.TimedLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import ArrayUtils.DynamicArray;
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

	/**
	 * Create and "Huffman Encoder" instance
	 */
	public HuffmanEncode(){
		super();
	}
	
	/**
	 * @param inputFile The input file
	 * @param outputFile The output file
	 * @throws FileNotFoundException Thrown if something goes wrong when we try to open both files
	 */
	public HuffmanEncode(File inputFile,File outputFile) throws FileNotFoundException{
		super(inputFile,outputFile);
	}
	
	/**
	 * @param inputFile The input file
	 * @param outputFile The output file
	 * @param dicoSize Dictionnary elements size (inputFile will be read dicoSize by dicoSize bits)
	 * @throws FileNotFoundException Thrown if something goes wrong when we try to open both files
	 */
	public HuffmanEncode(File inputFile,File outputFile,int dicoSize) throws FileNotFoundException {
		super(inputFile,outputFile);
		setDicoSize(dicoSize);
	}
	
	/**
	 * Size of each element in the dictionnary.
	 */
	private int dicoSize = 0;
	
	/**
	 * @return The size of each element in the dictionnary
	 */
	public int getDicoSize(){
		return dicoSize;
	}
	
	/**
	 * @param dicoSize Set the size of each element in the dictionnary
	 */
	public void setDicoSize(int dicoSize){
		this.dicoSize = dicoSize;
	}
	
	
	/**
	 * Generates an Array of Frequency Code for each elements from the input file.
	 * @return A Frequency Code Array
	 */
	private FreqCode[] genFreqCodeArray(){
		BitInputStream bis;
		DynamicArray<FreqCode> fca;
		BitArray ba;
		
		/**************************************************
		 * On ouvre l'inputFile
		 */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) {
			System.out.println("BitInputStream : " + ignore.getMessage());
			return null;
		}
		
		/**************************************************
		 * On créer un tableau de fréquence basé sur l'inputFile
		 */
		fca = new DynamicArray<FreqCode>(new FreqCode[0],(int) (Math.pow(2.0, dicoSize)/4), (int) (Math.pow(2.0, dicoSize)/4));

		boolean found;
		try {
			while(!isCanceled()){
				ba = bis.readBits(dicoSize);
				
				/**************************************************
				 * Dans le cas de la lecture du dernier element
				 * On l'ajoute (ou pas si il fait 0) et on quitte
				 */
				if(ba.size() != dicoSize){
					if(ba.size() != 0)
						fca.add(new FreqCode(ba,1));
					break;
				}
				
				/**************************************************
				 * On parcours notre tableau de freqcode à la recherche de la clef ba
				 */
				found = false;
				for(FreqCode fc: fca){
					if(ba.equals(fc.getKey())){
						fc.incFreq();
						found = true;
						break;
					}	
				}
				
				/**************************************************
				 * Si on ne l'a pas trouvé on l'ajoute
				 */
				if(!found)
					fca.add(new FreqCode(ba,1));
				
				/**************************************************
				 * On arrete si on nous le demande
				 */
				pauseIfIHaveTo();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**************************************************
		 * On ferme l'inputFile
		 */
		try {
			bis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/**************************************************
		 * On retourne le tableau de FreqCode
		 */
		return fca.getArray();
	}
	
	
	/**
	 * Encode the input file using the sorted Frequency code array <code>fca</code>
	 * and writes the encoded file to the outputStream <code>bos</code>.
	 * @param fca Frequency Code Array
	 * @param bos The stream to which the inputFile will be encoded
	 */
	private void encodeFile(FreqCode[] fca,BitOutputStream bos){
		BitArray ba;
		BitInputStream bis;
		
		/*************************************
		 * On sérialise le nombre d'éléments que l'on va encoder
		 */
		getLogger().log("On sérialise le nombre d'éléments que l'on va encoder");
		try {
			new ObjectOutputStream(bos).writeObject((Integer) new Integer((int) ( ((getInputFile().length()*8)-1)/getDicoSize()+1 ) ));
		} catch (IOException ignore) { return; }
		
		/*************************************
		 * On ouvre l'inputStream pour un nouveau parcours
		 */
		getLogger().log("On ouvre l'inputStream pour un nouveau parcours");
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		/*************************************
		 * On encode à la volée le fichier grâce au tableau de FreqCode
		 */
		getLogger().log("On encode à la volée le fichier grâce au tableau de FreqCode");
		try {
			while(!isCanceled()){
				ba = bis.readBits(dicoSize);
				
				if(ba.size() == 0)	break;
				
				for(FreqCode fc: fca){
					if(ba.equals(fc.getKey())){
						bos.writeBits(fc.getEncValue());
						break;
					}
				}
				
				pauseIfIHaveTo();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**************************************************
		 * On ferme l'inputFile
		 */
		getLogger().log("On ferme l'inputFile");
		try {
			bis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param ht Huffmantree to be serialized to the outputstream <code>bos</code>.
	 * @param bos
	 */
	private void serialiseArbre(HuffmanTree ht,BitOutputStream bos){
		
		/*************************************
		 * On sérialise l'huffmantree
		 */
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(ht);
		} catch (IOException e) {}

	}
	
	/**
	 * Main huffman encoder algorithm.
	 * 
	 * @see algo.Huffmaneur#huffmaner()
	 */
	@Override
	protected void huffmaner() {
		FreqCode[] list;
		
		/*************************************
		 * On vérifie la présence des différents paramètres
		 */
		getLogger().log("On vérifie la présence des différents paramètres");
		if(getInputFile() == null ||
				getOutputFile() == null ||
				getDicoSize() == 0){
			System.out.println("Paramètres incorrects");
		}
		
		/*************************************
		 * On génère le tableau de fréquence
		 */
		getLogger().log("On génère le tableau de fréquence");
		list = genFreqCodeArray();
		if(list == null) return;
		
		/*************************************
		 * On tri notre tableau de fréquence (dans l'ordre décroissant)
		 */
		getLogger().log("On tri notre tableau de fréquence (dans l'ordre décroissant)");
		Arrays.sort(list);
		
		/*************************************
		 * On créer notre arbre d'huffman
		 */
		getLogger().log("On créer notre arbre d'huffman");
		HuffmanTree ht = new HuffmanTree(list);
		
		/*************************************
		 * On construit l'arbre
		 */
		getLogger().log("On construit l'arbre");
		ht.Build();
		
		/*************************************
		 * On rempli le champ (EncodedValue) des freqcodes de notre liste
		 */
		getLogger().log("On rempli le champ (EncodedValue) des freqcodes de notre liste");
		ht.FillEncodedValues();
		
		/*************************************
		 * On créer l'outputstream
		 */
		getLogger().log("On créer l'outputstream");
		BitOutputStream bos;
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }
		
		/*************************************
		 * On Sérialise l'HuffmanTree
		 */
		getLogger().log("On Sérialise l'HuffmanTree");
		serialiseArbre(ht,bos);
		
		/*************************************
		 * On encode à la volée le fichier
		 */
		getLogger().log("On encode à la volée le fichier");
		encodeFile(list,bos);
		
		/*************************************
		 * On Ferme l'outputStream
		 */
		getLogger().log("On Ferme l'outputStream");
		try {
			bos.close();
		} catch (IOException ignore) {}
		
		getLogger().log("Fin");
	}
	
	private Logger logger = null;
	private Logger getLogger(){
		if(logger == null){
			logger = new TimedLog();
			((TimedLog)logger).start();
		}
		return logger;
	}
}