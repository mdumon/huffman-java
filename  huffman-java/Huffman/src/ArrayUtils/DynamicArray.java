package ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T> {
	T[] t;

	final static int DefaultCapacity = 10;
	final static int DefaultIncrementCapacity = 10;
	
	int capacity;
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	int incrementCapacity;
	public int getIncrementCapacity() {
		return incrementCapacity;
	}
	public void setIncrementCapacity(int incrementCapacity) {
		this.incrementCapacity = incrementCapacity;
	}
	
	int size = 0;
	public int size(){
		return size;
	}
	
	public DynamicArray(T[] t){
		this(t,DefaultCapacity,DefaultIncrementCapacity);
	}
	
	public DynamicArray(T[] t,int capacity){
		this(t,capacity,DefaultIncrementCapacity);
	}
	
	@SuppressWarnings("unchecked")
	public DynamicArray(T[] t,int capacity,int incrementCapacity){
		setCapacity(capacity);
		setIncrementCapacity(incrementCapacity);
		
		this.t = t;
		this.size = t.length;
	}
	
	private class GenericIterator implements Iterator<T>{
		
		private int index = 0;
		private DynamicArray<T> t;
		
		public GenericIterator(DynamicArray<T> t){
			this.t = t;
		}
		
		@Override
		public boolean hasNext() {
			return index < t.size();
		}

		@Override
		public T next() {
			return t.get(index++);
		}

		@Override
		public void remove() {
			t.remove(index);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new GenericIterator(this);
	}
	
	private void expand(){
		t = Arrays.copyOf(t, t.length + getIncrementCapacity());
	}
	
	public T get(int index) throws ArrayIndexOutOfBoundsException{
		if(index < 0 || index >= size())
			throw new ArrayIndexOutOfBoundsException();
		
		return t[index];
	}
	
	public void set(int index,T elem) throws ArrayIndexOutOfBoundsException{
		if(index < 0 || index >= size())
			throw new ArrayIndexOutOfBoundsException();
		
		t[index] = elem;
	}
	
	public void add(T elem){
		if(size >= t.length)
			expand();
		
		t[size++] = elem;
	}
	
	public void add(int index,T elem){
		
		if(index < 0 || index > size())
			throw new ArrayIndexOutOfBoundsException();
		
		if(size >= t.length)
			expand();
		
		T oldE = elem;
		for(int i = index; i < size();i++){
			oldE = t[i];
			t[i] = elem;
			elem = oldE;
		}
		t[size++] = oldE;
	}
	

	public T remove(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		T ret;
		
		ret = t[index];
		
		for(int i = index+1; i < size; i++)
			t[i-1] = t[i];
		
		size--;
		
		return ret;
	}
	
	public T removeLast(){
		size--;
		return t[size()];
	}
	
	public void clear(){
		size = 0;
	}
	
	public void setLast(T elem){
		t[size-1] = elem;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public T[] getArray(){
		return Arrays.copyOf(t, size);
	}	
}
