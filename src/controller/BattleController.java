package controller;

import model.heroes.Hero;
import model.spells.*;
import model.units.Archer;
import model.units.Pikeman;
import model.units.Swordsman;
import model.units.Angel;
import model.units.Cavalrist;
import model.units.Paladin;
import model.units.Unit;
import model.units.Army;
import model.units.AngelClone;
import java.util.*;

public class BattleController {

    private Scanner scanner;

    public BattleController() {
        this.scanner = new Scanner(System.in);
    }

    public void startBattle(Hero hero1, Hero hero2) {
        Army attackerArmy = hero1.getArmy();
        Army defenderArmy = hero2.getArmy();

        System.out.println("Началась битва между " + hero1.getName() + " и " + hero2.getName() + "!");

        while (!attackerArmy.isEmpty() && !defenderArmy.isEmpty()) {
            //  Ход игрока
            if (!attackerArmy.isEmpty()) {
                playerTurn(hero1, hero2);
            } else {
                System.out.println(hero1.getName() + " не имеет больше юнитов для атаки.");
                break;
            }

            //  Ход ИИ
            if (!defenderArmy.isEmpty()) {
                aiTurn(hero2, hero1);
            } else {
                System.out.println(hero2.getName() + " не имеет больше юнитов для атаки.");
                break;
            }
        }

        //  Определение победителя
        if (attackerArmy.isEmpty()) {
            System.out.println(hero2.getName() + " победил!");
        } else {
            System.out.println(hero1.getName() + " победил!");
        }
    }
    private void playerTurn(Hero attackerHero, Hero defenderHero) {
        applyEndOfTurnEffects(attackerHero); // <--- Применяем эффекты

        Army attackerArmy = attackerHero.getArmy();
        Army defenderArmy = defenderHero.getArmy();

        System.out.println("\n--- Ход " + attackerHero.getName() + " ---");
        displayArmies(attackerHero, defenderHero);

        action(attackerHero, defenderHero); // <--- Вызываем action()
    }

    private void action(Hero attackerHero, Hero defenderHero) {
        System.out.println("Выберите действие:");
        System.out.println("1. Атаковать");
        System.out.println("2. Использовать заклинание");

        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.print("Введите номер действия: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
            }
        }

