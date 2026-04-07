package exp6;
import java.time.LocalDate;

public class FullTimeEmployee extends Employee {
    protected String role;
    protected double baseSalary;
    protected double perfBonus;
    protected double hiringCommission;

    public FullTimeEmployee(String name, String pan, LocalDate joiningDate, String designation, Integer empID,
                            String role, double baseSalary, double perfBonus, double hiringCommission) {
        super(name, pan, joiningDate, designation, empID);
        this.role = role;
        this.baseSalary = baseSalary;
        this.perfBonus = perfBonus;
        this.hiringCommission = hiringCommission;
    }

    @Override
    public double calculateCTC() {
        if ("SWE".equalsIgnoreCase(role)) {
            return baseSalary + perfBonus;
        } else if ("HR".equalsIgnoreCase(role)) {
            return baseSalary + hiringCommission;
        } else {
            return baseSalary;
        }
    }
}