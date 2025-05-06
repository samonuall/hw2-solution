# hw2

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## Features
- Add a new transaction: First specify the amount and category. Then click on the Add transaction button. Adds the new transaction to the list and updates the total cost.
- Filter the transaction list by either amount or category: First specify the amount or category to be matched. Then click the corresponding Filter button. Highlights the matching transactions in the list.

## New Functionality
### 1. Undo a Transaction

To remove a transaction:

1. Click on the transaction row you want to remove.
2. Click the **"Remove Selected Transaction"** button.

Only valid (non-total) transactions can be removed. The table and total cost will update automatically. This feature improves usability by supporting controlled undo of user-added data.

### 2. Export to CSV: Design Summary

To improve extensibility, we implemented a CSV export feature using the MVC pattern.

- The **view** prompts the user for a filename and offers a dedicated export button.
- The **controller** handles input validation and calls a reusable model-layer class (`CSVExporter`) to handle file writing.
- The **model**'s `CSVExporter` class writes transactions to CSV format with a header line followed by one line per transaction.

This design applies object-oriented principles such as:
- **Open/Closed Principle**: `CSVExporter` can be extended for other export formats without modifying controller logic.
- **No Magic Strings**: Headers and data are structured cleanly and consistently.

Input validation ensures the file name ends with `.csv`, and user feedback is provided for success or failure.

