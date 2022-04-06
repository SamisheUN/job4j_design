package ru.job4j.io;

import java.io.File;

public class Dir {
    public static void main(String[] args) {
        File file = new File("c:\\projects");
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsoluteFile()));
        }
        for (File subfile : file.listFiles()) {
            if (!subfile.isDirectory()) {
                System.out.println(String.format("File %s size is about: %d Kb",
                        subfile.getName(),
                        (int) (subfile.length() * 0.00097656)));
            } else {
                System.out.println(String.format("Directory %s size misidentified as: %d Kb",
                        subfile.getName(),
                        ((int) (subfile.length() * 0.00097656))));
            }
        }
    }
}
