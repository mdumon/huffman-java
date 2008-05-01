package bitutils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends FilterOutputStream{

	private BitArray buffer = new BitArrayBooleanArray();

	public BitOutputStream(OutputStream out){
		super(out);
	}

	public void writeBits(BitArray b) throws IOException, ArrayTooShortException{
		this.writeBits(b, 0, b.size());
	}

	public void writeBits(BitArray b, int off, int len) throws IOException, ArrayTooShortException{

		if(b.size() == 0)
			return;

		if((len + off) > b.size()) throw new ArrayTooShortException();

		for(int i = off; i < (off + len); i++)
			buffer.add(b.get(i));
		System.out.println("Le buffer vaut avant : " + buffer);
		if(!buffer.isEmpty())
			write(buffer.removeWritableByteArray());
		
		System.out.println("Le buffer vaut après : " + buffer);
	}


	@Override
	public void flush() throws IOException{
		if(!buffer.isEmpty())
			this.write(buffer.removeByteArray2());

		super.flush();
	}
	
	public void close() throws IOException{
		if(!buffer.isEmpty())
			this.flush();
		
		super.close();
	}
}