package model.spells;

import model.units.Unit;

public class Fireball extends AbstractSpell {

    private int damage = 50; // Урон от Fireball

    public Fireball() {
        super("Fireball", 5);
    }

    @Override
    public void cast() {
        System.out.println("добавлено заклинание Fireball!");
    }

    @Override
    public void apply(Unit target) {
        // Наносим урон юниту
        target.takeDamage(damage); // Наносим урон
        String unitName = target.getClass().getSimpleName();
        System.out.println("Fireball применен к " + unitName + ". Нанесено " + damage + " урона.");
        System.out.println("Текущее здоровье юнита: " + target.getHealth());
    }
}