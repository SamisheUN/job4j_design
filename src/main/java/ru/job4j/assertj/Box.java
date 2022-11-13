package ru.job4j.assertj;

public class Box {
    private static final String UNKNOWN = "Unknown object";
    private final int edge;
    private int vertex;
    private String type = "";

    public Box(int vertex, int edge) {
        this.vertex = vertex;
        this.edge = edge;
        init();
    }

    private void init() {
        switch (vertex) {
            case 0:
                type = "Sphere";
                break;
            case 4:
                type = "Tetrahedron";
                break;
            case 8:
                type = "Cube";
            default:
                type = UNKNOWN;
                break;
        }
        if (UNKNOWN.equals(type)) {
            vertex = -1;
        }
        if (edge <= 0) {
            vertex = -1;
            type = UNKNOWN;
        }
    }

    public String whatsThis() {
        return this.type;
    }

    public int getNumberOfVertices() {
        return this.vertex;
    }

    public boolean isExist() {
        return this.vertex != -1;
    }

    public double getArea() {
        double a = edge;
        return switch (vertex) {
            case 0 -> 4 * Math.PI * (a * a);
            case 4 -> Math.sqrt(3) * (a * a);
            case 8 -> 6 * (a * a);
            default -> 0;
        };
    }
}