package model.units;

import java.io.Serializable;

public class Unit implements Serializable {
    private static final long serialVersionUID = 1L;
    private int attack;
    private int defense;
    private int health;
    private int cost;
    private String name;
    private Army army;
    private int healthBoost = 0;
    private int duration = 0;

    public Unit(int attack, int defense, int health, int cost) {
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.cost = cost;
    }

    //конструктор копирования
    public Unit(int attack, int defense, int health, int cost, String name) {
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.cost = cost;
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealthBoost() {
        return healthBoost;
    }

    public void setHealthBoost(int healthBoost) {
        this.healthBoost = healthBoost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }
}