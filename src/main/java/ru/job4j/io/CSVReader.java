package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    private static void argsQualityValidation(String path, String delimiter,
                                              String filter, String out) {
        if (!new File(path).exists()) {
            throw new IllegalArgumentException(String.format(
                    "Not exist %s", path));
        }
        if (delimiter.length() != 1) {
            throw new IllegalArgumentException("Incorrect delimiter.");
        }
        if (filter.length() < 1) {
            throw new IllegalArgumentException("Filter is too short.");
        }
        if (!out.equals("stdout")) {
            try {
                Paths.get(path);
            } catch (InvalidPathException | NullPointerException ex) {
                throw new IllegalArgumentException("Target path is incorrect.");
            }
        }
    }

    public static void handle(ArgsName argsNames) throws Exception {
        String path = argsNames.get("path");
        String delimiter = argsNames.get("delimiter");
        String filter = argsNames.get("filter");
        String out = argsNames.get("out");
        argsQualityValidation(path, delimiter, filter, out);
        Scanner scanner = new Scanner(Paths.get(path));
        scanner.useDelimiter(System.lineSeparator());
        String[] filters = filter.split(",");
        List<Integer> importantColumns = new ArrayList<>();
        StringBuilder rsl = new StringBuilder();
        boolean isLineTitle = true;
        while (scanner.hasNext()) {
            String[] lineAsList = scanner.next().split(delimiter);
            if (isLineTitle) {
                importantColumns = siftColumnNums(lineAsList, filters);
                isLineTitle = false;
            }
            for (int i = 0; i < importantColumns.size(); i++) {
                rsl.append(lineAsList[importantColumns.get(i)]);
                if (i < importantColumns.size() - 1) {
                    rsl.append(delimiter);
                } else if (scanner.hasNext()) {
                    rsl.append(System.lineSeparator());
                }
            }
        }
        printResult(rsl, out);
        scanner.close();
    }

    /**
     * Отсев номеров колонок, согласно фильтрам.
     *
     * @param line       титульная строка из файла
     * @param filtersArr массив фильтров, указанных для вывода
     * @return лист с номерами столбцов указанных в фильтрах
     * (только те что нужно вывести)
     */
    private static List<Integer> siftColumnNums(String[] line, String[] filtersArr) {
        List<Integer> importantColumns = new ArrayList<>();
        for (int filterIndex = 0; filterIndex < filtersArr.length; filterIndex++) {
            for (int lineIndex = 0; lineIndex < line.length; lineIndex++) {
                if (line[lineIndex].equals(filtersArr[filterIndex])) {
                    importantColumns.add(lineIndex);
                }
            }
        }
        return importantColumns;
    }

    /**
     * Вывод строк в файл или в консоль в зависимости от параметра.
     *
     * @param pathOrKey содержит либо путь к файлу вывода,
     *                  либо значение stdout для вывода в консоль.
     */
    private static void printResult(StringBuilder line, String pathOrKey) {
        if (pathOrKey.equals("stdout")) {
            System.out.print(line + System.lineSeparator());
        } else {
            try (FileOutputStream fos = new FileOutputStream(pathOrKey);
                 PrintStream ps = new PrintStream(fos)) {
                ps.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ArgsName argNames = ArgsName.of(args);
        try {
            handle(argNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
