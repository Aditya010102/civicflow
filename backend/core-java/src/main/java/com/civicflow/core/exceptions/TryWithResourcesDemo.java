package com.civicflow.core.exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResourcesDemo {

    public static void main(String[] args) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader("issues.txt")
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                System.out.println(line);
            }

        } catch (IOException exception) {

            System.out.println(
                    "Could not read file: "
                            + exception.getMessage()
            );
        }
    }
}