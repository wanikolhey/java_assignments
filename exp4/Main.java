package exp4;

public class Main {
    public static void main(String[] args) {
        try {
            double[] v1 = {1, 2, 3};
            double[] v2 = {4, 5, 6};

            Vector vec1 = new Vector(v1);
            Vector vec2 = new Vector(v2);

            Vector sum = vec1.add(vec2);
            Vector diff = vec1.subtract(vec2);
            double dot = vec1.dotProduct(vec2);

            System.out.println("Vector 1: " + vec1);
            System.out.println("Vector 2: " + vec2);
            System.out.println("Sum: " + sum);
            System.out.println("Difference: " + diff);
            System.out.println("Dot Product: " + dot);

        } catch (InvalidVectorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}