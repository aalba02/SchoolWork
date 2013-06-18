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
 * This class represents a Node and the attributes/behaviors that are
 * associated with a Node. Each node has a key and value.
 * @author Aileen Alba
 *
 */
public class Node {
	/**
	 * This field represents the {@link Node}'s key. The key is set by the Constructor
	 * {@link #Node(String, int)}. The value of key can be gotten using {@link #getKey()}.
	 */
	private String key;
	/**
	 * This field represents the {@link Node}'s value. The value is set by the Constructor
	 * {@link #Node(String, int)}. The value can be gotten using {@link #getValue()};
	 */
	private int value;
	/**
	 * This field represents the next Node. This value can be gotten using {@link #getNext()}. It
	 * can also be set using {@link Node#setNext(Node)}.
	 */
	private Node next;
	
	/**
	 * This is the constructor for the class {@link Node}. Initially, a
	 * {@link #Node} has {@link #key}, {@link Node} and {@link #value} set by given parameters.
	 * @param key The key given to {@code this} {@link Node}.
	 * @param value The value given to {@code this} {@link Node}.
	 */
	public Node(String key, int value, Node n){
		this.key = key;
		this.value = value;
		next = n;
	}
	
	/**
	 * This method sets the next {@link Node}.
	 * @param next the {@link #next} {@link Node} to be set to the link.
	 */
	public void setNext(Node next){
		this.next = next;
	}
	/**
	 * This method returns the {@link #next} {@link Node}.
	 * @return the {@link Node} following {@code this} {@link Node}.
	 */
	public Node getNext(){
		return next;
	}
	
	/**
	 * This method returns {@link #value} of {@code this} {@link Node}.
	 * @return the value of {@code this} {@link Node}.
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * This method returns {@link #key} of {@code this} {@link Node}.
	 * @return the key of {@code this} {@link Node}.
	 */
	public String getKey(){
		return key;
	}
}
