package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Andrey S";
        int age = 34;
        long third = 3L;
        double fourth = 4D;
        short fifth = 5;
        byte sixth = 6;
        char seventh = 7;
        boolean eighth = true;
        float ninth = 9F;
        var nextLine = System.lineSeparator();
        LOG.debug(
                "{}User info: name: {}, age: {};{}3:{},{}4:{},{}5:{},{}6:{},{}7:{},{}8:{},{}9:{}.",
                nextLine, name, age, nextLine, third, nextLine, fourth,
                nextLine, fifth, nextLine, sixth, nextLine,
                seventh, nextLine, eighth, nextLine, ninth
        );
    }
}