package ru.sber.encryptors;

import java.io.File;
import java.io.IOException;

public interface Encryptor  {
    File encrypt(File input, File outDir) throws IOException;
    File decrypt(File input, File outDir) throws IOException;
}
