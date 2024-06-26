package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        ForwardLinked.Node<T> newNode = new ForwardLinked.Node(value, null);
        if (null == head) {
            head = newNode;
        } else {
            ForwardLinked.Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        ForwardLinked.Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    public T deleteFirst() {
        if (null == head) {
            throw new NoSuchElementException();
        }
        Node<T> trash = head;
        head = head.next;
        T item = trash.item;
        trash.item = null;
        trash.next = null;
        size--;
        modCount++;
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<>() {
            private ForwardLinked.Node<T> current = head;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return null != current;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.item;
                current = current.next;
                return data;
            }
        };
        return iterator;
    }

    public void addFirst(T value) {
        head = new Node(value, head);
        size++;
        modCount++;
    }

    private static class Node<T> {
        private T item;
        private ForwardLinked.Node<T> next;

        Node(T element, ForwardLinked.Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}