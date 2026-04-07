package exp6;
import java.time.LocalDate;

public class ContractEmployee extends Employee {
    private int noOfHrs;
    private double hourlyRate;

    public ContractEmployee(String name, String pan, LocalDate joiningDate, String designation, Integer empID,
                            int noOfHrs, double hourlyRate) {
        super(name, pan, joiningDate, designation, empID);
        this.noOfHrs = noOfHrs;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateCTC() {
        return noOfHrs * hourlyRate;
    }
}