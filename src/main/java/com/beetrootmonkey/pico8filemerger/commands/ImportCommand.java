package com.beetrootmonkey.pico8filemerger.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author na
 */
public class ImportCommand implements Command {

    private String[] args;
    private String path;

    @Override
    public List<String> getLines(String line) {
        try {
            if (args == null) {
                return Arrays.asList(line);
            }

            File file = new File(path + args[0] + ".p8");

            List<String> lines = FileUtils.readLines(file);
            lines.add(0, "--" + line);
            lines.add("");
            return lines;
        } catch (IOException ex) {
            Logger.getLogger(ImportCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Arrays.asList(line);
    }

    @Override
    public void setArguments(String[] arguments) {
        this.args = arguments;
    }

    @Override
    public String getName() {
        return "@import";
    }

    public void setPath(String path) {
        this.path = path;
    }

}
