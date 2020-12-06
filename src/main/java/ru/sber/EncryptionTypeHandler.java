package ru.sber;

import lombok.NoArgsConstructor;
import ru.sber.encryptors.CaesarEncryptor;
import ru.sber.encryptors.Encryptor;
import ru.sber.encryptors.ShuffleEncryptor;
import ru.sber.encryptors.VigenereEncryptor;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor
public class EncryptionTypeHandler {
    private final Encryptor caesarCoder = new CaesarEncryptor();
    private final Encryptor shuffleCoder = new ShuffleEncryptor();
    private final Encryptor vigenereCoder = new VigenereEncryptor();

    public void encrypt(int number, File input, File outDir) throws IOException {
        if (number == 1) {
            caesarCoder.encrypt(input, outDir);
        } else if (number == 2) {
            shuffleCoder.encrypt(input, outDir);
        } else if (number == 3) {
            vigenereCoder.encrypt(input, outDir);
        }
    }

    public void decrypt(int number, File input, File outDir) throws IOException {
        if (number == 1) {
            caesarCoder.decrypt(input, outDir);
        } else if (number == 2) {
            shuffleCoder.decrypt(input, outDir);
        } else if (number == 3) {
            vigenereCoder.decrypt(input, outDir);
        }
    }
}
