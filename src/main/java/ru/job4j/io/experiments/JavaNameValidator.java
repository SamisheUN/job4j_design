package ru.job4j.io.experiments;

import static java.lang.Character.isDigit;

public class JavaNameValidator {
    public static boolean isNameValid(String name) {
        boolean rsl = false;
        if (name.isEmpty()
                || !isLowerLatinLetter(name.codePointAt(0))) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            if (isUpperLatinLetter(name.codePointAt(i))
                    || isSpecialSymbol(name.codePointAt(i))
                    || isLowerLatinLetter(name.codePointAt(i))
                    || isDigit(name.charAt(i))) {
                rsl = true;
            } else {
                rsl = false;
                break;
            }
        }
        return rsl;
    }

    public static boolean isSpecialSymbol(int code) {
        return (code == 36 || code == 95);
    }

    public static boolean isUpperLatinLetter(int code) {
        return code >= 65 && code <= 95;
    }

    public static boolean isLowerLatinLetter(int code) {
        return code >= 97 && code <= 122;
    }
}