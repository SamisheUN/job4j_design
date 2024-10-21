package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

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

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (condition.test(element)) {
                result = Optional.of(element);
                break;
            }
            data.addAll(element.getChildren());
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(node -> node.getValue().equals(value));
    }

    @Override
    public boolean isBinary() {
        return findByPredicate(node -> node.getChildren().size() > 2).isEmpty();
    }

    public static void main(String[] args) {
        Tree<Integer> tree = new SimpleTree<>(2);
        tree.add(2, 3);
        tree.add(2, 4);
        tree.add(2, 3);
        System.out.println(tree.findBy(2));
        System.out.println(tree.findBy(3));
        System.out.println(tree.findBy(4));
        System.out.println(tree.findBy(5));
    }
}