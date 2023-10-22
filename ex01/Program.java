package ex01;

import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class Program {
    private final List<String> linesFileA = new ArrayList<>();
    private final List<String> linesFileB = new ArrayList<>();
    private final List<Integer> occurrenceA = new ArrayList<>();
    private final List<Integer> occurrenceB = new ArrayList<>();
    private final SortedSet<String> dictionary = new TreeSet<>();

    public static void main(String[] files) {
        Program p = new Program();
        double similarity;

        if(files.length != 2) {
            System.err.println("Wrong number of input arguments");
            System.exit(-1);
        }

        p.getLines(files[0], p.linesFileA);
        p.getLines(files[1], p.linesFileB);

        p.dictionary.addAll(p.linesFileA);
        p.dictionary.addAll(p.linesFileB);

        p.createDictionary();
        p.frequencyOccurrence(p.linesFileA, p.occurrenceA);
        p.frequencyOccurrence(p.linesFileB, p.occurrenceB);

        similarity = p.calculateSimilarity();
        System.out.printf("%s%.2f", "Similarity = ", similarity);
    }

    private void getLines(String filePath, List<String> linesFile) {
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

    private void createDictionary() {
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

    private void frequencyOccurrence(List<String> lineFile, List<Integer> occurrence) {
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

    private double calculateSimilarity() {
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

    private int definedNumerator() {
        int numerator = 0;
        for(int i = 0; i < occurrenceA.size(); i++) {
            numerator += occurrenceA.get(i) * occurrenceB.get(i);
        }
        return numerator;
    }

    private int definedDenominator(List<Integer> occurrence) {
        int value = 0;
        for (Integer integer : occurrence) {
            value += integer * integer;
        }
        return value;
    }
}

