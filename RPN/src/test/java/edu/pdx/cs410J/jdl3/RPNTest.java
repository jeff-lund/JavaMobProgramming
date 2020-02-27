package edu.pdx.cs410J.jdl3;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class RPNTest
{
  @Test
  public void parseTokenize() {
    String arg = "3 4 +";
    String[] real = {"3", "4", "+"};
    assertArrayEquals(RPN.tokenize(arg), real);
  }

  @Test
  public void binaryAdditionOperationThreePlusFourGivesSeven() {
    Stack<String> stack = new Stack<>();
    stack.push("3");
    stack.push("4");
    RPN.binop("+", stack);
    assertEquals(stack.pop(), "7");
  }

  @Test
  public void simpleParseThreePlusFourGivesSeven() {
    String arg = "3 4 +";
    assertEquals(RPN.parse(arg), 7);
  }

  @Test
  public void nestedOperationsWork() {
    String arg = "3 5 8 * 7 + *";
    assertEquals(RPN.parse(arg), 141);
  }

  @Test
  public void twoOps() {
    String arg = "4 2 + 3 -";
    assertEquals(RPN.parse(arg), 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void divideByZero() {
    String arg = "4 0 /";
    RPN.parse(arg);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStringThrowsException() {
    String arg = "4 5 6 +";
    RPN.parse(arg);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidSymbolInArgument() {
    String arg = "3 4 S";
    RPN.parse(arg);
  }

  @Test (expected = IllegalArgumentException.class)
  public void notEnoughOperandsOnStack() {
    String arg = "3 +";
    RPN.parse(arg);
  }

  @Test
  public void testDivision() {
    String arg = "5 2 /";
    assertEquals(RPN.parse(arg), 2);
  }

  @Test
  public void sqrtTest() {
    String arg = "9 SQRT";
    assertEquals(RPN.parse(arg), 3);
  }

  @Test
  public void sqrtDoesIntegerSquareRoot() {
    String arg  = "10 SQRT";
    assertEquals(RPN.parse(arg), 3);
  }

  @Test
  public void highSqrtTruncating() {
    String arg = "15 SQRT";
    assertEquals(RPN.parse(arg), 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void sqrtWithNoOperand() {
    String arg = "SQRT";
    RPN.parse(arg);
  }

  @Test
  public void complexSqrtExpr() {
    String arg = "3 4 SQRT +";
    assertEquals(RPN.parse(arg), 5);
  }

  @Test
  public void maxOfArgsIsMax() {
    String arg = "3 4 5 6 7 8 MAX";
    assertEquals(RPN.parse(arg), 8);
  }

  @Test
  public void multiMax() {
    String args = "3 5 6 MAX 2 3 MAX";
    assertEquals(RPN.parse(args), 6);
  }

  @Test (expected = IllegalArgumentException.class)
  public void maxWithNoOps() {
    String args = "MAX";
    RPN.parse(args);
  }

  @Test
  public void lotsOfArguments() {
    String args = "3 4 + 5 6 * MAX SQRT";
    assertEquals(RPN.parse(args), 5);
  }
}
