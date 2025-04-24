package model.spells;

public abstract class AbstractSpell implements Spell {
    private final String name;
    private final int trainingTime;

    public AbstractSpell(String name, int trainingTime) {
        this.name = name;
        this.trainingTime = trainingTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTrainingTime() {
        return trainingTime;
    }
}