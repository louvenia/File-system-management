package ex01;

import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class Program {
    private static final List<String> linesFileA = new ArrayList<>();
    private static final List<String> linesFileB = new ArrayList<>();
    private static final List<Integer> occurrenceA = new Vector<>();
    private static final List<Integer> occurrenceB = new Vector<>();
    private static final SortedSet<String> dictionary = new TreeSet<>();

    public static int definedDenominator(List<Integer> occurrence) {
        int value = 0;
        for (Integer integer : occurrence) {
            value += integer * integer;
        }
        return value;
    }

    public static int definedNumerator() {
        int numerator = 0;
        for(int i = 0; i < occurrenceA.size(); i++) {
            numerator += occurrenceA.get(i) * occurrenceB.get(i);
        }
        return numerator;
    }

    public static double calculateSimilarity() {
        int numerator, valueA, valueB;
        double result, denominator;
        numerator = definedNumerator();
        valueA = definedDenominator(occurrenceA);
        valueB = definedDenominator(occurrenceB);
        denominator = sqrt(valueA) * sqrt(valueB);
        if(numerator != 0) {
            result = numerator/denominator;
        } else {
            result = 0;
        }
        return result;
    }

    public static void frequencyOccurrence(List<String> lineFile, List<Integer> occurrence) {
        int count;
        for (String d : dictionary) {
            count = 0;
            for (String l : lineFile) {
                if(d.equals(l)) {
                    count++;
                }
            }
            occurrence.add(count);
        }
    }
    public static void createDictionary() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("./ex01/dictionary.txt", false)) {
            for (String word : dictionary) {
                fileOutputStream.write(word.getBytes());
                fileOutputStream.write(' ');
            }
            fileOutputStream.write('\n');
        } catch (Exception error) {
            System.err.println("dictionary.txt cannot be created");
            System.exit(-1);
        }
    }

    public static void getLines(String filePath, List<String> linesFile) {
        File file = new File(filePath);
        try (Scanner scFiles = new Scanner(file)) {
            while(scFiles.hasNext()) {
                linesFile.add(scFiles.next());
            }
        } catch (Exception error) {
            System.err.println("The entered file does not exist");
            System.exit(-1);
        }
    }

    public static void main(String[] files) {
        double similarity;
        if(files.length != 2) {
            System.err.println("Wrong number of input arguments");
            System.exit(-1);
        }
        getLines(files[0], linesFileA);
        getLines(files[1], linesFileB);
        dictionary.addAll(linesFileA);
        dictionary.addAll(linesFileB);
        createDictionary();
        frequencyOccurrence(linesFileA, occurrenceA);
        frequencyOccurrence(linesFileB, occurrenceB);
        similarity = calculateSimilarity();
        System.out.printf("%s%.2f", "Similarity = ", similarity);
    }
}

