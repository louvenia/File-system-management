package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Program {
    private final Scanner filePath = new Scanner(System.in);

    public static void main(String[] args) {
        Program p = new Program();
        String strFilePath, lineFile = null;
        Map<String, String> signatures = new HashMap<>();
        p.getSignatures(signatures);
        while(!(strFilePath = p.filePath.nextLine()).equals("42")) {
            lineFile = p.readInputFiles(strFilePath, lineFile);
            p.defFileExtension(signatures, lineFile);
        }
        p.filePath.close();
    }

    private void getSignatures(Map<String, String> map) {
        String signaturesStr;
        String[] signaturesArr;

        try(FileInputStream file = new FileInputStream("./ex00/signatures.txt");
            Scanner bufBytes = new Scanner(file)) {
            while(bufBytes.hasNextLine()) {
                signaturesStr = bufBytes.nextLine();
                signaturesArr = signaturesStr.split(",");
                map.put(signaturesArr[0],signaturesArr[1].replaceAll("\\s",""));
            }
        } catch (Exception error) {
            System.err.println("signatures.txt does not exist at this path");
            filePath.close();
            System.exit(-1);
        }
    }

    private String readInputFiles(String inputFile, String result) {
        try(FileInputStream file = new FileInputStream(inputFile)) {
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

    private String byteArraysToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) {
            String toAppend = String.format("%02X", b);
            sb.append(toAppend);
        }
        return sb.toString();
    }

    private void defFileExtension(Map<String, String> map, String result) {
        boolean statusDef = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (result.contains(entry.getValue()))  {
                try(FileOutputStream resultFile = new FileOutputStream("./ex00/result.txt", true)) {
                    byte[] buffer = entry.getKey().getBytes();
                    resultFile.write(buffer);
                    resultFile.write('\n');
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
}
