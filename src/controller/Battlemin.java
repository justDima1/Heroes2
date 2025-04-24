package controller;

import controller.BattleController;
import model.heroes.Hero;
import model.units.*;
import java.util.ArrayList;
import java.util.List;

public class Battlemin {
    public static void main(String[] args) {
        // Создаем героев
        Hero hero1 = new Hero(0, 0, "Герой 1");
        Hero hero2 = new Hero(0, 0, "Герой 2");

        // Создаем армии (пример)
        Army army1 = hero1.getArmy();
        Army army2 = hero2.getArmy();

        // Добавляем юнитов в армии (пример)
        for (int i = 0; i < 2; i++) {  // Теперь только 2
            army1.addUnit(new Angel());
            army2.addUnit(new Angel());
        }

        // Добавляем юнитов в армии (пример)
        for (int i = 0; i < 5; i++) {
            army1.addUnit(new Archer());
            army2.addUnit(new Swordsman());
            army1.addUnit(new Pikeman());
        }
        // Добавляем изученные заклинания герою
        List<String> learnedSpells = new ArrayList<>();
        learnedSpells.add("Fireball");
        learnedSpells.add("Blessing");
        learnedSpells.add("Clone");
        learnedSpells.add("HealthBoost");
        hero1.setLearnedSpells(learnedSpells); // <--- Добавляем заклинания
        hero2.setLearnedSpells(learnedSpells); // <--- Добавляем заклинания

        // Создаем BattleController
        BattleController battleController = new BattleController();

        // Запускаем битву
        battleController.startBattle(hero1, hero2);
    }
}