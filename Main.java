//Carson Lutterloh
//cjl150530
//CS 2336.003

import UserInfoObject.*;
import LinkList.*;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Main {
    public static final int LIST_ROW_START = 1;
    public static final int LIST_COLUMN_START = 1;
    public static void main(String[] args) throws IOException {
        //Initialize all lists
        boolean notReservedList = false;    //This allows the readTheaterToList function to know whether # or . should be read
        boolean reservedList = true;
        //Auditorium 1
        File theater1 = new File("A1.txt");
        checkFile(theater1);
        int columnCountAudOne = findColumnCount(theater1);
        int rowCountAudOne = findRowCount(theater1);
        LinkedList audOneAvailable = readTheaterToList(theater1, notReservedList);
        LinkedList audOneReserved = readTheaterToList(theater1, reservedList);
        //Auditorium 2 
        File theater2 = new File("A2.txt");
        checkFile(theater2);
        int columnCountAudTwo = findColumnCount(theater2);
        int rowCountAudTwo = findRowCount(theater2);
        LinkedList audTwoAvailable = readTheaterToList(theater2, notReservedList);
        LinkedList audTwoReserved = readTheaterToList(theater2, reservedList);
        //Auditorium 3
        File theater3 = new File("A3.txt");
        checkFile(theater3);
        int columnCountAudThree = findColumnCount(theater3);
        int rowCountAudThree = findRowCount(theater3);
        LinkedList audThreeAvailable = readTheaterToList(theater3, notReservedList);
        LinkedList audThreeReserved = readTheaterToList(theater3, reservedList);
        
        //Reading file into hashmap
        File database = new File("userdb.dat");
        checkFile(database);
        HashMap<String, UserInfo> login = new HashMap<>();
        readDBFile(database, login);
        
        //Get user login
        Scanner input = new Scanner(System.in);
        boolean adminLogin = false;
        do {    //This continues until the admin logs on
            UserInfo currentUser = requestLogin(input, login);  //THIS IS THE MONEY MAKER! (AKA, how we add orders)
//If the admin logs on
            if (currentUser.getUsername().equals("admin"))  {
                adminLogin = true;
                System.out.println("Welcome admin!");
                int choice;
                int choiceTwo;
                do {
                    displayAdminMenu();
                    choice = getInt(input);
                    switch (choice) {
                        case 1: //View Auditorium - After one is selected, the program immediately loops back to the main menu
                            displayAuditoriumMenu();
                            choiceTwo = getInt(input, 3);
                            switch (choiceTwo) {
                            case 1: System.out.println("Displaying Auditorium 1...");
                                displayAuditorium(audOneAvailable, audOneReserved, columnCountAudOne);
                                continue;
                            case 2: System.out.println("Displaying Auditorium 2...");
                                displayAuditorium(audTwoAvailable, audTwoReserved, columnCountAudTwo);
                                continue;
                            case 3: System.out.println("Displaying Auditorium 3...");
                                displayAuditorium(audThreeAvailable, audThreeReserved, columnCountAudThree);
                                continue;
                            default: System.out.println("Error, invalid input");
                                break;
                        }
                        case 2: //Print Report 
                            //PRINT REPORT
                            int takenSeatsOne = audOneReserved.countNodes();    //The class handles null exceptions. It will just return 0
                            int takenSeatsTwo = audTwoReserved.countNodes();
                            int takenSeatsThree = audThreeReserved.countNodes();
                            System.out.println("\nTicket results for Guardians of the Galaxy 2 (The last 4 columns are only for this session)");
                            System.out.printf("%-15s%-15d%-15d%-15d%-15d%-15d$%-15.2f\n", "Auditorium 1", audOneAvailable.countNodes(), takenSeatsOne, currentUser.getOverallAdult(1), currentUser.getOverallSenior(1), currentUser.getOverallChild(1), currentUser.getOverallAuditoriumSales(1));
                            System.out.printf("%-15s%-15d%-15d%-15d%-15d%-15d$%-15.2f\n", "Auditorium 2", audTwoAvailable.countNodes(), takenSeatsTwo, currentUser.getOverallAdult(2), currentUser.getOverallSenior(2), currentUser.getOverallChild(2), currentUser.getOverallAuditoriumSales(2));
                            System.out.printf("%-15s%-15d%-15d%-15d%-15d%-15d$%-15.2f\n", "Auditorium 3", audThreeAvailable.countNodes(), takenSeatsThree, currentUser.getOverallAdult(3), currentUser.getOverallSenior(3), currentUser.getOverallChild(3), currentUser.getOverallAuditoriumSales(3));
                            System.out.printf("%-15s%-15d%-15d%-15d%-15d%-15d$%-15.2f\n", "Total", (audOneAvailable.countNodes() + audTwoAvailable.countNodes() + audThreeAvailable.countNodes()), (takenSeatsOne + takenSeatsTwo + takenSeatsThree),
                                currentUser.getOverallAdult(), currentUser.getOverallSenior(), currentUser.getOverallChild(), (currentUser.getOverallAuditoriumSales(1) + currentUser.getOverallAuditoriumSales(2) + currentUser.getOverallAuditoriumSales(3)));
                            break;
                        case 3: //Exit - Write files back to their files
                            System.out.println("Exiting...");
                            break;   //If the user chooses three, this goes to the end of the do-while statement, fails the 'while,' then prints out results
                        default: System.out.println("Error, invalid input");
                            break;   //If the user's choice is not 1-3, they get to enter another number 
                    }
                }   while (choice != 3);
            }
            //If anyone other than the admin logs in
            else    {
                System.out.println("Welcome valued customer!");
                int choice;
                int choiceTwo;
                int columnCount;    //The following 3 lines and this are all used to store different values depending on the user's selection of 1-3
                int rowCount;
                LinkedList audAvailable;
                LinkedList audReserved;
                //For modifyOrders
                int[] allCounts = {rowCountAudOne, rowCountAudTwo, rowCountAudThree, columnCountAudOne, columnCountAudTwo, columnCountAudThree};
                LinkedList[] allLists = {audOneAvailable, audTwoAvailable, audThreeAvailable, audOneReserved, audTwoReserved, audThreeReserved};
                do {
                    displayUserMenu();
                    choice = getInt(input);
                    //Switch with options 1-5
                    switch (choice) {
                        case 1: //Reserve Seats
                        displayAuditoriumMenu();
                        choiceTwo = getInt(input, 3);
                        switch (choiceTwo) {
                            case 1: 
                                displayAuditorium(audOneAvailable, audOneReserved, columnCountAudOne);
                                columnCount = columnCountAudOne;
                                rowCount = rowCountAudOne;
                                audAvailable = audOneAvailable;
                                audReserved = audOneReserved;
                                break;
                            case 2: 
                                displayAuditorium(audTwoAvailable, audTwoReserved, columnCountAudTwo);
                                columnCount = columnCountAudTwo;
                                rowCount = rowCountAudTwo;
                                audAvailable = audTwoAvailable;
                                audReserved = audTwoReserved;
                                break;
                            case 3: 
                                displayAuditorium(audThreeAvailable, audThreeReserved, columnCountAudThree);
                                columnCount = columnCountAudThree;
                                rowCount = rowCountAudThree;
                                audAvailable = audThreeAvailable;
                                audReserved = audThreeReserved;
                                break;
                            default: System.out.println("Error, invalid input");
                                continue;
                        }
                            break;
                        case 2: //View Order
                            displayUserOrder(currentUser);
                            continue; //Skips all code following the switch statement and loops to the top
                        case 3: //Update Order
                            boolean ordersExist = displayUserOrder(currentUser);
                            if (ordersExist)
                                modifyOrder(currentUser, input, allLists, allCounts);
                            continue; //Skips all code following the switch statement and loops to the top
                        case 4: //Display Receipt
                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Receipt for username: " + currentUser.getUsername());
                            displayUserOrder(currentUser);
                            System.out.printf("Your total amount spent on all orders is $%.2f\n", currentUser.getOverallSales());
                            System.out.println("-------------------------------------------------------------");
                            continue; //Skips all code following the switch statement and loops to the top
                        case 5: //Log Out
                            System.out.println("Logging out...");
                            continue; //Skips all code following the switch statement and loops to the top
                        default: System.out.println("Error, invalid input");
                            continue; //If the user's choice is not 1-3, they get to enter another number 
                    }
                    //THE USER ONLY GETS HERE IF THEY HAVE SELECTED TO RESERVE A SEAT
                    //Get adult tickets
                    String adult = "adult";
                    String senior = "senior";
                    String child = "child";
                    
                    //Get number of tickets for each type
                    int adultTickets = getTicketQuantity(adult, audAvailable.countNodes(), input);
                    int seniorTickets = getTicketQuantity(senior, audAvailable.countNodes() - adultTickets, input);
                    int childTickets = getTicketQuantity(child, audAvailable.countNodes() - adultTickets - seniorTickets, input);
                    if (adultTickets + seniorTickets + childTickets == 0) {
                        System.out.println("This message is confirming you did not purchase any tickets");
                        continue;   //If a user enters 0, they exit the auditorium without buying a ticket. This skips the rest of the do-while
                    }

                    //Get row and column for each individual adult seat
                    int totalTickets = adultTickets + seniorTickets + childTickets;
                    int[][] orderSeats = new int[totalTickets][2];
                    getSeats(totalTickets, orderSeats, rowCount, columnCount, input);
                    
                    //Check if seats are all available
                    boolean available = true;
                    for (int i = 0; i < orderSeats.length; i++) {
                        available = audAvailable.exists(orderSeats[i][0], orderSeats[i][1], 1); //The class method will deal with a null pointer, so it is okay
                        //TEST PRINT System.out.println("Are the seats available? " + available);
                        if (!available) {
                            System.out.println("The seats are not all available");
                            i = orderSeats.length;
                        }
                    }
                    //Reserve seats if all available and create a user order
                    if (available) {
                        for (int i = 0; i < orderSeats.length; i++) {
                            reserveSeats(orderSeats[i][0], orderSeats[i][1], 1, audAvailable, audReserved);
                        }
                        createUserOrder(currentUser, adultTickets, seniorTickets, childTickets, choiceTwo, orderSeats);
                    }
                    //Find the best available seats
                    else {  //If the seats are not all available!
                        int bestAvailableIndex[] = searchBestAvailable(rowCount, columnCount, totalTickets, audAvailable);  //Returns row and column in array
                        if (bestAvailableIndex[0] == -1) {  //If the best available seat row is still -1, there is no best seat
                            System.out.println("There is no best available seat (That quantity cannot be seated together in this auditorium). We apologize.");
                        }
                        else {
                            if (totalTickets > 1)
                                System.out.println("Your choice was unavailable. Would you like to book the seat(s) at row " + bestAvailableIndex[0] + ", columns " + bestAvailableIndex[1] + "-" + (bestAvailableIndex[1] + totalTickets - 1) + "?");
                            else
                                System.out.println("Your choice was unavailable. Would you like to book the seat(s) at row " + bestAvailableIndex[0] + ", column " + bestAvailableIndex[1] + "?");
                            System.out.println("(If yes, type Y, if no, type N)"); 
                            char check = Character.toUpperCase(input.next().charAt(0)); //Evaluates the first character a person enters (They can put "Yes" if they want)
                            input.nextLine();   //This keeps anything after the char from being used as input later
                            while (check != 'Y' && check != 'N') {
                                System.out.println("Please try again with a Y or N");
                                check = Character.toUpperCase(input.next().charAt(0));
                                input.nextLine();
                            }
                            if (check == 'Y')   {
                                int[][]bestSeats = new int[totalTickets][2];
                                for (int i = 0; i < totalTickets; i++) {
                                    bestSeats[i][0] = bestAvailableIndex[0];
                                    bestSeats[i][1] = bestAvailableIndex[1] + i;
                                }
                                for (int i = 0; i < bestSeats.length; i++) {
                                    reserveSeats(bestSeats[i][0], bestSeats[i][1], 1, audAvailable, audReserved);
                                }
                                createUserOrder(currentUser, adultTickets, seniorTickets, childTickets, choiceTwo, bestSeats);
                            }
                            else
                                System.out.println("Sorry about that. Thank you for using our system!");
                        }
                    }
                    //We are using two lists to handle ALL of our cases depending on the choice, 1-3. If the copy gets changed, the original array has to be changed too.
                    switch (choiceTwo) {
                        case 1:
                            audOneAvailable = audAvailable;
                            audOneReserved = audReserved;
                            break;
                        case 2:
                            audTwoAvailable = audAvailable;
                            audTwoReserved = audReserved;
                            break;
                        case 3:
                            audThreeAvailable = audAvailable;
                            audThreeReserved = audReserved;
                            break;
                        default:
                            break;
                    }
                }   while (choice != 5);
            }
        }  while (!adminLogin);
        //THE FOLLOWING EXECUTES ONLY AFTER THE ADMIN HAS HIT 'EXIT'
        try ( //Print 1 to file
            PrintWriter output1 = new PrintWriter(theater1)) {
            printAuditorium(audOneAvailable, rowCountAudOne, columnCountAudOne, output1, LIST_ROW_START, LIST_COLUMN_START);
        }
        try ( //Print 2 to file
            PrintWriter output2 = new PrintWriter(theater2)) {
            printAuditorium(audTwoAvailable, rowCountAudTwo, columnCountAudTwo, output2, LIST_ROW_START, LIST_COLUMN_START);
        }
        try ( //Print 3 to file
            PrintWriter output3 = new PrintWriter(theater3)) {
            printAuditorium(audThreeAvailable, rowCountAudThree, columnCountAudThree, output3, LIST_ROW_START, LIST_COLUMN_START);
        }
    }
    
    public static void checkFile (File filename) {
        if (!filename.exists()) {
                System.out.println("Error! The file " + filename + " does not exist in the directory, but is necessary to continue. Exiting...");
                System.exit(-1);
            }
    }
    
    //Reads the database file into a HashMap
    public static void readDBFile (File filename, HashMap<String, UserInfo> hashmap) throws IOException{
        String s;
        try (Scanner lineReader = new Scanner(filename)) {
            //Reads in each line of the file and separates it by spaces
            while(lineReader.hasNext()) {
                s = lineReader.nextLine();
                if (s.length() > 0) {   //Ensures non-empty lines aren't read
                    String[] words = s.split("[\\s+]");
                    if (words.length == 2)  {
                        //Creates an instance of UserInfo and makes that the value in our hashmap
                        UserInfo temp = new UserInfo(words[0], words[1]);
                        if (!hashmap.containsKey(words[0])) {
                            hashmap.put(words[0], temp);
                        }
                        else 
                            System.out.println("There was a duplicate username (" + words[0] + ") in " + filename + " that will not be added to the system twice");
                    }
                    else {
                        System.out.println("Error! Your " + filename + " file contains errors. (More than one username/password on a line");
                        System.exit(-1);
                    }
                }
            }
        }
    }
    
    //Asks for username/password and verifies it with existing database
    public static UserInfo requestLogin (Scanner input, HashMap<String, UserInfo> login) {
        boolean loggedIn = false;
        String username;
        String password;
        UserInfo temp = null;
        //Continues until a successful username AND password have been entered
        while (!loggedIn) {
            //Get username: if correct, continue. If not correct, come back here.
            System.out.print("Username: ");
            username = input.nextLine();
            if (login.containsKey(username))
                temp = login.get(username);
            else {
                System.out.println("That username does not exist in our system");
                continue;
            }
            int attempts = 0;
            //Give three attempts at entering a password before jumping back to username
            while (attempts != 3) {
                System.out.print("Password: ");
                password = input.nextLine();
                if (temp.getPassword().equals(password)) {
                    loggedIn = true;
                    attempts = 3;
                }
                else {
                    System.out.println("Wrong username/password combination. You have used try " + (attempts + 1) + " of 3");
                    attempts++;
                }
            }
        }
        return temp;
    }
    
    public static LinkedList readTheaterToList(File fileName, boolean reserved) throws IOException {
        String line;
        LinkedList list = new LinkedList();
        try (
            Scanner lineReader = new Scanner(fileName);
        ) {
            //Write the information to an array
            int i = 1;
            while (lineReader.hasNext()) {
                line = lineReader.nextLine();
                if (line.length() > 0) {    //This ensures that an empty line is not looked at
                    for (int j = 0; j < line.length(); j++) {
                        //Makes list for reserved seats
                        if (reserved && line.charAt(j) == '.') {  //Because we start j at 1, we must check the character 1 before it
                            DoubleLinkNode seat = new DoubleLinkNode(i, j + 1); //Our columns start at 1, so
                            list.addNode(seat);
                        }
                        //Makes list for available seats
                        else if (!reserved && line.charAt(j) == '#') {
                            DoubleLinkNode seat = new DoubleLinkNode(i, j + 1);
                            list.addNode(seat);
                        }
                    }
                    i++;
                }
            } 
        }
        return list;
    }
    
    public static int findColumnCount(File fileName) throws IOException {
        String line;
        int characters = 0;
        try (
            Scanner lineReader = new Scanner(fileName);
        ) {
            if (!lineReader.hasNext())
                return 0;
            line = lineReader.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) != ' ')  //This takes out any empty characters in the first line of the file to find the number of seats
                    characters++;
            }
        }
        return characters;
    }
    
    public static int findRowCount(File fileName) throws IOException {
        String line;
        int count;
        try (
            Scanner lineReader = new Scanner(fileName);
        ) {
            if (!lineReader.hasNext())
                return 0;
            count = 0;
            while (lineReader.hasNext()) {
                line = lineReader.nextLine();
                if (line.length() > 0) {    //This ensures that an empty line is not looked at
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void displayAdminMenu() {
        System.out.println("What would you like to do?");
        System.out.print("1. View Auditorium\n"
                + "2. Print Report\n"
                + "3. Exit\n");
    }
    
    public static void displayUserMenu() {
        System.out.println("What would you like to do?");
        System.out.print("1. Reserve Seats\n"
                + "2. View Orders\n"
                + "3. Update Order\n"
                + "4. Display Receipt\n"
                + "5. Log Out\n");
    }
    
    public static int getInt(Scanner input) throws InputMismatchException{
        int value = -1;
        boolean bad = true;
        do
        {
            try {
                value = input.nextInt();
                if (value != -1 && input.nextLine().equals(""))
                    bad = false;
                else
                    System.out.println("Please enter only one integer");
            }   catch (InputMismatchException ex)   {
                System.out.println("Incorrect entry. Please input only a positive integer.");
                input.nextLine();
            }
        } while(bad);
        return value;
    }
    //Only used with switches of 1-3, so value must be greater than 0 (We want to give the user less freedom here than with the method above
    public static int getInt(Scanner input, int max) throws InputMismatchException {    
        int value = -1;
        boolean bad = true;
        do
        {
            try {
                value = input.nextInt();
                if (value > 0 && value <= max && input.nextLine().equals(""))
                    bad = false;
                else
                    System.out.println("Please enter a valid number");
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect entry. Please input only a positive integer.");
                input.nextLine();
            }
        } while(bad);
        return value;
    }
    
    public static void displayAuditorium(LinkedList available, LinkedList reserved, int columnCount) {
        System.out.print(" ");
        for (int i = 0; i < columnCount; i++)
            System.out.print((i + 1) % 10); //Prints the top row of integers 0-9
        
        DoubleLinkNode curAvailable = available.getHead();
        DoubleLinkNode curReserved = reserved.getHead();
        
        int i = 0; //Counts through all the rows of the array
            for (int j = 0; j <= columnCount; j++) { //array[0].length is the column count
                if (j == 0) {
                    System.out.print("\n" + (i+1));
                    i++;
                }
                else {
                    //If there's another node
                    if (curAvailable != null || curReserved != null) {
                        //If the node is available, print #
                        if (curAvailable != null && curAvailable.getRow() == i && curAvailable.getColumn() == j) {
                            System.out.print('#');
                            curAvailable = curAvailable.getNext();
                        }
                        //If the node is reserved, print .
                        else if (curReserved != null && curReserved.getRow() == i && curReserved.getColumn() == j) {
                            System.out.print('.');
                            curReserved = curReserved.getNext();
                        }
                        //The curs have changed, so we must check again if either = null
                        if (j == columnCount && (curAvailable != null || curReserved != null)) { //If there's still a node (Both aren't null), but the columns are used up, make a new row
                            j = -1;
                        }
                    }
                }
            }
        System.out.println("\n");   //After the entire auditorium is printed, add a new line
    }
    
    public static void displayAuditoriumMenu() {
        System.out.println("Please select an auditorium");
        System.out.print("1. Auditorium 1\n"
                + "2. Auditorium 2\n"
                + "3. Auditorium 3\n");
    }
    
    public static void printAuditorium(LinkedList available, int rowCount, int columnCount, PrintWriter output, int rCount, int cCount) throws IOException{
        //This is always initialized with rCount 1 and cCount 1 by our global constants.
        //Our base case is when our theoretical 'cur' no longer exists, or when rCount and cCount exceed rowCount and columnCount
        //While we haven't hit our base case, we check if a node exists in the available list. If it does, we print #, otherwise we print .
        //We recursively call the function if the row is still in bounds, BUT, if the column is out of bounds, we set it back to 1 and increase the row number
        if (rCount <= rowCount && cCount <= columnCount) {
            if (available.exists(rCount, cCount, 1))
                output.print('#');
            else
                output.print('.');
        }
        if (cCount == columnCount && rCount <= rowCount) {
            cCount = 1;
            rCount++;
            output.println("");
            printAuditorium(available, rowCount, columnCount, output, rCount, cCount);  //RECURSIVE
        }
        else if (rCount <= rowCount) {
            cCount++;
            printAuditorium(available, rowCount, columnCount, output, rCount, cCount);  //RECURSIVE
        }
    }
    
    public static void getRow(int seatNum, int rowCount, int[][] adultSeats, Scanner input) {
        System.out.println("What row would you like to reserve a seat in?");
        int row = getInt(input);
        while (row < 1 || row > rowCount) {
            System.out.println("Error! Your chosen row is not in the theater. Please try again");
            row = getInt(input);
        }
        adultSeats[seatNum][0] = row;
    }
    
    public static void getColumn(int seatNum, int columnCount, int[][] adultSeats, Scanner input) {
        System.out.println("What column would you like to reserve a seat in?");
        int column = getInt(input);
        while (column < 1 || column > columnCount) {
            System.out.println("Error! Your chosen column is not in the theater. Please try again");
            column = getInt(input);
        }
        adultSeats[seatNum][1] = column;
    }
    
    public static int getTicketQuantity (String type, int availableSeats, Scanner input) {
        System.out.println("How many " + type + " tickets would you like to purchase?");
        int tickets = getInt(input);
        while (tickets < 0 || tickets > availableSeats) {
            System.out.println("Error! You must enter a number greater than 0 and less than or equal to " + availableSeats + " (The number of available seats)");
            tickets = getInt(input);
        }
        return tickets;
    }
    
    public static void getSeats(int tickets, int[][] array, int rowCount, int columnCount, Scanner input) {
        for (int i = 0; i < tickets; i++) {
            boolean duplicate = false;
            do {
                System.out.println("TICKET " + (i + 1) + " of " + tickets);
                getRow(i, rowCount, array, input);
                getColumn(i, columnCount, array, input);
                for (int j = 0; j <= i; j++) {
                    if (i == j) { //Skip checking itself
                    }
                    else if (array[i][0] == array[j][0] && array[i][1] == array[j][1]) {
                        duplicate = true;
                        System.out.println("Please try again. You entered a duplicate seat");
                        j = i + 1; //Prevents printing multiple statements
                    }
                    else {
                        duplicate = false;
                    }
                }
            } while (duplicate);
        }
    }
    
    public static void reserveSeats(int row, int column, int quantity, LinkedList audAvailable, LinkedList audReserved) {
        int i = column;
        while (i < column + quantity) { //Ex. If user reserves column 3 with 4 seats, it stops before 7. 3, 4, 5, and 6.
            //Remove the node from available and add it to reserved
            DoubleLinkNode removedNode = audAvailable.deleteNode(row, i);   
            audReserved.addNode(removedNode);
            i++;
        }
        System.out.println("Thank you! You have successfuly reserved seat " + column + " in row " + row);
    }
    
    public static void createUserOrder(UserInfo currentUser, int adultTickets, int seniorTickets, int childTickets, int auditorium, int[][] seats) {
        Order tempOrder = new Order(adultTickets, seniorTickets, childTickets, auditorium, seats);
        currentUser.getOrderList().add(tempOrder);
    }
    
    public static int[] searchBestAvailable(int rowCount, int columnCount, int quantity, LinkedList available) {
        //Find center
        double centerRow = (rowCount + 1) / 2.0;
        double centerColumn = (columnCount + 1) / 2.0;
        //TEST PRINT System.out.println("The center is at " + centerRow + " " + centerColumn);
        
        //FIND THE BEST AVAILABLE SEAT
        int i = 1;
        int j = 1;
        double distance = 10000;
        int closestRow = -1;
        int closestColumn = -1;
        //Check every seat in auditorium
        while (i <= rowCount) {
            while (j <= columnCount) {
                if (available.exists(i, j, quantity)) {
                    int middleSeat = (j + ((quantity - 1)/2)); //This lets us find the middle seat of the total quantity selected!
                    //If the seats exist and there distance is less than the current best seat distance, update the best seats
                    if (findDistance(centerRow, centerColumn, i, middleSeat) < distance) {
                        distance = findDistance(centerRow, centerColumn, i, middleSeat);
                        System.out.println("Found shortest distance of " + distance + " at " + i + ", " + middleSeat);
                        closestRow = i;
                        closestColumn = j;
                        //TEST PRINT System.out.println("Found a best available at " + closestRow + " " + closestColumn);
                        //TEST PRINT System.out.println("The distance is " + findDistance(centerRow, centerColumn, i, j));
                    }
                    //"In the event of a tie for distance, the row closest to the middle of the auditorium should be selected
                    else if (findDistance(centerRow, centerColumn, i, middleSeat) == distance) {
                        if (Math.abs(closestRow - centerRow) > Math.abs(i - centerRow)) {
                            closestRow = i;
                            closestColumn = j;
                        }
                    }
                }
                j++;
            }
            i++;
            j = 1;
        }
        //If the center row is not even
        int[] bestSeat = new int[2];
        bestSeat[0] = closestRow;
        bestSeat[1] = closestColumn;
        return bestSeat;
    }
    static double findDistance(double centerY, double centerX, double point1, double point2) {   //Only used by bestAvailable method
        return Math.sqrt(Math.pow(point2 - centerX, 2) + Math.pow(point1 - centerY, 2));    //Distance formula
    }
    
    public static boolean displayUserOrder(UserInfo user) {
        if (user.getOrderList().isEmpty()) {
            System.out.println("You have no past orders");
            return false;
        }
        else {
            //i is the order
            for (int i = 0; i < user.getOrderList().size(); i++) {
                System.out.println("ORDER " + (i + 1) + " - Auditorium " + user.getOrderList().get(i).getAuditorium() + " - $" + user.getOrderList().get(i).getOrderSales());
                user.displayTicketTypes(i);
                user.displayOrderSeats(i);
            }
            return true;
        }
    }
    
    //Add, delete, or cancel an order
    public static void modifyOrder(UserInfo user, Scanner input, LinkedList[] allLists, int[] countArray) {
        //TO REMEMBER: int[] allCounts = {rowCountAudOne, rowCountAudTwo, rowCountAudThree, columnCountAudOne, columnCountAudTwo, columnCountAudThree};
        //TO REMEMBER: LinkedList[] allLists = {audOneAvailable, audTwoAvailable, audThreeAvailable, audOneReserved, audTwoReserved, audThreeReserved};
        System.out.println("Which order would you like to modify?");
        int order = getInt(input);
        while (order < 1 || order > user.getOrderList().size()) {
            System.out.println("Error! You must choose an existing order");
            order = getInt(input);
        }
        //Update the necessary variables based on order choice
        LinkedList available;
        LinkedList reserved;
        if (user.getOrderList().get(order - 1).getAuditorium() == 1) {
                    available = allLists[0];
                    reserved = allLists[3];
        }
        else if (user.getOrderList().get(order - 1).getAuditorium() == 2) {
                    available = allLists[1];
                    reserved = allLists[4];
        }
        else {
                    available = allLists[2];
                    reserved = allLists[5];
        }
        int[][] currentSeatList = user.getOrderList().get(order - 1).getOrderSeats();
        
        System.out.println("Editing order " + order + "...");
        System.out.print("1. Add tickets to order\n"
                + "2. Delete tickets from order\n"
                + "3. Cancel order\n");
        int option = getInt(input, 3);
        switch(option) {
            case 1: //If the user chooses to add tickets...
                addTicketsToOrder(order, user, available, reserved, countArray, input);
                System.out.println("Thank you for attempting to add tickets!");
                break;
            case 2: //If the user chooses to delete tickets...
                int numSeats;
                int seat;
                do {
                    numSeats = user.getOrderList().get(order - 1).getOrderSeats().length;
                    //Display a list of options
                    user.displayOrderSeats(order - 1);
                    System.out.println("\tor enter " + (numSeats + 1) + " to exit");
                    System.out.println("What seat would you like to delete?");
                    seat = getInt(input, numSeats + 1);
                    if (seat == numSeats + 1) 
                        continue;
                    //Get rid of seat in auditoriums
                    deleteSeat(currentSeatList, seat - 1, available, reserved, user, order);
                    //Delete seat from the order
                    if (numSeats == 1) {    //If the order only has one seat, delete the whole order
                        System.out.println("All seats have been removed from the order");
                        user.getOrderList().remove(order - 1);
                        seat = 2;   //This ends the loop
                    }
                    else {
                        int[][] newSeatList = new int[numSeats - 1][2];
                        int j = 0;
                        for (int i = 0; i < newSeatList.length; i++) {
                            if (i == seat - 1)  //Skips copying the deleted seat
                                j++;
                            newSeatList[i][0] = currentSeatList[j][0];
                            newSeatList[i][1] = currentSeatList[j][1];
                            j++;
                        }
                    //Update the seat list (After deleting the one)
                    user.getOrderList().get(order - 1).setOrderSeats(newSeatList);
                    currentSeatList = user.getOrderList().get(order - 1).getOrderSeats();
                    }
                }   while (seat != numSeats + 1);
                break;
            case 3: //If the user chooses to cancel an order, get the order seat individually(remove it from reserved, add it to available, and delete the order), then delete the order
                for (int i = user.getOrderList().get(order - 1).getOrderSeats().length - 1; i >= 0 ; i--) {
                    deleteSeat(currentSeatList, i, available, reserved, user, order);
                }
                user.getOrderList().remove(order - 1);
                System.out.println("Removed order " + order);
                break;
            default:System.out.println("Error, invalid input");
                break;
        }
    }
    static void deleteSeat(int[][]currentSeat, int seat, LinkedList available, LinkedList reserved, UserInfo user, int order) {
        DoubleLinkNode removedNode = reserved.deleteNode(currentSeat[seat][0], currentSeat[seat][1]);   
        available.addNode(removedNode);
        if (seat < user.getOrderList().get(order - 1).getAdultTickets())
            user.getOrderList().get(order - 1).deleteOverallAdultTickets(); //Remove the number of tickets from the static total
        else if (seat < (user.getOrderList().get(order - 1).getAdultTickets() + user.getOrderList().get(order - 1).getSeniorTickets()))
            user.getOrderList().get(order - 1).deleteOverallSeniorTickets();
        else
            user.getOrderList().get(order - 1).deleteOverallChildTickets();
    }
    
    static void addTicketsToOrder(int order, UserInfo user, LinkedList audAvailable, LinkedList audReserved, int[] allCounts, Scanner input) {
        //TO REMEMBER: int[] allCounts = {rowCountAudOne, rowCountAudTwo, rowCountAudThree, columnCountAudOne, columnCountAudTwo, columnCountAudThree};
        int columnCount;
        int rowCount;
        if (user.getOrderList().get(order - 1).getAuditorium() == 1) {
            columnCount = allCounts[3];
            rowCount = allCounts[0];
        }
        else if (user.getOrderList().get(order - 1).getAuditorium() == 2) {
            columnCount = allCounts[4];
            rowCount = allCounts[1];
        }
        else {
            columnCount = allCounts[5];
            rowCount = allCounts[2];
        }
        
        String adult = "adult";
        String senior = "senior";
        String child = "child";
                    
        //Get number of tickets for each type
        displayAuditorium(audAvailable, audReserved, columnCount);
        int adultTickets = getTicketQuantity(adult, audAvailable.countNodes(), input);
        int seniorTickets = getTicketQuantity(senior, audAvailable.countNodes() - adultTickets, input);
        int childTickets = getTicketQuantity(child, audAvailable.countNodes() - adultTickets - seniorTickets, input);
        if (adultTickets + seniorTickets + childTickets == 0) {
            System.out.println("This message is confirming you did not purchase any tickets");
            
        }

        //Get row and column for each individual adult seat
        int totalTickets = adultTickets + seniorTickets + childTickets;
        int[][] orderSeats = new int[totalTickets][2];
        getSeats(totalTickets, orderSeats, rowCount, columnCount, input);
                    
        //Check if seats are all available
        boolean available = true;
        for (int i = 0; i < orderSeats.length; i++) {
            available = audAvailable.exists(orderSeats[i][0], orderSeats[i][1], 1); //The class method will deal with a null pointer, so it is okay
            //TEST PRINT System.out.println("Are the seats available? " + available);
            if (!available) {
                System.out.println("The seats are not all available");
                i = orderSeats.length;
            }
        }
        //Reserve seats if all available and create a user order
        if (available) {
            for (int i = 0; i < orderSeats.length; i++) {
                reserveSeats(orderSeats[i][0], orderSeats[i][1], 1, audAvailable, audReserved);
            }
            //Add tickets to the order itself
            addSeatList(user, order, totalTickets, orderSeats, adultTickets, seniorTickets, childTickets);
        }
        //Find the best available seats
        else {  //If the seats are not all available!
            int bestAvailableIndex[] = searchBestAvailable(rowCount, columnCount, totalTickets, audAvailable);  //Returns row and column in array
            if (bestAvailableIndex[0] == -1) {  //If the best available seat row is still -1, there is no best seat
                System.out.println("There is no best available seat (That quantity cannot be seated together in this auditorium). We apologize.");
            }
            else {
                if (totalTickets > 1)
                    System.out.println("Your choice was unavailable. Would you like to book the seat(s) at row " + bestAvailableIndex[0] + ", columns " + bestAvailableIndex[1] + "-" + (bestAvailableIndex[1] + totalTickets - 1) + "?");
                else
                    System.out.println("Your choice was unavailable. Would you like to book the seat(s) at row " + bestAvailableIndex[0] + ", column " + bestAvailableIndex[1] + "?");
                System.out.println("(If yes, type Y, if no, type N)"); 
                char check = Character.toUpperCase(input.next().charAt(0)); //Evaluates the first character a person enters (They can put "Yes" if they want)
                input.nextLine();   //This keeps anything after the char to be used as input later
                while (check != 'Y' && check != 'N') {
                    System.out.println("Please try again with a Y or N");
                    check = Character.toUpperCase(input.next().charAt(0));
                    input.nextLine();
                }
                if (check == 'Y')   {
                    int[][]bestSeats = new int[totalTickets][2];
                    for (int i = 0; i < totalTickets; i++) {
                        bestSeats[i][0] = bestAvailableIndex[0];
                        bestSeats[i][1] = bestAvailableIndex[1] + i;
                    }
                    for (int i = 0; i < bestSeats.length; i++) {
                        reserveSeats(bestSeats[i][0], bestSeats[i][1], 1, audAvailable, audReserved);
                    }           
                    addSeatList(user, order, totalTickets, bestSeats, adultTickets, seniorTickets, childTickets);
                }
                else
                    System.out.println("Sorry about that. Thank you for using our system!");
            }
        }    
    }
    
    public static void addSeatList(UserInfo user, int order, int totalTickets, int[][] orderSeats, int adultTickets, int seniorTickets, int childTickets) {
        int[][] currentSeatList = user.getOrderList().get(order - 1).getOrderSeats();
            int[][] newSeatList = new int[user.getOrderList().get(order - 1).getOrderSeats().length + totalTickets][2]; //New length in old length plus added tickets
            int j = 0;
            //Create a new list of seats to be added to the order. Add new adult seats, then old adult seats, new senior seats, then old senior seats, then new child seat, then old child seats
            for (int i = 0; i < newSeatList.length; i++) {
                    if (i < adultTickets) {
                        newSeatList[i][0] = orderSeats[i][0];
                        newSeatList[i][1] = orderSeats[i][1];
                    }
                    else if (i < adultTickets + user.getOrderList().get(order - 1).getAdultTickets()) {
                        newSeatList[i][0] = currentSeatList[j][0];
                        newSeatList[i][1] = currentSeatList[j][1];
                        j++;
                    }
                    else if (i < adultTickets + user.getOrderList().get(order - 1).getAdultTickets() + seniorTickets) {
                        newSeatList[i][0] = orderSeats[i - j][0];
                        newSeatList[i][1] = orderSeats[i - j][1];
                    }
                    else if (i < adultTickets + user.getOrderList().get(order - 1).getAdultTickets() + seniorTickets + user.getOrderList().get(order - 1).getSeniorTickets()) {
                        newSeatList[i][0] = currentSeatList[j][0];
                        newSeatList[i][1] = currentSeatList[j][1];
                        j++;
                    }
                    else if (i < adultTickets + user.getOrderList().get(order - 1).getAdultTickets() + seniorTickets + user.getOrderList().get(order - 1).getSeniorTickets() + childTickets) {
                        newSeatList[i][0] = orderSeats[i - j][0];
                        newSeatList[i][1] = orderSeats[i - j][1];
                    }
                    else  {
                        newSeatList[i][0] = currentSeatList[j][0];
                        newSeatList[i][1] = currentSeatList[j][1];
                        j++;
                    }
            }
            for (int i = 0; i < adultTickets; i++) {
                user.getOrderList().get(order - 1).addOverallAdultTickets();
            }
            for (int i = 0; i < seniorTickets; i++) {
                user.getOrderList().get(order - 1).addOverallSeniorTickets();
            }
            for (int i = 0; i < childTickets; i++) {
                user.getOrderList().get(order - 1).addOverallChildTickets();
            }
            user.getOrderList().get(order - 1).setOrderSeats(newSeatList);
    }
}