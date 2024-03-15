/*The ExpenseBack class serves as a backend component in the expense tracker application. 
 * It manages the collection of expenses, providing methods to add and retrieve expense details.
 */


package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseBack {
    private List<Expense> expenses;
    
    // Initialize a list to hold the information provided by the user
    public ExpenseBack() {
        this.expenses = new ArrayList<>();
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses); // Return a copy to prevent direct modification
    }
    
    public void addRecurringExpense(RecurringExpense expense, int daysBetween) {
        // Calculate the next occurrence date
        LocalDate nextOccurrenceDate = expense.getNextOccurrenceDate(expenses, daysBetween);

        // Set the next occurrence date for the recurring expense
        expense.setDate(nextOccurrenceDate);

        // Add the recurring expense to the list
        expenses.add(expense);
    }
    
    public double calculateTotalAmount() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
