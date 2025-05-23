package model.units;

import java.io.Serializable;

public class Angel extends Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    public Angel() {
        super(200, 30, 300, 100, "Angel");  // Устанавливаем имя в конструкторе
    }

    // Конструктор копирования
    public Angel(Angel original) {
        super(original.getAttack(), original.getDefense(), original.getHealth(), original.getCost(), original.getName());
    }
}