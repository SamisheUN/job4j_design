package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        if (linked.getSize() <= 0) {
            throw new NoSuchElementException();
        }
        T rsl = linked.get(0);
        linked.deleteFirst();
        return rsl;
    }

    public void push(T value) {
        linked.addFirst(value);
    }
}