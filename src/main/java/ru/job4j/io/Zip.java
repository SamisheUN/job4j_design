package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void validation(String[] args) {
        ArgsName argsNames = ArgsName.of(args);
        if (!new File(argsNames.get("d")).exists()) {
            throw new IllegalArgumentException("Directory not found.");
        }
        if (!(argsNames.get("e").charAt(0) == '.')) {
            throw new IllegalArgumentException("Incorrect excluded extension format.");
        }
    }

    public static void main(String[] args) {
        Zip zip = new Zip();
        validation(args);
        ArgsName argsNames = ArgsName.of(args);
        File zipFile = new File(argsNames.get("o"));
        List<Path> fileList = new ArrayList<>();
        try {
            fileList = Search.search(Path.of(argsNames.get("d")),
                    p -> !p.toFile().getName().endsWith(argsNames.get("e")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        zip.packFiles(fileList, zipFile);
    }

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(target)))) {
            for (var file : sources) {
                zip.putNextEntry(new ZipEntry(String.valueOf(file)));
                try (BufferedInputStream out = new BufferedInputStream(
                        new FileInputStream(String.valueOf(file)))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}