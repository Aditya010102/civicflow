package com.civicflow.core.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IssueFileReader {

    public String readFile(String fileName)
            throws IOException {

        return Files.readString(
                Path.of(fileName)
        );
    }
}