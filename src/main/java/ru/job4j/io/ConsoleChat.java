package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private List<String> fullLog = new ArrayList<>();
    private List<String> lisOfPhrases = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./chat.log", "./server.log");
        cc.run();
    }

    /**
     * run = 1 рабочий режим
     * run = 0 пауза
     * run = -1 отключение
     */
    public void run() {
        lisOfPhrases = readPhrases();
        Scanner in = new Scanner(System.in);
        int run = 1;
        while (run >= 0) {
            String command = in.nextLine();
            fullLog.add(command);
            switch (command) {
                case (OUT):
                    run = -1;
                    logAndPrint("Завершение работы.");
                    saveLog(fullLog);
                    break;
                case (STOP):
                    if (run == 1) {
                        run = 0;
                        logAndPrint("Остановлено.");
                    }
                    break;
                case (CONTINUE):
                    if (!(run == 1)) {
                        run = 1;
                        logAndPrint("Продолжение работы.");
                    }
                    break;
                default:
                    if (run == 1) {
                        logAndPrint(rndPhrase(lisOfPhrases));
                    }
                    break;
            }
        }

    }

    private String rndPhrase(List<String> listOfPhrases) {
        return String.valueOf(listOfPhrases.get(
                (int) (Math.random() * (listOfPhrases.size()))));
    }

    private List<String> readPhrases() {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader((new FileReader(botAnswers)))) {
            br.lines().map(s -> s + System.lineSeparator()).forEach(list::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            log.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logAndPrint(String line) {
        fullLog.add(line);
        System.out.println(line);
    }
}