package view;

import model.heroes.Hero;

import java.util.Scanner;

public class WitcherSchoolInterface {
    private Scanner scanner;

    public WitcherSchoolInterface() {
        scanner = new Scanner(System.in);
    }

    public void open(Hero hero) {
        System.out.println("\n--- Школа Ведьмаков ---");
        System.out.println("Этот функционал пока в разработке!");
        System.out.println("Нажмите Enter, чтобы продолжить...");
        scanner.nextLine(); // Ждем нажатия Enter
    }
}