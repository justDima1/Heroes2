package model.units;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Army implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Unit> units;

    public Army() {
        this.units = new ArrayList<>();
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
        unit.setArmy(this);
    }

    public void removeUnit(Unit unit) {

        this.units.remove(unit);

        System.out.println("Размер армии после удаления: " + units.size());
    }

    public boolean isEmpty() {
        return units.isEmpty();
    }

    public int getArcherCount() {
        int count = 0;
        for (Unit unit : units) {
            if (unit instanceof Archer) {
                count++;
            }
        }
        return count;
    }

    public int getPikemanCount() {
        int count = 0;
        for (Unit unit : units) {
            if (unit instanceof Pikeman) {
                count++;
            }
        }
        return count;
    }

    public int getSwordsmanCount() {
        int count = 0;
        for (Unit unit : units) {
            if (unit instanceof Swordsman) {
                count++;
            }
        }
        return count;
    }
}