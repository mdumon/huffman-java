package algo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import arbre1.Node;
import arbre1.ValueNode;
import bitutils.BitInputStream;

public class FreqCalc{
	
	public static ArrayList<Node> getFrequences(File fichier, int dicoSize) throws FileNotFoundException{
		
		ArrayList<Node> valueNodeList = new ArrayList<Node>();
		FileInputStream fp = new FileInputStream(fichier);
		BitInputStream bitInputStream = new BitInputStream((InputStream)fp);
		
		while(true)
		{
			ValueNode valueNode;
			try {
				valueNode = new ValueNode(bitInputStream.readBits(dicoSize),1);
			} catch (IOException e) {
				return valueNodeList;
			}
			if(valueNode.getValue().size() == 0) return valueNodeList;
			if(valueNodeList.contains(valueNode))
				((ValueNode)valueNodeList.get(valueNodeList.indexOf(valueNode))).incFreq();
			else {
				System.out.print("Ajout de " + valueNode);
				valueNodeList.add(valueNode);
			}
		}
		
	}
}