package exp6;
import java.time.LocalDate;

public class InternEmployee extends Employee {
    private double stipend;
    private String mentorName;

    public InternEmployee(String name, String pan, LocalDate joiningDate, String designation, Integer empID,
                          double stipend, String mentorName) {
        super(name, pan, joiningDate, designation, empID);
        this.stipend = stipend;
        this.mentorName = mentorName;
    }

    @Override
    public double calculateCTC() {
        return stipend;
    }

    public String getMentorName() {
        return mentorName;
    }
}