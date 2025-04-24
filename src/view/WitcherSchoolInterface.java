package view;

import model.buildings.WitcherSchool;
import model.heroes.Hero;
import model.spells.*;

import java.util.Scanner;

public class WitcherSchoolInterface {
    private Scanner scanner;
    private WitcherSchool school;

    public WitcherSchoolInterface(WitcherSchool school, Scanner scanner) {
        this.school = school;
        this.scanner = scanner;
    }

    public void open(Hero hero) {
        int choice;
        do {
            System.out.println("\n--- Школа Ведьмаков ---");
            System.out.println("Что вы хотите изучить?");

            Spell[] availableSpells = {
                    new Fireball(),
                    new Blessing(),
                    new HealthBoost(),
                    new Clone()
            };

            for (int i = 0; i < availableSpells.length; i++) {
                System.out.println((i + 1) + ". " + availableSpells[i].getName() +
                        " (Время обучения: " + availableSpells[i].getTrainingTime() + " сек.)");
            }

            System.out.println("0. Выйти");

            choice = -1;
            while (choice < 0 || choice > availableSpells.length) {
                System.out.print("Выберите номер заклинания для изучения: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Пожалуйста, введите число.");
                }
            }

            if (choice > 0 && choice <= availableSpells.length) {
                Spell selectedSpell = availableSpells[choice - 1];
                school.train(selectedSpell);
                hero.getLearnedSpells().add(selectedSpell.getName()); // <--- Добавляем заклинание
            } else if (choice == 0) {
                System.out.println("Вы покидаете школу ведьмаков.");
            } else {
                System.out.println("Неверный выбор.");
            }

            if (choice != 0) {
                System.out.println("Нажмите Enter, чтобы продолжить...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }
}