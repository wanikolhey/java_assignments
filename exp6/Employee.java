package exp6;
import java.time.LocalDate;

public abstract class Employee {
    public String name;
    private String pan;
    private LocalDate joiningDate;
    public String designation;
    public Integer empID;

    public Employee(String name, String pan, LocalDate joiningDate, String designation, Integer empID) {
        this.name = name;
        this.pan = pan;
        this.joiningDate = joiningDate;
        this.designation = designation;
        this.empID = empID;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public abstract double calculateCTC();
}