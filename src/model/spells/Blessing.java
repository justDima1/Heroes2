package model.spells;

import model.units.Unit;

public class Blessing extends AbstractSpell {
    private int damageBonus;
    private int duration;

    public Blessing() {
        super("Blessing", 40);
        this.damageBonus = 10;
        this.duration = 3;
    }

    @Override
    public void cast() {
        System.out.println("Произнесено заклинание Blessing!");
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void apply(Unit target) {
        String unitName = target.getClass().getSimpleName();
        int currentAttack = target.getAttack();
        target.setAttack(currentAttack + damageBonus); // Увеличиваем атаку
        target.setDuration(duration); // Устанавливаем длительность эффекта

        System.out.println("Благословение наложено на " + unitName +
                ". Атака увеличена на " + damageBonus +
                ". Длительность: " + duration + " хода.");
    }
}