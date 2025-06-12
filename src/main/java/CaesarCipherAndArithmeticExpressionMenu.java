/**
 * User: f.ganusevich
 * Date: 6/11/25
 */

import arithmeticExpression.ArithmeticExpressionEvaluatorMenuHandler;
import ceasarCipher.CaesarCipherMenuHandler;

import java.util.Scanner;

public class CaesarCipherAndArithmeticExpressionMenu {
  private static final String WELCOME = "Welcome to Gehtsoft Technical Assessment";
  private static final String OPTIONS = """
  Please choose an option:
  1. Caesar Cipher Encryption
  2. Caesar Cipher Decryption
  3. Arithmetic Expression Evaluation
  4. Exit
  Enter your choice: """;
  private static final String EXITING_PROGRAM = "Exiting program. Goodbye!";
  private static final String CONTINUE = "Continue? (y/n):";
  private static final String INVALID_OPTION = "Invalid option. Please choose a valid option.";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(WELCOME);
    while (true) {
      System.out.println(OPTIONS);
      int choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          CaesarCipherMenuHandler.encryptMenu(scanner);
          break;
        case 2:
          CaesarCipherMenuHandler.decryptMenu(scanner);
          break;
        case 3:
          ArithmeticExpressionEvaluatorMenuHandler.arithmeticExpressionMenu(scanner);
          break;
        case 4:
          System.out.println(EXITING_PROGRAM);
          return;
        default:
          System.out.println(INVALID_OPTION);
      }
      System.out.println(CONTINUE);
      String continueChoice = scanner.nextLine();
      if (!continueChoice.equalsIgnoreCase("y")) {
        System.out.println(EXITING_PROGRAM);
        break;
      }
    }
    scanner.close();
  }
}