package ru.job4j.io;

import java.io.*;

public class Analyze {
    public static void main(String[] args) {
        new Analyze().unavailable("./server.log", "./unavailable.csv");
    }

    private boolean isLineAboutShutdown(String line) {
        return '4' == line.charAt(0) || '5' == line.charAt(0);
    }

    public void unavailable(String source, String target) {
        String[] lineAsArray;
        boolean isOn = true;
        String currentOfflinePeriod = "--:--:--";
        try (BufferedReader read = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(
                             new FileOutputStream(target)
                     ))) {
            for (String line = read.readLine();
                 line != null; line = read.readLine()) {
                if (isOn && isLineAboutShutdown(line)) {
                    isOn = false;
                    lineAsArray = line.split(" ");
                    currentOfflinePeriod = lineAsArray[1];
                } else if (!isOn && !isLineAboutShutdown(line)) {
                    isOn = true;
                    lineAsArray = line.split(" ");
                    currentOfflinePeriod = currentOfflinePeriod + ";"
                            + lineAsArray[1] + ";";
                    out.println(currentOfflinePeriod);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}