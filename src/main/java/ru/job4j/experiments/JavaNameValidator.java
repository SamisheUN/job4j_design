package ru.job4j.experiments;

import static java.lang.Character.isDigit;

public class JavaNameValidator {
    public static boolean isNameValid(String name) {
        boolean rsl = true;
        int currentSymbolCode;
        if (name.isEmpty()
                || !isLowerLatinLetter(name.codePointAt(0))) {
            return false;
        }
        for (int i = 1; i < name.length(); i++) {
            currentSymbolCode = name.codePointAt(i);
            if (!(isUpperLatinLetter(currentSymbolCode)
                    || isSpecialSymbol(currentSymbolCode)
                    || isLowerLatinLetter(currentSymbolCode)
                    || isDigit(name.charAt(i)))) {
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