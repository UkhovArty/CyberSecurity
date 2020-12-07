package ru.sber;

import lombok.NoArgsConstructor;
import ru.sber.encryptors.CaesarEncryptor;
import ru.sber.encryptors.Encryptor;
import ru.sber.encryptors.ShuffleEncryptor;
import ru.sber.encryptors.VigenereEncryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@NoArgsConstructor
public class CodeCracker {
    private final CaesarEncryptor caesarEncryptor = new CaesarEncryptor();
    private final Encryptor shuffleEncryptor = new ShuffleEncryptor();
    private final VigenereEncryptor vigenereEncryptor = new VigenereEncryptor();

    public Map<String, Integer> decode(File input, File encDir) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выбран взлом шифра, пожалуйста выберите тип шифра, который хотите взломать: " +
                "1 - Цезарь, " +
                "2 - простые перестановки, " +
                "3 - шифр Виженера");
        int typeOfEncryption = scanner.nextInt();
        if (typeOfEncryption == 1) {
            return decodeFromCaesar(input, encDir);
        }
        if (typeOfEncryption == 2) {
            return decodeFromShuffle(input, encDir);
        }
        if (typeOfEncryption == 3) {
            return decodeFromVigenere(input, encDir);
        }
        return null;
    }

    private Map<String, Integer> decodeFromCaesar(File input, File encDir) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выбран взлом шифра Цезаря, пожалуйста введите вероятное слово");
        String probableWord = scanner.nextLine();
        StringBuilder builder;
        Map<String, Integer> result = new HashMap<>();
        for (int i = -26; i < 0; i++) {
            File out = caesarEncryptor.decrypt(input, encDir, i);
            FileReader reader = new FileReader(out);
            int c = 0;
            builder = new StringBuilder();
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
            String res = builder.toString();
            if (res.contains(probableWord)) {
                result.put(res, i);
            }
        }
        return result;
    }

    private Map<String, Integer> decodeFromShuffle(File input, File encDir) throws IOException {
        Map<String, Integer> result = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выбран взлом простых перестановок, пожалуйста введите вероятное слово");
        String probableWord = scanner.nextLine();
        File out = shuffleEncryptor.decrypt(input, encDir);
        FileReader reader = new FileReader(out);
        int c = 0;
        StringBuilder builder = new StringBuilder();
        while ((c = reader.read()) != -1) {
            builder.append((char) c);
        }
        String res = builder.toString();
        if (res.contains(probableWord)) {
            result.put(res, 0);
        } else {
            File out1 = shuffleEncryptor.encrypt(input, encDir);
            FileReader reader1 = new FileReader(out);
            int c1 = 0;
            StringBuilder builder1 = new StringBuilder();
            while ((c1 = reader1.read()) != -1) {
                builder1.append((char) c1);
            }
            String res1 = builder.toString();
            if (res1.contains(probableWord)) {
                result.put(res, 0);
            }
        }
        return result;
    }

    private Map<String, Integer> decodeFromVigenere(File input, File encDir) throws IOException {
        Map<String, Integer> result = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выбран взлом шифра Виженера, пожалуйста введите длину ключа");
        int keyLength = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Выбран взлом шифра Виженера, пожалуйста введите вероятное слово");
        String probableWord = scanner.nextLine();
        char[] perm = new char[keyLength];
        ArrayList<String> probKeys = new ArrayList<>();
        permutation(perm, 0, "abcdefghijklmnopqrstuvwxyz ", probKeys);
        for (String s : probKeys) {
            String out = vigenereEncryptor.decrypt(input, encDir, s);
            if (out.contains(probableWord)) {
                result.put(out + "\n" + s, s.length());
            }
        }
        return result;
    }

    private static void permutation(char[] perm, int pos, String str, ArrayList<String> probableKeys) {
        if (pos == perm.length) {
            probableKeys.add(new String(perm));
        } else {
            for (int i = 0; i < str.length(); i++) {
                perm[pos] = str.charAt(i);
                permutation(perm, pos + 1, str, probableKeys);
            }
        }
    }
}
