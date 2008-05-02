package algo2;

import groupe2.TimedLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

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
		FreqCode[] list;
		int nbFreqCode;
		int nbElements;
		BitArray ba;
		int dicoSize = getDicoSize();
		TimedLog tl = new TimedLog();
		long advTotal; // Total d'advance à avoir
		long advCurrent = 0;
		
		tl.start();
		
		
		/* On ouvre l'inputFile */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) {
			System.out.println("BitInputStream : " + ignore.getMessage());
			return;
		}
		
		tl.log("Création du tableau de fréquence, dico : " + getDicoSize());
		
		advTotal = getInputFile().length()*4;
		
		/* On créer un tableau de fréquence basé sur l'inputFile */
		list = new FreqCode[((int)Math.pow(2.0, dicoSize))/2];
		nbFreqCode = 0;
		nbElements = 0;
		boolean found;
		FreqCode fc;
		int i;
		try {
			while(true){
				ba = bis.readBits(dicoSize);
				
				/* Si on est à la fin du fichier on s'arrete */
				if(ba.size() == 0) break;
				
				nbElements++;
				advCurrent++;
				setAdvance((int)(advCurrent*100/advTotal));
				
				/* Si on a lu genre 3 bit alors que le dico en fait 9, on écrit notre dernier freqcode et on quitte */
				if(ba.size() != dicoSize){
					if(nbFreqCode >= list.length)
						list = Arrays.copyOf(list, list.length + 1);
					list[nbFreqCode++] = new FreqCode(ba,1);
					break;
				}
				
				for(i = 0; i < nbFreqCode; i++){
					fc = list[i];
					if(ba.equals(fc.getKey())){
						fc.incFreq();
						break;
					}	
				}
				if(i >= nbFreqCode){
					if(nbFreqCode >= list.length)
						list = Arrays.copyOf(list, list.length + list.length/2);
					list[nbFreqCode++] = new FreqCode(ba,1);
				}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tl.log("fin de la lecture du fichier (nbElements : " + nbElements +")");
		
		/* On ferme l'inputFile */
		try {
			bis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		tl.log("Tri du tableau de fréquence");
		
		list = Arrays.copyOf(list, nbFreqCode);
		
		/* On tri (ordre décroissant) notre tableau de fréquence */
		Arrays.sort(list);
		
		tl.log("Construction de l'huffman tree");
		/* On construit notre arbre d'huffman */
		HuffmanTree ht = new HuffmanTree();
		
		/* On obtient dans notre tableau de FreqCode l'encodage des elements */
		ht.Build(list);
		
		/*int tota = 0;
		int totb = 0;*/
		tl.log("Sérialisation de l'arbre");
		/* On ouvre l'outputFile */
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }
		
		/* On sérialise l'arbre au début du fichier */
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(ht);
			
			/* On sérialise le nombre de feuilles */
			oos.writeObject(new Integer(nbElements));
		} catch (IOException e) {}
		
		/*try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(ht);
			tota += baos.toByteArray().length;
		} catch (IOException e) {}
		tl.log("Arbre lenght => " + tota);
		*/
		
		
		
		tl.log("Encodage de l'inputStream");
		/* On réouvre notre inputStream */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		//int i = 0;

		/* On encode à la volée notre inputStream */
		try {
			while(true){
				ba = bis.readBits(dicoSize);
				
				if(ba.size() == 0) break;
				
				found = false;
				for(i = 0; i < nbFreqCode; i++){
					fc = list[i];
					if(ba.equals(fc.getKey())){
						//System.out.println("Ecriture du bit " + i++ + "[" + ba + "] => [" + fc.getEncValue() + "]" );
						//tota += fc.getEncValue().size();
						//totb += fc.getKey().size();
						bos.writeBits(fc.getEncValue());
						found = true;
						break;
					}
				}
				if(!found){
					tl.log("Erreur encodage inconnu pour ce BitArray");
				}
				
				advCurrent += 3;
				setAdvance((int)(advCurrent*100/advTotal));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//tl.log("Avant : " + totb);
		//tl.log("Après : " + tota);
		tl.log("Fin");
		
		/* On ferme les différentes streams */
		try {
			bos.close();
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}