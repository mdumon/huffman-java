package bitutils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends FilterOutputStream{

	private BitArrayBooleanList buffer;

	public BitOutputStream(OutputStream out){
		super(out);
	}

	public void writeBits(BitArrayBooleanList b) throws IOException, ArrayTooShortException{
		this.writeBits(b, 0, b.size());
	}

	public void writeBits(BitArrayBooleanList b, int off, int len) throws IOException, ArrayTooShortException{

		if(b.size() == 0)
			return;

		if((len + off) > b.size()) throw new ArrayTooShortException();

		buffer = new BitArrayBooleanList();

		for(int i = off; i < (off + len); i++)
			buffer.add(b.get(i));

		write(buffer.removeWritableByteArray());
	}


	@Override
	public void flush() throws IOException{
		if(buffer != null)
			this.write(buffer.removeByteArray());

		super.flush();
	}
}