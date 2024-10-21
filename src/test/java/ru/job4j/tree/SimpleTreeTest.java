package ru.job4j.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTreeTest {

    @Test
    void when6ElFindLastThen6() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.findBy(6)).isPresent();
    }

    @Test
    void whenElFindNotExistThenOptionEmpty() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        assertThat(tree.findBy(7)).isEmpty();
    }

    @Test
    void whenChildExistOnLeafThenNotAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.add(2, 6)).isFalse();
    }

    @Test
    void addResultTest() {
        Tree<Integer> tree = new SimpleTree<>(20);
        assertThat(tree.add(20, 30)).isTrue();
        assertThat(tree.add(20, 40)).isTrue();
        assertThat(tree.add(20, 30)).isFalse();
    }

    @Test
    void isBinaryTestNonBinaryTree() {
        Tree<Integer> tree = new SimpleTree<>(1);
        assertThat(tree.add(1, 2)).isTrue();
        assertThat(tree.add(1, 3)).isTrue();
        assertThat(tree.add(1, 4)).isTrue();
        assertThat(tree.isBinary()).isFalse();
    }

    @Test
    void isBinaryTestDeeperNonBinaryTree() {
        Tree<Integer> tree = new SimpleTree<>(1);
        assertThat(tree.add(1, 2)).isTrue();
        assertThat(tree.add(1, 3)).isTrue();
        assertThat(tree.add(3, 4)).isTrue();
        assertThat(tree.add(4, 5)).isTrue();
        assertThat(tree.add(4, 6)).isTrue();
        assertThat(tree.add(4, 7)).isTrue();
        assertThat(tree.isBinary()).isFalse();
    }

    @Test
    void isBinaryTestBinaryTree() {
        Tree<Integer> tree = new SimpleTree<>(1);
        assertThat(tree.add(1, 2)).isTrue();
        assertThat(tree.add(1, 3)).isTrue();
        assertThat(tree.add(3, 4)).isTrue();
        assertThat(tree.add(3, 5)).isTrue();
        assertThat(tree.add(4, 6)).isTrue();
        assertThat(tree.add(4, 7)).isTrue();
        assertThat(tree.isBinary()).isTrue();
    }

}