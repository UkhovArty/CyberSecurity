package ru.sber.encryptors;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RequiredArgsConstructor
public class CaesarEncryptor implements Encryptor {
    private final int bufferSize = 1024;
    private final Charset charset = StandardCharsets.UTF_8;
    private int shift = 28;
    private int crackerShift = 28;

    @Override
    public File encrypt(File input, File outDir) throws IOException {
        setShift();
        File outFile = new File(outDir, this.crackerShift + "_" + "Caesar_output.txt");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(input), bufferSize);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile), bufferSize);
        byte a = "a".getBytes(this.charset)[0];
        byte z = "z".getBytes(this.charset)[0];

        byte[] buffer = new byte[bufferSize];
        byte[] outBuffer = new byte[bufferSize];

        while (inputStream.read(buffer) != -1) {
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] >= (int) a && buffer[i] <= (int) z) {
                    outBuffer[i] = (byte) ((buffer[i] + shift - (int) a) % 26 + (int) a);
                } else {
                    outBuffer[i] = buffer[i];
                }
            }
            outputStream.write(outBuffer);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
        this.shift = 28;
        return outFile;
    }

    @Override
    public File decrypt(File input, File outDir) throws IOException {
        this.shift = 27;
        return encrypt(input, outDir);
    }

    public File decrypt(File input, File outDir, int shift) throws IOException {
        this.crackerShift = shift;
        this.shift = shift + 26;
        return encrypt(input, outDir);
    }

    private void setShift() {
        if (this.shift == 28) {
            System.out.println("Выбран тип кодировки/дешифровки сдвигом, введите число, на которое должен осуществляться сдвиг:");
            Scanner scanner = new Scanner(System.in);
            this.shift = scanner.nextInt();
            this.crackerShift = this.shift;
        } else if (this.shift == 27) {
            System.out.println("Выбран тип кодировки/дешифровки сдвигом, введите число, на которое должен осуществляться сдвиг:");
            Scanner scanner = new Scanner(System.in);
            this.shift = scanner.nextInt();
            this.crackerShift = -this.shift;
            this.shift = -this.shift + 26;
        } else {
            System.out.println("Ведется взлом");
        }
    }
}
