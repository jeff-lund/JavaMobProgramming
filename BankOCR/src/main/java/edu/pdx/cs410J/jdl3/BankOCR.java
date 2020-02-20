package edu.pdx.cs410J.jdl3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for getting started with a code kata
 * <p>
 * Use IntelliJ's "Refactor | Rename..." command to change the name of this
 * class (and its tests).
 */
public class BankOCR {


    public static void main(String[] args) {

    }

    public static List<String> makeTriples(String number) throws IllegalArgumentException {
        if (number.length() != 4 *  28) {
            throw new IllegalArgumentException("Invalid number string length");
        }

        List<String> results = new ArrayList<>();
        for (int characterIndex = 0; characterIndex < 9; ++characterIndex) {
            StringBuilder sb = new StringBuilder();
            for (int rowIndex = 0; rowIndex < 3; ++rowIndex) {
                for (int colIndex = characterIndex * 3;
                     colIndex < characterIndex * 3 + 3; ++colIndex) {
                    int position = colIndex + rowIndex * 28;
                    sb.append(number.charAt(position));
                }
            }
            results.add(sb.toString());
        }
        return results;
}

    public static String transform(String number)
            throws IllegalArgumentException {
        Map<String, String> map = new HashMap<>();
        map.put("     |  |", "1");
        map.put(" _  _||_ ", "2");
        map.put(" _  _| _|", "3");
        map.put("   |_|  |", "4");
        map.put(" _ |_  _|", "5");
        map.put(" _ |_ |_|", "6");
        map.put(" _   |  |", "7");
        map.put(" _ |_||_|", "8");
        map.put(" _ |_| _|", "9");
        map.put(" _ | ||_|", "0");

        String ret = map.get(number);
        if (ret == null) {
            throw new IllegalArgumentException("Unrecognized character");
        }

        return ret;
    }

    public static int parse(String number) throws NumberFormatException {
        List<String> triples = makeTriples(number);
        StringBuilder sb = new StringBuilder();
        for (String triple : triples) {
            sb.append(transform(triple));
        }
        return Integer.parseInt(sb.toString());
    }

    public static List<Integer> parse(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder buff = new StringBuilder();
        String curLine;
        while((curLine = br.readLine()) != null){
            buff.append(curLine + "\n");
        }

        List<Integer> toReturn = new ArrayList<Integer>();
        toReturn.add(parse(buff.toString()));
        return toReturn;
    }
}