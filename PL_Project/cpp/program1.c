#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <iomanip>
using namespace std;

void menu(void);
void addEntry(void);
int getMonth(void);
int getDate(void);
string getDayOfWeek(int, int);
void removeEntry(void);
int getEntryLine(void);
int displayLines(void);
int whichLine(int);
void modifyEntry(void);
void viewEntry(void);
void viewByDate(void);
void viewByKeyword(void);
void viewCalendar(void);
string getWeek(void);

string months[] = {"January", "February", "March", "April", "May", "June",
						"July", "August", "September", "October", "November", "December"};
const int monthsSize = 12;
int main() {
	int option;
	do{
		std::system ( "clear" );
		menu();
		cin >> option;

		// switch case for the users option
		switch(option){
			case 1:
				addEntry();
				break;
			case 2:
				removeEntry();
				sleep(3);
				break;
			case 3:
				modifyEntry();
				sleep(3);
				break;
			case 4:
				viewEntry();
				sleep(3);
				break;
			case 5:
				viewCalendar();
				sleep(3);
				break;
			case 6:
				std::system ( "clear" );
				cout << "Thank you for using the Calendar.\n";
				break;
			default:
				cout << "\nInvalid Option. Please choose between 1- 6\n";
   		 	sleep(1);
   		 	break;
		}
	} while(option != 6);
	return 0;
}

// Displays main men
void menu(){
	cout << "Main Menu\n";
	cout << "1. Add entry in the calendar\n";
	cout << "2. Remove entry from calendar\n";
	cout << "3. Modify entry in the calendar\n";
	cout << "4. View entry by date or keyword\n";
	cout << "5. View Calendar for the entire week or month\n";
	cout << "6. Exit\n";
	cout <<"==> Please select option (1-6): ";
	return;
}

void addEntry(){
	ifstream ifile("entries.txt");
	int input;

	//Check if file exists, if not creates it
	if (!(ifile.good())) {
		ofstream a_file("entries.txt");
	}
  string line = "";
  string newEntry = "";
  int size = 5;
  string prompt[5] = {"Enter a month: ", "Enter a date: ", "Enter day of the week: ",
		"Enter time (ex. 17:00-19:00 [equals 5-7pm]): ", "Enter appointment details: "};

	// Adding entry
	cout << "[To exit enter 0 at any time after day]\n";
	int monthInt;
	int date;

	// Prompt for adding an entry
	for(int i =0 ; i < size; i++){
		if(i == 0){
			monthInt = getMonth();
			line = months[monthInt -1];
		} else if(i == 1){
			date = getDate();
			ostringstream ss;
     	ss << date;
			line = string(":") + string(ss.str());
		} else if (i == 2){
			string day = getDayOfWeek(monthInt, date);
			line = ":" + day;
			cout << "Day of the week: " << day << "\n";
		} else if (i == 3){
			string time;
			cout << prompt[i];
			cin >> time;
			line = ":" + time;
		} else{
			string app;
			cout << prompt[i];
			cin.ignore();
		  getline (cin,app);
			line = ":" + app;
		}
		newEntry+= line;
		if(line.compare(":0") == 0) return;
	}
	cout << newEntry << "\n";
	ofstream myfile;
  myfile.open ("entries.txt", ios::app );
  myfile << newEntry << "\n";
  myfile.close();

  // Sort the files 
  system("sort -M entries.txt -o entries.txt");
	sleep(4);
}

// Get month from user
int getMonth(){
	
	// Print out months
	cout << "\nChoose a month\n";
	for(int i = 0; i < monthsSize; i++){
		cout << (i+1) << " " << months[i] + "\n";
	}

	// prompt user for month selection
	int monthInt;
	do{
		cout << " ==> What month? ";
		cin >> monthInt;
	} while(!(monthInt > 0 && monthInt < (monthsSize+ 1)));

	return monthInt;
	
}

// Gets date from user, must be in a certain range
// Not perfect doesn't check for days in month
int getDate(){
	int date;
	do{
		cout << "Enter a Date: ";
		cin >> date;
	} while(!(date > 0 && date <= 31));
	return date;
}

// Gets the day  of the week given the month and date
string getDayOfWeek(int month, int date){
	tm timeStruct = {};
	timeStruct.tm_year = 2011 - 1900;
	timeStruct.tm_mon = month - 1;
	timeStruct.tm_mday = date;
	timeStruct.tm_hour = 12;    //  To avoid any doubts about summer time, etc.
	mktime( &timeStruct );
	int day = timeStruct.tm_wday;
	if(day == 0)
		return "Sunday";
	else if(day == 1)
		return "Monday";
	else if(day == 2)
		return "Tuesday";
	else if(day == 3)
		return "Wednesday";
	else if(day == 4)
		return "Thursday";
	else if(day == 5)
		return "Friday";
	else
		return "Saturday";
}

void removeEntry(){
	int lineToRemove = getEntryLine();
	if(lineToRemove == -1) return;
	string line;
  // open input file
  ifstream in("entries.txt");

  // now open temp output file
  ofstream out("tmp.txt");
  
  int count = 0;
  // loop to read/write the file.  Note that you need to add code here to check
  // if you want to write the line
  while( getline(in,line) )
  {
  	count++;
    if(count != lineToRemove)
    	out << line << "\n";
  }
  in.close();
  out.close();    
  // delete the original file
  remove("entries.txt");
  // rename old to new
  rename("tmp.txt","entries.txt");
}

