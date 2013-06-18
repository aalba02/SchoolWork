/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodriguez
 *
 * Programming Assignment #5
 *
 * This project consists of implementing different sorting methods:
 *   - Merge Sort
 *   - Quick Sort
 *   - Counting Sort
 * This project also consists of keeping track of the time it takes these
 * sorting methods to sort a list of integers.
 *
 * @author Aileen Alba
 */
package edu.csupomona.cs.cs241.prog_assgmnt_5;

/**
 * This class represents the use of Counting Sort on a list of integers. Counting sort needs 
 * to know the greatest element in the list of the elements.This sort places the element into
 * a new array in the index of how many elements must be smaller than the element.
 * @author Aileen Alba
 *
 */
public class CountSort {
	/**
	 * This field represents the start time for the implementation of {@link CountSort}.
	 */
	private long startTime;
	/**
	 * This field represents the end time for the implementation of {@link CountSort}.
	 */
	private long endTime;
	/**
	 * This is the constructor for {@link CountSort}. Inside this constructor the
	 * {@link #startTime} is set at the beginning and the {@link #endTime} is
	 * set at the end of the constructor. The constructor begins the sorting method.
	 * If the size of the array is one the array is returned. If the size is two the
	 * first element is compared to the second element and if the first is greater it is
	 * swapped with the second element using {@link #swap(int[], int, int)}. If none of
	 * the previous apply {@link #countSort(int[], int)} is called.
	 * @param data the list of integers that is being sorted.
	 * @param k the biggest element in the array.
	 */
	public CountSort(int [] data, int k){
		this.startTime = System.currentTimeMillis();
		
		if(data.length == 1) return;
		else if(data.length == 2){
			if(data[0] > data[1])
				swap(data, 0, 1);
			return;
		}
		else{
			countSort(data, k);
		}
		
		this.endTime = System.currentTimeMillis();
	}
	/**
	 * This method implements the counting sort. First two arrays of ints are created one with the
	 * size of the biggest int in the list (B) and another the size of the original array (C).
	 * Next, {@link #initialize(int[])} is called to set the array elements to zeros. 
	 * @param data the list of integers that is being sorted
	 * @param max the biggest number in the element.
	 */
	private void countSort(int [] data, int max){
		
		int[]B = new int[max + 1];
		int[]C = new int[data.length];
		initialize(B); initialize(C);
		
		for(int i = 0; i < data.length; i++)
			B[data[i]] = B[data[i]] + 1;
		
		for(int j = 1; j < B.length; j++)
			B[j] = B[j] + B[j-1];

		for(int k = 0; k < data.length; k++){
			C[B[data[k]]-1] = data[k];
			B[data[k]] = B[data[k]] - 1;
		}	
		
		for(int i = 0 ; i < data.length; i++){
			data[i] = C[i];
		}
	}
	/**
	 * This method initializes the given array to all zeros. If loops through the
	 * array and sets each element equal to zero.
	 * @param data the array that is being set to zero at each index.
	 */
	private void initialize(int [] data){
		for(int i = 0; i < data.length; i++){
			data[i] = 0;
		}
	}
	/**
	 * This method swaps two indexes of an array. First a temp int is set to the
	 * element at index i. Then the element at index i is set to the element at index j.
	 * Lastly index j is set to the temp, which was the original element at index i.
	 * @param data the array that is being used.
	 * @param i index of first element being swapped.
	 * @param j index of second element being swapped.
	 */
	private void swap(int[] data, int i, int j){
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	/**
	 * This method returns the {@link #endTime} - {@link #startTime}.
	 * @return the time it took to implement {@link CountSort}.
	 */
	public long getTime(){

		return endTime - startTime;
	}
}
