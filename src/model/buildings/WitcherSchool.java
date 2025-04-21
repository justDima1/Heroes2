package model.buildings;

public class WitcherSchool extends Building {
    private int x;
    private int y;

    public WitcherSchool(int x, int y) {
        super("Школа Ведьмаков", 0); // Вызываем конструктор родительского класса
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}