#!/bin/perl

$stop = 1;
@months = ("January", "February", "March", "April",
    "May", "June", "July", "August", "September",
    "November", "December");
while($stop == 1){
  system clear;
  # prompts menu for user
  menu();
  print "==> Please select option (1-6): ";
  $input = <STDIN>;
  use v5.10.1;
  # switch case depening on user's selection
  given ($input) {
    when (1) { 
      addEntry(); 
      sleep 2;
    }
    when (2) { 
      removeEntry(); 
      sleep 2;
    }
    when (3) { 
      modifyEntry();
      sleep 2; 
    }
    when (4) { 
      viewEntry();
      sleep 2;
    }
    when (5) { 
      view();
      sleep 2;
    }
    when (6) { 
      system clear; 
      print "Thank you for using the Calendar\n"; 
      $stop=0; }
    default{ system clear; print "Invalid must be bewteen (1-6)\n\n"; }
  }
}

# menu prompt
sub menu(){
  print "1. Add entry in the calendar\n";
  print "2. Remove entry from calendar\n";
  print "3. Modify entry in calendar\n";
  print "4. View entry by date or keyword\n";
  print "5. View calendar for the entire week or month\n";
  print "6. Exit\n";
}

# adding entry to the calendar
sub addEntry(){

  $entry = "";
  # Entering valid month
  $monthInt = 0;
  do{
    print "Enter month (1-12)? ";
    $monthInt = <STDIN>;
  } while(!($monthInt > 0 && $monthInt < 13));  
   chomp($monthInt);
   $entry = $entry.$months[$monthInt-1].":";

  # Entering date
  $date = 0;
  do{
    print "Enter Date: ";
    $date = <STDIN>;
  } while(!($date > 0 && $date < 32));
   chomp($date);
   $entry = $entry.$date.":";

  # Enter day of week
  print "Enter Day of Week: ";
  $day = <STDIN>;
  chomp($day);
  $entry = $entry.$day.":";

  # enter time
  print "Enter time (format 12:00-13:00): ";
  $time = <STDIN>;
  chomp($time);
  $entry = $entry.$time.":";

  # enter appointment time
  print "Enter appointment details: ";
  $details = <STDIN>;
  chomp($details);
  $entry = $entry.$details;

  # used chmop because the \n was also getting added

  # check if entry exists
  open(FILE,"entries.txt");
  if (grep{/$entry/} <FILE>){
    print "Entry exists\n";
  }else{
    open (MYFILE, '>> entries.txt');
    print MYFILE $entry, "\n";
    close MYFILE or die $!;
  }
  close FILE;

  # sort file
  system(`sort -M entries.txt -o entries.txt;`);
  
   
}

# removing entry
sub removeEntry(){
  $count = 0;
  print "\n";

  # checks whether file exists
  open (FILE, "entries.txt") or die "File with entires does not exist. Please make sure to create an entry first.";
  while (<FILE>) {
    $count++;
    chomp;
    print $count, " $_\n";
  }

  # gets the line the user wnats to delete
  $select = 0;
  do{
      print("Which line? ");
      $select = <STDIN>;
  } while(!(($select > 0) && ($select <= $count))); 

  #delete entry by savin without it
  $countLines = 0;
  open( FILE, "<entries.txt" ); 
  my @LINES = <FILE>; 
  close( FILE ); 
  open( FILE, ">entries.txt" ); 
  foreach my $LINE ( @LINES ) { 
    $countLines++;
    print FILE $LINE unless ( $countLines == $select ); 
  } 
  close( FILE ); 
}

# modify entries
sub modifyEntry(){
  $count = 0;
  print "\n";

  # check if files exists
  open (FILE, "entries.txt") or die "File doesn't exist";
  while (<FILE>) {
    $count++;
    chomp;
    print $count, " $_\n";
  }

# gets the line the user wnats to modify
  $select = 0;
  do{
      print("Which line? ");
      $select = <STDIN>;
  } while(!(($select > 0) && ($select <= $count))); 

  # saves file including modification
  $countLines = 0;
  open( FILE, "<entries.txt" ); 
  my @LINES = <FILE>; 
  close( FILE ); 
  open( FILE, ">entries.txt" ); 
  foreach my $LINE ( @LINES ) { 
    $countLines++;
    
    if( $countLines == $select ){
      print "Prev Line: ", $LINE;
      print "Enter modification (Keep Format): ";
      $newLine = <STDIN>;
      print FILE $newLine;
    } else{
      print FILE $LINE;
    }

  } 
  close( FILE ); 

  # sort file
  system(`sort -M entries.txt -o entries.txt;`);
}

# view by entry menu
sub viewEntry(){

  print "1. By Date\n";
  print "2. By Keyword\n";
  $input = -1;
  do{
    print "What would you like to view the entry by? ";
    $input = <STDIN>;
  }while(!($input == 1 || $input == 2));

  $keyword = "";
  if($input == 2){
    print "Enter Keyword: ";
    $keyword = <STDIN>;
    chomp($keyword);
  } else {
    #Keyword becomes Date
    # Entering valid month
    $monthInt = 0;
    do{
      print "Enter month (1-12)? ";
      $monthInt = <STDIN>;
    } while(!($monthInt > 0 && $monthInt < 13));  
    chomp($monthInt);
    $keyword = $keyword.$months[$monthInt-1].":";

    # Entering date
    $date = 0;
    do{
      print "Enter Date: ";
      $date = <STDIN>;
    } while(!($date > 0 && $date < 32));
    chomp($date);
    $keyword = $keyword.$date.":";

  }

  $file = "entries.txt";
  open my $fh, $file or die "Could not open $file: $!";
  my @lines = grep /$keyword/, <$fh>;
  print @lines;
}

# menu to view calendar
sub view(){
  print "1. Week\n";
  print "2. Month\n";
  
  # loop until valid input
  $input = 0;
  do{
    print "Which would you like to view the calendar for: ";
    $input = <STDIN>;
  } while(!($input == 2 || $input == 1));  
  
  $keyword = "";

  # check for valid month
  $monthInt = 0;
  do{
    print "Enter month (1-12)? ";
    $monthInt = <STDIN>;
  } while(!($monthInt > 0 && $monthInt < 13));  
  chomp($monthInt);
  $keyword = $keyword.$months[$monthInt-1].":";

  # determines week
  if($input == 1){
    
    do{
      print "Which week of the month (1-5): ";
      $weekInt = <STDIN>;
    } while(!($weekInt > 0 && $weekInt < 6));  
    
    if($weekInt == 1){
      $keyword = $keyword."[1-7]";
    }elsif($weekInt == 2){
      $keyword = $keyword."([8-9]|1[0-4])";
    } elsif($weekInt == 3){
      $keyword = $keyword."(1[5-9]|2[0-1])";
    } elsif($weekInt == 4){
      $keyword = $keyword."2[2-8]";
    } else{
      $keyword = $keyword."(29|3[0-1])";
    }

  }

  # displays entries depending on keyword (date/month)
  $file = "entries.txt";
  open my $fh, $file or die "Could not open $file: $!";
  my @lines = grep /$keyword/, <$fh>;
  print @lines;
}
