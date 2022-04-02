package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analyze {
    public static void main(String[] args) {
        unavailable("./server.log", "./unavailable.csv");
    }

    private static boolean isLineAboutShutdown(String line) {
        return '4' == line.charAt(0) || '5' == line.charAt(0);
    }

    public static void unavailable(String source, String target) {
        List<String> resultList = new ArrayList<>();
        String[] lineAsArray;
        boolean isOn = true;
        String currentOfflinePeriod = "--:--:--";
        try (BufferedReader read = new BufferedReader(new FileReader(source))) {
            for (String line = read.readLine();
                 line != null; line = read.readLine()) {
                if (isOn && isLineAboutShutdown(line)) {
                    isOn = false;
                    lineAsArray = line.split(" ");
                    currentOfflinePeriod = lineAsArray[1];
                } else if (!isOn && !isLineAboutShutdown(line)) {
                    isOn = true;
                    lineAsArray = line.split(" ");
                    currentOfflinePeriod = currentOfflinePeriod + ";" + lineAsArray[1];
                    resultList.add(currentOfflinePeriod);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            for (String line : resultList) {
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}