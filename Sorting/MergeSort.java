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
 * This class represents the use of Merge Sort on a list of integers. Merge Sort is
 * a divide and conquer sort. It divides the elements into two new groups of arrays. It then
 * sorts the two smaller groups and at the end merges the all the pieces.
 * @author Aileen Alba
 *
 */
public class MergeSort {
	/**
	 * This field represents the start time for the implementation of {@link MergeSort}.
	 */
	private long startTime;
	/**
	 * This field represents the end time for the implementation of {@link MergeSort}.
	 */
	private long endTime;
	/**
	 * This is the constructor for {@link MergeSort}. Inside this constructor the
	 * {@link #startTime} is set at the beginning and the {@link #endTime} is
	 * set at the end of the constructor. The constructor begins the sorting method.
	 * If the size of the array is one the array is returned. If the size is two the
	 * first element is compared to the second element and if the first is greater it is
	 * swapped with the second element using {@link #swap(int[], int, int)}. If none of
	 * the previous apply {@link #mergeSort(int[], int, int)} is called.
	 * @param data the list of integers that is being sorted.
	 */
	public MergeSort(int [] data){
		this.startTime = System.currentTimeMillis();
		
		if(data.length == 1) return;
		else if(data.length == 2){
			if(data[0] > data[1])
				swap(data, 0, 1);		
		}
		else{
			mergeSort(data, 0, data.length);
		}
		
		this.endTime = System.currentTimeMillis();
	}
	/**
	 * This method sorts a list of ints from smallest to biggest. Uses {@link #merge(int[], int, int, int)}
	 * to merge the elements at the end.
	 * @param data the array that is going to be sorted
	 * @param startIndex the start index of array that is going to be sorted
	 * @param nElements the number of elements that will be sorted
	 */
	private void mergeSort(int[] data, int startIndex, int nElements){
		
		if(nElements > 1){
	
			int mid, newEnd;
			
			// The mid is middle index
			// newEnd is the new size of the array.
			mid = nElements/2;
			newEnd = nElements - mid;
			
			mergeSort(data, startIndex, mid);
			mergeSort(data, startIndex + mid, newEnd);
			
			// In the end merge
			merge(data, startIndex, mid, newEnd);		
		}	
	}
	
	/**
	 * This method is called to merge the halves that have been split. It is used
	 * by {@link #mergeSort(int[], int, int)}.
	 * @param data the array of ints that is being sorted
	 * @param startIndex the startIndex of the array
	 * @param mid the middle of the array 
	 * @param newEnd the new End of the half of the array.
	 */
	private void merge(int [] data, int startIndex, int mid, int newEnd){
		// New Array that will have sorted ints.
		int[] temp = new int[mid + newEnd];
		int copy = 0, copy1 = 0, copy2 = 0;
	
		// Merge the elements depending on whether element is greater or less
		while((copy1 < mid) && (copy2 < newEnd)){
			if(data[startIndex+copy1] < data[startIndex + mid + copy2])
				temp[copy++] = data[startIndex + (copy1++)];
			else
				temp[copy++] = data[startIndex + mid + copy2++];
		}
		
		// Copy any uncounted for elements to left and right sides
		while(copy1 < mid)
			temp[copy++] = data[startIndex + (copy1++)];
		while(copy2 < newEnd)
			temp[copy++] = data[startIndex + mid + (copy2++)];
		
		// Copy temp to array 
		for(int i = 0; i < mid + newEnd; i++){
			data[startIndex + i] = temp[i];
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
