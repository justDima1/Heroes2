package model.spells;

import model.units.Unit;

public interface Spell {
    String getName();
    int getTrainingTime(); // Время обучения в секундах
    void cast(); // Метод для "произнесения" заклинания

    void apply(Unit target);
}