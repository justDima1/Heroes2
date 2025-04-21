package view;

import model.City;
import model.heroes.Hero;
import model.map.GameMap;
import model.map.TerrainType;
import java.util.List;
import model.buildings.Building;
import model.buildings.WitcherSchool;

public class MapView {
    private GameMap gameMap;

    public MapView(GameMap gameMap) {this.gameMap = gameMap;
    }

    public void displayMap(int heroX, int heroY, int aiHeroX, int aiHeroY, int mapWidth, int mapHeight, TerrainType[][] terrain, String[][] mapData, int castleX, int castleY, int enemyCastleX, int enemyCastleY, City city, List<Hero> heroes, Hero currentHero) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                boolean heroDisplayed = false;

                // Проверяем, есть ли герой в текущей клетке
                for (Hero hero : heroes) {
                    if (i == hero.getX() && j == hero.getY()) {
                        // Определяем, какой символ использовать для текущего героя
                        if (hero == currentHero) {
                            System.out.print("H "); // Текущий герой
                        } else {
                            System.out.print("h "); // Остальные герои
                        }
                        heroDisplayed = true;
                        break; // Прерываем цикл, так как герой уже отображен
                    }
                }

                if (!heroDisplayed) {
                    Building building = null;
                    if (gameMap != null) {
                        building = gameMap.getBuilding(j, i);
                    }
                    if (building != null) {
                        if (building instanceof WitcherSchool) {
                            System.out.print("W ");
                        } else {
                            System.out.print("B ");
                        }
                    } else {
                        if (i == aiHeroY && j == aiHeroX) {
                            System.out.print("A "); // Отображаем AI-героя
                        } else if (i == city.getY() && j == city.getX()) {
                            System.out.print("C "); // Отображаем город
                        } else if (i == castleY && j == castleX) {
                            System.out.print("C "); // Отображаем замок
                        } else if (i == enemyCastleY && j == enemyCastleX) {
                            System.out.print("E "); // Отображаем вражеский замок
                        } else if (mapData[i][j] != null && !mapData[i][j].equals(".")) {
                            System.out.print(mapData[i][j] + " "); // Отображаем ресурсы
                        } else {
                            // Отображаем TerrainType
                            if (terrain[i][j] == TerrainType.GRASS) {
                                System.out.print(". "); // Отображаем траву
                            } else {
                                System.out.print("~ "); // Отображаем воду
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}