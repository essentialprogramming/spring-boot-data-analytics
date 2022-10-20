package com.util.io;

import java.io.IOException;


public class ResourceFileReader {

    public static String readFile(final String filename) throws IOException {
        try (final FileInputResource fileInputResource = new FileInputResource(filename)){
            return fileInputResource.getText();
        }
    }
}