int getEntryLine(){
	ifstream ifile("entries.txt");
	if (!(ifile.good())) {
		cout << "File with entries doesn't exist. Please make sure to create an entry first\n";
		return -1;
	}

	// Get number of lines
	cout << "\n";
	int countLines = displayLines();

	cout << "\n[To exit enter zero]\n";
	int select = whichLine(countLines);
	if(select == -1) return -1;

	return select;

}

// Display the lines in the file
int displayLines(){
	ifstream myfile("entries.txt");
	string line;
	int count = 0;

	if (myfile.is_open())
  {
    while ( myfile.good() )
    {
    	count++;
      getline (myfile,line);
      if(!(line.empty()))
      	cout << count << " " << line << endl;
    }
    myfile.close();
  }

  return count;
}

// Determine which line to delete
int whichLine(int countLines){
	int select;
	do{
		cout << "Which line? ";
		cin >> select;
		if(select == 0) return -1;
	} while(!((select > 0) && (select <= countLines)));
	return select;
}

// Used to modify entry
void modifyEntry(){
	int lineToRemove = getEntryLine();
	if(lineToRemove == -1) return;
	string line;
  // open input file
  ifstream in("entries.txt");

  // now open temp output file
  ofstream out("tmp.txt");
  
  int count = 0;
  // Decide which line will change
  while( getline(in,line) )
  {
  	count++;
    if(count != lineToRemove)
    	out << line << "\n";
    else{
    	string newline;
    	cout << "Previous Line: " << line << endl;
    	cout << "Enter modification (Keep Format): ";
    	cin.ignore();
    	getline(cin,newline);
    	out << newline << "\n";
    }
  }
  in.close();
  out.close();    
  // delete the original file
  remove("entries.txt");
  // rename old to new
  rename("tmp.txt","entries.txt");

  // Sort the files 
  system("sort -M entries.txt -o entries.txt");
}

// Menu to view entry by date or keyword
void viewEntry(){
	ifstream myFile("entries.txt");
	if (!(myFile.good())) {
		cout << "File with entries doesn't exist. Please make sure to create an entry first\n";
		return;
	}

	cout << "1. By Date" << endl;
	cout << "2. By Keyword" << endl;

	int input;

	// Loop until valid input
	do{
		cout << "What would you like to view the entry by? ";
		cin >> input;
	}while(!(input == 1 || input == 2));

	if(input == 1){
		viewByDate();
	} else{
		viewByKeyword();
	}
}

// function when user wants to view by date
void viewByDate(){
	int month = getMonth();
	int date = getDate();
	ostringstream ss;
  ss << date;
  string dateS = ":" + ss.str() + ":";
	string results = "";
	string line;
	ifstream myfile ("entries.txt");
  if (myfile.is_open())
  {
    while ( myfile.good() )
    {
      getline (myfile,line);
      // If month and date match up
      if((line.find(months[month-1]) == 0) && 
      		(line.find(dateS) == months[month-1].length())){
      	cout << line << endl;
      }
    }
    myfile.close();
  }
}

// function when user wants to view by date
void viewByKeyword(){
	string keyword;
	cout << "Enter keyword: ";
	cin >> keyword;

	string results = "";
	string line;
	ifstream myfile ("entries.txt");
  if (myfile.is_open())
  {
    while ( myfile.good() )
    {
      getline (myfile,line);
      // If keyword exists in line
      if(line.find(keyword) < line.length())
      	cout << line << endl;
    }
    myfile.close();
  }
}

// Menu to view Calendar
void viewCalendar(){
	cout << "1. By Week" << endl;
	cout << "2. By Month" << endl;

	int input;

	do {
			cout << "What would you like to calendar the entry by? ";
			cin >> input;
	} while(!(input == 1 || input == 2));

	// If user wants to view by week
	if(input == 2){
		int month = getMonth();
		string line;
		ifstream myfile ("entries.txt");
  	if (myfile.is_open())
  	{
    	while ( myfile.good() )
    	{
      	getline (myfile,line);
      	// If month and date match up
      	if(line.find(months[month-1]) == 0)
      		cout << line << endl;
    	}
    	myfile.close();
  	}
	} else if (input == 1){
		int month = getMonth();
		string line;
		string week = getWeek();
		ifstream myfile ("entries.txt");
  	if (myfile.is_open())
  	{
    	while ( myfile.good() )
    	{
      	getline (myfile,line);
      	// If month and date match up
      	if((line.find(months[month-1]) == 0) && 
      		(line.find("1") == months[month-1].length() + 1))
      		cout << line << endl;
    	}
    	myfile.close();
  	}
	}
}

// Not perfect but determines week of month
string getWeek(){
	int week;
	do{
			cout << "Which week from that month? ";
			cin >> week;
	} while(!((week > 0) && (week <= 5)));
	string weekS = "";
	if(week == 1){
		weekS = "[1-7]";
	}else if (week == 2){
		 weekS = "[8-14]";
	} else if(week == 3){
		 weekS = "[15-21]";
	} else if(week == 4){
		 weekS = "[22-28]";
	} else
		 weekS = "[29-31]";
	return weekS;
}