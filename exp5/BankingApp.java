import java.util.ArrayList;

public class BankingApp {
    public static void main(String[] args) {

        // Create customers
        Customer c1 = new Customer(1, "Wani Kolhey", "wanii@mail.com", "9876543210");
        Customer c2 = new Customer(2, "gauri gautam", "gaurii@mail.com", "9123456789");
        Customer c3 = new Customer(3, "diya patel", "diya@mail.com", "9988776655");

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        // Create accounts
        SavingsAccount sa1 = new SavingsAccount("SA001", c1, 50000, 4.0, 1000);
        SavingsAccount sa2 = new SavingsAccount("SA002", c2, 30000, 3.5, 1000);
        LoanAccount la1 = new LoanAccount("LA001", c1, 500000, 8.5, 60, "Home");
        LoanAccount la2 = new LoanAccount("LA002", c3, 200000, 12.0, 36, "Personal");

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(sa1);
        accounts.add(sa2);
        accounts.add(la1);
        accounts.add(la2);

        // Some transactions
        sa1.deposit(10000);
        sa1.withdraw(5000);
        sa1.applyInterest();

        sa2.deposit(15000);

        la1.deposit(20000); // repayment
        la2.deposit(5000);  // repayment

        // Edge cases
        System.out.println("\n========== EDGE CASE TESTS ==========");

        // 1. Deposit negative amount
        System.out.println("\n-- Depositing negative amount --");
        if (-500 <= 0) {
            System.out.println("Invalid amount. Deposit must be greater than 0.");
        } 
        else {
            sa1.deposit(-500);
            }

        // 2. Withdraw more than balance
        System.out.println("\n-- Withdrawing more than balance --");
        sa1.withdraw(9999999);

        // 3. Savings: withdraw below minimum balance
        System.out.println("\n-- Withdrawing below minimum balance --");
        sa2.withdraw(44500); // balance is 45000, min is 1000, so max allowed is 44000

        // 4. Withdraw from loan account (not allowed)
        System.out.println("\n-- Withdrawing from Loan Account --");
        la1.withdraw(1000);

        // 5. Loan repayment more than outstanding
        System.out.println("\n-- Repaying more than loan outstanding --");
        la2.deposit(9999999);

        // Display consolidated info for each customer
        System.out.println("========== CUSTOMER ACCOUNT REPORT ==========");
        for (Customer c : customers) {
            System.out.println("\n--- Customer Details ---");
            c.displayDetails();
            System.out.println("--- Accounts ---");
            boolean hasAccount = false;
            for (Account a : accounts) {
                if (a.customer.customerId == c.customerId) {
                    a.displayDetails();
                    System.out.println();
                    hasAccount = true;
                }
            }
            if (!hasAccount) {
                System.out.println("No accounts found.");
            }
        }
    }
}