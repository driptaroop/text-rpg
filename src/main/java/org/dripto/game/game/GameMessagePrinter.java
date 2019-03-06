package org.dripto.game.game;

import org.dripto.game.util.ConsoleColors;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class GameMessagePrinter {

    private static GameMessagePrinter mInstance;
    private Properties properties;

    private GameMessagePrinter() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("messages.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getMessage(String key){
        return getProperties().getProperty(key);
    }

    public static GameMessagePrinter getInstance() {
        if (mInstance == null) {
            mInstance = new GameMessagePrinter();
        }
        return mInstance;
    }


    public void printMessage(String msgKey, ConsoleColors color){
        printString(properties.getProperty(msgKey), color);
    }

    public void printMessageFormatter(String msgKey, ConsoleColors color, String... fmt){
        String formattedString = String.format(properties.getProperty(msgKey), fmt);
        printString(formattedString, color);
    }

    public void printMessages(String msgKeys, ConsoleColors color){
        Arrays.stream(msgKeys.split(" "))
                .forEach(msgKey -> printMessage(msgKey, color));
    }

    public void printString(String msg, ConsoleColors color){
        System.out.println(color.getColor() + msg);
        System.out.println(ConsoleColors.RESET.getColor());
        System.out.flush();
    }

    public void printAscii(String file){
        printString(readAscii(file), ConsoleColors.RESET);
    }

    public String readAscii(String file){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(file + ".txt")));) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
