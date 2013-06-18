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
package edu.csupomona.cs.cs241.prog_assgmnt_3;import java.util.Random;

/**


import java.util.Random;
/**
 * This Class generates a random String of four characters and a random number between 1 and 100.
 * First it uses {@link #initialize()} to initialize the first key. It then goes on to use the next
 * method {@link #getNextKey()} to initialize the next key but also compare the strings to make sure
 * that the same strings are not being used. Lastly its uses {@link #getNextValue()} to return a random
 * integer between 1 - 100.
 * @author Aileen Alba
 *
 */

public class AutoKeyValueGenerator {
	/**
	 * This is the current key used in the array of keys.
	 */
	private String key;
	/**
	 * This is the array of keys to keep a comparison that the same key was not used.
	 */
	private String [] keys;
	/**
	 * This is the size the array of keys will be.
	 */
	private int size;
	/**
	 * This is the constructor for {@link AutoKeyGenerator} this stores
	 * the size of the keys.
	 * @param n the size that will be used for the keys.
	 */
	public AutoKeyValueGenerator(int n){
		keys = new String[n];
		size = 0;
	}
	
	/**
	 * This method initializes the key. It sets the key to a string of four
	 * characters all which are lower case.
	 */
	public void initialize() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int N = alphabet.length();

		Random r = new Random();
		String key = "";
		for (int i = 0; i < 4; i++) {
			key+= alphabet.charAt(r.nextInt(N));
		}
		this.key = key;
	}
	
	/**
	 * This method is used to return the key and then initializes the next
	 * key. In this method {@link #isUsed(String)} is used to search through
	 * the array of keys to make sure that the key was not already used.
	 * @return the key given.
	 */
	public String getNextKey() {
		String temp = key;
		boolean keyNotUsed = false;
			
		while(!keyNotUsed){
			initialize();
			if(!isUsed(key)) keyNotUsed = true;
		}
			
		keys[size] = temp;
		size++;
		return temp;
	}
		
	/**
	 * This method checks that the given key has not been previously used.
	 * @param key2 this is the key that is being checked to see if it has been 
	 * previously been used.
	 * @return whether or not the key has been used.
	 */
	private boolean isUsed(String key2) {
		for(int i = 0; i < size; i++){
			if(keys[i].equals(key2)) return true;
		}
		return false;
	}

	/**
	 * This method is used to return a random integer between
	 * one and one hundred.
	 * @return a random number between one and one hundred.
	 */
	public int getNextValue() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100) + 1;
		return randomInt;
	}	
}
