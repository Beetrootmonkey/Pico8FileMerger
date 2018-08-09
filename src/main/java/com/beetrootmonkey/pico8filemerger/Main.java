package com.beetrootmonkey.pico8filemerger;

import com.beetrootmonkey.pico8filemerger.commands.Command;
import com.beetrootmonkey.pico8filemerger.commands.CommandRegistry;
import com.beetrootmonkey.pico8filemerger.commands.ImportCommand;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import com.google.common.io.Files;
import org.apache.log4j.Logger;

/**
 *
 * @author na
 */
public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class);
    private final static Map<String, String> HASHMAP = new HashMap<>();

    public static void main(String[] args) throws IOException {
        CommandRegistry.init();
        String input;
        String output;

        if (args.length >= 2) {
            input = args[0];
            output = args[1];

            LOGGER.info(String.format("Received argument for input path: \"%s\"", input));
            LOGGER.info(String.format("Received argument for output path: \"%s\"", output));
        } else if (args.length == 1) {
            input = args[0];
            LOGGER.info(String.format("Received argument for input path: \"%s\"", input));
            output = input;
            LOGGER.info(String.format("Using same path as output path: \"%s\"", output));
        } else {
            input = StringUtils.substringBeforeLast(new File("").getAbsolutePath(), "/");
            LOGGER.info(String.format("Using local path as input path: \"%s\"", input));
            output = input;
            LOGGER.info(String.format("Using same path as output path: \"%s\"", output));
        }

        while (true) {
            File[] directories = new File(input).listFiles();
            for (File f : directories) {
                if (f.isDirectory()) {
                    boolean success = mergeFiles(f.getAbsolutePath(), output);
                    if (success) {
                        LOGGER.info(String.format("...Finished!"));
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOGGER.error(ex);
                break;
            }
        }
        
        LOGGER.info("Shutting down...");
    }

    private static boolean detectFileChange(File file) {
        try {
            String oldHash = HASHMAP.get(file.getAbsolutePath());
            HashCode hc = Files.asByteSource(file).hash(Hashing.crc32());
            if (!hc.toString().equals(oldHash)) {
                HASHMAP.put(file.getAbsolutePath(), hc.toString());
                LOGGER.debug(String.format("Calculated new CRC32 hash for file \"%s\": %s", file.getAbsolutePath(), hc.toString()));
                return true;
            }
        } catch (IOException ex) {
            LOGGER.error(ex);
        }

        return false;
    }

    private static boolean mergeFiles(String inputPath, String outputPath) throws IOException {
        List<String> newFileContent = new LinkedList<>();
        final String mainFile = "_main.p8";

//        String path = "src/main/resources/com/beetrootmonkey/pico8filemerger/";
        File file = new File(inputPath + File.separator + mainFile);

        if (!file.exists()) {
            LOGGER.debug(String.format("Unable to find main file \"%s\"", file.getAbsolutePath()));
            return false;
        }

        boolean fileChanged = detectFileChange(file);

        if (!fileChanged) {
            LOGGER.debug("No file changes - skipping!");
            return false;
        }

        LOGGER.debug("Reading main file...");


        LOGGER.info(String.format("Building project: \"%s\"", inputPath));

        List<String> lines = FileUtils.readLines(file);

        lines.stream().forEachOrdered(l -> {
            Command command = getCommand(l);
            if (command != null) {
                LOGGER.debug(String.format("Found command: \"%s\"", command.getName()));
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
        LOGGER.debug(String.format("Writing final content to file \"%s\"", newFile.getAbsolutePath()));

        FileUtils.writeLines(newFile, newFileContent);

        return true;
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
