package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
        System.lineSeparator();
    }

    @Test
    void isThisTetr() {
        Box box = new Box(4, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron");
        System.lineSeparator();
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube");
        System.lineSeparator();
    }

    @Test
    void returnVerticles() {
        Box box = new Box(8, 10);
        int vertexCount = box.getNumberOfVertices();
        assertThat(vertexCount).isEqualTo(8);
        System.lineSeparator();
    }

    @Test
    void returnZeroVerticles() {
        Box box = new Box(0, 10);
        int vertexCount = box.getNumberOfVertices();
        assertThat(vertexCount).isEqualTo(0);
        System.lineSeparator();
    }

    @Test
    void isExistBoxExist() {
        Box box = new Box(0, 10);
        boolean boxExist = box.isExist();
        assertThat(boxExist).isTrue();
        System.lineSeparator();
    }

    @Test
    void isNonexistentBoxExist() {
        Box box = new Box(-1, 10);
        boolean boxExist = box.isExist();
        assertThat(boxExist).isFalse();
        System.lineSeparator();
    }

    @Test
    void sphereArea() {
        Box box = new Box(0, 10);
        double boxArea = box.getArea();
        assertThat(boxArea).isBetween(1255d, 1257d);
        System.lineSeparator();
    }

    @Test
    void tetrArea() {
        Box box = new Box(4, 10);
        double boxArea = box.getArea();
        assertThat(boxArea).isEqualTo(Math.sqrt(3) * (10 * 10));
        System.lineSeparator();
    }

    @Test
    void cubeArea() {
        Box box = new Box(8, 10);
        double boxArea = box.getArea();
        assertThat(boxArea).isEqualTo(6 * (10 * 10));
        System.lineSeparator();
    }
}