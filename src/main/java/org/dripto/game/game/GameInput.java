package org.dripto.game.game;

import org.dripto.game.util.ConsoleColors;

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

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readInput(){
        return scanner.nextLine();
    }

    public String readInput(String msgKey, ConsoleColors color){
        printer.printMessages(msgKey, color);
        return readInput();
    }

    public int readIntegerInput(){
        boolean incorrectInput = true;
        int result = Integer.MIN_VALUE;
        while (incorrectInput) {
            try {
                result = Integer.parseInt(readInput());
                incorrectInput = false;
            } catch (NumberFormatException e) {
                printer.printString("That wasn't an interger. Please try again", ConsoleColors.RED);
                incorrectInput = true;
            }
        }
        return result;
    }

    public int readIntegerInput(String msgKey, ConsoleColors color){
        printer.printMessages(msgKey, color);
        return readIntegerInput();
    }
}
