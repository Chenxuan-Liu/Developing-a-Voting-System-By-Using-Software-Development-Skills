package com.jetbrains;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public void readFile(String Inputfile){
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("E:\\CSVDemo.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
                System.out.println(getRecordFromLine(scanner.nextLine()));
            }
        }

    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello");
    }
}
