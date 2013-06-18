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

public interface HashTable<K,V> {
	public void add(K key, V value);
	public V remove(K key);
	public V lookup(K key);
	public void printReport();
}
