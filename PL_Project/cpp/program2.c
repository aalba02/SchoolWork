#include <iostream>
#include <iomanip>
using namespace std;

bool pinPrompt(void);
void menu(void);
void checkToSave(float*, float*);
void saveToCheck(float*, float*);
void withdraw(float*, float*);
void withdrawMoney(float*);

int main() {
		int option;
		// Bool will be equal to whether user knows pin
		bool proceed = pinPrompt();
		float savings, checking;
		savings = checking = 1000;

		if (proceed) {
			do {

				// Swicth which is determine by user's selection
				std::system ( "clear" );
				menu();
				cin >> option;

				switch (option) {
					case 1:
						checkToSave(&checking, &savings);
						break;
					case 2:
						saveToCheck(&checking, &savings);
						break;
					case 3:
						cout << "\nYour savings's account balance is $" << setprecision(2) << fixed << savings << "\n";
						sleep(1);
						break;
					case 4:
						cout << "\nYour checking's account balance is $" << setprecision(2) << fixed << checking << "\n";
						sleep(1);
						break;
					case 5:
						withdraw(&checking, &savings);
						break;
					case 6:
						std::system ( "clear" );
						cout << "Thank you for using the ATM system.\n";
						break;
					default:
						cout << "\nInvalid Option. Please choose between 1- 6\n";
   		 			sleep(1);
   		 			break;
					}
			} while (option != 6);
		} else {
				cout << "Too many illegal PINs. Try again later." << endl;
		}
		return 0;
}

// prompt for pin
bool pinPrompt() {
		int pin, attempt = 0;
		cout << "*** Welcome to Cal Poly's ATM ***\n";
		while (attempt < 3) {
				cout << "Please enter your PIN: ";
				cin >> pin;
				if (pin == 111) return true;
				attempt++;
				std::system ( "clear" );
		}
		return false;
}

// menu for the program
void menu() {

				cout << "***   Welcome to Cal Poly's ATM System***\n";
				cout << "(1) Transfer from checking account to savings account\n";
				cout << "(2) Transfer from savings account to checking account\n";     
				cout << "(3) Savings account balance\n";
				cout << "(4) Checking account balance\n";     
				cout << "(5) Withdraw cash from either account\n";
				cout << "(6) Exit\n";
				cout << "==> Please select an option (1-6) : ";
						
		return;
}

// Transfering money from check to saving
void checkToSave(float* checkPtr, float* savePtr) {
		float amount;

		cout << "\nHow much would you like to transfer from checking to savings?\n";
		cout << "[To return to the main menu enter zero]\n";
		cout << "Amount $ ";
		cin >> amount;
		

		if (amount == 0) {
				return;
		}
		// checks for amount entered by use to not be neg and less than balance
		else if (amount > *checkPtr || amount < 0) {
				cout << "\nTransaction not completed.";
				sleep(1);
				return;
		} else {
				*checkPtr -= amount;
				*savePtr += amount;
				cout << "\nTransaction completed\n";
				sleep(1);
		}
		return;
}
// Transfering money from saving to check
void saveToCheck(float* checkPtr, float* savePtr) {
		float amount;

		cout << "\nHow much would you like to transfer from savings to checking?\n";
		cout << "[To return to the main menu enter zero]\n";
		cout << "Amount $ ";
		cin >> amount;
		if (amount == 0) {
				return;
		}
		// checks for amount entered by use to not be neg and less than balance
		if (amount > *savePtr || amount < 0) {
				cout << "\nTransaction not completed.\n";
				sleep(1);
				return;
		} else {
				*savePtr -= amount;
				*checkPtr += amount;
				cout << "\nTransaction completed\n";
				sleep(1);
		}
		return;    
}

// withdraw menu option
void withdraw(float* checkPtr, float* savePtr) {
		int option;

		cout << "\nAccounts\n";
		cout << "1) Checking account\n";
		cout << "2) Savings account\n";
		cout << "[Enter any other number to return to main menu]\n\n";
		cout << "Which account would you like to withraw from? ";
		cin >> option;

		switch (option) {
				case 1:
						// withdraw from checking
						withdrawMoney(checkPtr);
						break;
				case 2:
						// withdraw from saving
						withdrawMoney(savePtr);
						break;
				default:
						return;
		}
		return;
}

// function to withdraw money
void withdrawMoney(float* fPtr) {
		float amount;

		cout << "\nHow much would you like to withdraw?\n";
		cout << "[To return to the main menu enter zero]\n";
		cout << "Amount $ ";
		cin >> amount;

		if (amount == 0) {
				return;
		}
		if (amount > *fPtr || amount < 0) {
				cout << "\nTransaction not completed.\n";
				sleep(1);
				return;
		} else {
				*fPtr -= amount;
				cout << "\n$" << setprecision(2) << fixed << amount << " has been withdrawn\n";
				cout << "You have $" << setprecision(2) << fixed << *fPtr << " remaining in this account.\n";
				sleep(3);
		}
		return;   
}