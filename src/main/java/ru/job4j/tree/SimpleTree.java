package ru.job4j.tree;

import java.util.*;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> parentAdress = findBy(parent);
        boolean result = false;
        if (findBy(child).isEmpty() && parentAdress.isPresent()) {
            parentAdress.get().getChildren().add(new Node<>(child));
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (element.getValue().equals(value)) {
                result = Optional.of(element);
                break;
            }
            data.addAll(element.getChildren());
        }
        return result;
    }
}