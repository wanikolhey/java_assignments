package exp4;

public class Vector {
    private double[] components;

    public Vector(double[] newV) throws InvalidVectorException {
        if (newV == null || newV.length == 0) {
            throw new InvalidVectorException("Vector components cannot be null or empty.");
        }
        if (newV.length != 2 && newV.length != 3) {
            throw new InvalidVectorException("Vector can only have 2 or 3 components.");
        }
        this.components = newV.clone();
    }

    private void checkDimension(Vector other) throws InvalidVectorException {
        if (this.components.length != other.components.length) {
            throw new InvalidVectorException("Vectors must have the same dimension.");
        }
    }

    public Vector add(Vector other) throws InvalidVectorException {
        checkDimension(other);
        double[] result = new double[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            result[i] = this.components[i] + other.components[i];
        }
        return new Vector(result);
    }

    public Vector subtract(Vector other) throws InvalidVectorException {
        checkDimension(other);
        double[] result = new double[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            result[i] = this.components[i] - other.components[i];
        }
        return new Vector(result);
    }

    public double dotProduct(Vector other) throws InvalidVectorException {
        checkDimension(other);
        double result = 0;
        for (int i = 0; i < this.components.length; i++) {
            result += this.components[i] * other.components[i];
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < components.length; i++) {
            sb.append(components[i]);
            if (i < components.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}