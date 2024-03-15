package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The main class for the Expense Tracker application.
 */
public class ExpenseTrackerApp extends Application {
    // Backend logic for managing expenses
    private ExpenseBack expenseBack = new ExpenseBack();

    // Label displaying the total amount of expenses
    private Label totalAmountLabel = new Label("Total Amount: $0.00");

    //The entry point of the application.
    public static void main(String[] args) {
        launch(args);
    }

    // The method where the UI is initialized and the main components are set up.
    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        // UI components
        TextField descriptionField = new TextField();
        TextField amountField = new TextField();
        DatePicker datePicker = new DatePicker();
        Button addButton = new Button("Add Expense");
        ListView<String> expenseListView = new ListView<>();
        RadioButton recurringExpenseRadio = new RadioButton("Recurring Expense");
        TextField daysBetweenField = new TextField();

        // Group for radio buttons (recurring and one-time expenses)
        ToggleGroup expenseTypeGroup = new ToggleGroup();
        recurringExpenseRadio.setToggleGroup(expenseTypeGroup);

        // Event handlers
        addButton.setOnAction(e -> {
            try {
                // Get user input from the UI
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate date = datePicker.getValue();

                // Validate user input
                if (description.isEmpty()) {
                    showErrorDialog("Please enter a description.");
                    return;
                }

                if (amount <= 0) {
                    showErrorDialog("Invalid amount format. Please enter an amount greater than zero.");
                    return;
                }

                if (date == null) {
                    showErrorDialog("Please enter a date.");
                    return;
                }

                if (recurringExpenseRadio.isSelected()) {
                    // Handle recurring expense
                    try {
                        int daysBetween = Integer.parseInt(daysBetweenField.getText());
                        if (daysBetween <= 0) {
                            showErrorDialog("Invalid days between occurrences. Please enter a positive number.");
                            return;
                        }

                        // Add both a regular expense and a recurring expense
                        Expense newExpense = new Expense(description, amount, date);
                        expenseBack.addExpense(newExpense);

                        RecurringExpense recurringExpense = new RecurringExpense(description, amount, date);
                        expenseBack.addRecurringExpense(recurringExpense, daysBetween);
                        
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid days between occurrences. Please enter a valid number.");
                        return;
                    }
                } else {
                    // Add a regular expense
                    Expense newExpense = new Expense(description, amount, date);
                    expenseBack.addExpense(newExpense);
                }
                updateExpenseListView(expenseListView);
                updateTotalAmountLabel();
            } catch (NumberFormatException ex) {
                // Handle the case where the amount is not a valid double
                showErrorDialog("Invalid amount format. Please enter a valid number.");
            }
        });

        // Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 20;");

        // Form layout for user input
        VBox formLayout = new VBox(10);
        formLayout.setAlignment(Pos.CENTER_LEFT);
        formLayout.getChildren().addAll(
                createLabeledField("Description:", descriptionField),
                createLabeledField("Amount:", amountField),
                createLabeledField("Date:", datePicker),
                recurringExpenseRadio,
                createLabeledField("Days Between Occurrences:", daysBetweenField),
                addButton
        );

        // Layout for displaying the list of expenses
        VBox listViewLayout = new VBox(10);
        listViewLayout.setAlignment(Pos.CENTER_LEFT);
        listViewLayout.getChildren().addAll(
                new Label("Expense List:"),
                expenseListView
        );

        // Add components to the main layout
        mainLayout.getChildren().addAll(formLayout, listViewLayout, totalAmountLabel);

        // Set up the scene
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize expense list view
        updateExpenseListView(expenseListView);
    }


    // Helper method to create an HBox with a labeled control.
    private HBox createLabeledField(String label, Control control) {
        Label titleLabel = new Label(label);
        titleLabel.setMinWidth(120);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(titleLabel, control);

        return hbox;
    }

    // Helper method to display an error dialog with a given message.
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Helper method to update the total amount label with the current total amount of expenses.
    private void updateTotalAmountLabel() {
        double totalAmount = expenseBack.calculateTotalAmount();
        totalAmountLabel.setText(String.format("Total Amount: $%.2f", totalAmount));
    }


    // Helper method to update the expense list view with the latest data.
    private void updateExpenseListView(ListView<String> listView) {
        listView.getItems().clear();
        expenseBack.getAllExpenses().forEach(expense ->
                listView.getItems().add(
                        String.format("%s: $%.2f on %s", expense.getDescription(), expense.getAmount(), expense.getDate())
                )
        );
    }
}
