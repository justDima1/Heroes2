package model.map;

import controller.GameController;
import model.buildings.Building;
import model.buildings.WitcherSchool;
import model.heroes.Hero;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMap {
    private TerrainType[][] terrain;
    private String[][] map;
    private int width;
    private int height;
    private int castleX;
    private int castleY;
    private int enemyCastleX;
    private int enemyCastleY;
    private Random random = new Random();
    private Building[][] buildings;
    private GameMap gameMap;
    private WitcherSchool witcherSchool;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.terrain = new TerrainType[width][height];
        this.map = new String[width][height];
        this.buildings = new Building[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                terrain[i][j] = random.nextDouble() < 0.6? TerrainType.GRASS : TerrainType.SWAMP;
                map[i][j] = ".";
            }
        }
        castleX = 0;
        castleY = 0;
        enemyCastleX = width - 1;
        enemyCastleY = height - 1;

        placeResources();
        placeObstacles();
        placeFixedMine();
        placeWitcherSchool();//<---Тут
        createDiagonalPath();
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(width).append(";").append(height).append("\n");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sb.append(terrain[i][j]).append(";");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public void loadMapDataFromString(String mapData) {
        String[] lines = mapData.split("\n");
        String[] dimensions = lines[0].split(";");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);
        this.terrain = new TerrainType[width][height];

        int lineIndex = 1;
        for (int i = 0; i < width; i++) {
            String[] cellValues = lines[lineIndex++].split(";");
            for (int j = 0; j < height; j++) {
                int ordinal = Integer.parseInt(cellValues[j]);
                terrain[i][j] = TerrainType.values()[ordinal];
            }
        }
    }
    public int collectResources(int x, int y) {
        if (map[x][y].equals("G")) {
            map[x][y] = "."; // Убираем золото с карты
            return 500;
        } else if (map[x][y].equals("Z")) {
            return 5;
        }
        return 0;
    }

    private void createDiagonalPath() {
        for (int i = 0; i < Math.min(width, height); i++) {
            map[i][i] = "+";
        }
    }
    private void placeResources() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j].equals(".")) {
                    double rand = random.nextDouble();
                    if (rand < 0.1) {
                        map[i][j] = "G"; // Золото
                    }
                }
            }
        }
    }

    private void placeObstacles() {
        int trees = 3;
        int stones = 2;
        while (trees > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (map[x][y].equals(".")) {
                map[x][y] = "T";
                trees--;
            }
        }
        while (stones > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (map[x][y].equals(".")) {
                map[x][y] = "X";
                stones--;
            }
        }
    }

    private void placeFixedMine() {
        int mineX = 2;
        int mineY = width - 3;
        if (mineX >= 0 && mineX < height && mineY >= 0 && mineY < width) {
            map[mineX][mineY] = "Z"; // Шахта
        }
    } public void placeWitcherSchool() {
        int x = 5;
        int y = 5;
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if(witcherSchool == null){
                witcherSchool = new WitcherSchool(x, y);
            }

            buildings[x][y] = witcherSchool;
            map[x][y] = "W";
            System.out.println("Школа Ведьмаков размещена на координатах (" + x + ", " + y + ")");
        } else {
            System.out.println("Не удалось разместить Школу Ведьмаков");
        }
    }

    public String checkResources(int x, int y) {
        if (map[x][y].equals("G")) {
            return "G";
        } else if (map[x][y].equals("Z")) {
            return "Z";
        }
        return null;
    }
    public TerrainType[][] getTerrain() {
        return terrain;
    }
    public String[][] getMap() {
        return map;
    }

    public int getCastleX() {
        return castleX;
    }

    public int getCastleY() {
        return castleY;
    }

    public int getEnemyCastleX() {
        return enemyCastleX;
    }

    public int getEnemyCastleY() {
        return enemyCastleY;
    }

    public void setMap(String[][] map) {
        this.map = map;
    }

    public void initializeMap() {
    }
    public String convertMapToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(width).append(",").append(height).append("\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(terrain[i][j]).append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void convertStringToMap(String data) {
        String[] lines = data.split("\n");
        String[] dimensions = lines[0].split(",");
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
        terrain = new TerrainType[height][width];

        for (int i = 0; i < height; i++) {
            String[] terrainValues = lines[i + 1].split(",");
            for (int j = 0; j < width; j++) {
                terrain[i][j] = TerrainType.valueOf(terrainValues[j]);
            }
        }
    }

    public void setTerrain(TerrainType[][] terrain) {
    }

    public Building getBuilding(int x, int y) {
        return buildings[x][y];
    }

    public void setBuilding(Building building, int x, int y) {
        buildings[x][y] = building;
    }
}