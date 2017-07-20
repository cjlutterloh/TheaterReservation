//cjlutterloh
//CS 2336

package UserInfoObject;

public class Order {
    public static final double ADULT_PRICE = 10.00;
    public static final double SENIOR_PRICE = 7.50;
    public static final double CHILD_PRICE = 5.25;
    //Declare variables
    int adultTickets;
    int seniorTickets;
    int childTickets;
    int auditorium;
    int[][] orderSeats; //This is always int[quantity][2]
    //Separate variables - will only be accessed by UserInfo
    static int[] totalAdult = new int[3];
    static int[] totalSenior = new int[3];
    static int[] totalChild = new int[3];
    
    //Constructor
    public Order(int adultTickets, int seniorTickets, int childTickets, int auditorium, int[][] orderSeats) {
        if (orderSeats.length != (adultTickets + seniorTickets + childTickets)) {
            System.out.println("ERROR! Somehow, your order has a different number of tickets and seats");
        }
        this.adultTickets = adultTickets;
        this.seniorTickets = seniorTickets;
        this.childTickets = childTickets;
        this.auditorium = auditorium;
        this.orderSeats = orderSeats;
        //For overall counts in the late program
        totalAdult[auditorium - 1] += adultTickets;
        totalSenior[auditorium - 1] += seniorTickets;
        totalChild[auditorium - 1] += childTickets;
    }
    
    //Accessors
    public int getAdultTickets() {
        return adultTickets;
    }
    public int getSeniorTickets() {
        return seniorTickets;
    }
    public int getChildTickets() {
        return childTickets;
    }
    public int getAuditorium() {
        return auditorium;
    }
    public int[][] getOrderSeats() {
        return orderSeats;
    }
    
    //Mutators
    public void setAdultTickets(int adultTickets) {
        this.adultTickets = adultTickets;
    }
    public void setSeniorTickets(int seniorTickets) {
        this.seniorTickets = seniorTickets;
    }
    public void setChildTickets(int childTickets) {
        this.childTickets = childTickets;
    }
    public void setAuditorium(int auditorium) {
        this.auditorium = auditorium;
    }
    public void setOrderSeats(int[][] orderSeats) {
        this.orderSeats = orderSeats;
    }
    
    public void deleteOverallAdultTickets() {
        totalAdult[auditorium - 1] -= 1;
        adultTickets -= 1;
    }
    public void addOverallAdultTickets() {
        totalAdult[auditorium - 1] += 1;
        adultTickets += 1;
    }
    public void deleteOverallSeniorTickets() {
        totalSenior[auditorium - 1] -= 1;
        seniorTickets -= 1;
    }
    public void addOverallSeniorTickets() {
        totalSenior[auditorium - 1] += 1;
        seniorTickets += 1;
    }
    public void deleteOverallChildTickets() {
        totalChild[auditorium - 1] -= 1;
        childTickets -= 1;
    }
    public void addOverallChildTickets() {
        totalChild[auditorium - 1] += 1;
        childTickets += 1;
    }
    
    //Other methods
    public void displayOrderSeats() {
        System.out.print("\tSeats: ");
        String type;
        for (int i = 0; i < orderSeats.length; i++) {
            if (i + 1 <= adultTickets)
                type = "Adult";
            else if (i + 1 <= adultTickets + seniorTickets)
                type = "Senior";
            else 
                type = "Child";
            System.out.print((i + 1) + ".(" + orderSeats[i][0] + ", " + orderSeats[i][1] + ") " + type + " ");
        }
        System.out.println("");
    }
    public void displayTicketTypes() {
        System.out.println("\tAdults: " + adultTickets);
        System.out.println("\tSeniors: " + seniorTickets);
        System.out.println("\tChildren: " + childTickets);
    }
    public double getOrderSales() {
        return ((adultTickets * ADULT_PRICE) + (seniorTickets * SENIOR_PRICE) + (childTickets * CHILD_PRICE));
    }
}

