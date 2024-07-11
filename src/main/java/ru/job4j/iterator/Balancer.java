package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        int nodePointer = 0;
        int nodesSize = nodes.size();
        while (source.hasNext()) {
            nodes.get(nodePointer).add(source.next());
            nodePointer = (nodePointer + 1) % nodesSize;
        }
    }
}