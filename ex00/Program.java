package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Program {
    private static final Scanner filePath = new Scanner(System.in);
    private static final String txtResult = "./ex00/result.txt";
    private static final String txtSignatures = "./ex00/signatures.txt";

    public static void defFileExtension(Map<String, String> map, String result) {
        boolean statusDef = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (result.contains(entry.getValue()))  {
                try {
                    FileOutputStream resultFile = new FileOutputStream(txtResult, true);
                    byte[] buffer = entry.getKey().getBytes();
                    resultFile.write(buffer);
                    resultFile.write('\n');
                    resultFile.close();
                    statusDef = true;
                    System.out.println("PROCESSED");
                    break;
                } catch (Exception error) {
                    System.err.println("result.txt cannot be created");
                    filePath.close();
                    System.exit(-1);
                }
            }
        }
        if (!statusDef) {
            System.out.println("UNDEFINED");
        }
    }

    public static String byteArraysToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) {
            String toAppend = String.format("%02X", b);
            sb.append(toAppend);
        }
        return sb.toString();
    }

    public static String readInputFiles(String inputFile, String result) {
        try {
            FileInputStream file = new FileInputStream(inputFile);
            byte[] bufBytes = new byte[8];
            file.read(bufBytes, 0, 8);
            result = byteArraysToHex(bufBytes);
        } catch (Exception error) {
            System.err.println("The entered file does not exist at this path");
            filePath.close();
            System.exit(-1);
        }
        return result;
    }

    public static void getSignatures(Map<String, String> map) {
        String signaturesStr;
        String[] signaturesArr;
        try {
            FileInputStream file = new FileInputStream(txtSignatures);
            Scanner bufBytes = new Scanner(file);
            while(bufBytes.hasNextLine()) {
                signaturesStr = bufBytes.nextLine();
                signaturesArr = signaturesStr.split(",");
                map.put(signaturesArr[0],signaturesArr[1].replaceAll("\\s",""));
            }
            file.close();
            bufBytes.close();
        } catch (Exception error) {
            System.err.println("signatures.txt does not exist at this path");
            filePath.close();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        String strFilePath, lineFile = " ";
        Map<String, String> signatures = new HashMap<>();
        getSignatures(signatures);
        while(!(strFilePath = filePath.nextLine()).equals("42")) {
            lineFile = readInputFiles(strFilePath, lineFile);
            defFileExtension(signatures, lineFile);
        }
        filePath.close();
    }
}
