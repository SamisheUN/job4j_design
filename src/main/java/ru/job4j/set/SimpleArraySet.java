package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArraySet<T> implements SimpleSet<T> {

    private SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean rsl = !contains(value);
        if (rsl) {
            set.add(value);
        }
        return rsl;
    }

    public boolean contains(T value) {
        boolean found = false;
        for (int i = 0; i < set.size(); i++) {
            if (Objects.equals(value, set.get(i))) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}