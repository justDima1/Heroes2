package controller;

import model.units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniBattle {
    private double playerWinChance;
    public MiniBattle() {
        this.playerWinChance = 0.5;
    }

    public boolean startBattle() {
        System.out.println("\n--- Мини-битва начинается! ---");

        // Генерируем команду игрока (3 случайных юнита)
        List<Unit> playerTeam = generateRandomUnits();
        System.out.println("Команда игрока:");
        for (Unit unit : playerTeam) {
            System.out.println("  - " + unit.getClass().getSimpleName());
        }

        // Генерируем команду системы (3 случайных юнита)
        List<Unit> systemTeam = generateRandomUnits();
        System.out.println("Команда системы:");
        for (Unit unit : systemTeam) {
            System.out.println("  - " + unit.getClass().getSimpleName());
        }

        // Имитируем сражение
        boolean playerWon = simulateBattle(playerTeam, systemTeam);

        if (playerWon) {
            System.out.println("Победила команда игрока!");
            return true; // Победа игрока
        } else {
            System.out.println("Победила команда системы!");
            return false; // Победа системы (считаем как поражение игрока)
        }
    }

    // Метод для генерации случайных юнитов (заглушка)
    private List<Unit> generateRandomUnits() {
        List<Unit> randomUnits = new ArrayList<>();
        Random random = new Random();

        // Список классов юнитов (должен быть инициализирован где-то в классе)
        List<Class<? extends Unit>> unitClasses = new ArrayList<>();
        unitClasses.add(Archer.class);
        unitClasses.add(Swordsman.class);
        unitClasses.add(Pikeman.class);
        unitClasses.add(Cavalrist.class);
        unitClasses.add(Angel.class);
        unitClasses.add(Paladin.class);

        for (int i = 0; i < 3; i++) {
            Class<? extends Unit> unitClass = unitClasses.get(random.nextInt(unitClasses.size()));
            try {
                Unit unit = unitClass.getDeclaredConstructor().newInstance();
                randomUnits.add(unit);
            } catch (Exception e) {
                System.err.println("Ошибка при создании юнита: " + e.getMessage());

                randomUnits.add(new Archer()); // Юнит по умолчанию
            }
        }

        return randomUnits;
    }

    // Метод для имитации сражения между двумя командами
    private boolean simulateBattle(List<Unit> playerTeam, List<Unit> systemTeam) {
        Random random = new Random();
        return random.nextDouble() < playerWinChance; // true - победила команда игрока, false - победила команда системы
    }
}