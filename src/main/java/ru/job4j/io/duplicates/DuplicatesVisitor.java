package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private HashMap<FileProperty, List<Path>> filesCatalog = new HashMap<>();

    public void showDups() {
        for (var val : filesCatalog.values()) {
            if (val.size() > 1) {
                for (var filePa : val) {
                    System.out.println(filePa);
                }
                System.out.println();
            }
        }
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes atts) throws IOException {
        FileProperty fileProperties =
                new FileProperty(atts.size(), filePath.getFileName().toString());
        List<Path> fileCopiesList = new ArrayList<>();
        FileVisitResult rsl = FileVisitResult.CONTINUE;
        if (filesCatalog.containsKey(fileProperties)) {
            fileCopiesList = filesCatalog.get(fileProperties);
            fileCopiesList.add(filePath);
            rsl = super.visitFile(filePath, atts);
        } else {
            fileCopiesList.add(filePath);
        }
        filesCatalog.put(fileProperties, fileCopiesList);
        return rsl;
    }
}
