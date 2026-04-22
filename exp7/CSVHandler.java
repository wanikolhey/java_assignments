import java.io.*;
import java.util.*;

public class CSVHandler {
    private String filepath;

    public CSVHandler(String filepath) {
        this.filepath = filepath;
    }

    public void readFromCSV() {
        System.out.println("\n========== READ OPERATION ==========");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            System.out.println("✓ File read successfully:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("✗ IOException while reading from CSV file: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }

    public void addStudentRow(String data) {
        System.out.println("\n========== CREATE OPERATION ==========");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("✓ New student row added successfully: " + data);
        } catch (IOException e) {
            System.err.println("✗ IOException while adding student row: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }
    public void deleteRowFromCSV(int rowIndex) {
        System.out.println("\n========== DELETE OPERATION ==========");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            StringBuilder content = new StringBuilder();
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex != rowIndex) {
                    content.append(line).append(System.lineSeparator());
                }
                currentIndex++;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(content.toString());
                System.out.println("✓ Row at index " + rowIndex + " deleted successfully.");
            } catch (IOException e) {
                System.err.println("✗ IOException while writing to CSV file: " + e.getClass().getName());
                System.err.println("  Error Message: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("✗ IOException while reading from CSV file: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }

    public void updateRowInCSV(int rowIndex, String newData) {
        System.out.println("\n========== UPDATE OPERATION ==========");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            StringBuilder content = new StringBuilder();
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex == rowIndex) {
                    content.append(newData).append(System.lineSeparator());
                } else {
                    content.append(line).append(System.lineSeparator());
                }
                currentIndex++;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(content.toString());
                System.out.println("✓ Row at index " + rowIndex + " updated successfully.");
            } catch (IOException e) {
                System.err.println("✗ IOException while writing to CSV file: " + e.getClass().getName());
                System.err.println("  Error Message: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("✗ IOException while reading from CSV file: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }

    public void insertRowInCSV(int rowIndex, String newData) {
        System.out.println("\n========== CREATE OPERATION (INSERT AT INDEX) ==========");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            StringBuilder content = new StringBuilder();
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex == rowIndex) {
                    content.append(newData).append(System.lineSeparator());
                }
                content.append(line).append(System.lineSeparator());
                currentIndex++;
            }

            if (rowIndex >= currentIndex) {
                content.append(newData).append(System.lineSeparator());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(content.toString());
                System.out.println("✓ Row inserted at index " + rowIndex + " successfully.");
            } catch (IOException e) {
                System.err.println("✗ IOException while writing to CSV file: " + e.getClass().getName());
                System.err.println("  Error Message: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("✗ IOException while reading from CSV file: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }

    // Calculate percentage based on 5 marks
    public double calculatePercentage(int marks1, int marks2, int marks3, int marks4, int marks5) {
        double average = (marks1 + marks2 + marks3 + marks4 + marks5) / 5.0;
        return average;
    }

    // Update all student percentages based on their marks
    public void updateAllPercentages() {
        System.out.println("\n========== UPDATE PERCENTAGES ==========");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                for (int i = 0; i < lines.size(); i++) {
                    String currentLine = lines.get(i);

                    // Skip header row
                    if (i == 0) {
                        writer.write(currentLine);
                        writer.newLine();
                        continue;
                    }

                    String[] parts = currentLine.split(",");
                    if (parts.length >= 8) {
                        try {
                            int marks1 = Integer.parseInt(parts[3].trim());
                            int marks2 = Integer.parseInt(parts[4].trim());
                            int marks3 = Integer.parseInt(parts[5].trim());
                            int marks4 = Integer.parseInt(parts[6].trim());
                            int marks5 = Integer.parseInt(parts[7].trim());

                            double percentage = calculatePercentage(marks1, marks2, marks3, marks4, marks5);

                            // Rebuild the line with updated percentage
                            String updatedLine = parts[0] + "," + parts[1] + "," + parts[2] + "," +
                                    parts[3] + "," + parts[4] + "," + parts[5] + "," +
                                    parts[6] + "," + parts[7] + "," + String.format("%.1f", percentage);

                            writer.write(updatedLine);
                            writer.newLine();
                            System.out.println("  Student " + parts[0] + " (" + parts[1] + "): Percentage = " + String.format("%.1f", percentage) + "%");
                        } catch (NumberFormatException e) {
                            // If parsing fails, keep the original line
                            writer.write(currentLine);
                            writer.newLine();
                        }
                    } else {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                }
                System.out.println("✓ All percentages updated successfully.");
            } catch (IOException e) {
                System.err.println("✗ IOException while writing to CSV file: " + e.getClass().getName());
                System.err.println("  Error Message: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("✗ IOException while reading from CSV file: " + e.getClass().getName());
            System.err.println("  Error Message: " + e.getMessage());
        }
    }

}