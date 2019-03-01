package org.dripto.game.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class GameMessagePrinter {

    private static GameMessagePrinter mInstance;
    Properties properties;

    private GameMessagePrinter() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("messages.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameMessagePrinter getInstance() {
        if (mInstance == null) {
            mInstance = new GameMessagePrinter();
        }
        return mInstance;
    }


    public void printMessage(String msgKey){
        System.out.println(properties.getProperty(msgKey));
        System.out.flush();
    }

    public void printMessages(String msgKeys){
        Arrays.stream(msgKeys.split(" "))
                .forEach(this::printMessage);
    }
}
