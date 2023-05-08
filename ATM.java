import java.util.*;

class Transaction {
    private Date timestamp;
    private String description;

    public Transaction(Date timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }
}

class BankAccount {
    private String accountHolder;
    private String accountNumber;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountHolder, String accountNumber, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Your Current Balance Is: " + balance);
        addTransactionToHistory("Deposit: +" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal Successful, Current Balance is: " + balance);
            addTransactionToHistory("Withdrawal: -" + amount);
        } else {
            System.out.println("Insufficient Balance!!, Current Balance is: " + balance);
        }
    }

    public void displayDetails() {
        System.out.println("Account Holder Name: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Balance: " + balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void transfer(BankAccount otherAccount, double amount) {
        if (amount <= balance) {
            balance -= amount;
            otherAccount.deposit(amount);
            System.out.println("Transfer Successful, Current Balance is: " + balance);
            addTransactionToHistory("Transfer to " + otherAccount.getAccountNumber() + ": -" + amount);
            otherAccount.addTransactionToHistory("Transfer from " + accountNumber + ": +" + amount);
        } else {
            System.out.println("Insufficient Balance!!, Current Balance is: " + balance);
        }
    }

    private void addTransactionToHistory(String description) {
        Date timestamp = new Date();
        Transaction transaction = new Transaction(timestamp, description);
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getTimestamp() + " - " + transaction.getDescription());
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("Choose Your Option:");
            System.out.println("Create new account 1");
            System.out.println("Select existing account 2");
            System.out.println("Exit 3");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Account Holder Name:");
                    String accountHolder = sc.next();

                    System.out.println("Enter Account Number:");
                    String accountNumber = sc.next();

                    System.out.println("Enter The Initial Balance:");
                    double initialBalance = sc.nextDouble();

                    BankAccount account = new BankAccount(accountHolder, accountNumber, initialBalance);
                    accounts.add(account);
                    break;
                    case 2:
                    System.out.println("Enter Account Number:");
                    String selectedAccountNumber = sc.next();

                    BankAccount selectedAccount = null;
                    for (BankAccount account1 : accounts) {
                        if (account1.getAccountNumber().equals(selectedAccountNumber)) {
                            selectedAccount = account1;
                            break;
                        }
                    }

                    if (selectedAccount == null) {
                        System.out.println("Invalid Account Number!!");
                        break;
                    }

                    int accountChoice;
                    do {
                        System.out.println("\nSelected Account:");
                        selectedAccount.displayDetails();
                        System.out.println("\nMenu:");
                        System.out.println("Choose Your Option:");
                        System.out.println("Account Details 1");
                        System.out.println("Deposit 2");
                        System.out.println("Withdraw 3");
                        System.out.println("Transfer 4");
                        System.out.println("Transaction History 5");
                        System.out.println("Exit 6");
                        accountChoice = sc.nextInt();

                        switch (accountChoice) {
                            case 1:
                                selectedAccount.displayDetails();
                                break;
                            case 2:
                                System.out.println("Enter The amount:");
                                double amount = sc.nextDouble();
                                selectedAccount.deposit(amount);
                                break;
                            case 3:
                                System.out.println("Enter The Amount:");
                                double withdrawAmount = sc.nextDouble();
                                selectedAccount.withdraw(withdrawAmount);
                                break;
                            case 4:
                                System.out.println("Enter the Account Number to transfer to:");
                                String transferAccountNumber = sc.next();

                                BankAccount transferAccount = null;
                                for (BankAccount acc : accounts) {
                                    if (acc.getAccountNumber().equals(transferAccountNumber)) {
                                        transferAccount = acc;
                                        break;
                                    }
                                }

                                if (transferAccount == null) {
                                    System.out.println("Invalid Account Number!");
                                   
                                    break;
                                }

                                System.out.println("Enter the amount to transfer:");
                                double transferAmount = sc.nextDouble();
                                selectedAccount.transfer(transferAccount, transferAmount);
                                break;
                            case 5:
                                selectedAccount.displayTransactionHistory();
                                break;
                            case 6:
                                System.out.println("Exiting...");
                                break;
                            default:
                                System.out.println("Invalid Input!!");
                        }
                    } while (accountChoice != 6);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Input!!");
            }
        } while (choice != 3);

        sc.close();
    }
}

