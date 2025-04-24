package model.heroes;

import model.units.Archer;
import model.units.Army;
import model.units.Pikeman;
import model.units.Swordsman;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private int x;
    private int y;
    private Army army;
    private String name;
    private int stableBonusTurnsLeft;
    private int movesLeft = 5;
    private int health = 100;
    private List<String> learnedSpells = new ArrayList<>();
    public Hero(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.army = new Army();
        this.name = name;
        this.learnedSpells = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            army.addUnit(new Archer());
        }
        for (int i = 0; i < 5; i++) {
            army.addUnit(new Swordsman());
        }
        for (int i = 0; i < 8; i++) {
            army.addUnit(new Pikeman());
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStableBonusTurnsLeft() {
        return stableBonusTurnsLeft;
    }
    public void setStableBonusTurnsLeft(int stableBonusTurnsLeft) {
        this.stableBonusTurnsLeft = stableBonusTurnsLeft;
    }
    public boolean hasStableBonus() {
        return stableBonusTurnsLeft > 0;
    }
    public void decreaseStableBonusTurns() {
        if (stableBonusTurnsLeft > 0) {
            stableBonusTurnsLeft--;
        }
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Army getArmy() {
        return army;
    }
    public void setArmy(Army army) {
        this.army = army;
    }
    public int getMovesLeft() {
        return movesLeft;
    }
    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public List<String> getLearnedSpells() { // Геттер
        return learnedSpells;
    }

    public void setLearnedSpells(List<String> learnedSpells) { // Сеттер
        this.learnedSpells = learnedSpells;
    }
}