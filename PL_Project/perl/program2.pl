#!/bin/perl

#initial balance
$checking = 1000.00;
$saving = 1000.00;

$welcome="*** Welcome to Cal Poly' ATM ***";

print $welcome, "\n";

$count=1;
# Checking for pin here
while (1) {
  # Get user input
  print "Please enter your PIN: ";
  $input=<STDIN>;

  #if it equals the pin then exit
  if($input == 111){  last; }
  system clear;
  if( $count == 3){
    print "Too many illegal PINs. Try later again.\n";
    exit 1;
  }
  $count++;
  
}

system clear;

# while loop for menu
$stop=0;
while($stop == 0){
  menu();
  print "==> Please select option (1-6): ";
  $input = <STDIN>;
  use v5.10.1;
  given ($input) {
    when (1) { checkToSave(); }
    when (2) { saveToCheck();}
    when (3) { 
      print "Saving's account balance: \$ $saving \n"; 
      sleep 1;}
    when (4) { 
      print "Checking's account balanace: \$ $checking \n";
      sleep 1; }
    when (5) { withdraw();
      sleep 1;}
    when (6) { $stop=1; }
    default       { }
  }
  system clear;
}

print "Thank you for using the ATM System.\n";

# Main Menu
sub menu(){
  print "$welcome\n";
  print "(1) Transfer from checking account to savings account\n";
  print "(2) Transfer from savings account to checking account\n";
  print "(3) Savings account balance\n";
  print "(4) Checkings account balance\n";
  print "(5) Withdraw Cash from either account\n";
  print "(6) Exit\n";
}

# Transfer from checkings to savings
sub checkToSave(){
  $amount = getInput("transfer");
  if($amount == 0){
    return;
  }
  # check if amount is greater than balance or neg 
  elsif( $amount > $checking || $amount < 0) {
    print "Transcation not completed\n";
    sleep 1;
  }else{
    print "Transcation completed\n";
    $checking = $checking - $amount;
    $saving = $saving + $amount;
    sleep 1;
  }
}

# Transfer from savings to checkings
sub saveToCheck(){
  $amount = getInput("transfer");
  if($amount == 0){
    return;
  }
  # check if amount is greater than balance or neg 
  elsif( $amount > $saving || $amount < 0) {
    print "Transcation not completed\n";
    sleep 1;
  }else{
    print "Transcation completed\n";
    $checking = $checking + $amount;
    $saving = $saving - $amount;
  }
}

#Withdraw from either account
sub withdraw(){

  print "\nAccounts:\n";
  print "1. Savings Account\n";
  print "2. Checking Account\n";
  print "[Enter any other number to return to main menu]\n\n";
  print "Which account would you like to withraw from? ";
  $account = <STDIN>;

  if( $account == 1){
    $amount = getInput("withdraw");

    # check if amount is greater than balance or neg 
    if($amount > $saving || $amount < 0){
      print "Transaction not completed\n";
    } else{
      $saving = $saving - $amount;
      print "\nTransaction completed\n";
      print "\$$amount has been withdrawn\n";
      print "You have \$$saving remaining in this account\n";
      sleep 2;
    }
  } elsif($account == 2){
    $amount = getInput("withdraw");
    if($amount > $checking || $amount < 0){
      print "Transaction not completed\n";
    } else{
      $checking = $checking - $amount;
      print "\nTransaction completed\n";
      print "\$$amount has been withdrawn\n";
      print "You have \$$saving remaining in this account\n";
      sleep 2;
    }
  } else{
    return;
  }
  sleep 1;
}

# Get input from user
sub getInput(){
  print "\nHow much money would you like to @_? \n";
  print "[To return to the main menu enter zero]\n";
  print "Amount \$ ";
  $amount = <STDIN>;
  return $amount;
}
