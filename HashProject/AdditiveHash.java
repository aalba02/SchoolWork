/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodriguez
 *
 * Programming Assignment #3
 *
 * This assignment creates four different hash tables, with different hashing functions.
 * They all implement the interface HashTable<K,V> which is give by the professor.
 * Along with creating a HashTable a KeyValueGenerator must be created which creates random
 * keys and values. This assignment is more to experiment on the different hashing techniques.
 *
 * @author Aileen Alba
 */
package edu.csupomona.cs.cs241.prog_assgmnt_3;
/**
 * This class represents the Additive Hash Table. The class implements
 * the HashTable<String, Integer> interface. This Table differs from
 * the other tables because it Adds the values of each letter and gets the 
 * hash by mod of the size.
 * @author Aileen Alba
 *
 */
public class AdditiveHash implements HashTable <String, Integer>{
	/**
	 * This field represents the size of the {@link AdditiveHash}.
	 * The size is given by the constructor {@link #AdditiveHash(int)}.
	 */
	private long size;
	/**
	 * This field represents the array of Nodes. The size of the array 
	 * is determined by the {@link #size}.
	 */
	private Node[]table;
	/**
	 * UsedBuckets is used to stored the number of usedBuckets.
	 * Longest Chain is used to keep track of the longest chain.
	 * StoredElements keeps track of the number of elements stored.
	 */
	private long usedBuckets, longestChain, storedElements;
	
	/**
	 * This is the constructor of {@link AdditiveHash}. Initially,
	 * the {@link #size} is set by parameter and the size of the 
	 * array {@link #table} is also set by the parameter.
	 * @param size The size of {@link #table}.
	 */
	public AdditiveHash(int size){
		this.size = size;
		table = new Node[size];
		usedBuckets = 0;
		longestChain = 0;
	}
	/**
	 * This method adds a {@link Node} to the {@link AdditiveHash}{@link #table}. If the index in
	 * {@link #table} already has a {@code Node} then the new {@link Node} is 
	 * linked this {@link #table} index.
	 * @param key the key that is added apart of the new {@link Node}.
	 * @param value the value that is added apart of the new {@link Node}.
	 */
	public void add(String key, Integer value) {
		
		double loadFactor = (usedBuckets*1.0/size);
		if(loadFactor > 0.75 || longestChain > 5){
			resize();
		}
		
		storedElements++;
		int chain = 1;
		int index = hash(key);
		
		if(table[index] == null){
			table[index] = new Node(key,value, null);
			usedBuckets++;
		}
		else {
			Node temp = table[index];
			while(temp.getNext() != null){
				temp = temp.getNext();
				chain++;
			}
			if(chain > longestChain) longestChain = chain;
			temp.setNext(new Node(key, value, null));
		}
			
	}

	/**
	 * This method removes the given key from the {@link AdditiveHash}
	 * {@link #table}. If the {@link Node} is linked to the chain it must be looked
	 * for before it is removed.
	 * @param key the key that is being removed from the {@link AdditiveHash} {@link #table}.
	 * @return the value of the {@link Node} that is removed.
	 */
	public Integer remove(String key) {
		
		int index = hash(key);
		Node prev = null;
		Node temp = table[index];
		if(temp == null) return -1;
		
		if(temp.getKey().equals(key)){
			table[index] = temp.getNext();
			temp.setNext(null);
			return temp.getValue();
		}
		
		while(temp != null){
			prev = temp;
			temp = temp.getNext();
			if(temp.getKey().equals(key)){
				prev.setNext(temp.getNext());
				temp.setNext(null);
				return temp.getValue();
			}
		}
		return -1;
	}
	/**
	 * This method finds the key in the {@link AdditiveHash} {@link #table}.
	 * After finding it it returns the value of the {@link Node} if the
	 * {@link Node} is in the table.
	 * @param key the key that is being looked for in the {@link AdditiveHash} {@link #table}.
	 * @return value of the found key.
	 */
	public Integer lookup(String key) {
		int index = hash(key);
		Node temp = table[index];
		
		while(temp != null){
			if(temp.getKey().equals(key)) return temp.getValue();
			temp = temp.getNext();
		}
		return -1;
	}

	/**
	 * This method prints out the report. It prints out the Load Factor which is the number
	 * of used buckets divided by the size. The Longest Chain of Nodes. It also prints out the
	 * Density Factor which is the number of stored elements divided by the size. And lastly,
	 * the chaining factor which is the number of Stored Elements divided by the number of usedBuckets.
	 */
	public void printReport() {
		System.out.printf("Load Factor: %6.4f\n", (usedBuckets*1.0/size) );
		System.out.printf("Longest Chain: %d\n", longestChain );
		System.out.printf("Density Factor: %6.4f\n", (storedElements*1.0/size));
		System.out.printf("Chaining Factor: %6.4f\n", (storedElements*1.0/usedBuckets));
	}
	/**
	 * This method finds the hash of the key from {@code this} {@link Node}.
	 * @param key the String that is being hashed.
	 * @return the hash of the key.
	 */
	public int hash(String key){
		long hash = calculateSum(key) % size;
		return (int)hash;

	}
	/**
	 * This method calculates the sum of a string before is hashed. This method
	 * is called in {@link #hash(String)} to find the sum, and then hashed. The sum
	 * is calculated by multiplying the value of the letter by 26 to the power
	 * of the index in which the letter is in the String. The sum is then found by
	 * adding all the values of the letters.
	 * @param s the string that is being calculated.
	 * @return the value of the sum of the string.
	 */
	public long calculateSum(String s){
		char[] c = s.toCharArray();
		long sum = 0;
		int exp = c.length - 1;
		for(int i = 0; i <c.length; i++){
			sum+=(long)(valueOfLetter(c[i]) * Math.pow(26, exp));
			exp--;
		}
		
		return sum;
	}
	/**
	 * This method finds the value of a letter. Used in {@link #calculateSum(String)}.
	 * @param c the letter that needs a value.
	 * @return the value of the letter.
	 */
	public long valueOfLetter(char c){
		return Character.getNumericValue(c) - 10;
	}
	
	/**
	 * This method is used to resize the {@link #table} when it reaches a load 
	 * factor greater than .75 or a chain greater than 5. This method first sets
	 * the old table to a temp array of Nodes. It sets the old table to a new table
	 * with a new size. After it searches through the old table and adds the element
	 * to {@link this} HashTable.
	 */
	public void resize(){
		Node[]temp = this.table;
		usedBuckets = 0;
		longestChain = 0;
		storedElements = 0;
		table = new Node[(int)(size*1.5)];
		
		for(int i = 0; i < temp.length; i++){
			
			if(temp[i] != null){	
				Node tempNode = temp[i];
				while(tempNode != null){
					this.add(tempNode.getKey(), tempNode.getValue());
					tempNode = tempNode.getNext();
					
				}
			}
		}
		this.size = table.length;
	}
	
}
