package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Andrey S";
        int age = 34;
        Long third = 3L;
        Double fourth = 4D;
        Short fifth = 5;
        Byte sixth = 6;
        char seventh = 7;
        Boolean eighth = true;
        var nextLine = System.lineSeparator();
        LOG.debug("{}User info: name: {}, age: {};{}3:{},{}4:{},{}5:{},{}6:{},{}7:{},{}8:{}",
                nextLine, name, age, nextLine, third, nextLine, fourth,
                nextLine, fifth, nextLine, sixth, nextLine, seventh, nextLine, eighth);
    }
}