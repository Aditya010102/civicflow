package com.civicflow.core.collections;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<T> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public T get(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }
}