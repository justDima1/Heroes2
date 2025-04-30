package Tests;

import controller.AIController;
import controller.BattleController;
import model.City;
import model.Player;
import model.heroes.Hero;
import model.map.GameMap;
import model.units.Archer;
import model.units.Pikeman;
import model.units.Swordsman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.GameController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest  {
    private GameController gameController;
    private GameMap gameMap;
    private Hero hero;
    private City playerCity;
    private AIController aiController;
    private Player player1;
    private Player player2;
    private List<Player> players;
    private Hero initialHero;
    private Hero aiHero;
    private final int mapWidth = 10;
    private final int mapHeight = 10;
    private Scanner scannerMock;
    private GameController mapView;
    private City currentHero;
    @BeforeEach
    void setUp() {
        initialHero = new Hero(1, 1, "TestHero");
        aiHero = new Hero(8, 8, "AIHero");
        player1 = new Player("Player", initialHero, 1000);
        player2 = new Player("AI", aiHero, 1000);
        playerCity = new City("TestCity", 1, 1);
        gameMap = new GameMap(mapWidth, mapHeight);
        gameController = new GameController(true, mapWidth, mapHeight);
        gameController.setGameMap(gameMap);
        gameController.setHero(initialHero);
        gameController.setAiHero(aiHero);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameController.setPlayers(players);
        gameController.setPlayerCity(playerCity);
    }
    @Test
    public void testHeroMovesToCity() {
        System.out.println("Начальные координаты героя: (" + initialHero.getX() + ", " + initialHero.getY() + ")");
        System.out.println("Координаты города: (" + playerCity.getX() + ", " + playerCity.getY() + ")");
        gameController.moveHeroTo(playerCity.getX(), playerCity.getY());
        System.out.println("Координаты героя после перемещения: (" + initialHero.getX() + ", " + initialHero.getY() + ")");
        assertEquals(playerCity.getX(), initialHero.getX(), "X-координата не совпадает");
        assertEquals(playerCity.getY(), initialHero.getY(), "Y-координата не совпадает");
        System.out.println(" Герой находится на клетке замка!");
    }

    @Test
    void testMoveHero() {
        // Задаем начальные координаты герою
        initialHero.setX(2);
        initialHero.setY(2);
        System.out.println("Начальные координаты героя: (" + initialHero.getX() + ", " + initialHero.getY() + ")");
        // Перемещаем героя
        gameController.moveHeroTo(3, 3);
        System.out.println("Координаты героя после перемещения: (" + initialHero.getX() + ", " + initialHero.getY() + ")");
        // Проверяем, что координаты героя изменились
        assertEquals(3, initialHero.getX(), "X-координата не совпадает");
        assertEquals(3, initialHero.getY(), "Y-координата не совпадает");
    }
    @Test
    void testSimpleObstacle() {
        // 1. Подготовка
        GameMap simpleMap = new GameMap(3, 3);
        Hero simpleHero = new Hero(1, 1, "SimpleHero");
        int obstacleX = 2;
        int obstacleY = 1;
        simpleMap.getMap()[obstacleX][obstacleY] = "X";
        // 2. Действие
        simpleHero.setX(obstacleX);
        simpleHero.setY(obstacleY);
        // 3. Проверка
        assertEquals(obstacleX, simpleHero.getX());
        assertEquals(obstacleY, simpleHero.getY());
    }
    @Test
    void testAiWin() {
        // 1. Подготовка
        // Устанавливаем героев на одну клетку
        initialHero.setX(5);
        initialHero.setY(5);
        aiHero.setX(5);
        aiHero.setY(5);
        // Убираем всех юнитов у игрока
        initialHero.getArmy().getUnits().clear();
        // Добавляем юнитов ИИ
        for (int i = 0; i < 3; i++) {
            aiHero.getArmy().addUnit(new Pikeman());
        }
        // 2. Действие: Проводим битву
        gameController.testBattle(initialHero, aiHero);
        // 3. Проверка: ИИ должен победить (у игрока не должно быть героя)
        assertNull(player1.getCurrentHero(), "ИИ не победил");
    }
    @Test
    void testPlayerWin() {
        // 1. Подготовка
        // Устанавливаем героев на одну клетку
        initialHero.setX(5);
        initialHero.setY(5);
        aiHero.setX(5);
        aiHero.setY(5);
        // Убираем всех юнитов у ИИ
        aiHero.getArmy().getUnits().clear();
        // Добавляем юнитов игроку
        for (int i = 0; i < 3; i++) {
            initialHero.getArmy().addUnit(new Pikeman());
        }
        // 2. Действие: Проводим битву
        gameController.testBattle(initialHero, aiHero);
        // 3. Проверка: Игрок должен победить (у ИИ не должно быть героя)
        assertNull(player2.getCurrentHero(), "Игрок не победил");
    }
    @Test
    void testCollectGoldIncreasesGoldAmount() {
        // 1. Подготовка
        int initialGold = player1.getGold().getAmount();
        // 2. Действие: Собираем золото
        gameController.collectGold(player1);
        // 3. Проверка: Количество золота должно увеличиться
        assertEquals(initialGold + 500, player1.getGold().getAmount(), "Метод collectGold не увеличил количество золота");
    }
    @Test
    void testCollectGemsResetsTurnsSinceLastCollection() {
        // 1. Подготовка
        gameController.setTurnsSinceLastCollection(5); // Устанавливаем счетчик в ненулевое значение
        // 2. Действие: Собираем гемы
        gameController.collectGems(player1);
        // 3. Проверка: Счетчик должен сброситься
        assertEquals(0, gameController.getTurnsSinceLastCollection(), "Метод collectGems не сбросил счетчик ходов");
    }
}
/*
    assertEquals(expected, actual):
    Проверяет, что expected равно actual.
    Подходит для сравнения примитивных типов, объектов (с использованием метода equals()) и строк

    assertNotEquals(unexpected, actual):
    Проверяет, что unexpected не равно actual.

    assertTrue(condition):
    Проверяет, что condition истинно.

    assertFalse(condition):
    Проверяет, что condition ложно.

    assertNull(object):
    Проверяет, что object равно null.
    assertNotNull(object):
    Проверяет, что object не равно null

    assertSame(expected, actual):
    Проверяет, что expected и actual ссылаются на один и тот же объект в памяти.
    Использует оператор == для сравнения.

    assertNotSame(unexpected, actual):
    Проверяет, что unexpected и actual не ссылаются на один и тот же объект в памяти.

    assertArrayEquals(expectedArray, actualArray):
    Проверяет, что два массива равны (содержат одинаковые элементы в одинаковом порядке).

    assertThrows(expectedType, executable):
    Проверяет, что выполнение executable приводит к выбросу исключения типа expectedType.
    assertDoesNotThrow(executable):
    Проверяет, что выполнение executable не приводит к выбросу исключения.

    @After в JUnit 4). Эти методы используются для очистки тестовой среды и освобождения ресурсов.
 */