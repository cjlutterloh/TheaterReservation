//Carson Lutterloh
//cjl150530
//CS 2336.003

package LinkList;

public class DoubleLinkNode extends BaseNode {
    //Variables - These can be accessed by the entire package for ease
    DoubleLinkNode next;
    DoubleLinkNode prev;
    
    //Constructor
    public DoubleLinkNode(int row, int column) {
        super(row, column);
        next = null;
        prev = null;
    }
    
    //Mutators
    public void setNext(DoubleLinkNode n)
    {
        next = n;
    }
    public void setPrev(DoubleLinkNode p)
    {
        prev = p;
    }
    
    //Accessors
    public DoubleLinkNode getNext()
    {
        return next;
    }
    public DoubleLinkNode getPrev()
    {
        return prev;
    }
}
