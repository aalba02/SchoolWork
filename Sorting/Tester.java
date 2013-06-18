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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This class runs the test cases for the three sorting methods.
 * The output of these tests are sent to files in the folder.
 * @author Aileen Alba
 *
 */
public class Tester {
	/**
	 * This field represents the max element the random numbers go up to.
	 */
	private int maxElement;
	/**
	 * This is field represents the size of the list of ints.
	 */
	private int size;
	/**
	 * This is the constructor for the class Tester. Initially the {@link #size} is
	 * set to s and {@link #maxElement} is set to e.
	 * @param s the size of the array of ints.
	 * @param e the max element the random numbers go up to.
	 */
	public Tester(int s, int e){
		this.size = s;
		this.maxElement = e;
	}
	
	/**
	 * This method runs the test with the given {@link #size} and {@link #maxElement}.
	 * @throws IOException
	 */
	protected void runTest() throws IOException{
		
		Random n = new Random();
		String s = "";
		
		// file name is set to the size and 0 to maxElement
		String fileName = size + "_0to" + maxElement+ ".txt";
		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		
		out.write("\nSize: " + size + "\tNumbers between 0 and " + maxElement);
		
		// Loop for # of times to run the test		
		for(int i = 1; i < 101; i++){
			// This will be the unsorted array of ints.
			int[] data = new int[this.size];
			
			// This will be the unsorted array of ints to be used by mergeSort.
			int[] data1 = new int[this.size];
			
			// This will be the unsorted array of ints to be used by quickSort.
			int[] data2 = new int[this.size];
			
			// This will be the unsorted array of ints to be used by countSort.
			int[] data3 = new int[this.size];

			// will keep track of the biggest & smallest element
			// biggest element is needed for countSort
			int highK = 0;
			int low = 100;
			
			// Fill the array with size and element
			for(int k = 0; k < this.size; k++){
				
				// Element being added to list
				int val = n.nextInt(this.maxElement) + 1;
				data[k] = val;	data1[k] = val;
				data2[k] = val;	data3[k] = val;
				
				// Checks if current value is higher, if so make highK = val
				// Checks if current value is lower, if so make low = val
				if(val > highK) highK = val;
				if(val < low) low = val;
			}

			

			if(i == 1){
				out.write("\nExample That Sort Works.");
				out.write("\nUnsorted Array:\n");
				out.write(printArray(data));
				
				out.write("\nSorted: \n");
			}
			
			
			// Create the different types of sorts
			MergeSort ms = new MergeSort(data1);
			CountSort cs = new CountSort(data3, highK);
			QuickSort qs = new QuickSort(data2);
			
			// Takes turns printing out the sorted array of each different type
			// of sort method.
			
			if(i == 1) {
				out.write("Merge Sort: \n");
				out.write(printArray(data1));
				out.write("\nQuick Sort: \n");
				out.write(printArray(data2));
				out.write("\nCount Sort: \n");
				out.write(printArray(data3));
			}
			
			
			
			
			s+=i + "\t" + ms.getTime() + "\t" + qs.getTime() + "\t" + cs.getTime();
			s+="\t" + low + "\t" + highK + "\t  " + size + "\n";

		}
		
		out.write("\nTime it took to sort: \n");
		out.write("      Merge   Quick   Count     Min     Max        Size\n");
		out.write(s);
		
		// closes the file.
		out.close();
	}

	/**
	 * This method prints out the array if the array size is of multiple 10, if not it calls
	 * {@link #print(int[])}.
	 * @param A the array of ints that is being printed.
	 * @return the String of printout so it can be written to file.
	 */
	private String printArray(int[]A){
		
		// checks if the length is of multiple of 10
		if(A.length%10 != 0) {
			return print(A);
		}
		
		int i = 0;
		int j = A.length/10; // # of rows
		
		String s = "";
		while(i != j){
			int j1 = j;
			for(int k = 0; k < 10; k++){
				if(k == 0) {
					// print first element of row
					s+=A[i] + "\t";
				}
				else{
					// prints the following elements of this row which will be
					// first index plus the number of j1 which is incremented each
					// time by the number of row.
					// ex. size 20 --> index  0 2 4 6 8 10 12 14 16 18
					//						  1 3 5 7 9 11 13 15 17 19 
					s+=A[j1 + i] + "\t";
					j1+=j;

				}
			}
			i++;
			s+="\n";
		}
		return s;
		
	}

	/**
	 * This print method is called if the array size is not of multiple 10.
	 * Prints out the array in ten columns.
	 * @param a array of ints that is being printed out.
	 * @return the string of the print out to be written to the file.
	 */
	private String print(int[] a) {
		String print = "";
		for(int i = 0; i < a.length; i++){
			if(i%10 == 0) print+="\n";
			print+=a[i] + " \t";
		}
		print+="\n";
		return print;
	}
	

}
