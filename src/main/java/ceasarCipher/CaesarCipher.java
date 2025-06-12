package ceasarCipher;

/**
 * User: f.ganusevich
 * Date: 6/11/25
 *
 * Класс для шифрования и дешифрования цезарем
 * */

public class CaesarCipher {
  private static final int LATIN_ALPHABET_SIZE = 26;
  private static final int CYRILLIC_ALPHABET_SIZE = 32;

  public static String encrypt(String text, int shift) {
    StringBuilder result = new StringBuilder();
    for (char c : text.toCharArray()) {
      if (c >= 'A' && c <= 'Z') { // Латиница верхний регистр
        result.append(shiftChar(c, shift, 'A', LATIN_ALPHABET_SIZE));
      } else if (c >= 'a' && c <= 'z') { // Латиница нижний регистр
        result.append(shiftChar(c, shift, 'a', LATIN_ALPHABET_SIZE));
      } else if (c >= 'А' && c <= 'Я') { // Кириллица верхний регистр
        result.append(shiftChar(c, shift, 'А', CYRILLIC_ALPHABET_SIZE));
      } else if (c >= 'а' && c <= 'я') { // Кириллица нижний регистр
        result.append(shiftChar(c, shift, 'а', CYRILLIC_ALPHABET_SIZE));
      } else {
        // Неалфавитные символы оставляем без изменений
        result.append(c);
      }
    }
    return result.toString();
  }

  public static String decrypt(String text, int shift) {
    return encrypt(text, -shift);
  }

  /**
   * Метод для сдвига символа с учетом базового символа и размера алфавита.
   * Обрабатывает wrap-around и поддерживает отрицательные сдвиги.
   */
  private static char shiftChar(char c, int shift, char base, int alphabetSize) {
    int offset = c - base;
    int shifted = (offset + shift) % alphabetSize;
    if (shifted < 0) {
      shifted += alphabetSize;
    }
    return (char) (base + shifted);
  }


  /**
   * Дешифровка без знания сдвига — перебор всех вариантов.
   * Возвращает массив всех возможных расшифровок.
   */
  public static String[] bruteForceDecrypt(String text) {
    int maxShift = Math.max(LATIN_ALPHABET_SIZE, CYRILLIC_ALPHABET_SIZE);
    String[] results = new String[maxShift];
    for (int shift = 0; shift < maxShift; shift++) {
      results[shift] = decrypt(text, shift);
    }
    return results;
  }
}
