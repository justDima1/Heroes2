package Tests;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static main.SaveManager.saveGame;
import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadTest {

    @Test
    void testSaveGameCreatesFile() {
        String filename = "test_save.sav";
        saveGame(filename); // Замените на ваш метод сохранения игры
        File file = new File("saves/"+filename);
        file.delete(); // Очистка после теста
    }

    private void saveGame(String filename) {
    }

    @Test
    void testLoadGameFileExists() {
        String filename = "existing_save.sav";
        // Создаем фиктивный файл сохранения
        File file = new File("saves/"+filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            fail("Не удалось создать тестовый файл");
        }
        //assertTrue(loadGameFromFile(filename), "Загрузка должна быть успешной, если файл существует"); // Замените на ваш метод загрузки
        file.delete(); // Очистка после теста
    }

    @Test
    void testLoadGameFileNotExists() {
        String filename = "non_existing_save.sav";
        File file = new File("saves/"+filename);
        assertFalse(file.exists());
        // assertFalse(loadGameFromFile(filename), "Загрузка должна завершиться неудачей, если файл не существует"); // Замените на ваш метод загрузки
    }
}
/*testSaveGameCreatesFile():

Задает имя файла "test_save.sav".
Вызывает метод saveGame(filename) (который в данном тесте пустой — это важно!). Предполагается, что этот метод должен сохранять игру в файл с указанным именем.
Создает объект File для файла сохранения (в подкаталоге “saves/”).
Проверяет, что файл был создан. НО! В тесте нет никакой проверки. Метод saveGame(filename) пустой, поэтому никакой файл не создаётся!
Удаляет файл после теста (очистка).
Цель теста (в теории): проверить, что метод сохранения игры создает файл на диске.
Реальная проблема: тест ничего не проверяет, так как метод saveGame пустой.
testLoadGameFileExists():

Задает имя файла "existing_save.sav".
Создает фиктивный файл сохранения (в поддиректории «saves/»).
Вызывает метод loadGameFromFile(filename) (который закомментирован!). Предполагается, что этот метод должен загружать игру из файла.
Проверяет, что загрузка прошла успешно. НО! Проверка закомментирована.
Удаляет файл после теста (очистка).
Цель теста (в теории): проверить, что метод загрузки игры успешно загружает данные, если файл существует.
Реальная проблема: тест создает файл, но не вызывает метод загрузки и ничего не проверяет. Он просто создает и удаляет файл.
testLoadGameFileNotExists():

Задает имя файла "non_existing_save.sav".
Создает объект File для файла сохранения (в подкаталоге “saves/”).
Проверяет, что файл не существует, используя assertFalse(file.exists()). Это единственная проверка, которая действительно выполняется в этом классе!
Вызывает метод loadGameFromFile(filename) (который закомментирован!).
Проверяет, что загрузка завершилась неудачно. НО! Проверка закомментирована.
Цель теста (в теории): проверить, что метод загрузки игры завершается ошибкой, если файл не существует.
Реальная проблема: тест только проверяет, что файл не существует. Он не вызывает метод загрузки и не проверяет его поведение.*/