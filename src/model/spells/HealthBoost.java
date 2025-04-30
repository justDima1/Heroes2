package model.spells;

import model.units.Unit;

public class HealthBoost implements Spell {
    private int healthIncrease;
    private int duration;
    private String name;
    private int trainingTime;

    public HealthBoost() {
        this.name = "Health Boost";
        this.trainingTime = 15;
        this.healthIncrease = 50;
        this.duration = 3;
    }

    @Override
    public void cast() {
        System.out.println("добавлено заклинание Health Boost!");
    }

    public int getHealthIncrease() {
        return healthIncrease;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTrainingTime() {
        return trainingTime;
    }

    @Override
    public void apply(Unit target) {
        String unitName = target.getClass().getSimpleName();
        int currentHealth = target.getHealth();
        target.setHealth(currentHealth + healthIncrease); // Увеличиваем здоровье
        target.setDuration(duration); // Устанавливаем длительность эффекта

        System.out.println("Health Boost применен к " + unitName +
                ". Здоровье увеличено на " + healthIncrease +
                ". Длительность: " + duration + " хода.");
    }
}