        if (choice == 1) {
            // Логика атаки
            attackAction(attackerHero, defenderHero);
        } else if (choice == 2) {
            // Логика использования заклинания
            if (attackerHero.getLearnedSpells().isEmpty()) { // Проверяем наличие изученных заклинаний
                System.out.println("У вас нет изученных заклинаний. Выберите другое действие.");
                action(attackerHero, defenderHero); // Возвращаемся к выбору действия
            } else {
                castSpellAction(attackerHero, defenderHero, defenderHero.getArmy()); // Передаем армию защищающегося
            }
        }
    }

    private void aiTurn(Hero attackerHero, Hero defenderHero) {
        applyEndOfTurnEffects(attackerHero); // <--- Применяем эффекты

        Army attackerArmy = attackerHero.getArmy();
        Army defenderArmy = defenderHero.getArmy();

        System.out.println("\n--- Ход " + attackerHero.getName() + " (AI) ---");

        if (attackerArmy.getUnits().isEmpty()) {
            System.out.println("У " + attackerHero.getName() + " (AI) нет юнитов для атаки!");
            return;
        }

        if (defenderArmy.getUnits().isEmpty()) {
            System.out.println("У " + defenderHero.getName() + " нет целей для атаки!");
            return;
        }

        String attackerUnit = attackerArmy.getUnits().get(new Random().nextInt(attackerArmy.getUnits().size())).getClass().getSimpleName();
        String defenderUnit = defenderArmy.getUnits().get(new Random().nextInt(defenderArmy.getUnits().size())).getClass().getSimpleName();

        attack(attackerUnit, defenderUnit, attackerArmy, defenderArmy);
    }

    private String chooseAttacker(List<String> attackerUnits) {

        if (attackerUnits.isEmpty()) {
            System.out.println("У вас нет юнитов для атаки!");
            return null;
        }

        System.out.println("Выберите отряд для атаки:");
        for (int i = 0; i < attackerUnits.size(); i++) {
            System.out.println((i + 1) + ". " + attackerUnits.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > attackerUnits.size()) {
            System.out.print("Введите номер отряда: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
            }
        }

        return attackerUnits.get(choice - 1);
    }

    private String chooseTarget(List<String> defenderUnits) {
        if (defenderUnits.isEmpty()) {
            System.out.println("У противника нет целей для атаки!");
            return null;
        }

        System.out.println("Выберите цель для атаки:");
        for (int i = 0; i < defenderUnits.size(); i++) {
            System.out.println((i + 1) + ". " + defenderUnits.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > defenderUnits.size()) {
            System.out.print("Введите номер цели: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            }
        }

        return defenderUnits.get(choice - 1);
    }

    public void attack(String attackerUnitType, String defenderUnitType, Army attackerArmy, Army defenderArmy) {
        System.out.println(attackerUnitType + " атакует " + defenderUnitType + "!");
        List<Unit> attackerSquad = new ArrayList<>();
        for (Unit unit : attackerArmy.getUnits()) {
            if (Objects.equals(unit.getClass().getSimpleName(), attackerUnitType)) {
                attackerSquad.add(unit);
            }
        }
        List<Unit> defenderSquad = new ArrayList<>();
        for (Unit unit : defenderArmy.getUnits()) {
            if (Objects.equals(unit.getClass().getSimpleName(), defenderUnitType)) {
                defenderSquad.add(unit);
            }
        }
        if (attackerSquad.isEmpty() || defenderSquad.isEmpty()) {
            System.out.println("Нет юнитов для атаки или защиты!");
            return;
        }
        int baseDamage = attackerSquad.get(0).getAttack();
        int defenderHealth = defenderSquad.get(0).getHealth();

        // <--- Добавляем проверку на ноль
        if (defenderHealth == 0) {
            defenderHealth = 1; // Чтобы избежать деления на ноль
        }

        int totalDamage = baseDamage * attackerSquad.size();
        int killedUnits = Math.min(totalDamage / defenderHealth, defenderSquad.size());
        System.out.println(totalDamage);
        System.out.println(defenderHealth);
        int unitsRemoved = 0;
        List<Unit> unitsToRemove = new ArrayList<>();
        for (int i = 0; i < defenderArmy.getUnits().size(); i++) {
            if (unitsRemoved >= killedUnits) break;
            if (Objects.equals(defenderArmy.getUnits().get(i).getClass().getSimpleName(), defenderUnitType)) {
                unitsToRemove.add(defenderArmy.getUnits().get(i));
                unitsRemoved++;
            }
        }
        for (Unit unitToRemove : unitsToRemove) {
            defenderArmy.getUnits().remove(unitToRemove);
        }
        if (killedUnits > 0) {
            System.out.println("Убито " + killedUnits + " юнитов.");
        } else {
            System.out.println("Никто не погиб");
        }
        System.out.println("Сражение завершено.");
    }
    private void displayArmies(Hero hero1, Hero hero2) {
        System.out.println("\n--- Армия " + hero1.getName() + "(Вы)---");
        displayArmy(hero1.getArmy());
        System.out.println("\n--- Армия " + hero2.getName() + "(ИИ) ---");
        displayArmy(hero2.getArmy());
    }

    private void displayArmy(Army army) {
        Map<String, Integer> unitCounts = new HashMap<>();

        for (Unit unit : army.getUnits()) {
            String unitType = unit.getClass().getSimpleName();
            unitCounts.put(unitType, unitCounts.getOrDefault(unitType, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : unitCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private List<String> getSquads(Army army) {
        List<String> squads = new ArrayList<>();
        Map<String, Integer> unitCounts = new HashMap<>();

        for (Unit unit : army.getUnits()) {
            String unitType = unit.getClass().getSimpleName();
            unitCounts.put(unitType, unitCounts.getOrDefault(unitType, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : unitCounts.entrySet()) {
            squads.add(entry.getKey());
        }
        return squads;
    }
    private void attackAction(Hero attackerHero, Hero defenderHero) {
        Army attackerArmy = attackerHero.getArmy();
        Army defenderArmy = defenderHero.getArmy();

        //  Выбор атакующего отряда
        List<String> attackerUnits = getSquads(attackerArmy);
        String attackerUnit = chooseAttacker(attackerUnits);
        if (attackerUnit == null) {
            return;
        }

        //  Выбор цели
        List<String> defenderUnits = getSquads(defenderArmy);
        String defenderUnit = chooseTarget(defenderUnits);
        if (defenderUnit == null) {
            return;
        }

        //  Атака
        attack(attackerUnit, defenderUnit, attackerArmy, defenderArmy);
    }
    private void castSpellAction(Hero attackerHero, Hero defenderHero, Army defenderArmy) {
        System.out.println("Выберите заклинание для использования:");
        List<String> learnedSpells = attackerHero.getLearnedSpells();
        if (learnedSpells.isEmpty()) {
            System.out.println("У вас нет изученных заклинаний.");
            return;
        }

        for (int i = 0; i < learnedSpells.size(); i++) {
            System.out.println((i + 1) + ". " + learnedSpells.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > learnedSpells.size()) {
            System.out.print("Введите номер заклинания: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
            }
        }

        String spellName = learnedSpells.get(choice - 1);

        Spell spell = createSpellInstance(spellName);

        if (spell == null) {
            System.out.println("Не удалось создать экземпляр заклинания: " + spellName);
            return;
        }

        Army targetArmy;
        if (spellName.equals("Fireball")) {
            targetArmy = defenderHero.getArmy();
        } else {
            targetArmy = attackerHero.getArmy();
        }

        String targetUnitType = chooseTargetForSpell(targetArmy, spellName);

        if (targetUnitType == null) {
            System.out.println("Не выбрана цель для заклинания.");
            return;
        }

        applySpellToUnits(spell, targetArmy, targetUnitType);
        System.out.println("Вы использовали заклинание: " + spellName + " на " + targetUnitType);

        // Удаляем убитых юнитов после применения Fireball
        if (spellName.equals("Fireball")) {
            removeDeadUnits(targetArmy, targetUnitType);
        }
    }

    private void applySpellToUnits(Spell spell, Army army, String targetUnitType) {
        List<Unit> unitsToAdd = new ArrayList<>(); // Список юнитов для добавления после итерации

        Iterator<Unit> iterator = army.getUnits().iterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getClass().getSimpleName().equals(targetUnitType)) {
                if (spell instanceof Clone) {  // Отдельная логика для клонирования
                    if (unit instanceof Angel) {

                        // Создаем клона и добавляем его в список для добавления
                        AngelClone clone = new AngelClone();
                        unitsToAdd.add(clone);
                        System.out.println("Юнит AngelClone добавлен!");
                    } else {
                        System.out.println("Заклинание Clone можно применять только к Ангелам.");
                    }
                } else {
                    spell.apply(unit); // Применяем остальные заклинания
                }
            }
        }

        // Добавляем клонированных юнитов в армию после итерации
        for (Unit unitToAdd : unitsToAdd) {
            unitToAdd.setArmy(army); // Устанавливаем армию клону
            army.getUnits().add(unitToAdd);
        }
    }

    public void removeDeadUnits(Army army, String targetUnitType) {
        Iterator<Unit> iterator = army.getUnits().iterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getClass().getSimpleName().equals(targetUnitType) && unit.getHealth() <= 0) {
                iterator.remove();
                System.out.println("Юнит " + unit.getClass().getSimpleName() + " убит заклинанием.");
            }
        }
    }

    private String chooseTargetForSpell(Army army, String spellName) {
        System.out.println("Выберите цель для заклинания:");
        List<String> availableUnits = getSquads(army); // Получаем все доступные типы юнитов

        // Если заклинание Clone, то предлагаем только Ангелов
        List<String> defenderUnits = new ArrayList<>();
        if (spellName.equals("Clone")) {
            for (String unitType : availableUnits) {
                if (unitType.equals("Angel")) {
                    defenderUnits.add(unitType);
                }
            }
            if (defenderUnits.isEmpty()) {
                System.out.println("В армии нет Ангелов, которых можно клонировать.");
                return null; // Нет Ангелов для клонирования
            }
        } else {
            defenderUnits = availableUnits; // Для остальных заклинаний - все типы юнитов
        }

        // Выводим доступные типы юнитов на экран
        for (int i = 0; i < defenderUnits.size(); i++) {
            System.out.println((i + 1) + ". " + defenderUnits.get(i));
        }

        // Запрашиваем ввод пользователя
        int choice = -1;
        while (choice < 1 || choice > defenderUnits.size()) {
            System.out.print("Введите номер цели: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
            }
        }
        return defenderUnits.get(choice - 1);
    }

    private Spell createSpellInstance(String spellName) {
        switch (spellName) {
            case "Fireball":
                return new Fireball();
            case "Blessing":
                return new Blessing();
            case "HealthBoost":
                return new HealthBoost();
            case "Clone":
                return new Clone();
            default:
                System.out.println("Неизвестное заклинание: " + spellName);
                return null;
        }
    }

    private void applyEndOfTurnEffects(Hero hero) {
        Army army = hero.getArmy();
        for (Unit unit : army.getUnits()) {
            if (unit.getDuration() > 0) {
                unit.setDuration(unit.getDuration() - 1);
                if (unit.getDuration() == 0) {
                    if (unit.getAttack() > 5) {
                        unit.setAttack(unit.getAttack() - 10);
                        System.out.println("Действие Blessing на " + unit.getClass().getSimpleName() + " закончилось.");
                    }
                    if (unit.getHealthBoost() > 0) {
                        unit.setHealthBoost(0);
                        System.out.println("Действие HealthBoost на " + unit.getClass().getSimpleName() + " закончилось.");
                    }

                    int currentHealth = unit.getHealth();
                    unit.setHealth(currentHealth - 50);
                    System.out.println("Действие HealthBoost на " + unit.getClass().getSimpleName() + " закончилось, здоровье возвращено к прежнему значению.");
                }
            }
        }
    }

}