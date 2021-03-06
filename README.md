# TheaterReservation

All programming done in Java language in the NetBeans IDE.

**For FULL details, please see the specifications document**

Movie theater reservation system. 



**OVERALL DESCRIPTION**

Reads in three theater configurations of any size from .txt files (Assumes all rows have the same number of seats - this information is validated in the program). A '.' represents a reserved seat, and a '#' represents an open seat. Keeps track of a user database (Username and password) stored in a .dat file utilizing a hashmap. Transactions are only stored for the current session, but all transactions can be updated/deleted by the user. Users have the option to select seats all around the theater, and reserve seats for children, adults, and seniors separately. Program recognizes administrator login to report sales and terminate the current session.

**HIGHLIGHTED FUNCTIONALITY**

One of the programs major highlights is its ability to suggest seats. If a user asks to reserve seats that are not available, the program automatically finds the given quantity in a row closest to the center of the theater, then asks the user if they would like those seats instead. If unavailable in that quantity, the program notifies the user.

**DATA STRUCTURES**

Primary data structures include a hashmap, array list, and custom doubly linked list (Made from scratch)

**VALIDATION**

All user information is validated within the program. A given username has three attempts to enter a password before given the chance to reenter a username. 

**ADDITIONAL FILES**

The A1.txt, A2.txt, A3.txt and userdb.dat files are all example test cases.



Additional details found within the folders!
