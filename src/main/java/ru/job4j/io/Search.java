package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        argsQuantityValidation(args.length);
        argsQualityValidation(Path.of(args[0]), args[1]);
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static void argsQuantityValidation(int length) {
        if (length < 2) {
            throw new IllegalArgumentException("Not enough required arguments to start search.");
        }
    }

    private static void argsQualityValidation(Path path, String extention) {
        File file = path.toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format(
                    "Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "Not directory %s", file.getAbsoluteFile()));
        }
        if (extention.length() < 1) {
            throw new IllegalArgumentException(
                    "End of file name parameter is too short.");
        }
    }
}