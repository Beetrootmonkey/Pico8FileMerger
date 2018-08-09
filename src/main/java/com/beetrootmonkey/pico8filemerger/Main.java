package com.beetrootmonkey.pico8filemerger;

import com.beetrootmonkey.pico8filemerger.commands.Command;
import com.beetrootmonkey.pico8filemerger.commands.CommandRegistry;
import com.beetrootmonkey.pico8filemerger.commands.ImportCommand;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author na
 */
public class Main {

    public static void main(String[] args) throws IOException {
        CommandRegistry.init();
        String input = null;
        String output = null;
        
        if (args.length >= 2) {
            input = args[0];
            output = args[1];
            
            System.out.println(String.format("Received argument for input path: \"%s\"", input));
            System.out.println(String.format("Received argument for output path: \"%s\"", output));
        } else if (args.length == 1) {
            input = args[0];
            System.out.println(String.format("Received argument for input path: \"%s\"", input));
            output = input;
            System.out.println(String.format("Using same path as output path: \"%s\"", output));
        } else {
            input = StringUtils.substringBeforeLast(new File("").getAbsolutePath(), "/");
            System.out.println(String.format("Using local path as input path: \"%s\"", input));
            output = input;
            System.out.println(String.format("Using same path as output path: \"%s\"", output));
        }

        File[] directories = new File(input).listFiles();
        for(File f : directories) {
            if (f.isDirectory()) {
                System.out.println(String.format("Found directory: \"%s\"", f.getAbsolutePath()));
                mergeFiles(f.getAbsolutePath(), output);
            }
        }
        System.out.println("Finished mergin!");
        
    }
    
    private static void mergeFiles(String inputPath, String outputPath) throws IOException {
        List<String> newFileContent = new LinkedList<>();
        final String mainFile = "_main.p8";

//        String path = "src/main/resources/com/beetrootmonkey/pico8filemerger/";
        File file = new File(inputPath + File.separator + mainFile);
        
        if (!file.exists()) {
            System.out.println(String.format("Unable to find main file \"%s\"", file.getAbsolutePath()));
            return;
        }
        System.out.println("Reading main file...");

        List<String> lines = FileUtils.readLines(file);

        lines.stream().forEachOrdered(l -> {
            Command command = getCommand(l);
            if (command != null) {
                System.out.println(String.format("Found command: \"%s\"", command.getName()));
                if (command instanceof ImportCommand) {
                    ((ImportCommand) command).setPath(inputPath);
                }
                command.getLines(l).stream().forEachOrdered(line -> newFileContent.add(line));
            } else {
                newFileContent.add(l);
            }
        });
        
        String newFilePath = outputPath != null ? outputPath : StringUtils.substringBeforeLast(inputPath, File.separator);
        String newFileName = StringUtils.substringAfterLast(inputPath, File.separator);
        File newFile = new File(newFilePath + File.separator + newFileName + ".p8");
        System.out.println(String.format("Writing final content to file \"%s\"", newFile.getAbsolutePath()));
        
        FileUtils.writeLines(newFile, newFileContent);
        
//        print(newFile);
    }

    private static void print(List<String> list) {
        list.stream().forEach(l -> {
            System.out.println(l);
        });
    }

    private static Command getCommand(String line) {
        if (line == null) {
            return null;
        }

        String[] words = line.split(" ");
        String[] args = new String[0];
        String cmd = null;
        if (words != null && words.length > 0) {
            cmd = words[0];
            args = new String[words.length - 1];
            for (int i = 0; i < args.length; i++) {
                args[i] = words[i + 1];
            }
        }

        if (cmd == null) {
            return null;
        }

        String command = cmd;

        Optional optional = CommandRegistry.commands.stream().filter(c -> {

            return command.equals(c.getName());
        }).findFirst();
        Command cmdObj = optional.isPresent() ? (Command) optional.get() : null;
        if (cmdObj != null) {
            cmdObj.setArguments(args);
        }
        return cmdObj;
    }

}
