package arithmeticExpression;

/**
 * User: f.ganusevich
 * Date: 6/11/25
 *
 * Класс для парсинга и вычисления арифметических выражений
 */
public class ExpressionParser {
  private final String expression;
  private int index;

  /**
   * Создаёт парсер для заданного арифметического выражения.
   * Удаляет все пробельные символы из входной строки.
   *
   * @param expression арифметическое выражение в виде строки
   */
  public ExpressionParser(String expression) {
    this.expression = expression.replaceAll("\\s+", "");
    this.index = 0;
  }

  /**
   * Выполняет полный разбор и вычисление выражения.
   * Проверяет, что после вычисления не осталось лишних символов.
   *
   * @return результат вычисления выражения
   * @throws IllegalArgumentException при синтаксической ошибке
   */
  public double evaluate() {
    double result = parseAddSubtract();
    if (index < expression.length()) {
      throw new IllegalArgumentException("Unexpected character at position " + index + ": '" + expression.charAt(index) + "'");
    }
    return result;
  }

  /**
   * Разбор и вычисление операций сложения и вычитания.
   *
   * @return результат вычисления текущей части выражения
   */
  private double parseAddSubtract() {
    double result = parseMultiplyDivide();
    while (index < expression.length()) {
      char op = expression.charAt(index);
      if (op != '+' && op != '-') break;
      index++;
      double right = parseMultiplyDivide();
      if (op == '+') result += right;
      else result -= right;
    }
    return result;
  }

  /**
   * Разбор и вычисление операций умножения и деления.
   *
   * @return результат вычисления текущей части выражения
   * @throws ArithmeticException при делении на ноль
   */
  private double parseMultiplyDivide() {
    double result = parseUnary();
    while (index < expression.length()) {
      char op = expression.charAt(index);
      if (op != '*' && op != '/') break;
      index++;
      double right = parseUnary();
      if (op == '*') result *= right;
      else {
        if (right == 0) throw new ArithmeticException("Division by zero");
        result /= right;
      }
    }
    return result;
  }

  /**
   * Обработка унарных операторов + и -.
   * Поддерживает выражения типа "-5" или "+3".
   *
   * @return результат вычисления с учётом унарного оператора
   */
  private double parseUnary() {
    if (index < expression.length()) {
      char c = expression.charAt(index);
      if (c == '+') {
        index++;
        return parseUnary();
      } else if (c == '-') {
        index++;
        return -parseUnary();
      }
    }
    return parseParentheses();
  }

  /**
   * Обработка скобок.
   * Если текущий символ '(' — рекурсивно вычисляет выражение внутри скобок.
   *
   * @return результат вычисления выражения в скобках или число
   * @throws IllegalArgumentException при несбалансированных скобках
   */
  private double parseParentheses() {
    if (index < expression.length() && expression.charAt(index) == '(') {
      index++;
      double result = parseAddSubtract();
      if (index >= expression.length() || expression.charAt(index) != ')') {
        throw new IllegalArgumentException("Unbalanced parentheses");
      }
      index++;
      return result;
    } else {
      return parseNumber();
    }
  }

  /**
   * Парсит число с возможной десятичной точкой.
   *
   * @return значение числа в виде double
   * @throws IllegalArgumentException если число отсутствует или некорректно
   */
  private double parseNumber() {
    int start = index;
    boolean dotSeen = false;
    while (index < expression.length()) {
      char c = expression.charAt(index);
      if (Character.isDigit(c)) {
        index++;
      } else if (c == '.') {
        if (dotSeen) break;
        dotSeen = true;
        index++;
      } else {
        break;
      }
    }
    if (start == index) {
      throw new IllegalArgumentException("Number expected at position " + index);
    }
    String numberStr = expression.substring(start, index);
    try {
      return Double.parseDouble(numberStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number: " + numberStr);
    }
  }
}