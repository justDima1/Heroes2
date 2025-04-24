package model;

import model.spells.Spell;

public class SpellTrainer {

    public void trainSpell(Spell spell) {
        System.out.println("Начало обучения заклинанию " + spell.getName());

        Thread trainingThread = new Thread(() -> {
            try {
                Thread.sleep(spell.getTrainingTime() * 1000); // Преобразуем секунды в миллисекунды
            } catch (InterruptedException e) {
                System.err.println("Обучение прервано!");
                return; // Выходим из потока, если обучение прервано
            }
            System.out.println("Обучение заклинанию " + spell.getName() + " завершено!");
            spell.cast(); // После обучения произносим заклинание
        });
        trainingThread.start(); // Запускаем поток обучения
    }
}