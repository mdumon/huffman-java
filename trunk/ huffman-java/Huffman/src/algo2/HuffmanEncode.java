package algo2;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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
		int dicoSize = getDicoSize();
		
		/* On ouvre l'inputFile */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		System.out.println("Création du tableau de fréquence, dico : " + getDicoSize());
		/* On créer un tableau de fréquence basé sur l'inputFile */
		list = new ArrayList<FreqCode>();
		boolean found;
		try {
			while(true){
				ba = bis.readBits(dicoSize);
				/* System.out.println("lu : " + ba + "\nsize : " + ba.size()); */
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
		
		/* On ferme l'inputFile */
		try {
			bis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		System.out.println("Tri du tableau de fréquence");
		/* On tri notre tableau de fréquence */
		Collections.sort(list);
		FreqCode[] fca = list.toArray(new FreqCode[0]);
		
		System.out.println("Construction de l'huffman tree");
		/* On construit notre arbre d'huffman */
		HuffmanTree ht = new HuffmanTree();
		
		/* On obtient dans notre tableau de FreqCode l'encodage des elements */
		ht.Build(fca);
		
		
		int tota = 0;
		int totb = 0;
		System.out.println("Sérialisation de l'arbre");
		/* On ouvre l'outputFile */
		try {
			bos = new BitOutputStream(new FileOutputStream(getOutputFile()));
		} catch (FileNotFoundException ignore) { return; }
		
		/* On sérialise l'arbre au début du fichier */
		try {
			new ObjectOutputStream(bos).writeObject(ht);
		} catch (IOException e) {}
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(ht);
			tota += baos.toByteArray().length;
		} catch (IOException e) {}
		System.out.println("Arbre lenght => " + tota);
		
		
		System.out.println("Encodage de l'inputStream");
		/* On réouvre notre inputStream */
		try {
			bis = new BitInputStream(new BufferedInputStream(new FileInputStream(getInputFile())));
		} catch (FileNotFoundException ignore) { return; }
		
		//int i = 0;

		/* On encode à la volée notre inputStream */
		try {
			while(true){
				ba = bis.readBits(getDicoSize());
				if(ba.size() != getDicoSize()) break;
				
				found = false;
				for(FreqCode fc: list){
					if(ba.equals(fc.getKey())){
						//System.out.println("Ecriture du bit " + i++ + "[" + ba + "] => [" + fc.getEncValue() + "]" );
						tota += fc.getEncValue().size();
						totb += 8;
						bos.writeBits(fc.getEncValue());
						found = true;
						break;
					}
				}
				if(!found){
					System.out.println("Erreur encodage inconnu pour ce BitArray");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Avant : " + totb);
		System.out.println("Après : " + tota);
		
		
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