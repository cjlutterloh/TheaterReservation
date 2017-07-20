//Carson Lutterloh
//CS 2336

package LinkList;

public abstract class BaseNode {
    //Variables
    int row;
    int column;
    
    //Constructor
    public BaseNode(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    //Mutators
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    
    //Accessors
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}

