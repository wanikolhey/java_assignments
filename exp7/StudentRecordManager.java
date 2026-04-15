import java.io.*;
import java.util.*;

public class StudentRecordManager {

    static final String FILE_NAME = "Students.csv";
    static Scanner sc = new Scanner(System.in);

    // ── Create File ─────────────────────────────────────────────
    static void createFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            bw.write("studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage");
            bw.newLine();

            bw.write("1,Aditya,CSE,80,85,90,88,96,0");
            bw.newLine();

            bw.write("2,Riya,IT,75,70,80,76,87,0");
            bw.newLine();

            System.out.println("File created successfully.");

        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Add Records ─────────────────────────────────────────────
    static void addRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            for (int i = 0; i < 3; i++) {
                System.out.print("ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Branch: ");
                String branch = sc.nextLine();

                System.out.print("Marks1: ");
                int m1 = Integer.parseInt(sc.nextLine());

                System.out.print("Marks2: ");
                int m2 = Integer.parseInt(sc.nextLine());

                System.out.print("Marks3: ");
                int m3 = Integer.parseInt(sc.nextLine());

                bw.write(id + "," + name + "," + branch + "," + m1 + "," + m2 + "," + m3 + ",0,0,0");
                bw.newLine();
            }

            System.out.println("3 records added.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Display ─────────────────────────────────────────────────
    static void display() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Records ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Update Marks4 & Marks5 ──────────────────────────────────
    static void updateMarks() {
        try {
            File file = new File(FILE_NAME);
            List<String> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            lines.add(br.readLine()); // header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                System.out.println("Updating for ID: " + p[0]);

                System.out.print("Marks4: ");
                p[6] = sc.nextLine();

                System.out.print("Marks5: ");
                p[7] = sc.nextLine();

                lines.add(String.join(",", p));
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Marks updated.");

        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Calculate Percentage ────────────────────────────────────
    static void calculatePercentage() {
        try {
            File file = new File(FILE_NAME);
            List<String> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            lines.add(br.readLine());

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                double sum = 0;
                for (int i = 3; i <= 7; i++) {
                    sum += Double.parseDouble(p[i]);
                }

                double percent = sum / 5;
                p[8] = String.format("%.2f", percent);

                lines.add(String.join(",", p));
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println("Percentage updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Delete Record ───────────────────────────────────────────
    static void deleteRecord() {
        try {
            System.out.print("Enter ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            File file = new File(FILE_NAME);
            List<String> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            lines.add(br.readLine());

            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                if (Integer.parseInt(p[0]) == id) {
                    found = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            System.out.println(found ? "Deleted successfully." : "ID not found.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // ── Exception Demo ──────────────────────────────────────────
    static void exceptionDemo() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("wrongfile.csv"));
        } catch (IOException e) {
            System.out.println("[EXCEPTION DEMO] " + e.getMessage());
        }
    }

    // ── MAIN MENU ───────────────────────────────────────────────
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== STUDENT CSV MENU ====");
            System.out.println("1. Create File");
            System.out.println("2. Add 3 Records");
            System.out.println("3. Display Records");
            System.out.println("4. Update Marks4 & Marks5");
            System.out.println("5. Calculate Percentage");
            System.out.println("6. Delete Record");
            System.out.println("7. Exception Demo");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            switch (ch) {
                case 1: createFile(); break;
                case 2: addRecords(); break;
                case 3: display(); break;
                case 4: updateMarks(); break;
                case 5: calculatePercentage(); break;
                case 6: deleteRecord(); break;
                case 7: exceptionDemo(); break;
                case 0:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}