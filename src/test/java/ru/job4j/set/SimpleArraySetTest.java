package ru.job4j.set;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.util.IterableUtil.iterator;

class SimpleArraySetTest {

    @Test
    void whenAddNonNull() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddSomeElementsNonNull() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.contains(1)).isFalse();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
        assertThat(set.contains(2)).isFalse();
        assertThat(set.add(2)).isTrue();
        assertThat(set.contains(2)).isTrue();
        assertThat(set.add(2)).isFalse();
        assertThat(set.contains(3)).isFalse();
        assertThat(set.add(3)).isTrue();
        assertThat(set.contains(3)).isTrue();
        assertThat(set.add(3)).isFalse();
    }

    @Test
    void whenAddNull() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void whenAddManyToIncreaseSize() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        for (int i = 0; i < 51; i++) {
            set.add(1000000000 + i);
        }
        assertThat(set.contains(1000000050)).isTrue();
        assertThat(set.contains(1000000051)).isFalse();
    }

    @Test
    void iteratorSimpleTest() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.add(null)).isTrue();
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.next() == null);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenNoSuchElementException() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.add(1)).isTrue();
        assertThat(set.add(2)).isTrue();
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.next() == null);
        assertThat(iterator.next().equals(1));
        assertThat(iterator.next().equals(2));
        assertThat(iterator.hasNext()).isFalse();
        assertThatThrownBy(() -> iterator().next())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenChangesAfterIteratorCreation() {
        SimpleSet<Integer> set = new SimpleArraySet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.add(1)).isTrue();
        assertThat(set.add(2)).isTrue();
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.next()).isEqualTo(null);
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(set.add(3)).isTrue();
        assertThatThrownBy(iterator::next)
                .isInstanceOf(ConcurrentModificationException.class);

    }
}