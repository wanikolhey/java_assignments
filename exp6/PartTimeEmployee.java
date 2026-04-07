package exp6;
import java.time.LocalDate;

public class PartTimeEmployee extends ContractEmployee {
    private double fixedAllowance;

    public PartTimeEmployee(String name, String pan, LocalDate joiningDate, String designation, Integer empID,
                            int noOfHrs, double hourlyRate, double fixedAllowance) {
        super(name, pan, joiningDate, designation, empID, noOfHrs, hourlyRate);
        this.fixedAllowance = fixedAllowance;
    }

    @Override
    public double calculateCTC() {
        return super.calculateCTC() + fixedAllowance;
    }
}