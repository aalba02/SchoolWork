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

import java.io.IOException;

/**
 * This Method runs the tests for the different types of Test.
 * It calls the class test to run the tests. These tests are
 * send to files to make reading the statistics easier.
 * @author Aileen Alba
 *
 */
public class Main {

	public static void main(String [] args) throws IOException{
	
		
	    Tester t1 = new Tester(100,1000);
		t1.runTest();
		
		Tester t2 = new Tester(1000,10000);
		t2.runTest();
		
		Tester t3 = new Tester(10000,100000);
		t3.runTest();
		
		// These are extra tests that were ran.
		
//		System.out.print("\nSize: 1000\tNumbers between 0 and 1000");
//	    Tester t4 = new Tester(1000,1000);
//		t4.runTest();
//		
//		System.out.print("\nSize: 10000\tNumbers between 0 and 10000");
//		Tester t5 = new Tester(10000,10000);
//		t5.runTest();
//		
//		System.out.print("\nSize: 100000\tNumbers between 0 and 100000");
//		Tester t6 = new Tester(100000,100000);
//		t6.runTest();
		
		System.out.println("\nTest Runs Complete Check Folder for Text File");

	
	}
}
