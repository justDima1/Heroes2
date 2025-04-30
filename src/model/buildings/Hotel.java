package model.buildings;

import controller.HotelTimer;
import model.Player;
import model.units.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Hotel {
    private final int capacity = 5;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final List<Thread> rooms = new ArrayList<>(capacity); // Используем List<Thread> для отслеживания занятых номеров
    private int occupiedRooms = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Пул потоков для NPC и игроков
    private final int x;
    private final int y;
    private HotelTimer hotelTimer;
    private Process logger;
    private static final Logger LOGGER = Logger.getLogger(HotelTimer.class.getName());

    public Hotel(int x, int y) {
        this.x = 6;
        this.y = 2;
        this.hotelTimer = new HotelTimer(this); // Создаем таймер отеля
        // Инициализация списка номеров
        for (int i = 0; i < capacity; i++) {
            rooms.add(null); // Номер свободен
        }
    }

    public boolean enter(Player player) throws InterruptedException {
        // Проверяем, открыт ли отель
        if (!hotelTimer.isHotelOpen()) {
            System.out.println("Отель принимает посетителей только с 90 по 110 секунду.");
            player.loseHealthAllUnits(0.01); // Штраф за неудачное посещение
            System.out.println("Ваши юниты потеряли 1% здоровья за неудачную попытку.");
            return false;
        }

        lock.lock();
        try {
            // Пытаемся найти свободный номер
            for (int i = 0; i < capacity; i++) {
                if (isRoomAvailable(i)) {
                    // Нашли свободный номер
                    final int roomNumber = i;
                    occupiedRooms++;
                    System.out.println(player.getName() + " заселяется в отель в номер " + (roomNumber + 1) + ". Свободных номеров: " + (capacity - occupiedRooms));

                    // Выбираем услугу случайным образом (короткий или длинный отдых)
                    Random random = new Random();
                    int serviceType = random.nextInt(2); // 0 - короткий, 1 - длинный
                    int duration;
                    double healthBonus;

                    if (serviceType == 0) {
                        duration = 1;
                        healthBonus = 0.02; // 2%
                        System.out.println("Выбран короткий отдых (1 ход, +2% к здоровью).");
                    } else {
                        duration = 3;
                        healthBonus = 0.03; // 3%
                        System.out.println("Выбран длинный отдых (3 хода, +3% к здоровью).");
                    }

                    // Создаем и запускаем поток для имитации отдыха
                    Thread hotelThread = new Thread(() -> {
                        try {
                            Thread.sleep(duration * 1000); // Имитируем отдых
                            player.healUnits(healthBonus); // Начисляем бонус к здоровью
                            System.out.println(player.getName() + " отдохнул и восстановил здоровье юнитов на " + (healthBonus * 100) + "%.");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            lock.lock();
                            try {
                                occupiedRooms--;
                                rooms.set(roomNumber, null); // Освобождаем номер
                                System.out.println(player.getName() + " выселяется из отеля из номера " + (roomNumber + 1) + ". Свободных номеров: " + (capacity - occupiedRooms));
                                condition.signalAll(); // Оповещаем все ждущие потоки
                            } finally {
                                lock.unlock();
                            }
                        }
                    });
                    rooms.set(roomNumber, hotelThread); // Занимаем номер
                    executor.submit(hotelThread); // Запускаем поток
                    return true; // Успешное заселение
                }
            }

            // Если все номера заняты, предлагаем подождать
            System.out.println("В отеле нет свободных номеров.");
            player.loseHealthAllUnits(0.01); // Штраф за неудачное посещение
            System.out.println("Ваши юниты потеряли 1% здоровья за неудачную попытку.");
            return false;
        } finally {
            lock.unlock();
        }
    }
    public int getOccupancy() {
        lock.lock();
        try {
            return occupiedRooms;
        } finally {
            lock.unlock();
        }
    }
    public int getAvailableRooms() {
        lock.lock();
        try {
            return capacity - occupiedRooms;
        } finally {
            lock.unlock();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getCapacity() {
        return capacity;
    }
    private boolean isRoomAvailable(int roomNumber) {
        return rooms.get(roomNumber) == null;
    }
    public void оccupyRandomRoom() {
        lock.lock();
        try {
            Random random = new Random();
            int roomNumber = random.nextInt(capacity);
            if (rooms.get(roomNumber) == null) {
                rooms.set(roomNumber, new Thread(() -> {
                }));
                occupiedRooms++;
                System.out.println("Комната " + (roomNumber + 1) + " занята.");
            }
        } finally {
            lock.unlock();
        }
    }
    public void setOccupiedRooms(int occupiedRooms) {
        lock.lock();
        try {
            this.occupiedRooms = occupiedRooms;
            // Убедимся, что количество занятых номеров не превышает вместимость отеля
            if (this.occupiedRooms > capacity) {
                this.occupiedRooms = capacity;
            }
            // Обновим список номеров в соответствии с новым количеством занятых номеров
            for (int i = 0; i < capacity; i++) {
                if (i < this.occupiedRooms) {
                    // Занимаем номер (создаем фиктивный поток)
                    if (rooms.get(i) == null) {
                        rooms.set(i, new Thread());
                    }
                } else {
                    // Освобождаем номер
                    rooms.set(i, null);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}

