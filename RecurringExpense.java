/*The RecurringExpense class represents a recurring expense in the expense tracker application.
 * It extends the Expense class and adds functionality to handle recurring expenses.
 */

package application;

import java.time.LocalDate;
import java.util.List;

public class RecurringExpense extends Expense {
	/*Calculates and returns the date of the next occurrence based on the specified recurrence pattern
     *and the existing expenses.
     */
    public LocalDate getNextOccurrenceDate(List<Expense> existingExpenses, int daysBetween) {
        // Find the last occurrence date
        LocalDate lastOccurrenceDate = existingExpenses.stream()
                .filter(e -> e instanceof RecurringExpense && e.getDescription().equals(this.getDescription()))
                .map(Expense::getDate)
                .max(LocalDate::compareTo)
                .orElse(getDate());

        // Calculates the next occurrence date by adding the specified number of days
        return lastOccurrenceDate.plusDays(daysBetween);
    }

    // Constructor with necessary parameters
    public RecurringExpense(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

}
