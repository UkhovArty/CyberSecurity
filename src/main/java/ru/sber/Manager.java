package ru.sber;

import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor
public class Manager {
    private final EncryptionTypeHandler handler = new EncryptionTypeHandler();
    private final String ENCODE = "encode";
    public void decide(int type, String encOrDec, File input, File output) {
        if (encOrDec.equals(ENCODE)) {
            try {
                handler.encrypt(type, input, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                handler.decrypt(type, input, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
