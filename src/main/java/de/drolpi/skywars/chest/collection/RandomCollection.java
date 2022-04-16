package de.drolpi.skywars.chest.collection;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCollection<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private transient double total = 0;

    public void add(double weight, E result) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }

    public int size() {
        return map.size();
    }
}