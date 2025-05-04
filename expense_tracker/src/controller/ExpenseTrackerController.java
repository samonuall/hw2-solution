package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /** 
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class 
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Initialize view listeners
    this.view.addApplyAmountFilterListener(e -> applyAmountFilter());
    this.view.addApplyCategoryFilterListener(e -> applyCategoryFilter());
    this.view.addClearFilterListener(e -> clearFilter());
    this.view.addRemoveTransactionListener(e -> removeSelectedTransaction());
  }
  
  private void applyAmountFilter() {
      // Get amount filter value from view
      // Create AmountFilter instance
      // Set filter
      // Apply filter
      System.out.println("Apply Amount Filter action triggered");
  }

  private void applyCategoryFilter() {
      // Get category filter value from view
      // Create CategoryFilter instance
      // Set filter
      // Apply filter
      System.out.println("Apply Category Filter action triggered");
  }

  private void clearFilter() {
      setFilter(null);
      applyFilter();
      System.out.println("Clear Filter action triggered");
  }

  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  public void applyFilter() {
    List<Transaction> filteredTransactions;
    // If no filter is specified, show all transactions.
    if (filter == null) {
      filteredTransactions = model.getTransactions();
    }
    // If a filter is specified, show only the transactions accepted by that filter.
    else {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      filteredTransactions = filter.filter(transactions);
    }
    view.displayFilteredTransactions(filteredTransactions);
  }

  // Method to remove the selected transaction
  public void removeSelectedTransaction() {
    int selectedIndex = view.getSelectedTransactionIndex();
    List<Transaction> displayedTransactions = view.getDisplayedTransactions();

    // Check if a row is selected
    if (selectedIndex < 0) {
      view.showErrorMessage("Please select a transaction to remove.");
      return;
    }

    // Check if the selected index is valid within the displayed transactions list
    // (This also prevents removing the "Total" row, as it's not in displayedTransactions)
    if (selectedIndex >= displayedTransactions.size()) {
      view.showErrorMessage("Invalid selection. Cannot remove the total row or header.");
      return;
    }

    // Get the actual transaction object from the displayed list
    Transaction transactionToRemove = displayedTransactions.get(selectedIndex);

    // Remove the transaction from the model
    model.removeTransaction(transactionToRemove);

    // Refresh the view (which will re-apply the filter and update the table)
    refresh();
  }
    
}
