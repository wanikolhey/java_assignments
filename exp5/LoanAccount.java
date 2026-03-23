public class LoanAccount extends Account {
    double principalAmount;
    double interestRate;
    int tenureMonths;
    String loanType;

    LoanAccount(String accountNumber, Customer customer, double principalAmount, double interestRate, int tenureMonths, String loanType) {
        super(accountNumber, customer, principalAmount, "Loan");
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.loanType = loanType;
    }

    @Override
    void deposit(double amount) {
        // deposit means repayment in loan account
        if (amount > balance) {
            System.out.println("Repayment exceeds outstanding loan amount.");
        } else {
            balance -= amount;
            System.out.println("Loan - Repayment of: " + amount);
            System.out.println("Remaining Loan: " + balance);
        }
    }

    @Override
    void withdraw(double amount) {
        System.out.println("Withdrawal not allowed on Loan Account.");
    }

    double calculateEmi() {
        double r = interestRate / 100 / 12;
        double emi = principalAmount * r * Math.pow(1 + r, tenureMonths) / (Math.pow(1 + r, tenureMonths) - 1);
        return Math.round(emi * 100.0) / 100.0;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Loan Type: " + loanType);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Tenure: " + tenureMonths + " months");
        System.out.println("EMI: " + calculateEmi());
        System.out.println("Outstanding: " + balance);
    }
}
