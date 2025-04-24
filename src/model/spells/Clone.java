package model.spells;

import model.units.Unit;
import model.units.Angel;
import model.units.AngelClone;

public class Clone extends AbstractSpell {

    public Clone() {
        super("Clone", 7); // Заклинание "Clone" требует 7 секунд обучения
    }

    @Override
    public void cast() {
        System.out.println("Произнесено заклинание Clone!");
    }

    @Override
    public void apply(Unit target) {
        if (target instanceof Angel) {
            String unitName = target.getClass().getSimpleName();
            System.out.println("Попытка клонирования " + unitName);
            try {
                // Создаем не копию ангела, а экземпляр класса AngelClone
                AngelClone clone = new AngelClone();

                // Добавляем клона в армию
                clone.setArmy(target.getArmy());
                target.getArmy().getUnits().add(clone);
                System.out.println("Юнит AngelClone успешно добавлен!");

            } catch (Exception e) {
                System.out.println("Ошибка при клонировании юнита: " + e.getMessage());
            }
        } else {
            System.out.println("Заклинание Clone можно применять только к Ангелам.");
        }
    }
}