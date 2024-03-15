/*The Expense class represents an individual expense within the expense tracker application
 *It keeps track of three main things: 
 *	what the spending was for (description)
 *	how much money was spent (amount)
 *	the date when the spending occurred.
 */


package application;

import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;

    // Constructors
    
    /* Expense has three constructors
     *string description for the description of expense
     *double amount for the cost of the expense
     *LocalDate date to add the date of the expense
     */
    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    // Get inputs and set it to variables 
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
