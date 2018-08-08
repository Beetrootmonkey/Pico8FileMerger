package com.beetrootmonkey.pico8filemerger.commands;

import java.util.List;

/**
 *
 * @author na
 */
public interface Command {
    
    public String getName();
    
    public void setArguments(String[] arguments);  
    
    public List<String> getLines(String line);
}
