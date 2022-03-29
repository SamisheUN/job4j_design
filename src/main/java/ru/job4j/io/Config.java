package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }

    /**
     * Шаблон комментария: обязательно одиночный # и далее любые символы или конец строки
     * <p>
     * Шаблон верного функционального присвоения состоит из частей:
     * с начала строки любые символы, кроме #
     * (чтобы не читались комментарии подходящие под функциональные строки, но пробелом перед #)
     * обязательная группа больше одного буквенного (это и есть параметр)
     * затем любые символы (пробелы, или часть параметра)
     * обязательное =
     * любые символы, больше одного буквенного, затем любые до конца строки
     * <p>
     * Все остальные варианты - выброс исключения.
     */
    public boolean isLineInformative(String line) {
        boolean rsl = false;
        Pattern correctPattern = Pattern.compile("^(.[^#]+)?\\S+(.+)?=(.+)?\\S+(.+)?$");
        Pattern commentPattern = Pattern.compile("^#(.+)?$");
        if (line.length() > 0
                && !line.matches(String.valueOf(commentPattern))) {
            if (line.matches(String.valueOf(correctPattern))) {
                rsl = true;
            } else {
                throw new IllegalArgumentException("The line is incorrect.");
            }
        }
        return rsl;
    }

    public void load() {
        List<String> array = new ArrayList<>();
        String[] lineArr;
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(array::add);
            for (String line : array) {
                if (isLineInformative(line)) {
                    lineArr = line.split("=");
                    values.put(lineArr[0], lineArr[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        if (!values.containsKey(key)) {
            throw new UnsupportedOperationException("Don't impl this method yet!");
        }
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}