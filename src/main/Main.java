package main;

import controller.GameController;

import java.io.IOException;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame();

    }
}