package BitUtils;

/**
 * @author Franck Séhédic
 */

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * BitInputStream Allows us to work with any InputStream on a bit based basis
 */
public class BitInputStream extends FilterInputStream{
	
	public BitInputStream(InputStream in) {
		super(in);
	}
	
	private byte byteBuf;
	private int bbIndex = 0;
	
	public int read() throws IOException{
		byte[] buf = new byte[2];
		byte retVal;
		
		if( (bbIndex % 8) == 0)
			return in.read();
		
		buf[0] = byteBuf;
		buf[1] = (byte)in.read();
		
		try{
			retVal = new BitArray(buf,8,bbIndex).toByteArray()[0];
		}catch(ArrayTooShortException ex1){
			retVal = 0;
		}
		
		byteBuf = buf[1];
		
		return retVal;
	}
	

	public boolean readBit() throws IOException{
		if((bbIndex % 8) == 0){
			byteBuf = (byte)in.read();
			bbIndex = 0;
		}
		
		return ((byteBuf >> bbIndex++) & 1) == 1;
	}
	
	public BitArray readBits(int nbBits) throws IOException{
		BitArray ba = new BitArray();
		
		while(nbBits-- > 0)
			ba.add(readBit());
		
		return ba;
	}
	
	private int markedBbIndex;
	private byte markedByteBuf;
	@Override
	public void mark(int readLimit){
		if(markSupported()){
			this.mark(readLimit);
			markedByteBuf = byteBuf;
			markedBbIndex = bbIndex;
		}
	}
	
	@Override
	public void reset(){
		if(markSupported()){
			this.reset();
			byteBuf = markedByteBuf;
			bbIndex = markedBbIndex;
		}
	}

}

/*
BitInputStream file;
file = new BitOutputStream(new FileOuputStream("monfichier"));

ByteArray b;
file.readBit(b,9);
file.readBit(b,9);


file.writeBits(b);
file.writeBits(b);
*/