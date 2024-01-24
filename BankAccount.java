import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BankAccount {
    private int accountId;
    private int pin;
    private String name;
    private double balance;

    public BankAccount(int accountId, int pin, String name, double balance) {
        this.accountId = accountId;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit approved. New balance: P" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal approved. New balance: P" + balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds");
            return false;
        }
    }

    public boolean transfer(BankAccount receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("Transfer approved!");
            return true;
        } else {
            System.out.println("Transfer failed! Try again.");
            return false;
        }
    }
}

class BankingApp {
    private static Map<Integer, BankAccount> accounts = new HashMap<>();
    private static BankAccount loggedInAccount;

    public static void main(String[] args) {
        initializeAccounts();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Bank!");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    System.out.println("Exiting Bank. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please Try Later.");
            }
        }
    }

    private static void initializeAccounts() {
        accounts.put(412435, new BankAccount(412435, 7452, "Chris Sandoval", 32000.0));
        accounts.put(264863, new BankAccount(264863, 1349, "Marc Yim", 1000.0));
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter account ID here: ");
        int accountId = scanner.nextInt();

        System.out.print("Enter PIN here: ");
        int pin = scanner.nextInt();

        BankAccount account = accounts.get(accountId);

        if (account != null && account.getPin() == pin) {
            loggedInAccount = account;
            System.out.println("Login successful. Hello, " + loggedInAccount.getName() + "!");
            showMainMenu(scanner);
        } else {
            System.out.println("Invalid account ID or PIN. Please Retry.");
        }
    }

    private static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Cash-in");
            System.out.println("3. Money Transfer");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    cashIn(scanner);
                    break;
                case 3:
                    moneyTransfer(scanner);
                    break;
                case 4:
                    System.out.println("Logging out. Thank you, " + loggedInAccount.getName() + "!");
                    loggedInAccount = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void checkBalance() {
        System.out.println("Your current balance: P" + loggedInAccount.getBalance());
    }

    private static void cashIn(Scanner scanner) {
        System.out.print("Enter the amount to deposit: P");
        double amount = scanner.nextDouble();

        loggedInAccount.deposit(amount);
    }

    private static void moneyTransfer(Scanner scanner) {
        System.out.print("Enter recievers's account ID: ");
        int receiverId = scanner.nextInt();

        BankAccount receiver = accounts.get(receiverId);

        if (receiver != null && receiver != loggedInAccount) {
            System.out.print("Enter the amount to transfer: P");
            double amount = scanner.nextDouble();

            loggedInAccount.transfer(receiver, amount);
        } else {
            System.out.println("Invalid receiver account ID");
        }
    }
}


