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
        System.err.println("Missing command line arguments");
        System.exit(1);
    }

    public static int parse(String expression) throws IllegalArgumentException {
        String[] tokens = tokenize(expression);
        Stack<String> stack = new Stack<String>();

        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                stack.push(token);
            } else {
                try {
                  binop(token, stack);
                } catch (EmptyStackException ex) {
                  throw new IllegalArgumentException(ex.getMessage());
                }
            }
        }
        if (stack.size() != 1) {
          throw new IllegalArgumentException("Excess arguments left on stack");
        }

        return Integer.parseInt(stack.pop());
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