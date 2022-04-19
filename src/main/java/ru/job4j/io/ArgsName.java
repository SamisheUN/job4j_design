package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[]{"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(
                    String.format("Key %s not present in argument list", key));
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        for (var arg : args) {
            entryFormatValidation(arg);
            arg = arg.substring(1);
            String[] splittedLine = arg.split("=", 2);
            values.put(splittedLine[0], splittedLine[1]);

        }
    }

    private void entryFormatValidation(String line) {
        if (!Pattern.matches("^-(.+)=(.+)$", line)) {
            throw new IllegalArgumentException(
                    String.format("Incorrect entry format at: %s", line));
        }
    }
}