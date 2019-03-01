package org.dripto.game.game;

import java.util.Scanner;

public class GameInput {
    private static GameInput mInstance;
    Scanner scanner = new Scanner(System.in);
    GameMessagePrinter printer = GameMessagePrinter.getInstance();

    private GameInput() {
    }

    public static GameInput getInstance() {
        if (mInstance == null) {
            mInstance = new GameInput();
        }
        return mInstance;
    }

    public String readInput(){
        return scanner.nextLine();
    }

    public String readInput(String msgKey){
        printer.printMessages(msgKey);
        return readInput();
    }

    public int readIntegerInput(){
        return Integer.parseInt(readInput());
    }

    public int readIntegerInput(String msgKey){
        printer.printMessages(msgKey);
        return readIntegerInput();
    }
}
