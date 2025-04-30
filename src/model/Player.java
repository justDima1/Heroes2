package model;

import model.heroes.Hero;
import model.units.Unit; // Импортируй Unit
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Hero> heroes = new ArrayList<>();
    private Hero currentHero;
    private Gold gold; // Изменено на Gold
    private Gems gems; // Изменено на Gems
    private City city;
    private int resources;
    private List<Unit> units = new ArrayList<>();

    public Player(String name, Hero hero, int startGold) {
        this.name = name;
        this.heroes.add(hero);
        this.currentHero = hero;
        this.gold = new Gold(startGold); // Создаем объект Gold
        this.gems = new Gems(10); // Создаем объект Gems
        this.resources = 0;
        this.units = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public Gold getGold() {
        return gold;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }

    public Gems getGems() {
        return gems;
    }

    public void setGems(Gems gems) {
        this.gems = gems;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void addHero(Hero aiHero) {
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public void clearUnits() {
        this.units.clear();
    }
    public void loseHealthAllUnits(double percentage) {
        for (Unit unit : units) {
            unit.loseHealth(percentage);
        }
    }

    public void healUnits(double percentage) {
        for (Unit unit : units) {
            int health = unit.getHealth();
            health += (int) (health * percentage);
            unit.setHealth(health);
        }
    }
}