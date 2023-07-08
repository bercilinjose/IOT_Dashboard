package com.eoxys.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorLogger {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ColorLogger.class);
    
    
    public void logDebug(String logging) {
        LOGGER.debug("\u001B[34m" + logging + "\u001B[0m");
    }
    public void logInfo(String logging) {
        LOGGER.info("\u001B[32m" + logging + "\u001B[0m");
    }
    
    public void logError(String logging) {
        LOGGER.error("\u001B[31m" + logging + "\u001B[0m");
    }
    public String dateTime() {
    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   return dtf.format(now);  
    }
}
