import java.io.IOException;
import java.util.Scanner;

public class program2 {
	private static double savings = 1000;
	private static double checking = 1000;

	public static void main (String [] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		
		// First checks whether pin prompt returns true or false
		if(pinPrompt()){
			int option;
			do{
				System.out.print("\033[H\033[2J");
				System.out.flush();
				// prints out main menu
				menu();
				option = sc.nextInt();
				// swicth depending on user selection
				switch(option){
					case 1:
						checkToSave();
						Thread.sleep(2000);
						break;
					case 2:
						saveToCheck();
						Thread.sleep(2000);
						break;
					case 3:
						System.out.printf("\nYour savings's account balance is $ %.2f\n", savings);
						Thread.sleep(2000);
						break;
					case 4:
						System.out.printf("\nYour checking's account balance is $ %.2f\n", checking);
						Thread.sleep(2000);
						break;
					case 5:
						withdraw();
						Thread.sleep(2000);
						break;
          case 6:
          	System.out.print("\033[H\033[2J");
						System.out.flush();
			      System.out.println("Thank you for using the ATM system.");
            break;
					default:
						System.out.println("Invalid Option. Please choose between 1-6");
						Thread.sleep(2000);
            break;
				}
			} while(option != 6);
		}else
			System.out.println("Too many illegal PINs. Try later again.");
	}
	
	// function used for the pin prompt
	public static boolean pinPrompt() throws IOException{ 
		Scanner sc = new Scanner(System.in);
		int pin, count = 0;
		System.out.println("*** Welcome to Cal Poly's ATM ***");
		// checks to see it can only be tried out 3 times
		while(count < 3){
			System.out.print("Please enter your PIN: ");
			pin = sc.nextInt();		
			if(pin == 111) return true;
			count++;
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
		return false;
	}
	
	// prints out main menu
	public static void menu(){
		System.out.println("*** Welcome to Cal Poly's ATM ***");
		System.out.println("(1) Transfer from checking account to savings account");
		System.out.println("(2) Transfer from savings account to checking account");
		System.out.println("(3) Savings account balance");
		System.out.println("(4) Checking account balance");
		System.out.println("(5) Withdraw cash from either account");
		System.out.println("(6). Exit");
		System.out.print("==> Please select option (1-6): ");
	}
	
	// transfers money from checkings to savings account
	public static void checkToSave(){
		Scanner sc = new Scanner(System.in);
		System.out.println("\nHow much would you like to transfer from checking to savings? ");
		System.out.println("[To return to the main menu enter zero]");
		System.out.print("Amount $ ");
		double amount = sc.nextDouble();
		
		if(amount == 0) return;
		// checks if amount is greater than balance or negative
		else if(amount > checking || amount < 0){
			System.out.println("Transasction not completed");
			return;
		} else{
			System.out.println("Transcation completed");
			checking-=amount;
			savings+=amount;
		}
	}

	// transfers money from saving to checkings
	public static void saveToCheck(){
		Scanner sc = new Scanner(System.in);
		System.out.println("\nHow much would you like to transfer from savings to checking? ");
		System.out.println("[To return to the main menu enter zero]");
		System.out.print("Amount $ ");
		double amount = sc.nextDouble();
		
		if(amount == 0) return;

		// checks if amount is greater than balance or negative
		else if(amount > savings || amount < 0){
			System.out.println("Transasction not completed");
			return;
		} else{
			System.out.println("Transcation completed");
			checking+=amount;
			savings-=amount;
		}
	}
	
	// withdraw money from accounts
	public static void withdraw(){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Accounts:");
		System.out.println("1. Savings");
		System.out.println("2. Checking ");
		System.out.println("[Enter any other number to return to main menu]\n");
		System.out.print("Which account would you like to withdraw from? ");
		int option = sc.nextInt();
		
		// goes to method depending on whether withdrawing from savins or checking
		switch(option){
		
			case 1:
				withdrawSave();
				break;
			case 2:
				withdrawCheck();
				break;
			default:
				return;		
		}
	}
	
	// withdrawing from savings
	public static void withdrawSave(){	
		Scanner sc = new Scanner(System.in);
		System.out.println("How much would you like to withdraw? ");
		System.out.println("[To return to the main menu enter zero]");
		System.out.print("Amount $ ");
		double amount = sc.nextDouble();
		if(amount == 0) return;
		// checks if amount is greater than balance or negative
		else if(amount > savings || amount < 0){
			System.out.println("Transasction not completed");
			return;
		} else{
			System.out.println("Transcation completed");
			savings-=amount;
		}
	}

	// withdrawing for checking
	public static void withdrawCheck(){	
		Scanner sc = new Scanner(System.in);
		System.out.println("How much would you like to withdraw? ");
		System.out.println("[To return to the main menu enter zero]");
		System.out.print("Amount $ ");
		double amount = sc.nextDouble();
		if(amount == 0) return;
		// checks if amount is greater than balance or negative
		else if(amount > checking || amount < 0){
			System.out.println("\nTransasction not completed");
			return;
		} else{
			System.out.println("\nTranscation completed");
			checking-=amount;
		}
	}
}
