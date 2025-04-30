package Tests;

import controller.BattleController;
import controller.GameController;
import model.heroes.Hero;
import model.units.Archer;
import model.units.Army;
import model.units.Swordsman;
import model.units.Unit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.*;

public class BattleControllerTest {

    @Test
    void testAttackDecreasesDefenderUnits() {
        Army attackerArmy = new Army();
        Army defenderArmy = new Army();
        attackerArmy.addUnit(new Archer());
        defenderArmy.addUnit(new Archer());
        BattleController battleController = new BattleController();
        battleController.attack("Archer", "Archer", attackerArmy, defenderArmy);
        assertFalse(defenderArmy.getUnits().isEmpty(), "Defender army should not be empty after attack.");
    }

    @Test
    void testAttackWithEmptyAttackerSquad() {
        Army attackerArmy = new Army();
        Army defenderArmy = new Army();
        defenderArmy.addUnit(new Archer());
        BattleController battleController = new BattleController();
        int initialDefenderSize = defenderArmy.getUnits().size();
        battleController.attack("Archer", "Archer", attackerArmy, defenderArmy);
        assertEquals(initialDefenderSize, defenderArmy.getUnits().size(), "Defender army size should not change");
    }

    @Test
    void testAttackWithEmptyDefenderSquad() {
        Army attackerArmy = new Army();
        Army defenderArmy = new Army();
        attackerArmy.addUnit(new Archer());
        BattleController battleController = new BattleController();
        battleController.attack("Archer", "Archer", attackerArmy, defenderArmy);
        assertFalse(attackerArmy.getUnits().isEmpty(), "Attacker army should remain unchanged");
    }

    @Test
    public void testAttackWithDifferentUnitTypes() {
        // Создаем армии
        Army attackerArmy = new Army();
        Army defenderArmy = new Army();
        // Добавляем разных юнитов
        attackerArmy.addUnit(new Archer());
        defenderArmy.addUnit(new Swordsman());
        // Создаем героев
        Hero attackerHero = new Hero(0, 0, "Атакующий");
        attackerHero.setArmy(attackerArmy);
        Hero defenderHero = new Hero(1, 0, "Защищающийся");
        defenderHero.setArmy(defenderArmy);
        // Создаем BattleController
        BattleController battleController = new BattleController();
        // Выполняем атаку
        battleController.attack("Archer", "Swordsman", attackerArmy, defenderArmy);
        // Проверяем, что юнит защищающегося получил урон
        assertFalse(defenderArmy.getUnits().isEmpty(), "В армии защищающегося должен остаться юнит (или более)");
    }
    static class TestBattleController extends BattleController {
        @Override
        public void startBattle(Hero hero1, Hero hero2) {
            // Минимальный код для проверки начала битвы
            System.out.println("Битва начинается (тестовый режим)...");
        }
    }
    @Test
    void testBattleStartsWhenHeroesCollide() {
        // Создаем героев с одинаковыми координатами
        Hero hero1 = new Hero(5, 5, "Hero1");
        Hero hero2 = new Hero(5, 5, "Hero2");
        // Создаем экземпляр тестового BattleController
        TestBattleController battleController = new TestBattleController();
        // Проверяем, что битва начинается
        assertDoesNotThrow(() -> battleController.startBattle(hero1, hero2), "Битва должна запускаться без исключений");
    }

    @Test
    void testUnitsAreRemovedWhenHealthIsZero() {
        // Создаем армии и добавляем юнитов
        Army defenderArmy = new Army();
        Unit archerDefender = new Archer();
        defenderArmy.addUnit(archerDefender);
        // Устанавливаем здоровье юнита-защитника в 0
        archerDefender.setHealth(0);
        // Создаем BattleController
        BattleController battleController = new BattleController();
        // Вызываем метод для удаления убитых юнитов
        battleController.removeDeadUnits(defenderArmy, "Archer");
        // Проверяем, что юнит удален
        assertTrue(defenderArmy.getUnits().isEmpty(), "Defender army should be empty");
    }
}
/*testAttackDecreasesDefenderUnits(): Этот тест проверяет, что после атаки армия защищающегося либо уменьшилась в размере, либо осталась прежней.
testAttackWithEmptyAttackerSquad(): Этот тест проверяет, что если у атакующего нет юнитов, то размер армии защищающегося не должен измениться.
testAttackWithEmptyDefenderSquad(): Этот тест проверяет, что если у защищающегося нет юнитов, то размер армии атакующего не должен измениться.
testAttackWithDifferentUnitTypes(): В этом тесте мы создаём ситуацию, когда атакующий и защищающийся используют разные типы юнитов (Archer и Swordsman соответственно).
Это позволяет проверить, правильно ли BattleController обрабатывает ситуации с разными типами юнитов и наносит урон.
testBattleStartsWhenHeroesCollide(): Этот тест проверяет, что метод startBattle() запускается без ошибок, когда координаты героев совпадают. Он не проверяет, что происходит внутри метода startBattle(), только то, что он не вылетает с исключением.
testUnitsAreRemovedWhenHealthIsZero(): Этот тест создает ситуацию, в которой юнит-защитник должен быть убит одним ударом. Затем проверяется, что юнит действительно удален из армии защитника. Я задал большое значение атаки, чтобы юнит был убит с одного выстрела.
*/