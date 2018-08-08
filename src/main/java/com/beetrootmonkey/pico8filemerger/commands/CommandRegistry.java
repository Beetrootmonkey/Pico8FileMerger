package com.beetrootmonkey.pico8filemerger.commands;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author na
 */
public class CommandRegistry {
    public static List<Command> commands = new ArrayList<>();
    
    public static void init() {
      commands.add(new ImportCommand());
    };
}
