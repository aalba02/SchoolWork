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
 * This class is the main and takes into account all Hashing Methods
 * To use Hashing or non Hashing just comment out the hash function in add.
 * @author Aileen Alba
 *
 */
public class Main {
	
	public static void main(String [] args){
		
		int elements = 2500; // number of elements 
		int tableSize = 10000; // size of table
		int MAX_SIZE = 15000; // greatest number of elements
		int increment = 2500;	// the number the elements are incrementing
		
		
		for(int k = elements; k <= MAX_SIZE; k+=increment){
//			System.out.println("Additive Hashing Print Report");
			System.out.println("Multiplication Hashing Print Report");
//			System.out.println("Rotation Additive Hashing Print Report");
//			System.out.println("Rotation Multiplication Hashing Print Report");
			System.out.println("Elements: " + elements + "\tTable Size: " + tableSize + "\n");
			for(int j = 0; j < 10; j++){
//				AdditiveHash hs = new AdditiveHash(tableSize);
//				MultiplicationHash hs = new MultiplicationHash(tableSize);
//				RotationAdditiveHash hs = new RotationAdditiveHash(tableSize);
				RotationMultiplyHash hs = new RotationMultiplyHash(tableSize);
			
				AutoKeyValueGenerator av = new AutoKeyValueGenerator(elements);
				av.initialize();
				System.out.println("Report #" + (j+1) + ": ");
				for(int i = 0; i < elements; i++){
					hs.add(av.getNextKey(), av.getNextValue());
				}
				hs.printReport();
				System.out.println();
			}
			elements+=increment;
		}

	}

}
