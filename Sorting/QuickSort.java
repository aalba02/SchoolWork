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
 * This class represents the use of Quick Sort on a list of integers. Quick Sort
 * is also a divide and conquer sort. Unlike merge sort, quick sort uses and element of as a
 * pivot and splits at that place after the elements are placed on the right side of the
 * pivot.
 * @author Aileen Alba
 *
 */
public class QuickSort {
	/**
	 * This field represents the start time for the implementation of {@link QuickSort}.
	 */
	private long startTime;
	/**
	 * This field represents the end time for the implementation of {@link QuickSort}.
	 */
	private long endTime;
	/**
	 * This is the constructor for {@link QuickSort}. Inside this constructor the
	 * {@link #startTime} is set at the beginning and the {@link #endTime} is
	 * set at the end of the constructor. The constructor begins the sorting method.
	 * If the size of the array is one the array is returned. If the size is two the
	 * first element is compared to the second element and if the first is greater it is
	 * swapped with the second element using {@link #swap(int[], int, int)}. If none of
	 * the previous apply {@link #quickSort(int[], int, int)} is called.
	 * @param data the list of integers that is being sorted.
	 */
	public QuickSort(int[]data){
		this.startTime = System.currentTimeMillis();
		
		if(data.length == 1) return;
		else if(data.length == 2){
			if(data[0] > data[1])
				swap(data, 0, 1);
		}
		else{
			quickSort(data, 0, data.length-1);		
		}
		
		this.endTime = System.currentTimeMillis();
		
	}
	/**
	 * This method uses Quick Sort to sort the array. It first checks that the
	 * size of the array is greater than 1 element. Next {@link #partition(int[], int, int)},
	 * is called to find the index of the pivot and qucikSort is called again.
	 * @param data the list of ints that is being sorted
	 * @param start the start index of the array
	 * @param end the number of elements the array has which is the last index
	 */
	private void quickSort(int[]data, int start, int end){
		int x;
		if(end - start >= 1){
			x = partition(data, start, end);
			quickSort(data, start, x-1);
			quickSort(data, x + 1, end);
		}
		else
			return;
	}
	/**
	 * This method is used by {@link #quickSort(int[], int, int)} to move
	 * all the elements greater than the pivot to the right and all the elements
	 * less than pivot to the left.
	 * @param data the array that is being sorted.
	 * @param start the start index of the array
	 * @param end the end index also the number of elements.
	 * @return the index of the pivot number.
	 */
	private int partition(int[] data, int start, int end) {
		
		// 	Index of element after pivot and last index.
		int bigNum = start + 1;
		int smallNum = end;
		
		// pivot is set to first element in array.
		int pivot = data[start];
		
		// while the indexes have not crossed.
		while(smallNum  > bigNum){
			
			// while a number bigger than pivot has not been found
			while(data[bigNum] <= pivot && bigNum < end )
				bigNum++;
			
			// while a number smaller than pivot has not been found
			while(data[smallNum ] > pivot && smallNum  > start)
				smallNum --;
			
			// swap the elements that the end.
			if(bigNum < smallNum ){
				swap(data, bigNum, smallNum );
			}
		}
		
		// At the end swap the pivot with the index of where it belongs
		swap(data, start, smallNum );
		
		return smallNum ;
		
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
