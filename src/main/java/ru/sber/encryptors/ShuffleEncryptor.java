package ru.sber.encryptors;

import java.io.*;

public class ShuffleEncryptor implements Encryptor {
    private final int BUF_SIZE = 1024;
    private int key;


    @Override
    public File encrypt(File input, File outDir) throws IOException {
        File outFile = new File(outDir, input.getName());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), BUF_SIZE);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile), BUF_SIZE);
        byte[] buf = new byte[BUF_SIZE];

        while (inputStream.read(buf) != -1) {
            for (int i = 0; i < buf.length; i += 2) {
                byte tmp = buf[i];
                buf[i] = buf[i + 1];
                buf[i + 1] = tmp;
            }
            outputStream.write(buf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outFile;
    }

    @Override
    public File decrypt(File input, File outDir) throws IOException {
        return encrypt(input, outDir);
    }

}
