package model.buildings;

import model.SpellTrainer;
import model.spells.Spell;
import model.buildings.Building;

public class WitcherSchool extends Building {
    private int x;
    private int y;
    private SpellTrainer spellTrainer = new SpellTrainer();

    public WitcherSchool(int x, int y) {
        super("Школа Ведьмаков", 0);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void train(Spell spell) {
        spellTrainer.trainSpell(spell);
    }
}