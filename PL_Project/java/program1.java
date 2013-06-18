import java.util.*;
import java.io.File;
import java.io.*;
import java.text.*;

public class program1{
	public static String [] months = {"January", "February", "March", "April", "May", "June",
						"July", "August", "September", "October", "November", "December"};
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		int option = 0;
		do{
			System.out.print("\033[H\033[2J");
			System.out.flush();
			menu();
			option = sc.nextInt();
			switch(option){
				case 1:
					addEntry();
					Thread.sleep(3000);
					break;
				case 2:
					removeEntry();
					Thread.sleep(2000);
					break;
				case 3:
					modifyEntry();
					Thread.sleep(2000);
					break;
				case 4:
				 	viewEntry();
					Thread.sleep(4000);
					break;
				case 5:
					viewCalendar();
					Thread.sleep(4000);
					break;
				case 6:
					System.out.print("\033[H\033[2J");
					System.out.flush();
			    System.out.println("Thank you for using the Calendar");
          break;
        default:
						System.out.println("Invalid Option. Please choose between 1-6");
						Thread.sleep(2000);
            break;
			}

		} while(option != 6);
	}

	public static void menu(){
		System.out.println("Main Menu");
		System.out.println("1. Add entry in the calendar");
		System.out.println("2. Remove entry from calendar");
		System.out.println("3. Modify entry in the calendar");
		System.out.println("4. View entry by date or keyword");
		System.out.println("5. View calendar for the entire week or month");
		System.out.println("6. Exit");
		System.out.print("==> Please select option (1-6): ");
	}

	// Add Entries adds an entry to entries.txt
	public static void addEntry() throws Exception{
		Scanner sc = new Scanner(System.in);
		Scanner file = null;
		
		File f = new File("entries.txt");

		// If file doesn't exist then create it.
		if(!(f.exists()))
		{
			f.createNewFile();
		}

		// Start adding entry fields
		String newEntry = "";
		String line = "";
		String [] prompt = {"Enter a month: ", "Enter a date: ", "Enter day of the week: ",
					"Enter time (ex. 17:00-19:00 [equals 5-7pm]): ", "Enter appointment details: "};
		
		// Adding a new entry
		System.out.println("[To exit enter 0 at any time]");
		String inputDate = "";
		for(int i = 0; i < prompt.length; i++){
			if(i == 0){
				int monthInt = getMonth();
				inputDate+= "/" + monthInt + "/2011";
				line = months[monthInt-1] + "";
			}
			else if(i == 1){
				int dateInt = getDate();
				inputDate = dateInt + inputDate;
				line = dateInt + "";
			} else if (i == 2){
				line = getDayOfWeek(inputDate);
			}else{
				System.out.print(prompt[i]);
				line = sc.nextLine();
			}
			if(line.equals("0")) return;
			if(i == (prompt.length -1))
				newEntry+= line;
			else
				newEntry+= line + ":";
		}

		// Check if entry exists
		boolean entryExists = false;
		Scanner filesc = new Scanner(new File("entries.txt"));
		while(filesc.hasNext()){
			String textLine = filesc.nextLine();
			if(textLine.matches(newEntry)){
				entryExists = true;
			} 
		}

		// Append to file
		if(entryExists){
			System.out.println("\nEntry already exists");
		} else{
			try{

    	String filename= "entries.txt";
    	FileWriter fw = new FileWriter(filename,true); 
    	fw.write(newEntry);
    	fw.close();
			} catch(IOException ioe){
    		System.err.println("IOException: " + ioe.getMessage());
			}	
			System.out.print("\nNew Entry Added: " + newEntry);
		}
		
		
		

		// Sorts files
		Runtime.getRuntime().exec("sort -M entries.txt -o entries.txt");
		
		
	}

	// Method to remove an entry
	public static void removeEntry() throws Exception{
		
		int lineToRemove = getEntryLine();
		if(lineToRemove == -1) return;

		File inputFile = new File("entries.txt");
		File tempFile = new File("temp.txt");

		// Create reader and writer (which will be a new file)ls
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		int count = 1;
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
    		if(count == lineToRemove) {
   				count++;
    			continue;
    		}
    		count++;
    		writer.write(currentLine + "\n");
		}

		writer.close();
		boolean successful = tempFile.renameTo(inputFile);
	}

	// Used to Modify an entry
	public static void modifyEntry() throws Exception{
		Scanner sc = new Scanner(System.in);
		int lineToModify = getEntryLine();
		if(lineToModify == -1) return;
		File inputFile = new File("entries.txt");
		File tempFile = new File("temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		int count = 1;
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
				System.out.println(count);
    		if(count == lineToModify) {
   				count++;
   				System.out.println("Previous Entry: " + currentLine);
   				System.out.print("Enter modification (Keep Format): ");
   				String newLine = sc.nextLine();
   				writer.write(newLine + "\n");
   				continue;
    		}
    		count++;
    		writer.write(currentLine + "\n");
		}

		writer.close();
		boolean successful = tempFile.renameTo(inputFile);
		// Sorts files
		Runtime.getRuntime().exec("sort -M entries.txt -o entries.txt");
	}

	public static void viewEntry() throws Exception{

		// Check if File Exist
		File f = new File("entries.txt");
		if(!(f.exists())) {
			System.out.println("File with entires does not exist. Please make sure to create an entry first.");
			return;
		}

		// Sub menu to check how to view entries
		Scanner sc = new Scanner(System.in);
		System.out.println("1. By Date");
		System.out.println("2. By Keyword");
		int input;
		do {
			System.out.print("What would you like to view the entry by? ");
			input = sc.nextInt();
		}while(!(input == 1 || input == 2));

		if(input == 1){
			viewByDate();
		} else{
			viewByKeyword();
		}

	}

	public static void viewCalendar() throws Exception{
		// Sub menu to check how to view entries
		Scanner sc = new Scanner(System.in);
		System.out.println("1. By Week");
		System.out.println("2. By Month");
		int input;
		do {
			System.out.print("What would you like to calendar the entry by? ");
			input = sc.nextInt();
		}while(!(input == 1 || input == 2));

		Scanner display = new Scanner(new File("entries.txt"));
		if(input == 1){
			int month = getMonth();
			String week = getWeek();
			while(display.hasNext()){
				String line = display.nextLine();
				if(line.contains(months[month-1]) && line.matches(".*:" + week + ":.*")){
					System.out.println(line);
				}
			}

		}
		else if(input == 2){
			int month = getMonth();
			while(display.hasNext()){
				String line = display.nextLine();
				if(line.contains(months[month-1])){
					System.out.println(line);
				}
			}
		}

	}


	// View Entry by a Date
	public static void viewByDate() throws Exception{

		int month = getMonth();
		int date = getDate();

		String results = "";
		Scanner sc = new Scanner(new File("entries.txt"));
		while(sc.hasNext()){
			String line = sc.nextLine();
			if(line.contains(months[month-1]) && line.contains(":" + date + ":")){
				results += line + "\n";
			}
		}
		if(results.equals("")){
			System.out.println("No results found");
		} else
			System.out.println(results);

	}

	// Determine which week user wants to display
	public static String getWeek(){
		int week;
		Scanner read = new Scanner(System.in);
		do{
			System.out.print("Which week would you like to search? ");
			week = read.nextInt();
		} while(!((week > 0) && (week <= 5)));

		String weekS = "";
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

	// View entry by keyword
	public static void viewByKeyword() throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a Keyword: ");
		String key = sc.nextLine();

		Scanner read = new Scanner(new File("entries.txt"));
		String results = "";
		while(read.hasNext()){
			String line = read.nextLine();
			if(line.contains(key)){
				results += line + "\n";
			}
		}

		if(results.equals("")){
			System.out.println("No results found");
		} else
			System.out.println(results);

	}

	// This method is print lines then get users option
	public static int getEntryLine() throws Exception{
		// Check if files exists
		File f = new File("entries.txt");
		if(!(f.exists())) {
			System.out.println("File with entires does not exist. Please make sure to create an entry first.");
			return -1;
		}

		// Get number of lines
		System.out.println();
		int countLines = displayLines(f);

		// Allow user to select which line they would like to delete
		System.out.println("\n[To exit enter zero]");
		int select = whichLine(countLines);
		if(select == -1) return -1;

		return select;
	}

	// Display lines in file
	public static int displayLines(File f) throws Exception{
		Scanner sc = new Scanner(f);
		int countLines = 0;
		// Print out entries iine file
		while(sc.hasNext()){
			countLines++;
			System.out.print(countLines + ":  " + sc.nextLine() + "\n");
		}
		return countLines;
	}


	// Determine which line will be deleted
	public static int whichLine(int countLines){
		Scanner read = new Scanner(System.in);
		int select;
		do{
			System.out.print("Which line? ");
			select = read.nextInt();
			if(select == 0) return -1;
		} while(!((select > 0) && (select <= countLines)));
		return select;
	}

	// Get the month from user
	public static int getMonth(){

		Scanner sc = new Scanner(System.in);
		System.out.println("\nChoose a month: ");
		
		
		for(int i = 0; i < months.length; i++){
			System.out.println((i+1) + "\t" + months[i]);
		}

		int monthInt;

		do{
			System.out.print("What month? ");
			monthInt = sc.nextInt();
		}while(!(monthInt > 0 && monthInt < (months.length + 1)));
		return monthInt;

	}

	// Get Date from the user
	public static int getDate(){
		Scanner sc = new Scanner(System.in);
		int date;
		do{
			System.out.print("Enter a Date: ");
			date = sc.nextInt();
		} while(!(date > 0 && date <= 31));
		return date;
	}

	// Get Day of Week
	public static String getDayOfWeek(String input_date) throws Exception{
  	SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
  	Date dt1=format1.parse(input_date);
  	DateFormat format2=new SimpleDateFormat("EEEE"); 
  	String finalDay=format2.format(dt1);
  	System.out.println("Day of week: " + finalDay);
  	return finalDay;
	}
}