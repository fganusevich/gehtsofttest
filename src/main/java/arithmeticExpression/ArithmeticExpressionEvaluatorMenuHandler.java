package arithmeticExpression;

import java.util.Scanner;

/**
 * User: f.ganusevich
 * Date: 6/11/25
 */

public class ArithmeticExpressionEvaluatorMenuHandler {
  public static void arithmeticExpressionMenu(Scanner scanner){
    System.out.println("Enter an arithmetic expression to evaluate:");
    String expression = scanner.nextLine();
    try {
      double result = new ExpressionParser(expression)
          .evaluate();
      System.out.println("Result: " + result);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
