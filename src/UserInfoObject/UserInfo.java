//Carson Lutterloh
//cjl150530
//CS 2336.003

package UserInfoObject;

import java.util.*;

public class UserInfo {
    public static final double ADULT_PRICE = 10.00;
    public static final double SENIOR_PRICE = 7.50;
    public static final double CHILD_PRICE = 5.25;
    //Declare Variables
    String username;
    String password;
    ArrayList<Order> orders = new ArrayList<>();
    
    //Constructor
    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    //Accessors
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<Order> getOrderList() {
        return orders;
    }
    
    //Mutators
    private void setUsername(String username) { //Currently, we are leaving this private. If, in the future, we want to give users the ability to change their name, we can add this
        this.username = username;
    }
    private void setPassword(String password) { //Currently, we are leaving this private. If, in the future, we want to give users the ability to change their password, we can add this
        this.password = password;
    }
    private void setOrders(ArrayList<Order> orders) { //Currently, this function has no purpose
        this.orders = orders;
    }
    
    //Other methods
    //The n is for the specific order number
    public void displayTicketTypes(int n) {
        try {
            orders.get(n).displayTicketTypes();
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Error (UserInfo.java): Tickets for a nonexistent order cannot be displayed");
        }
    }
    //The n is for the specific order number
    public void displayOrderSeats(int n) {
        try {
            orders.get(n).displayOrderSeats();
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Error (UserInfo.java): Tickets for a nonexistent order cannot be displayed");
        }
    }
    //The n is to get the overall in a specific auditorium for ALL users
    public int getOverallAdult(int n) {
        return Order.totalAdult[n - 1];
    }
    public int getOverallSenior(int n) {
        return Order.totalSenior[n - 1];
    }
    public int getOverallChild(int n) {
        return Order.totalChild[n - 1];
    }
    public double getOverallAuditoriumSales(int n) {
        return ((Order.totalAdult[n - 1] * ADULT_PRICE) + (Order.totalSenior[n - 1] * SENIOR_PRICE) + (Order.totalChild[n - 1] * CHILD_PRICE));
    }
    //The following are overall for all users
    public int getOverallAdult() {
        return (Order.totalAdult[0] + Order.totalAdult[1] + Order.totalAdult[2]);
    }
    public int getOverallSenior() {
        return (Order.totalSenior[0] + Order.totalSenior[1] + Order.totalSenior[2]);
    }
    public int getOverallChild() {
        return (Order.totalChild[0] + Order.totalChild[1] + Order.totalChild[2]);
    }
    //Gets overall sales for ONE user
    public double getOverallSales() {
        double overallSales = 0;
        for (int i = 0; i < orders.size(); i++) {
            overallSales += this.getOrderList().get(i).getOrderSales();
        }
        return overallSales;
    }
    
}
