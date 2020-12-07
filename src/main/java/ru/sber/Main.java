package ru.sber;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        File input = new File("C:\\Users\\auhov\\IdeaProjects\\CyberSecurity\\src\\main\\resources\\input_file.txt");
        File encDir = new File("src/main/resources/output");
        File CrackDir = new File("src/main/resources/CrackOutput");
        File CrackResult =  new File("src/main/resources/CrackOutput/CrackResult.txt");
        CodeCracker codeCracker = new CodeCracker();
        String CHOOSE_ENCRYPT_TYPE = "Выберите тип кодировки: 1 - сдвигом, " +
                "2 - простые перестановки," +
                " 3 - шифр Виженера," +
                " 4 - использовать взломщик," +
                " 0 - завершить выполнение."
                + "\n"
                + "введите соответствующую цифру ниже:";
        String CODE_OR_DECODE = "Вы хотите закодировать, или декодировать информацию?" +
                " напишите encode," +
                " если хотите закодировать," +
                " decode - если хотите декодировать";
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);
        int typeOfEncryptor = 5;
        do {
            System.out.println(CHOOSE_ENCRYPT_TYPE);
            typeOfEncryptor = scanner.nextInt();
            if (typeOfEncryptor == 0) {
                break;
            } else if (typeOfEncryptor == 4) {
                Map<String, Integer> result = codeCracker.decode(input, CrackDir);
                Files.write(Paths.get("src/main/resources/CrackOutput/CrackResult.txt"),
                        result.entrySet().stream().map(k->k.getKey()+"\r\n"+k.getValue()).collect(Collectors.toList()),
                        StandardCharsets.UTF_8);
            } else {
                scanner.nextLine();
                System.out.println(CODE_OR_DECODE);
                String encOrDec = scanner.nextLine().toLowerCase();
                manager.decide(typeOfEncryptor, encOrDec, input, encDir);
                System.out.println("Шифровка/Дешифровка успешно завершена");
            }
        } while (true);
    }
}
