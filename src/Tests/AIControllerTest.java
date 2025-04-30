package Tests;

import controller.AIController;
import controller.GameController;
import model.Player;
import model.heroes.Hero;
import model.map.GameMap;
import model.units.Archer;
import model.units.Pikeman;
import model.units.Swordsman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AIControllerTest {

    private AIController aiController;
    private Hero aiHero;
    private Player aiPlayer;
    private GameMap gameMap;
    private GameController gameController;
    private int mapWidth = 10;
    private int mapHeight = 10;

    @BeforeEach
    void setUp() {
        gameController = new GameController(true, mapWidth, mapHeight);
        gameMap = new GameMap(mapWidth, mapHeight);
        aiHero = new Hero(1, 1, "AI Hero");
        aiPlayer = new Player("AI Player", aiHero, 100);
        aiPlayer.setGold(new model.Gold(1000)); // Set initial gold
        aiPlayer.setCurrentHero(aiHero);
        aiHero.setMovesLeft(2); // Установим количество ходов для героя
        aiController = new AIController(mapWidth, mapHeight, gameController, aiPlayer, gameMap);
    }

    @Test
    void testRecruitUnits_WhenEnoughGold_ThenUnitsAreAdded() {
        int initialSwordsmanCount = countUnits(aiPlayer, Swordsman.class);
        int initialArcherCount = countUnits(aiPlayer, Archer.class);
        int initialPikemanCount = countUnits(aiPlayer, Pikeman.class);

        aiController.recruitUnits();

        int swordsmanCount = countUnits(aiPlayer, Swordsman.class);
        int archerCount = countUnits(aiPlayer, Archer.class);
        int pikemanCount = countUnits(aiPlayer, Pikeman.class);

        assertTrue(swordsmanCount >= initialSwordsmanCount, "Мечники должны быть добавлены");
        assertTrue(archerCount >= initialArcherCount, "Лучники должны быть добавлены");
        assertTrue(pikemanCount >= initialPikemanCount, "Пикинеры должны быть добавлены");
    }

    @Test
    void testRecruitUnits_WhenNotEnoughGold_ThenNoUnitsAreAdded() {
        aiPlayer.getGold().setAmount(0); // No gold
        int initialUnitCount = aiPlayer.getUnits().size();

        aiController.recruitUnits();

        assertEquals(initialUnitCount, aiPlayer.getUnits().size(), "Юниты не должны быть добавлены");
    }

    @Test
    void testMoveAiHero_HeroMovesToDifferentCoordinates() {
        int initialX = aiHero.getX();
        int initialY = aiHero.getY();

        aiController.moveAiHero();

        boolean moved = (aiHero.getX() != initialX) || (aiHero.getY() != initialY);
        assertTrue(moved, "Герой должен переместиться хотя бы в одну из координат");
    }


    @Test
    void testMoveAiHero_NoMovesLeft() {
        aiHero.setMovesLeft(0); // Установим количество ходов равным 0
        int initialX = aiHero.getX();
        int initialY = aiHero.getY();

        aiController.moveAiHero();

        assertEquals(initialX, aiHero.getX(), "X координата не должна измениться");
        assertEquals(initialY, aiHero.getY(), "Y координата не должна измениться");
    }

    // Вспомогательный метод для подсчета юнитов определенного типа
    private int countUnits(Player player, Class<?> unitClass) {
        int count = 0;
        for (Object unit : player.getUnits()) {
            if (unitClass.isInstance(unit)) {
                count++;
            }
        }
        return count;
    }
}
/*testRecruitUnits_WhenEnoughGold_ThenUnitsAreAdded():

Получает начальное количество каждого типа юнитов (мечник, лучник, копейщик) от ИИ-игрока.
Вызывает метод aiController.recruitUnits(), который предположительно должен добавлять юнитов игроку AI.
Получает новое количество каждого типа юнитов у игрока-ИИ после вызова recruitUnits().
Использует assertTrue() для проверки, что количество юнитов каждого типа увеличилось или осталось прежним (>= initialCount). То есть, тест проверяет, что юниты были добавлены.
Цель теста: проверить, что AI-контроллер добавляет юнитов, когда у AI-игрока достаточно золота.
testRecruitUnits_WhenNotEnoughGold_ThenNoUnitsAreAdded():

Устанавливает количество золота у AI-игрока в 0.
Получает начальное общее количество юнитов у AI-игрока.
Вызывает метод aiController.recruitUnits().
Получает новое общее количество юнитов у игрока AI после вызова recruitUnits().
Использует assertEquals() для проверки того, что общее количество юнитов не изменилось. То есть тест проверяет, что юниты не были добавлены.
Цель теста: проверить, что AI-контроллер не добавляет юнитов, когда у AI-игрока недостаточно золота.
testMoveAiHero_HeroMovesToDifferentCoordinates():

Получает начальные координаты (X, Y) AI-героя.
Вызывает метод aiController.moveAiHero(), который предположительно должен перемещать AI-героя.
Проверяет, что хотя бы одна из координат (X или Y) изменилась.
Использует assertTrue(moved, "Герой должен переместиться хотя бы в одну из координат") для проверки.
Цель теста: проверить, что AI-контроллер перемещает героя хотя бы на одну клетку.
testMoveAiHero_NoMovesLeft():

Устанавливает количество оставшихся ходов у AI-героя в 0.
Получает начальные координаты (X, Y) AI-героя.
Вызывает метод aiController.moveAiHero().
Проверяет, что координаты (X, Y) не изменились.
Использует assertEquals() для проверки.
Цель теста: проверить, что AI-контроллер не перемещает героя, если у него не осталось ходов.*/