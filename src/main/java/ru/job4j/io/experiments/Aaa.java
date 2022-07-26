package ru.job4j.io.experiments;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Aaa {

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Path.of("I:\\5. Видео-каша"), new Bbb());
        var fileMap = Bbb.getMap();
        double allMass = 0;
        for (var file : fileMap.entrySet()) {
            if (file.getValue().size() > 1) {
                allMass = allMass + (file.getKey() * file.getValue().size() - 1);
                System.out.println("размер " + file.getKey() / (1024 * 1024));
                System.out.println("шт " + file.getValue().size());
                System.lineSeparator();
            }
        }
        System.out.println(allMass / (1024 * 1024));
    }

    public static class Bbb extends SimpleFileVisitor<Path> {
        private static HashMap<Long, List<Path>> map = new HashMap<>();

        public static HashMap<Long, List<Path>> getMap() {
            return map;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!map.containsKey(attrs.size())) {
                List<Path> paths = new ArrayList<>();
                paths.add(file.toAbsolutePath());
                map.put(attrs.size(), paths);
            } else {
                List<Path> paths = map.get(attrs.size());
                paths.add(file.toAbsolutePath());
                map.put(attrs.size(), paths);
            }
            return CONTINUE;
        }
    }
}
