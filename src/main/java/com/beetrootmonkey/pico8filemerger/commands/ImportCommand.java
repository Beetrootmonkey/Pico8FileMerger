package com.beetrootmonkey.pico8filemerger.commands;

import com.beetrootmonkey.pico8filemerger.Main;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author na
 */
public class ImportCommand implements Command {
    private final static Logger LOGGER = Logger.getLogger(ImportCommand.class);

    private String[] args;
    private String path;

    @Override
    public List<String> getLines(String line) {
        try {
            if (args == null) {
                return Arrays.asList(line);
            }

            File file = new File(path + File.separator + args[0] + ".p8");

            if (!file.exists()) {
                LOGGER.error(String.format("Unable to find or read import file \"%s\"", file));
            }

            List<String> lines = FileUtils.readLines(file);
            lines.add(0, "--" + line);
            return lines;
        } catch (IOException ex) {
            LOGGER.error(ex);
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
