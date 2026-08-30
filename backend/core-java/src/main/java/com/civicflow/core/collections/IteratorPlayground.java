package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorPlayground {

    public static void main(String[] args) {

        List<String> cities =
                new ArrayList<>();

        cities.add("Patna");
        cities.add("Delhi");
        cities.add("Mumbai");

        Iterator<String> iterator =
                cities.iterator();

        while (iterator.hasNext()) {

            String city = iterator.next();

            System.out.println(city);
        }
    }
}