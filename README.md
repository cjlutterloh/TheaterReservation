# TheaterReservation
Movie theater reservation system. 
Reads in theater configuration from a .txt file (Assumes all rows have the same number of seats - this information is validated in the program). Keeps track of a user database (Username and password) stored in a .txt file utilizing a hashmap. Transactions are only stored for the current session, but all transactions can be updated/deleted by the user. Program recognizes administrator login to report sales and terminate the current session.
Primary data structures include a hashmap, array list, and custom doubly linked list (Made from scratch)
