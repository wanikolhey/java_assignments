package exp6;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Employee> employeeMap = new HashMap<>();

        employeeMap.put(1, new FullTimeEmployee("Alice", "PAN123", LocalDate.of(2020, 1, 15), "Software Engineer", 101, "SWE", 60000, 5000, 2000));
        employeeMap.put(2, new Manager("Bob", "PAN456", LocalDate.of(2019, 3, 10), "Project Manager", 102, "HR", 80000, 10000, 5000, 3000, 2000));
        employeeMap.put(3, new ContractEmployee("Charlie", "PAN789", LocalDate.of(2021, 6, 1), "Contractor", 103, 160, 50));
        employeeMap.put(4, new InternEmployee("Diana", "PAN234", LocalDate.of(2024, 7, 1), "Intern", 104, 18000, "Alice"));
        employeeMap.put(5, new PartTimeEmployee("Ethan", "PAN567", LocalDate.of(2022, 4, 5), "Part-time Analyst", 105, 80, 40, 2500));
        employeeMap.put(6, new SeniorManager("Farah", "PAN890", LocalDate.of(2017, 11, 20), "Senior Manager", 106,
                "HR", 110000, 15000, 10000, 6000, 5000, 12, 12000));

        System.out.println("=== All Employees ===");
        printEmployees(new ArrayList<>(employeeMap.values()));

        System.out.println("\n=== Managers (simple type check) ===");
        List<Employee> managers = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            if (employee instanceof Manager) {
                managers.add(employee);
            }
        }
        printEmployees(managers);

        System.out.println("\n=== CTC between 20000 and 90000 ===");
        List<Employee> ctcRange = new ArrayList<>();
        for (Employee employee : employeeMap.values()) {
            double ctc = employee.calculateCTC();
            if (ctc >= 20000 && ctc <= 90000) {
                ctcRange.add(employee);
            }
        }
        printEmployees(ctcRange);

        System.out.println("\n=== Total CTC By Employee Type ===");
        Map<String, Double> typeSummary = new HashMap<>();
        for (Employee employee : employeeMap.values()) {
            String type = employee.getClass().getSimpleName();
            double current = typeSummary.getOrDefault(type, 0.0);
            typeSummary.put(type, current + employee.calculateCTC());
        }
        for (Map.Entry<String, Double> entry : typeSummary.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    private static void printEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(
                    "Employee ID: " + employee.empID
                            + ", Name: " + employee.name
                            + ", Type: " + employee.getClass().getSimpleName()
                            + ", CTC: " + employee.calculateCTC());
        }
    }
}