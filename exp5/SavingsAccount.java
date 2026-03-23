public class SavingsAccount extends Account {
    double interestRate;
    double minimumBalance;

    SavingsAccount(String accountNumber, Customer customer, double balance, double interestRate, double minimumBalance) {
        super(accountNumber, customer, balance, "Savings");
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
    void deposit(double amount) {
        balance += amount;
        System.out.println("Savings - Deposited: " + amount);
        System.out.println("New Balance: " + balance);
    }

    @Override
    void withdraw(double amount) {
        if (balance - amount < minimumBalance) {
            System.out.println("Cannot withdraw. Minimum balance of " + minimumBalance + " must be maintained.");
        } else {
            balance -= amount;
            System.out.println("Savings - Withdrawn: " + amount);
            System.out.println("New Balance: " + balance);
        }
    }

    void applyInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        System.out.println("Interest applied: " + interest);
        System.out.println("New Balance: " + balance);
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Minimum Balance: " + minimumBalance);
    }
}
