package algo1.test;

import java.util.ArrayList;

import tri.test.Tri;

import algo1.FreqCalc;
import arbre1.Node;
import arbre1.ValueNode;

public class FreqCalcTest {
	
	public static void main(String args[]){
		ArrayList<Node> nodeList = null;
		try{
			nodeList = FreqCalc.getFrequences("/home/quentin/Documents/workspace/Huffman/src/algo1/test/fichierTest", 8);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Liste de frequence" );
		for(int i=0 ; i < nodeList.size();i++){
			System.out.print("Valeur:" + ((ValueNode)nodeList.get(i)).getValue());
			System.out.println(" Frequence: " + ((ValueNode)nodeList.get(i)).getFreq());
		}
		ValueNode[] valueNodeTab = new ValueNode[nodeList.size()];
		nodeList.toArray(valueNodeTab);
		
		Tri.sort(valueNodeTab);

		System.out.println("TABLEAU TRIE:");
		for(int j=0;j < valueNodeTab.length;j++){
			System.out.println(valueNodeTab[j].getValue() + " " + valueNodeTab[j].getFreq());
		}
		
	}
}
