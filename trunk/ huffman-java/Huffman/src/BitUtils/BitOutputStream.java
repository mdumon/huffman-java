package BitUtils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends FilterOutputStream{

	public BitOutputStream(OutputStream out){
			super(out);
	}
	
	private byte[] output;
	private byte octet;
	private int j;
	
	public void write(byte[] b) throws IOException{
		out.write(b);
	}

	public void writeBits(BitArray b) throws IOException{
		this.writeBits(b, 0, b.size());
	}
	
	public void writeBits(BitArray b, int off, int len) throws IOException{
		
		j = 0;
		octet = 0;
		output = new byte[b.size() / 8];
		
		for(int i = off; i < b.size(); i++){
			if(b.get(i)){
				octet += 1;
			}
			else{
				octet += 0;
			}
				octet <<= 1;
			
			/* juste un souci ici
			 * si je veux mettre dans le tableau tous les 8 bits
			 * il va le faire quand i vaut 0 lol
			 */	
			if((i % 8) == 0){
				output[j] = octet;
				j++;
			}
			
		}
		
		this.write(output);
	}
	
	public void flush() throws IOException{
		output[j] = octet;
		this.write(output);
		out.flush();
	}
	
	public void close() throws IOException{
		out.close();
	}
}
