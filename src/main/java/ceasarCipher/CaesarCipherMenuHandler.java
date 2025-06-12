package ceasarCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * User: f.ganusevich
 * Date: 6/11/25
 */

public class CaesarCipherMenuHandler {
  private static final String RESULT = "Result: ";
  private static final String CONSOLE_OR_FILE_PATTERN = "Do you want to enter the text from console or a file? (%s/%s):%n";
  private static final String CONSOLE = "c";
  private static final String FILE = "f";
  private static final String INVALID_CHOICE = "Invalid choice. Returning to menu.";
  private static final String INVALID_SHIFT = "Invalid shift value. Returning to menu.";
  private static final String YES = "y";
  private static final String NO = "n";

  public static void encryptMenu(Scanner scanner) {
    String text = readText(scanner, CaesarCipherAction.ENCRYPT);
    if (text == null) return; // ошибка чтения
    Integer shift = readShiftValue(scanner);
    if (shift == null) return;
    String encryptedText = CaesarCipher.encrypt(text, shift);
    System.out.println(RESULT + encryptedText);
  }

  public static void decryptMenu(Scanner scanner) {
    String text = readText(scanner, CaesarCipherAction.DECRYPT);
    if (text == null) return;
    System.out.printf("Do you know the shift value? (%s/%s):", YES, NO);
    String knowShift = scanner.nextLine().trim().toLowerCase();
    if (YES.equals(knowShift)) {
      Integer shift = readShiftValue(scanner);
      if (shift == null) return;
      String decryptedText = CaesarCipher.decrypt(text, shift);
      System.out.println(RESULT + decryptedText);
    } else if (NO.equals(knowShift)) {
      System.out.println("Trying all possible shifts:");
      String[] possibilities = CaesarCipher.bruteForceDecrypt(text);
      for (int i = 0; i < possibilities.length; i++) {
        System.out.printf("Shift %2d: %s%n", i, possibilities[i]);
      }
    } else {
      System.out.println(INVALID_CHOICE);
    }
  }

  /**
   * Читает текст либо из консоли, либо из файла по выбору пользователя.
   * Возвращает null в случае ошибки.
   */
  private static String readText(Scanner scanner, CaesarCipherAction action) {
    System.out.printf(CONSOLE_OR_FILE_PATTERN, CONSOLE, FILE);
    String choice = scanner.nextLine().trim().toLowerCase();
    if (CONSOLE.equals(choice)) {
      System.out.printf("Enter text to %s:%n", action.getAction());
      return scanner.nextLine();
    } else if (FILE.equals(choice)) {
      System.out.println("Enter full path to the file:");
      String path = scanner.nextLine().trim();
      return getTextFromFile(path);
    } else {
      System.out.println(INVALID_CHOICE);
      return null;
    }
  }

  /**
   * Считывает и возвращает сдвиг (Integer).
   * Возвращает null при ошибке.
   */
  private static Integer readShiftValue(Scanner scanner) {
    System.out.println("Enter shift value (integer):");
    String input = scanner.nextLine();
    try {
      return Integer.parseInt(input.trim());
    } catch (NumberFormatException e) {
      System.out.println(INVALID_SHIFT);
      return null;
    }
  }

  /**
   * Достаем текст из файла
   */
  private static String getTextFromFile(String path) {
    try {
      List<String> lines = Files.readAllLines(Path.of(path));
      // Объединяем все строки из файла в один текст с пробелом между строками
      return String.join(" ", lines);
    } catch (Exception e) {
      System.out.println("Failed to read file: " + e.getMessage());
      return null;
    }
  }
}