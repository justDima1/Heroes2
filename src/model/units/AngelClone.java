package model.units;

import java.io.Serializable;

public class AngelClone extends Unit implements Serializable {
    private static final long serialVersionUID = 1L;

    public AngelClone() {
        super(100, 15, 1, 0, "AngelClone");
        setName("AngelClone");
    }

    // Конструктор копирования
    public AngelClone(AngelClone original) {
        super(original.getAttack(), original.getDefense(), original.getHealth(), original.getCost(), original.getName());
    }
}