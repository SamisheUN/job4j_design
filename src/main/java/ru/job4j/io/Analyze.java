package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analyze {
    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLineAboutShutdown(String line) {
        return '4' == line.charAt(0) || '5' == line.charAt(0);
    }

    public void unavailable(String source, String target) {
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
        writeToFile(resultList, target);
    }

    private void writeToFile(List<String> list, String target) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            for (String line : list) {
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}