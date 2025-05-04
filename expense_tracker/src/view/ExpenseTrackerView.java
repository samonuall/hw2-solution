package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  private JTextField categoryFilterField;
  private JButton categoryFilterBtn;

  private JTextField amountFilterField;
  private JButton amountFilterBtn;

  private JButton clearFilterBtn;
  private JButton removeTransactionBtn; // Added remove button
  private JButton exportBtn; // Added export button

  private List<Transaction> displayedTransactions = new ArrayList<>();

  public ExpenseTrackerView() {
    setTitle("Expense Tracker");
    setSize(600, 400);

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    transactionsTable = new JTable(model);
    addTransactionBtn = new JButton("Add Transaction");

    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();
    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");

    JLabel amountFilterLabel = new JLabel("Filter by Amount:");
    amountFilterField = new JTextField(10);
    amountFilterBtn = new JButton("Filter by Amount");

    clearFilterBtn = new JButton("Clear Filter");
    removeTransactionBtn = new JButton("Remove Selected Transaction"); // Initialize remove button
    exportBtn = new JButton("Export to CSV"); // Initialize export button
    exportBtn.setToolTipText("Export current transactions to CSV file");

    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel filterPanel = new JPanel();
    filterPanel.add(amountFilterBtn);
    filterPanel.add(categoryFilterBtn);
    filterPanel.add(clearFilterBtn);

    JPanel actionPanel = new JPanel();
    actionPanel.add(removeTransactionBtn);
    actionPanel.add(exportBtn); // Add export button to action panel

    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    // Create a combined panel for filters and actions at the bottom
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.add(filterPanel, BorderLayout.NORTH);
    southPanel.add(actionPanel, BorderLayout.SOUTH);
    add(southPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if (amountField.getText().isEmpty()) {
      return 0;
    } else {
      return Double.parseDouble(amountField.getText());
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
  }

  public void addApplyAmountFilterListener(ActionListener listener) {
    amountFilterBtn.addActionListener(listener);
  }

  public double getAmountFilterInput() {
    String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  public void addClearFilterListener(ActionListener listener) {
    clearFilterBtn.addActionListener(listener);
  }

  // Add listener for the remove button
  public void addRemoveTransactionListener(ActionListener listener) {
    removeTransactionBtn.addActionListener(listener);
  }

  public void addExportListener(ActionListener listener) {
    exportBtn.addActionListener(listener);
  }

  public String getExportFileName() {
    return JOptionPane.showInputDialog(
      this,
      "Enter output CSV filename (must end with .csv):"
    );
  }

  public void showInfoMessage(String message) {
    JOptionPane.showMessageDialog(
      this,
      message,
      "Info",
      JOptionPane.INFORMATION_MESSAGE
    );
  }

  // Getter for the remove button
  public JButton getRemoveTransactionBtn() {
    return removeTransactionBtn;
  }

  // Method to get the selected row index
  public int getSelectedTransactionIndex() {
    return transactionsTable.getSelectedRow();
  }

  // Method to show error messages
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void refreshTable(List<Transaction> transactions) {
    model.setRowCount(0);
    this.displayedTransactions = transactions;

    int rowNum = 0; // Start row numbering from 0 for internal logic
    double totalCost = 0;

    for (Transaction t : transactions) {
      totalCost += t.getAmount();
      // Add row with serial number starting from 1 for display
      model.addRow(new Object[]{rowNum + 1, t.getAmount(), t.getCategory(), t.getTimestamp()});
      rowNum++;
    }

    // Add total row only if there are transactions
    if (!transactions.isEmpty()) {
        model.addRow(new Object[]{"Total", totalCost, null, null}); // Display total cost in Amount column
    }

    transactionsTable.updateUI(); // Use updateUI for better refresh
  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  public void displayFilteredTransactions(List<Transaction> filteredTransactions) {
    refreshTable(filteredTransactions);
  }

  public List<Transaction> getDisplayedTransactions() {
    // Return an unmodifiable list to prevent external modification
    return java.util.Collections.unmodifiableList(displayedTransactions);
  }

  // Optional: remove if no longer needed
  // public void highlightRows(List<Integer> rowIndexes) { ... }

  // public void highlightRows(List<Integer> rowIndexes) {
  //     // The row indices are being used as hashcodes for the transactions.
  //     // The row index directly maps to the the transaction index in the list.
  //     transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
  //         @Override
  //         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
  //                                                       boolean hasFocus, int row, int column) {
  //             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  //             if (rowIndexes.contains(row)) {
  //                 c.setBackground(new Color(173, 255, 168)); // Light green
  //             } else {
  //                 c.setBackground(table.getBackground());
  //             }
  //             return c;
  //         }
  //     });

  //     transactionsTable.repaint();
  // }

}
