package com.util.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class FileInputResource implements AutoCloseable {

    private static final int BYTE_RANGE = 1024;

    private final URL file;
    private final InputStream inputStream;

    public FileInputResource(final String fileName) throws IOException {
        file = InputResource.getURL(fileName);
        inputStream = new BufferedInputStream(file.openStream());
    }

    public URL getFile() {
        return file;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public byte[] getBytes() throws IOException {
        try (final InputStream inputStream = getInputStream()) {
            return readStream(inputStream);
        }
    }

    public String getText() throws IOException {
        try (final InputStream inputStream = getInputStream()) {
            return new String(readStream(inputStream), StandardCharsets.UTF_8);
        }
    }

    /**
     * Converts an input stream into a byte array.
     */
    public static byte[] readStream(final InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, nRead);
            }
            outputStream.flush();
            return outputStream.toByteArray();
        }
    }


    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
