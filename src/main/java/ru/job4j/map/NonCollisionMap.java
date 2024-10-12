package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {
    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        boolean rsl = false;
        MapEntry<K, V> entry = new MapEntry<>(key, value);
        int index = keyToIndex(entry.key);
        if (table[index] == (null)) {
            table[index] = entry;
            count++;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        V result = null;
        int index = keyToIndex(key);
        if (table[index] != null
                && Objects.hashCode(table[index].key) == Objects.hashCode(key)
                && Objects.equals(table[index].key, key)) {
            result = table[index].value;
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = keyToIndex(key);
        if (table[index] != null
                && Objects.hashCode(table[index].key) == Objects.hashCode(key)
                && Objects.equals(table[index].key, key)) {
            table[index] = null;
            count--;
            modCount++;
            result = true;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new MapIterator(table);
    }

    private class MapIterator implements Iterator<K> {
        private int expectedModCount = modCount;
        private int index = 0;
        private MapEntry<K, V>[] data;

        public MapIterator(MapEntry<K, V>[] data) {
            this.data = data;
        }

        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            while (index < data.length && data[index] == null) {
                index++;
            }
            return index < data.length;
        }

        @Override
        public K next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K key = data[index].key;
            index++;
            return key;
        }
    }

    private int keyToIndex(K key) {
        return key == null ? 0 : indexFor(hash(Objects.hashCode(key)));
    }

    private int hash(int hashCode) {
        return hashCode ^ hashCode >>> 16;
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        capacity = capacity * 2;
        MapEntry<K, V>[] tableExpanded = new MapEntry[capacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                tableExpanded[keyToIndex(table[i].key)] = table[i];
            }
        }
        table = tableExpanded;
    }

    private static class MapEntry<K, V> {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        NonCollisionMap map = new NonCollisionMap();
        System.out.println("Expected 0, returned: " + map.hash(0));
        System.out.println("Expected 65535, returned: " + map.hash(65535));
        System.out.println("Expected 65537, returned: " + map.hash(65536));
        System.out.println("Expected 0, returned: " + map.indexFor(0));
        System.out.println("Expected 7, returned: " + map.indexFor(7));
        System.out.println("Expected 0, returned: " + map.indexFor(8));
    }
}
