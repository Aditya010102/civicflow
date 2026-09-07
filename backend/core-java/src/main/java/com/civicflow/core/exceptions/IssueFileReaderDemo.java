package com.civicflow.core.exceptions;

import java.io.IOException;

public class IssueFileReaderDemo {

    public static void main(String[] args) {

        IssueFileReader reader =
                new IssueFileReader();

        try {

            String content =
                    reader.readFile("issues.txt");

            System.out.println(content);

        } catch (IOException exception) {

            System.out.println(
                    "Could not read issue file."
            );
        }
    }
}