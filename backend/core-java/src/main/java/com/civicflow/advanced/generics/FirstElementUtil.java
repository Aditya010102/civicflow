package com.civicflow.advanced.generics;

import java.util.List;

public class FirstElementUtil {

    public static <T> T first(
            List<T> values
    ) {

        if (values == null ||
                values.isEmpty()) {

            return null;
        }

        return values.get(0);
    }
}