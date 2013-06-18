#!/bin/csh
set stop = 0
set months = ("January" "Februaray" "March" "April" "May" "June" "July" "August" "September" "October" "November" "December")
while ($stop == 0)
  clear
  cat << ENDOFMENU
1 : Add entry in the calendar.
2 : Remove entry from calendar
3 : Modify entry in calendar
4 : View entry by date or keyword
5 : View calendar for the entire week or month
6 : Exit
ENDOFMENU
  echo -n "==> Please select option (1-6): "
  set reply = $<
  echo ""
  switch ($reply)
    case "1":
      # Get month
      set monthInt = 0
      while (!($monthInt > 0 && $monthInt < 13))
        echo -n "Enter month (1-12): "
        @ monthInt = $<
      end
      # Get Date
      set dateInt = 0
      while(!($dateInt > 0 && $dateInt < 32))
        echo -n "Enter date: "
        @ dateInt = $<
      end
      set entry = `echo ${months[$monthInt]}:${dateInt}` 
      # Get Day of Week
      echo -n "Enter Day of Week: "
      set dayOfWeek = $<
      
      # Get Time
      echo -n "Enter time (format 12:00-13:00): "
      set tim = $< 

      #Get details
      echo -n "Enter appointment details: "
      set details = "$<"
      set entry = `echo ${months[$monthInt]}:${dateInt}:${dayOfWeek}:${tim}:${details}`
      
      set exists = `grep -c "$entry" entries.txt`

      if ( $exists == 1) then
        echo ""
        echo "File already exists"
      else
        #Add to files
        set file = `echo $entry >> entries.txt`
      endif

      # Sort entries in the end by month
      sort -M entries.txt -o entries.txt
      sleep 2 
      breaksw
    case "2":
      if(!(-e "entries.txt")) then
        echo "File with entires does not exist. Please make sure to create an entry first."
        sleep 2
        breaksw
      endif
      
      # Display lines
      set count = 0
      foreach line ( "`cat entries.txt`" )
        @ count++
        echo "$count $line"
      end
      
      # Which line to delete?
      set select = 0
      while(!(($select > 0) && ($select <= $count)))
        echo -n "Which line? "
        @ select = $< 
      end
      
      # remove specific line
      sed "${select}"d entries.txt > tmp.txt
      
      # delete old entry file
      rm -r entries.txt
      # rename entry file
      mv tmp.txt entries.txt 
      sleep 2
      breaksw
    case "3":
      if(!(-e "entries.txt")) then
        echo "File with entires does not exist. Please make sure to create an entry first."
        sleep 2
        breaksw
      endif
      
      # Display lines
      set count = 0
      foreach line ( "`cat entries.txt`" )
        @ count++
        echo "$count $line"
      end
      
      # Which line to modify?
      set select = 0
      while(!(($select > 0) && ($select <= $count)))
        echo -n "Which line? "
        @ select = $< 
      end

      set track = 0
      foreach line ( "`cat entries.txt`" )
        @ track++
        if($track == $select) then
          echo "Previous line: $line"
          echo -n "Enter modification (Keep Format): "
          set newLine = "$<"
          echo $newLine >> temp.txt
        else
          echo $line >> temp.txt
        endif
      end 

      # delete old entry file
      rm -r entries.txt
      # rename entry file
      mv temp.txt entries.txt 

      # Sort entries in the end
      sort -M entries.txt -o entries.txt

      sleep 3
      breaksw
    case "4":
      echo "1. By Date"
      echo "2. By Keyword"
      set input = -1
      
      # Get option from user  
      while(!($input == 1 || $input == 2))
        echo -n "What would you like to view the entry by? "
        @ input = $<
      end
      
      set keyword = ""
      if($input == 2) then
        echo -n "Enter Keyword: "
        set keyword = "$<"
      else
        # Get month
        set monthInt = 0
        while (!($monthInt > 0 && $monthInt < 13))
          echo -n "Enter month (1-12): "
          @ monthInt = $<
        end
        # Get Date
        set dateInt = 0
        while(!($dateInt > 0 && $dateInt < 32))
          echo -n "Enter date: "
          @ dateInt = $<
        end
        set keyword = `echo ${months[$monthInt]}:${dateInt}:`
      endif
      
      # Display the ones which match pattern 
      grep "${keyword}" entries.txt 
      sleep 2
      breaksw 
    case "5":
      echo "1. Week"
      echo "2. Month"
      
      set input = 0
      while(!($input == 2 || $input == 1))
        echo -n "Which would you like to view the calendar for: "
        set input = $<
      end

      set monthInt = 0
      # Get month
      while (!($monthInt > 0 && $monthInt < 13))
        echo -n "Enter month (1-12): "
        @ monthInt = $<
      end
      set keyword = $months[$monthInt]
      
      # If user selected week get week as well 
      if($input == 1) then
        set weekInt = -1
        while(!($weekInt > 0 && $weekInt < 6))
          echo -n "Which week of the month (1-5): "
          set weekInt = $<
        end
        if($weekInt == 1) then
          set keyword = `echo ${keyword}:"[1-7]":`
        else if($weekInt == 2) then
          echo "hey"
        else if($weekInt == 3) then
          echo "hey"
        else if($weekInt == 4) then
          echo "hey"
        else
          echo "hey"
        endif
      endif 
      echo $keyword
      grep "${keyword}" entries.txt 
      sleep 2 
      breaksw  
    case "6":
      set stop = 1
      clear
      echo "Thank you for using the Calendar"
      breaksw
    default:
      echo "Invalid Option. Please choose between 1- 6"
      sleep 2
      breaksw
  endsw
end