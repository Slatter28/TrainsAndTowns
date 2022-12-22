package main.java.com.models;


public class nodo {

    private final int from;
    private final int to;
    private final int weight;

    public nodo(int from, int to, int weight) {
        if (from < 0)
            throw new IllegalArgumentException("Deben ser positivos");
        if (to < 0)
            throw new IllegalArgumentException("Deben ser positivos");
        if (weight < 0)
            throw new IllegalArgumentException("El peso debe ser positivo");
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public int weight() {
        return weight;
    }
}

