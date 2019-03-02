package org.dripto.game.exception;

public class WrongChoiceException extends Exception {
    public WrongChoiceException(String incorrect_choice) {
        super(incorrect_choice);
    }
}
