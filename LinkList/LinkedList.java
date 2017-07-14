//Carson Lutterloh
//cjl150530
//CS 2336.003

package LinkList;

public class LinkedList {
    //Variables - accessible inside the package for ease of use
    DoubleLinkNode head;
    DoubleLinkNode tail;
    
    //Constructor
    public LinkedList() {
        head = null;
        tail = null;
    }
    public LinkedList(DoubleLinkNode n) {
        head = n;
        tail = n;
    }
    public LinkedList(DoubleLinkNode n, DoubleLinkNode m) {
        head = n;
        tail = m;
    }
    
    //Mutators
    public void setHead(DoubleLinkNode n)
    {
        head = n;
    }
    public void setTail(DoubleLinkNode m)
    {
        tail = m;
    }
    
    //Accessors
    public DoubleLinkNode getHead()
    {
        return head;
    }
    public DoubleLinkNode getTail()
    {
        return tail;
    }
    
    //Other Functions
    public void addNode(DoubleLinkNode n)
    {
        //1. Add to empty list - Make the added node the head
        if (head == null) {
            //TEST PRINT System.out.println("Adding node to empty list");
            head = n;
            tail = n;
            head.next = null;
            head.prev = null;
        }
        //2. Add to beginning of list
        else if (n.row <= head.row && n.column < head.column) {
            //TEST PRINT System.out.println("Adding node to the beginning of the list");
            n.next = head;  //The next pointer of n is now the head
            head = n;   //The head now becomes n
            n.next.prev = n;  //The "old" head now has a previous node. We could do head.prev in line 50, but don't want head to ever have a prev.
            n.prev = null; //As the head, it has no prev
        }
        //3. Add to middle or 4. end of list
        else {
            DoubleLinkNode cur = head;
            while (cur.next != null && (cur.next.row < n.row || (cur.next.row == n.row && cur.next.column < n.column))) { //Cur goes up if the row is less than n's row, or if the tow is the same and column is less
                cur = cur.next; //Shifts the cur node up until it is right before where the new node should go
            }
            //If adding to the middle of the list
            if (cur.next != null) {
                //TEST PRINT System.out.println("Adding node to the middle of the list");
                n.next = cur.next;
                cur.next.prev = n;
                cur.next = n;
                n.prev = cur;
            }
            //If adding to the end of the list
            else {
                //TEST PRINT System.out.println("Adding node to the end of the list");
                cur.next = n;
                n.prev = cur;
                tail = n;
                n.next = null;
            }
        }
    }
    
    public DoubleLinkNode deleteNode(int row, int column)
    {
        //1. Delete from empty list
        if (head == null) {
            //TEST PRINT System.out.println("Deleting from empty list...");
            return head;
        }
        //2. Delete head (Case 1. More than 1 node exists. Case 2. head is the only existing node)
        else if (head.row == row && head.column == column && head.next != null) {
            //TEST PRINT System.out.println("Deleting from head of list...");
            DoubleLinkNode hold = head;
            head = head.next;
            hold.next = null;
            head.prev = null;
            return hold;
        }
        else if (head.row == row && head.column == column && head.next == null) {
            //TEST PRINT System.out.println("Deleting from head of list...(No next)");
            DoubleLinkNode hold = head;
            head = null;
            hold.next = null;
            return hold;
        }
        //3. Delete from middle or 4. end of list or 5. Node doesn't exist
        else {
            DoubleLinkNode cur = head;
            while (cur.next != null && (cur.next.row != row || cur.next.column != column)) { //Cur goes up if the row or column don't match
                cur = cur.next;
            }
            //5. Delete nonexistent node
            if (cur.next == null && (cur.row != row || cur.column != column)) { //If no more nodes exist and the row or column don't match, no delete
                //TEST PRINT System.out.println("Node not found!");
                return null; //not found
            }
            //4. Delete node from end of list
            else if (cur.next.next == null && (cur.next.row == row && cur.next.column == column)) {
                //TEST PRINT System.out.println("Deleting from end of list...");
                DoubleLinkNode hold = cur.next;
                tail = cur;
                cur.next = null;
                hold.prev = null;
                return hold;
            }
            //3. Delete node from middle of list
            else {
                //TEST PRINT System.out.println("Deleting from middle of list...");
                DoubleLinkNode hold = cur.next;
                cur.next = cur.next.next;
                hold.next = null;
                cur.next.prev = cur;
                hold.prev = null;
                return hold;
            }
        }
    }
    
    public boolean exists(int row, int column, int quantity) {
        DoubleLinkNode cur = head;
        boolean exists = true;
        int i = 0;
        
        //If list is empty, the node doesn't exist
        if (head == null) {
            return false;
        }
        //If head is matching, return true if it's the only one, and return false if it has nothing after but should
        else if (head.row == row && head.column == column) {
            if (quantity == 1) {
                return true;
            }
            else if (head.next == null) {
                return false;
            }
        }
        //Remember, cur will stop AT what it is looking for
        while (cur.next != null && (cur.row != row || cur.column != column)) { //Cur goes up if the row or column don't match
            cur = cur.next;
        }
        //TEST PRINT System.out.println("Cur is at row " + cur.row + " and column " + cur.column);
        
        while (exists && i < quantity) {
            if ((cur != null) && (cur.row != (row) || cur.column != (column + i))) { //If no more nodes exist and the row or column don't match, return false
                //TEST PRINT System.out.println("Node not found! At row " + (row) + " and column " + (column + i));
                return false; //not found
            }
            //If tail is matching and is the last in the quantity, return true, otherwise return false
            else if (tail.row == row && tail.column == column + i) {
                return i == quantity - 1;
            }
            //If the node exists and both row and columns match, move to next node
            else {
                //TEST PRINT System.out.println("Node " + i + " found!");
            }
            cur = cur.next;
            i++;
        }
        return exists;
    }
    
    public void printList() {
        DoubleLinkNode cur = head;
        while (cur != null) {
            System.out.printf("Seat %d, Column %d\n", cur.row, cur.column);
            cur = cur.next;
        }
        System.out.println("");
    }
    
    public int countNodes() {
        int count = 0;
        DoubleLinkNode cur = head;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }
}
