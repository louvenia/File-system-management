package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class Program {
    private static final Scanner inCommands = new Scanner(System.in);
    private static Path currentPath;

    public static void main(String[] args) {
        validatorArgs(args);
        System.out.println(currentPath);
        chooseCommand();
        inCommands.close();
    }

    private static void validatorArgs(String[] args) {
        if(args.length != 1) {
            errorMessage("Enter the path to the folder");
        } else if(!args[0].startsWith("--current-folder=")) {
            errorMessage("Enter folder path using flag --current-folder=");
        }
        currentPath = Paths.get(args[0].replace("--current-folder=", ""));
        if(!currentPath.isAbsolute()) {
            errorMessage("Enter the absolute path to the folder");
        } else if(!Files.isDirectory(currentPath)) {
            errorMessage("This path does not belong to a folder");
        }
    }

    private static void errorMessage(String message) {
        System.err.println(message);
        inCommands.close();
        System.exit(-1);
    }

    private static void chooseCommand() {
        String commandLine;
        String[] command;
        while(!(commandLine = inCommands.nextLine()).equals("exit")) {
            command = commandLine.split("\\s+");
            switch (command[0]) {
                case "ls":
                    lsCommand(command);
                    break;
                case "mv":
                    mvCommand(command);
                    break;
                case "cd":
                    cdCommand(command);
                    break;
                default:
                    System.err.println("This command " + "\"" +command[0] + "\"" + " is not supported by the program");
                    break;
            }
        }
    }

    private static void lsCommand(String[] command) {
        if(command.length < 3) {
            Path lsPath = currentPath;
            if(command.length == 2) {
                lsPath = changePath(command[1]);
            }
            if(Files.isDirectory(lsPath)) {
                File[] listFolder = lsPath.toFile().listFiles();
                for(int i = 0; i < Objects.requireNonNull(listFolder).length; i++) {
                    long sizeFolder = 0L;
                    if (listFolder[i].isDirectory()) {
                        sizeFolder = determiningSizeDirectory(listFolder[i], sizeFolder);
                        System.out.println(listFolder[i].getName() + " " + sizeFolder + " KB");
                    } else {
                        System.out.println(listFolder[i].getName() + " " + (listFolder[i].length() / 1024) + " KB");
                    }
                }
            } else {
                System.err.println("Incorrect data for the ls command");
            }
        } else {
            System.err.println("Incorrect number of arguments for the command ls");
        }
    }

    private static long determiningSizeDirectory(File listFolder, long size) {
        File[] listDirectory = listFolder.listFiles();
        for(int i = 0; i < Objects.requireNonNull(listDirectory).length; i++) {
            if(listDirectory[i].isDirectory()) {
                determiningSizeDirectory(listDirectory[i], size);
            } else {
                size += listDirectory[i].length() / 1024;
            }
        }
        return size;
    }

    private static void mvCommand(String[] command) {
        if(command.length == 3) {
            try {
                Path what = changePath(command[1]);
                Path where = changePath(command[2]);
                if (Files.exists(what) && Files.isDirectory(where)) {
                    Files.move(what, where.resolve(what.getFileName()));
                } else if (Files.exists(what)) {
                    Files.move(what, what.resolveSibling(where.getFileName()));
                } else {
                    System.err.println("Incorrect data for the mv command");
                }
            } catch (IOException error) {
                throw new RuntimeException(error);
            }
        } else {
            System.err.println("Incorrect number of arguments for the command mv");
        }
    }

    private static void cdCommand(String[] command) {
        if(command.length == 2) {
            Path where = changePath(command[1]);
            if(Files.isDirectory(where)) {
                currentPath = where;
                System.out.println(currentPath);
            } else {
                System.err.println("Incorrect data for the cd command");
            }
        } else {
            System.err.println("Incorrect number of arguments for the command cd");
        }
    }

    private static Path changePath(String path) {
        Path wherePath = Paths.get(currentPath + "/" + path);
        return wherePath.normalize();
    }
}
