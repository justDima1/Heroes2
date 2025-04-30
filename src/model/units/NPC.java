package model.units;

import model.Player;
import model.buildings.Hotel;

import java.util.Random;

public class NPC implements Runnable {
    private String name;
    private Hotel hotel;

    public NPC(String name, Hotel hotel) {
        this.name = name;
        this.hotel = hotel;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(10000)); // NPC пытается заселиться каждые несколько секунд
                hotel.enter(new Player(name, null, 0)); // NPC пытается заселиться в отель
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}