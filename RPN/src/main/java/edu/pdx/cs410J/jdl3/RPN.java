package edu.pdx.cs410J.jdl3;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * A class for getting started with a code kata
 * <p>
 * Use IntelliJ's "Refactor | Rename..." command to change the name of this
 * class (and its tests).
 */
public class RPN {
    public static void main(String[] args) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < args.length; ++i) {
        sb.append(args[i] + " ");
      }
      String polish = sb.toString().trim();
      try {
        int val = parse(polish);
        System.out.println(val);
      } catch (IllegalArgumentException ex) {
        System.err.println(ex.getMessage());
        System.exit(1);
      }

        System.exit(0);
    }

    public static int parse(String expression) throws IllegalArgumentException {
        String[] tokens = tokenize(expression);
        Stack<String> stack = new Stack<String>();

        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                stack.push(token);
            } else {
                if (token.equals("SQRT")) {
                    doSqrt(stack);
                } else if (token.equals("MAX")) {
                  doMax(stack);
                } else {
                    try {
                        binop(token, stack);
                    } catch (EmptyStackException ex) {
                        throw new IllegalArgumentException(ex.getMessage());
                    }
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Excess arguments left on stack");
        }

        return Integer.parseInt(stack.pop());
    }

  private static void doMax(Stack<String> stack) throws IllegalArgumentException {
    if (stack.empty()) {
      throw new IllegalArgumentException("No operands with MAX");
    }

    int max = Integer.parseInt(stack.pop());
    while (!stack.empty()) {
      int temp = Integer.parseInt(stack.pop());
      if (temp > max) {
        max = temp;
      }
    }
    stack.push(String.valueOf(max));
  }

  private static void doSqrt(Stack<String> stack) throws IllegalArgumentException {
        try {
            double num = Double.parseDouble(stack.pop());
            stack.push(String.valueOf((int) Math.sqrt(num)));
        } catch (EmptyStackException ex) {
            throw new IllegalArgumentException(
                    "No operands on stack to square root");
        }
    }

    public static void binop(String operator, Stack<String> stack)
            throws EmptyStackException {
        int right = Integer.parseInt(stack.pop());
        int left = Integer.parseInt(stack.pop());

        switch (operator) {
            case "+":
                stack.push(String.valueOf(left + right));
                break;
            case "-":
                stack.push(String.valueOf(left - right));
                break;
            case "/":
                if (right == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                stack.push(String.valueOf(left / right));
                break;
            case "*":
                stack.push(String.valueOf(left * right));
                break;
            default:
                throw new IllegalArgumentException(
                        "Unrecognized symbol: " + operator);
        }
    }

    public static String[] tokenize(String expr) {
        return expr.split(" ");
    }
}