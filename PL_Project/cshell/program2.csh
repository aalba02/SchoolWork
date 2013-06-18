#!/bin/csh
set welcome = "*** Welcome to Cal Poly's ATM ***"
echo "$welcome"

# This section sets up the pin verifcation
set count = 0
while( $count != 3)
  echo -n "Please enter your PIN: "
  set pin = $<

  # Checking if pin == 111, is so break out of the while
  if( $pin == "111") break
  @ count ++                  # incremement count
  clear                       # clear screen

  # check to see if use has tried 3 times
  if($count == 3) then
    echo "Too many illegal PINs. Try later again."
    exit 1
  endif
end

set saving = 1000.00
set checking = 1000.00

# If user succesfully enters the pin they continue here
set stop = 0
while ($stop == 0)
  clear
# Main menu
  echo "$welcome"
  cat << ENDOFMENU
(1) Transfer from checking account to saving account
(2) Transfer from savings account to checking account
(3) Savings account balance
(4) Checking account balance
(5) Withdraw Cash from either account
(6) Exit
ENDOFMENU
  echo -n "==> Please select option (1-6): "
  set reply = $<
  echo ""
  switch ($reply)
    
    # Option 1: Allow user to transfer money from checkings to savings
    case "1":
      echo "How much would you like to transfer from checking to savings?"
      echo "[To return to the main menu enter zero]"
      echo -n "Amount $ "
      set amount = $<

      # Check to see if amount is negative, amount is gt checking or equal zero
      set ng = `echo "$amount < 0" | bc` 
      set gtn = `echo "$amount > $checking" | bc` 
      set equal = `echo "$amount == 0" | bc` 
      # Check that the amount is not more than checkings amount
      if ( $equal == 1 ) then
        breaksw
      else if( $gtn == 1 || $ng == 1 ) then
        echo "Transaction not completed"
        sleep 1
      else
        echo "Transaction completed"
        sleep 1
        # Do calculation
        set saving = `echo "$saving + $amount" | bc -l`
        set checking = `echo "$checking - $amount" | bc -l`
      endif
      breaksw  

    # Option 2: Allow user to transfer money from savings to checkings
    case "2":
      echo "How much would you like to transfer from savings to checking?"
      echo "[To return to the main menu enter zero]"
      echo -n "Amount $ "
      set amount = $<

      # Check to see if amount is negative, amount is gt savings or equal zero
      set ng = `echo "$amount < 0" | bc` 
      set gtn = `echo "$amount > $checking" | bc` 
      set equal = `echo "$amount == 0" | bc` 
      if ( $equal == 1 ) then
        breaksw
      else if( $gtn == 1 || $ng == 1 ) then
        echo "Transaction not completed"
        sleep 1
      else
        echo "Transaction completed"
        sleep 1
        set saving = `echo "$saving - $amount" | bc -l`
        set checking = `echo "$checking + $amount" | bc -l`
      endif
      breaksw 

    # Display the saving's account balance
    case "3":
      echo "Savings account balance: $ $saving"
      sleep 1
      breaksw 

    # Display the checking's account balance
    case "4":
      echo "Checkings account balance: $ $checking" 
      sleep 1
      breaksw 

    # Option 5: Withdraw cash from accounts
    case "5":

cat << ACCOUNTS
Accounts:
1. Savings Account
2. Checking Account
[Enter any other number to return to main menu]
ACCOUNTS
      echo ""
      echo -n "Which account would you like to withdraw from? "
      set account = $<

      # Withdraw menu only presented if a valid account is choosen
      if($account == 1 || $account == 2) then
        echo "How much would you like to withraw?"
        echo "[To return to the main menu enter zero]"
        echo -n "Amount $ "
        set amount = $<
      endif

      # Check if amount is zero or less than zero
      set ng = `echo "$amount < 0" | bc` 
      set zero = `echo "$amount == 0" | bc` 

      # If users selects to withdraw from savings
      if( $account == 1 && $zero == 0) then
        # Check that there are sufficient fund to withdraw
        set gt = `echo "$amount $saving" | awk '{if ($1 > $2) print "t"; else print "f"}'`
        if( $gt == "t" || $ng == 1) then
          echo "Transaction not completed"
          sleep 1
        else
          set saving = `echo "$saving - $amount" | bc -l`
          echo "$ $amount has been withdrawn"
          echo "You have $ $saving remaining in this account"
          sleep 1
        endif
      
      # If users selects tto withdraw from $checking
      else if ( $account == 2 && $zero == 0) then 
        set gt = `echo "$amount $checking" | awk '{if ($1 > $2) print "t"; else print "f"}'`
        # Check that there are sufficient fund to withdraw
        if ( $gt == "t" || $ng == 1) then
          echo "Transcation not completed" 
          sleep 1
        else  
          set checking = `echo "$checking - $amount" | bc -l`
          echo "$ $amount has been withdrawn"
          echo "You have $ $checking remaining in this account"
          sleep 1
        endif
      
      # Any other option than showed
      else
        echo ""
      endif
      breaksw 
    case "6":
      set stop = 1
      clear
      echo "Thank you for using the ATM system." 
      breaksw

    default:
      breaksw
  endsw
end
