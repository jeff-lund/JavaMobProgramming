package edu.pdx.cs410J.jdl3;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;
public class BankOCRTest
{
  public String example =  "    _  _     _  _  _  _  _ \n" +
                           "  | _| _||_||_ |_   ||_||_|\n" +
                           "  ||_  _|  | _||_|  ||_| _|\n" +
                           "                           \n";
  @Test
  public void canInstantiateKataClass() {
    new BankOCR();
  }

  @Test
  public void canParseElements() {
    List<String> result = BankOCR.makeTriples(this.example);
    assertEquals(result.get(0), "   "
                                     + "  |"
                                     + "  |");
    assertEquals(result.get(1), " _ "
                                     + " _|"
                                     + "|_ ");
    assertEquals(result.get(2), " _ "
                                      +" _|"
                                      +" _|");
  }

  @Test
  public void oneStringIsTransformedInto1() {
    String one = "     |  |";
    assertEquals(BankOCR.transform(one), "1");
  }

  @Test
  public void successfullyParseNumber() {
    int result = 123456789;
    assertEquals(BankOCR.parse(this.example), result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumberInString() {
    String malformed = "    _  _     _  _  _  _  _ \n"
                     + "  | _| _||_||_ |_   ||_||_|\n"
                     + "  ||_  _|  | _||_|  ||_|  |\n"
                     + "                           ";

    BankOCR.parse(malformed);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooShortStringThrowsException() {
    String malformed = "    _  _     _  _  _  _  _ \n"
            + "  | _| _|||_ |_   ||_||_|\n"
            + "  ||_  _|  | _||_|  ||_|  |\n"
            + "                           ";

    BankOCR.parse(malformed);
  }

  @Test
  public void zeroParsesCorrectly() {
    String zero = " _  _  _     _  _  _  _  _ \n"
                + "| | _| _||_||_ |_   ||_||_|\n"
                + "|_||_  _|  | _||_|  ||_| _|\n"
                + "                           \n";
    assertEquals(BankOCR.parse(zero), 23456789);
  }

  @Test
  public void parseOneEntryFromFile() throws IOException {
    int result = 123456789;
    File f = new File("example.txt");
    assertEquals(BankOCR.parse(f).get(0).intValue(), result);
  }
}
