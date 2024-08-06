package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.birthday = birthday;
        this.children = children;
    }

    public static void main(String[] args) {
        Calendar birthday = Calendar.getInstance();
        Map<User, Object> map = new HashMap<>();
        User userOne = new User("Andrey", 9000, birthday);
        User userTwo = new User("Andrey", 9000, birthday);
        map.put(userOne, new Object());
        map.put(userTwo, new Object());
        int hashCodeOne = userOne.hashCode();
        int hashCodeTwo = userTwo.hashCode();
        int hashOne = hashCodeOne ^ (hashCodeOne >>> 16);
        int hashTwo = hashCodeTwo ^ (hashCodeTwo >>> 16);
        int bucketOne = hashOne & 15;
        int bucketTwo = hashTwo & 15;
        map.forEach((k, v) -> System.out.println("key: " + k + " value: " + v));
        System.out.println("bucketOne: " + bucketOne);
        System.out.println("bucketTwo: " + bucketTwo);
    }
}
