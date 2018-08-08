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

        List<String> newFile = new LinkedList<>();

        String path = "src/main/resources/com/beetrootmonkey/pico8filemerger/";
        File file = new File(path + "_main.p8");

        List<String> lines = FileUtils.readLines(file);

        lines.stream().forEachOrdered(l -> {
            Command command = getCommand(l);
            if (command != null) {
                if (command instanceof ImportCommand) {
                    ((ImportCommand) command).setPath(path);
                }
                command.getLines(l).stream().forEachOrdered(line -> newFile.add(line));
            } else {
                newFile.add(l);
            }
        });
        
        FileUtils.writeLines(new File(path + "test.p8"), newFile);

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
