package org.dripto.game.util;

import java.util.Scanner;

public enum GameInput {
    INSTANCE;
    Scanner scanner = new Scanner(System.in);
    GameMessagePrinter printer = GameMessagePrinter.INSTANCE;

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
