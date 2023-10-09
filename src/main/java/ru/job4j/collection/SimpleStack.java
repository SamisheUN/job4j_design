package ru.job4j.collection;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        T rsl = linked.get(0);
        linked.deleteFirst();
        return rsl;
    }

    public void push(T value) {
        linked.addFirst(value);
    }
}