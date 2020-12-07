package ru.sber.encryptors;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VigenereEncryptor implements Encryptor {
    private final int bufferSize = 1024;
    private final Charset charset = StandardCharsets.UTF_8;
    private String shift = "";

    @Override
    public File encrypt(File input, File outDir) throws IOException {
        setShift();
        File outFile = new File(outDir, this.shift + "_output.txt");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile), bufferSize);
        byte a = "a".getBytes(this.charset)[0];
        byte z = "z".getBytes(this.charset)[0];

        byte[] buffer = new byte[bufferSize];
        byte[] outBuffer = new byte[bufferSize];

        while (inputStream.read(buffer) != -1) {
            int j = 0;
            for (int i = 0; i < buffer.length; i++) {
                if (j >= shift.length()) {
                    j = 0;
                }
                if (buffer[i] >= (int) a && buffer[i] <= (int) z) {
                    outBuffer[i] = (byte) (((buffer[i] - (int) a) + (shift.charAt(j)  - (int) a)) % 26 + (int) a);
                    j++;
                } else {
                    outBuffer[i] = buffer[i];
                }
            }
            outputStream.write(outBuffer);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        this.shift = "";
        return outFile;
    }

    @Override
    public File decrypt(File input, File outDir) throws IOException {
        setShift();
        File outFile = new File(outDir, shift + "_output.txt");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), bufferSize);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile), bufferSize);
        byte a = "a".getBytes(this.charset)[0];
        byte z = "z".getBytes(this.charset)[0];

        byte[] buf = new byte[bufferSize];
        byte[] outBuf = new byte[bufferSize];

        while (inputStream.read(buf) != -1) {
            int j = 0;
            for (int i = 0; i < buf.length; i++) {
                if (j>= shift.length()) {
                    j = 0;
                }
                if (buf[i] >= (int) a && buf[i] <= (int) z) {
                    outBuf[i] = (byte) (((buf[i] - (int) a) + 26 - (shift.charAt(j) - (int) a)) % 26 + (int) a);
                    j++;
                } else {
                    outBuf[i] = buf[i];
                }
            }
            outputStream.write(outBuf);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        this.shift = "";
        return outFile;
    }

    public String decrypt(File input, File outDir, String shift) throws IOException {
        this.shift = shift;
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), bufferSize);
        byte a = "a".getBytes(this.charset)[0];
        byte z = "z".getBytes(this.charset)[0];

        byte[] buf = new byte[bufferSize];
        byte[] outBuf = new byte[bufferSize];

        while (inputStream.read(buf) != -1) {
            int j = 0;
            for (int i = 0; i < buf.length; i++) {
                if (j>= shift.length()) {
                    j = 0;
                }
                if (buf[i] >= (int) a && buf[i] <= (int) z) {
                    outBuf[i] = (byte) (((buf[i] - (int) a) + 26 - (shift.charAt(j) - (int) a)) % 26 + (int) a);
                    j++;
                } else {
                    outBuf[i] = buf[i];
                }
            }
        }

        return new String(outBuf);
    }

    private void setShift() {
        if (this.shift.equals("")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Выбран метод шифра Виженера, введите пожалуйста ключевое слово");
            this.shift = scanner.nextLine();
        }
    }
}
