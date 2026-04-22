import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filepath = "Students.csv";
        
        CSVHandler csvHandler = new CSVHandler(filepath);

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           STUDENT RECORDS CRUD OPERATIONS                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        // READ Operation - Display initial data
        System.out.println("\n--- Step 1: Reading Initial Data ---");
        csvHandler.readFromCSV();

        // CREATE Operation - Add 3 new student rows with marks4 and marks5 as 0
        System.out.println("\n--- Step 2: Creating 3 New Student Records (with marks4 and marks5 = 0) ---");
        csvHandler.addStudentRow("103,Amit Patel,Mechanical,88,86,84,0,0");
        csvHandler.addStudentRow("104,Neha Singh,Electrical,92,89,91,0,0");
        csvHandler.addStudentRow("105,Vikram Reddy,Civil,79,81,78,0,0");

        // READ Operation - Display data after adding new rows
        System.out.println("\n--- Step 3: Reading Data After Adding New Students ---");
        csvHandler.readFromCSV();

        // UPDATE Operation - Update new students with marks4 and marks5 values
        System.out.println("\n--- Step 4: Updating New Students with Marks4 and Marks5 ---");
        System.out.println("\n========== UPDATE OPERATION ==========");
        csvHandler.updateRowInCSV(3, "103,Amit Patel,Mechanical,88,86,84,87,85");
        System.out.println("✓ Updated Student 103 with marks4=87, marks5=85");
        
        System.out.println("\n========== UPDATE OPERATION ==========");
        csvHandler.updateRowInCSV(4, "104,Neha Singh,Electrical,92,89,91,90,94");
        System.out.println("✓ Updated Student 104 with marks4=90, marks5=94");
        
        System.out.println("\n========== UPDATE OPERATION ==========");
        csvHandler.updateRowInCSV(5, "105,Vikram Reddy,Civil,79,81,78,80,82");
        System.out.println("✓ Updated Student 105 with marks4=80, marks5=82");

        // READ Operation - Display data after updates
        System.out.println("\n--- Step 5: Reading Data After Updates ---");
        csvHandler.readFromCSV();

        // Calculate and update all percentages
        System.out.println("\n--- Step 6: Calculating and Updating Percentages ---");
        csvHandler.updateAllPercentages();

        // READ Operation - Display final data with percentages
        System.out.println("\n--- Step 7: Reading Final Data with Updated Percentages ---");
        csvHandler.readFromCSV();

        // DELETE Operation - Delete one student record (index 2)
        System.out.println("\n--- Step 8: Deleting a Student Record ---");
        csvHandler.deleteRowFromCSV(2);

        // READ Operation - Display final data after deletion
        System.out.println("\n--- Step 9: Final Data After Deletion ---");
        csvHandler.readFromCSV();

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           ALL CRUD OPERATIONS COMPLETED SUCCESSFULLY            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }
}
