package algo1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import arbre1.ValueNode;
import bitutils.BitInputStream;

public class FreqCalc{
	
	public static ArrayList<ValueNode> getFrequences(String fichier, int dicoSize) throws FileNotFoundException{
		
		ArrayList<ValueNode> valueNodeList = new ArrayList<ValueNode>();
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
				valueNodeList.get(valueNodeList.indexOf(valueNode)).incFreq();
			else
				valueNodeList.add(valueNode);
		}
		
	}
}