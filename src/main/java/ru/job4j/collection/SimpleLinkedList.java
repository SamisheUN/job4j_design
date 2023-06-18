package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {
    private int size = 0;
    private int modCount = 0;
    private Node<E> head;

    /**
     * @Override
     **/
    public void add(E value) {
        Node<E> newNode = new Node(value, null);
        if (null == head) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * @Override
     **/
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    /**
     * @Override
     **/
    public Iterator<E> iterator() {
        return new SimpleLinkedListIterator();
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    private class SimpleLinkedListIterator implements Iterator<E> {
        private Node<E> current = head;
        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return null != current;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = current.item;
            current = current.next;
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return data;
        }
    }
}