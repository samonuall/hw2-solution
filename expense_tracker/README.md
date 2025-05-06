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

- Export to CSV: Click on the export to CSV button. Then specify a valid file name, and a csv file of the current transactions will be created and stored on your computer.
