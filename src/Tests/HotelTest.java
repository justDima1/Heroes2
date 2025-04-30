package Tests;

import model.buildings.Hotel;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelTest {

    @Test
    public void testHotelCapacityIsFive() throws Exception {
        Hotel hotel = new Hotel(2, 6);
        // Получаем доступ к приватному полю "capacity"
        Field capacityField = Hotel.class.getDeclaredField("capacity");
        capacityField.setAccessible(true); // Отключаем проверку доступа
        // Получаем значение поля
        int capacity = (int) capacityField.get(hotel);

        // Проверяем, что значение поля равно 5
        assertEquals(5, capacity);
    }
    @Test
    public void testGetAvailableRoomsReturnsCorrectValue() throws Exception {
        Hotel hotel = new Hotel(2, 6);
        // Получаем доступ к приватному полю "capacity"
        Field capacityField = Hotel.class.getDeclaredField("capacity");
        capacityField.setAccessible(true); // Отключаем проверку доступа
        // Получаем значение поля
        int capacity = (int) capacityField.get(hotel);

        assertEquals(capacity, hotel.getAvailableRooms()); // Доступные комнаты должны быть равны вместимости при создании
    }
}
/*otel.class.getDeclaredField("capacity"): Получаем объект Field, представляющий приватное поле capacity в классе Hotel.
capacityField.setAccessible(true): Отключаем проверку доступа к приватному полю. Это необходимо для того, чтобы можно было получить значение поля.
capacityField.get(hotel): Получаем значение поля из объекта hotel.
assertEquals(5, capacity): Проверяем, что значение поля равно 5.*/