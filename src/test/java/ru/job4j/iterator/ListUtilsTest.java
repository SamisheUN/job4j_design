package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeFirstElement() {
        ListUtils.addBefore(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(2, 1, 3);
    }

    @Test
    void whenAddBeforeLastElement() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.addBefore(input, 2, 4);
        assertThat(input).hasSize(4).containsSequence(1, 2, 4, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void removeif() {
        Predicate<Integer> filter = i -> i.equals(3);
        ListUtils.removeIf(input, filter);
        assertThat(input).hasSize(1).containsSequence(1);
    }

    @Test
    void replaceif() {
        Predicate<Integer> filter =  i -> i == 1;
        ListUtils.replaceIf(input, filter, 999);
        assertThat(input).hasSize(2).containsSequence(999, 3);
    }

    @Test
    void removeAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5));
        ListUtils.removeAll(input, list);
        assertThat(input).hasSize(0);
    }
}