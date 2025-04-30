package controller;

import model.buildings.Hotel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.logging.Level;

public class HotelTimer {
    private boolean isOpen = false; // Флаг, показывающий, открыт ли отель
    private Timer timer;
    private Hotel hotel;
    private static final Logger LOGGER = Logger.getLogger(HotelTimer.class.getName());

    public HotelTimer(Hotel hotel) {
        this.hotel = hotel;
        startTimer();
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = 0; // Счетчик секунд

            @Override
            public void run() {
                seconds++;
                if (seconds >= 90 && seconds <= 110) { // Отель открыт с 90 по 110 секунду
                    if (!isOpen) {
                        LOGGER.info("Отель открыт!");
                        System.out.println("Отель открыт!");
                        isOpen = true;
                    }
                } else {
                    if (isOpen) {
                        System.out.println("Отель закрыт!");
                        isOpen = false;
                    }
                }

                if (seconds >= 120) { // Цикл повторяется каждые 120 секунд
                    seconds = 0;
                }
            }
        }, 0, 5000); // Запускаем задачу каждые 5 секунд (5000 миллисекунд)
    }

    public boolean isHotelOpen() {
        return isOpen;
    }

    public void stopTimer() {
        timer.cancel();
    }
}