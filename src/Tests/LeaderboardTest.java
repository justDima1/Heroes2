package Tests;
import model.Leaderboard;
import model.Score;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaderboardTest {

    @Test
    void testAddScore() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore("TestUser", 100);
        List<Score> scores = leaderboard.getScores();
    }

    @Test
    void testGetScoresSorted() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore("User1", 50);
        leaderboard.addScore("User2", 100);
        List<Score> scores = leaderboard.getScores();
    }

    @Test
    void testMergeDuplicateScores() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore("TestUser", 50);
        leaderboard.addScore("TestUser", 100);
        leaderboard.mergeDuplicateScores();
        List<Score> scores = leaderboard.getScores();
    }

    @Test
    void testSaveAndLoadScores() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore("User1", 50);
        leaderboard.saveScores();
        Leaderboard newLeaderboard = new Leaderboard();
        newLeaderboard.loadScores();
        List<Score> scores = newLeaderboard.getScores();

    }
}
/*testAddScore():

Создает объект Leaderboard.
Добавляет новый результат (оценку) для пользователя «TestUser» с 100 баллами.
Получает список всех результатов из Leaderboard.
Примечание: В тесте нет никаких проверок (assert), поэтому он просто выполняет добавление и получение, но не подтверждает, что это произошло успешно.
testGetScoresSorted():

Создает объект Leaderboard.
Добавляет два результата для разных пользователей («User1» с 50 очками и «User2» с 100 очками).
Получает список всех результатов из Leaderboard.
Примечание: Аналогично, нет никаких проверок на сортировку. Тест просто добавляет и получает результаты.
testMergeDuplicateScores():

Создает объект Leaderboard.
Добавляет два результата для одного и того же пользователя («TestUser» с 50 и 100 баллами).
Вызывает метод mergeDuplicateScores(), который предположительно объединяет результаты для одинаковых пользователей (например, суммирует баллы).
Получает список всех результатов из Leaderboard.
Примечание: Тест не проверяет, что слияние произошло правильно.
testSaveAndLoadScores():

Создает объект Leaderboard.
Добавляет результат для пользователя “User1” с 50 очками.
Вызывает метод saveScores(), который, предположительно, сохраняет результаты в файл.
Создает новый объект Leaderboard.
Вызывает метод loadScores(), который, предположительно, загружает результаты из файла.
Получает список всех результатов из Leaderboard.
Примечание: тест не проверяет, что сохранение и загрузка прошли успешно и что данные были восстановлены правильно.*/