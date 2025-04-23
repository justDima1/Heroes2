package controller;

import main.HeroData;
import main.SaveData;
import main.SaveManager;
import model.*;
import model.buildings.*;
import model.heroes.Hero;
import model.map.GameMap;
import model.map.MapEditor;
import model.map.TerrainType;
import model.units.*;
import model.units.Stack;
import org.junit.jupiter.api.Test;
import view.CityView;
import view.MapView;
import model.units.Archer;
import model.units.Pikeman;
import model.units.Unit;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import view.WitcherSchoolInterface;
import model.buildings.WitcherSchool;

public class GameController {
    private City playerCity;
    protected Hero hero;
    private Hero aiHero;
    private MapView mapView;
    private int mapWidth;
    private int mapHeight;
    private Scanner scanner;
    private GameMap gameMap;
    private int currentPlayer;
    private Random random = new Random();
    private int movesPerTurn = 5;
    private int movesLeft = movesPerTurn;
    private BattleController battleController = new BattleController();
    private int turnsSinceLastCollection = 5;
    private CityView cityView;
    private Tavern tavern;
    private List<Player> players;
    private List<Hero> heroes;
    private boolean playerTurnStarted = false;
    private Player player;
    private Hero currentHero;
    public AIController aiController;
    private int turnsCount = 0;
    private Hero aihero;
    private int currentTurn = 0;
    private Player aiPlayer;
    private int turnsSinceLastRecruit = 0;
    private Leaderboard leaderboard = new Leaderboard();
    private String currentPlayerName = "Игрок";
    private Hero initialHero;
    private WitcherSchoolInterface witcherSchoolInterface;

    public GameController() {
        scanner = new Scanner(System.in);
        mapWidth = 10;
        mapHeight = 10;
        hero = new Hero(1, 1, "Саня");
        aiHero = new Hero(8, 8, "Саня");
        battleController = new BattleController();
        cityView = new CityView(scanner);
        System.out.println("Введите имя игрока:");
        currentPlayerName = scanner.nextLine();
        for (int i = 0; i < 15; i++) {
            hero.getArmy().addUnit(new Pikeman());
        }
        for (int i = 0; i < 10; i++) {
            hero.getArmy().addUnit(new Archer());
        }
        for (int i = 0; i < 7; i++) {
            hero.getArmy().addUnit(new Swordsman());
        }
        for (int i = 0; i < 10; i++) {
            aiHero.getArmy().addUnit(new Pikeman());
        }
        for (int i = 0; i < 5; i++) {
            aiHero.getArmy().addUnit(new Archer());
        }
        for (int i = 0; i < 3; i++) {
            aiHero.getArmy().addUnit(new Swordsman());
        }
        gameMap = new GameMap(mapWidth, mapHeight);
        mapView = new MapView(gameMap);
        witcherSchoolInterface = new WitcherSchoolInterface();
        //gameMap.placeWitcherSchool();
        currentPlayer = 1;
        playerCity = new City("Орешек", 0, 0);
    }
    public GameController(boolean isTest, int mapWidth, int mapHeight) {
        scanner = new Scanner(System.in);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        hero = new Hero(1, 1, "Саня");
        aiHero = new Hero(8, 8, "Саня");
        battleController = new BattleController();
        cityView = new CityView(scanner);
        currentPlayerName = "TestPlayer";
        mapView = new MapView(gameMap);
        gameMap = new GameMap(mapWidth, mapHeight);
        currentPlayer = 1;
        playerCity = new City("Орешек", 0, 0);
    }
    public GameController(String testPlayer) {
    }

    private void showSaveGameDialog() {
        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog(null, "Введите имя игрока:");
            if (playerName != null && !playerName.isEmpty()) {
                String saveName = JOptionPane.showInputDialog(null, "Введите имя сохранения:");
                if (saveName != null && !saveName.isEmpty()) {
                    saveGame(playerName, saveName);
                    JOptionPane.showMessageDialog(null, "Игра сохранена успешно!");
                } else {
                    JOptionPane.showMessageDialog(null, "Имя сохранения не введено!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Имя игрока не введено!");
            }
        });
    }

    public void handleWin() {
        System.out.println("Поздравляем! Вы победили!");
        leaderboard.addScore(currentPlayerName, 50); // 50 очков за победу
        System.out.println("Таблица лидеров:");
        displayLeaderboard();
        startGame(); // Возвращаемся в стартовое меню
    }

    public void addBuildingScore() {
        leaderboard.addScore(currentPlayerName, 4);
        leaderboard.saveScores();
    }
    public void addMineScore(){
        leaderboard.addScore(currentPlayerName,2);
        leaderboard.saveScores();
    }
    public void addGoldScore(){
        leaderboard.addScore(currentPlayerName,1);
        leaderboard.saveScores();
    }

    private void displayLeaderboard() {
        leaderboard.mergeDuplicateScores();
        List<Score> scores = leaderboard.getScores();
        if (scores.isEmpty()) {
            System.out.println("Таблица лидеров пуста.");
        } else {
            System.out.println("-----------------------");
            System.out.println("   Таблица лидеров    ");
            System.out.println("-----------------------");
            for (int i = 0; i < scores.size(); i++) {
                Score score = scores.get(i);
                System.out.println((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore());
            }
            System.out.println("-----------------------");
        }
        leaderboard.saveScores();
    }
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите действие:");
        System.out.println("1 - Новая игра");
        System.out.println("2 - Редактор карт");
        System.out.println("3 - Загрузить карту");
        System.out.println("4 - Таблица лидеров");
        int choice = scanner.nextInt();
        scanner.nextLine();

        // Объявляем переменные перед switch
        Hero initialHero = new Hero(1, 1, "Саня");
        Hero aiHero = new Hero(8, 8, "СаняИИ");
        Player player1 = new Player("Player", initialHero, 1000);
        Player player2 = new Player("AI", aiHero, 1000);

        switch (choice) {
            case 1:

                mapWidth = 10;
                mapHeight = 10;
                gameMap = new GameMap(mapWidth, mapHeight);
                players = new ArrayList<>();
                player1.setCurrentHero(initialHero);
                players.add(player1);
                player2.setCity(new City("ИИ Сити", 8, 8));
                player2.setCurrentHero(aiHero);
                players.add(player2);

                players.get(0).setCurrentHero(initialHero);
                players.get(1).setCurrentHero(aiHero);

                aihero = aiHero; // Инициализируем aihero!
                aiController = new AIController(mapWidth, mapHeight, this, players.get(1), gameMap);
                break;
            case 2:
                MapEditor mapEditor = new MapEditor(10, 10);
                mapEditor.startEditor();
                startGame();
                return;
            case 3:
                System.out.println("Введите имя файла карты для загрузки:");
                String filename = scanner.nextLine();
                loadMapFromFile(filename);
                mapWidth = 10;
                mapHeight = 10;
                players = new ArrayList<>();

                // Создаем первого героя и даем ему имя "Саня"

                player1.setCurrentHero(initialHero);
                players.add(player1);

                // Создаем второго героя (AI) и даем ему имя "СаняИИ"

                player2.setCity(new City("ИИ Сити", 8, 8));
                player2.setCurrentHero(aiHero);
                players.add(player2);

                players.get(0).setCurrentHero(initialHero);
                players.get(1).setCurrentHero(aiHero);

                aihero = aiHero; // Инициализируем aihero!
                aiController = new AIController(mapWidth, mapHeight, this, players.get(1), gameMap);
                break;
            case 4:
                displayLeaderboard();
                startGame();
            default:
                System.out.println("Некорректный выбор.");
                return;
        }
        // Ищем город на карте и устанавливаем его координаты
        String[][] map = gameMap.getMap();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (map[i][j].equals("C") || map[i][j].equals("E")) {
                    playerCity = new City("Захваченный город",i ,j);
                    break;
                }
            }
        }
        aiController = new AIController(mapWidth, mapHeight, this, players.get(1), gameMap);

        if (playerCity == null) {
            playerCity = new City("Тверь",1,1);
            TownHall townHall = new TownHall();
            playerCity.addBuilding(townHall);

        }
        //  this.townHall = new TownHall();
        Gold gold = new Gold(1000);
        Gems gems = new Gems(10);
        player1.setGold(gold);
        player1.setGems(gems);

        // cityView = new CityView(playerCity);

        startPlayerTurn();
        runGameLoop();
    }
    private void loadMapFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new java.io.File(filename + ".txt"))) {
            mapWidth = 10;
            mapHeight = 10;
            gameMap = new GameMap(mapWidth, mapHeight);
            TerrainType[][] terrain = new TerrainType[mapHeight][mapWidth]; // Изменили тип
            String[][] map = new String[mapWidth][mapHeight];

            System.out.println("Начинаем загрузку карты из файла " + filename + ".txt");

            for (int i = 0; i < mapHeight; i++) {
                if (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    System.out.println("Прочитана строка " + i + ": " + line);
                    for (int j = 0; j < mapWidth; j++) {
                        if (j < line.length()) {
                            char c = line.charAt(j);
                            terrain[i][j] = getTerrainType(c);
                            map[i][j] = String.valueOf(c);
                        } else {
                            terrain[i][j] = TerrainType.GRASS;
                            map[i][j] = ".";
                        }
                    }
                } else {
                    for (int j = 0; j < mapWidth; j++) {
                        terrain[i][j] = TerrainType.GRASS;
                        map[i][j] = ".";
                    }
                }
            }
            gameMap.setTerrain(terrain);
            gameMap.setMap(map);
            System.out.println("Карта загружена из файла " + filename + ".txt");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
    }
    public TerrainType getTerrainType(char symbol) {
        switch (symbol) {
            case '.':
                return TerrainType.GRASS;
            case 'T':
                return TerrainType.TREE;
            case 'G':
                return TerrainType.GOLD;
            case 'Z':
                return TerrainType.GEMS;
            case 'H':
                return TerrainType.HERO;
            case 'C':
                return TerrainType.CITY;
            case '+':
                return TerrainType.ROAD;
            case 'X':
                return TerrainType.STONE;
            case 'A':
                return TerrainType.HERO;
            case 'E':
                return TerrainType.CITY;
            default:
                return TerrainType.GRASS; // По умолчанию - трава
        }
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        Player player1 = new Player("Player1", null, 0);
        Player player2 = new Player("AI", null, 0);
        players.add(player1);
        players.add(player2);
        System.out.println("Игроки инициализированы");

    }
    public void saveGame(String playerName, String saveName) {
        if (gameMap == null) {
            System.out.println("Ошибка: gameMap не инициализирован!");
            return;
        }
        String mapData = gameMap.convertMapToString();
        HeroData hero1Data = new HeroData(players.get(0).getCurrentHero().getX(), players.get(0).getCurrentHero().getY(), players.get(0).getCurrentHero().getHealth());
        HeroData hero2Data = new HeroData(players.get(1).getCurrentHero().getX(), players.get(1).getCurrentHero().getY(), players.get(1).getCurrentHero().getHealth());
        int player1Gold = players.get(0).getGold().getAmount();
        int player1Gems = players.get(0).getGems().getAmount();
        int player2Gold = players.get(1).getGold().getAmount();
        int player2Gems = players.get(1).getGems().getAmount();
        System.out.println("Сохранение: player1Gold = " + player1Gold + ", player1Gems = " + player1Gems);
        System.out.println("Сохранение: player2Gold = " + player2Gold + ", player2Gems = " + player2Gems);
        List<UnitData> player1UnitsData = getUnitsData(players.get(0));
        List<UnitData> player2UnitsData = getUnitsData(players.get(1));
        System.out.println("Сохранено " + player1UnitsData.size() + " типов юнитов для игрока 1");
        Hero hero = players.get(0).getCurrentHero();
        for (UnitData unitData : player1UnitsData) {
            System.out.println("  Сохранено: " + unitData.getUnitType() + " - " + unitData.getCount());
        }

        // Сохраняем данные о постройках
        int townHallLevel = 1; // Значение по умолчанию
        boolean crossbowTowerBuilt = false;
        boolean ArenaBuilt = false;
        boolean ArmoryBuilt = false;
        boolean CathedralBuilt = false;
        boolean GatesBuilt = false;
        boolean GuardPostBuilt = false;

        // Проверяем, существует ли город и здания
        if (playerCity != null && playerCity.getBuildings() != null) {
            // Ищем ратушу и получаем ее уровень
            for (Building building : playerCity.getBuildings()) {
                if (building instanceof TownHall) {
                    townHallLevel = ((TownHall) building).getLevel();
                    break;
                }
            }
            // Проверяем наличие остальных зданий
            for (Building building : playerCity.getBuildings()) {
                if (building instanceof CrossbowTower) {
                    crossbowTowerBuilt = true;
                } else if (building instanceof Arena) {
                    ArenaBuilt = true;
                } else if (building instanceof Armory) {
                    ArmoryBuilt = true;
                } else if (building instanceof Cathedral) {
                    CathedralBuilt = true;
                } else if (building instanceof Gates) {
                    GatesBuilt = true;
                } else if (building instanceof GuardPost) {
                    GuardPostBuilt = true;
                }
            }
        }

        SaveData saveData = new SaveData(mapData, hero1Data, hero2Data, currentTurn, player1Gold, player2Gold, "map1", mapWidth, mapHeight, player1UnitsData, player2UnitsData, player1Gems, player2Gems, townHallLevel, crossbowTowerBuilt, ArenaBuilt, ArmoryBuilt, CathedralBuilt, GatesBuilt, GuardPostBuilt);
        SaveManager.saveGame(saveData, playerName, saveName);
    }
    private List<UnitData> getUnitsData(Player player) {
        List<UnitData> unitsData = new ArrayList<>();
        Hero hero = player.getCurrentHero();
        if (hero == null) {
            return unitsData;
        }
        List<Unit> units = hero.getArmy().getUnits();

        if (units == null) {
            return unitsData;
        }
        if (units.isEmpty()) {
            return unitsData;
        }

        Map<String, Integer> unitCounts = new HashMap<>();
        if (units != null && !units.isEmpty()) {
            for (Unit unit : units) {
                String unitType = unit.getClass().getSimpleName();
                unitCounts.put(unitType, unitCounts.getOrDefault(unitType, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : unitCounts.entrySet()) {
                String unitType = entry.getKey();
                int count = entry.getValue();
                unitsData.add(new UnitData(unitType, count));
            }
        }
        return unitsData;
    }
    public void loadGame(String playerName, String saveName) {
        SaveData loadedData = SaveManager.loadGame(playerName, saveName);
        if (loadedData != null) {
            mapWidth = loadedData.getMapWidth();
            mapHeight = loadedData.getMapHeight();

            gameMap = new GameMap(mapWidth, mapHeight);
            gameMap.convertStringToMap(loadedData.getMapData());

            if (players != null && players.size() >= 2) {

                Hero hero1 = new Hero(0, 0, "Hero1"); // Создаем героя для первого игрока
                Hero hero2 = new Hero(5, 5, "Hero2"); // Создаем героя для второго игрока

                players.get(0).addHero(hero1);  // Добавляем героя первому игроку
                players.get(1).addHero(hero2);  // Добавляем героя второму игроку
                hero1 = players.get(0).getCurrentHero();
                hero2 = players.get(1).getCurrentHero();
                if (hero1 != null) {
                    hero1.setX(loadedData.getHero1Data().getX());
                    hero1.setY(loadedData.getHero1Data().getY());
                    hero1.setHealth(loadedData.getHero1Data().getHealth());
                    if (hero1.getArmy() != null) {
                        hero1.getArmy().getUnits().clear();
                    }
                }
                if (hero2 != null) {
                    hero2.setX(loadedData.getHero2Data().getX());
                    hero2.setY(loadedData.getHero2Data().getY());
                    hero2.setHealth(loadedData.getHero2Data().getHealth());
                    if (hero2.getArmy() != null) {
                        hero2.getArmy().getUnits().clear();
                    }
                }
                // Восстанавливаем ресурсы ИГРОКА (правильно!)
                int player1Gold = loadedData.getPlayer1Gold();
                int player2Gold = loadedData.getPlayer2Gold();
                int player1Gems = loadedData.getPlayer1Gems();
                int player2Gems = loadedData.getPlayer2Gems();

                players.get(0).getGold().setAmount(player1Gold);
                players.get(1).getGold().setAmount(player2Gold);
                players.get(0).getGems().setAmount(player1Gems);
                players.get(1).getGems().setAmount(player2Gems);

                currentTurn = loadedData.getCurrentTurn();
            }
            if (players != null && players.size() > 0) {
                if (players.get(0).getCurrentHero() != null) {

                    restoreUnits(players.get(0), loadedData.getPlayer1Units());

                }
                if (players.get(1).getCurrentHero() != null) {

                    restoreUnits(players.get(1), loadedData.getPlayer2Units());

                }


            }
            // Восстанавливаем здания
            if (playerCity != null) {
                playerCity.getBuildings().clear();
                if (loadedData.getTownHallLevel() > 0) {
                    TownHall townHall = new TownHall();
                    for (int i = 1; i < loadedData.getTownHallLevel(); i++) {
                        townHall.upgrade();
                    }
                    playerCity.addBuilding(townHall);
                }
                if (loadedData.isCrossbowTowerBuilt()) {
                    playerCity.addBuilding(new CrossbowTower());
                }
                if (loadedData.isArenaBuilt()) {
                    playerCity.addBuilding(new Arena());
                }
                if (loadedData.isArmoryBuilt()) {
                    playerCity.addBuilding(new Armory());
                }
                if (loadedData.isCathedralBuilt()) {
                    playerCity.addBuilding(new Cathedral());
                }
                if (loadedData.isGatesBuilt()) {
                    playerCity.addBuilding(new Gates());
                }
                if (loadedData.isGuardPostBuilt()) {
                    playerCity.addBuilding(new GuardPost());
                }
            }
            //aiController = new AIController(mapWidth, mapHeight, this, players.get(1), gameMap);
            mapView = new MapView(gameMap);

        } else {
            System.out.println("Ошибка при загрузке игры!");
        }
    }

    private Unit createUnitByType(String unitType) {
        switch (unitType) {
            case "Pikeman":
                return new Pikeman();
            case "Archer":
                return new Archer();
            case "Cavalrist":
                return new Cavalrist();
            case "Angel":
                return new Angel();
            case "Paladin":
                return new Paladin();
            case "Swordsman":
                return new Swordsman();
            default:
                System.out.println("Неизвестный тип юнита: " + unitType);
                return null;
        }
    }

    private void restoreUnits(Player player, List<UnitData> unitsData) {
        if (player != null) {
            // Очищаем армию героя перед добавлением новых юнитов
            player.getCurrentHero().getArmy().getUnits().clear();

            if (unitsData != null) {
                System.out.println("Загружено " + unitsData.size() + " типов юнитов для игрока " + (players.indexOf(player) + 1));
                for (UnitData unitData : unitsData) {
                    System.out.println("  Загружено: " + unitData.getUnitType() + " - " + unitData.getCount());
                    try {
                        String unitType = unitData.getUnitType();
                        for (int i = 0; i < unitData.getCount(); i++) {
                            Unit unit = createUnitByType(unitType);
                            if (unit != null) {
                                player.getCurrentHero().getArmy().addUnit(unit);
                            } else {
                                System.out.println("    Не удалось создать юнит: " + unitType);
                            }
                        }

                    } catch (Exception e) {
                        System.err.println("Error restoring unit: " + e.getMessage());
                    }
                }
            }
        }
    }



    public void showLoadGameDialog() {
        String playerName = JOptionPane.showInputDialog(null, "Введите имя игрока:");

        if (playerName != null && !playerName.isEmpty()) {
            File[] saveFiles = SaveManager.getSaveFiles(playerName);

            if (saveFiles.length > 0) {
                String[] saveNames = new String[saveFiles.length];
                for (int i = 0; i < saveFiles.length; i++) {
                    saveNames[i] = saveFiles[i].getName().replace(".sav", "").replace(playerName + "_", "");
                }
                String selectedSave = (String) JOptionPane.showInputDialog(
                        null,
                        "Выберите сохранение:",
                        "Загрузка игры",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        saveNames,
                        saveNames[0]);

                if (selectedSave != null) {
                    loadGame(playerName, selectedSave);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Сохранения для игрока " + playerName + " не найдены. Начните новую игру.");
                // Здесь можно добавить код для начала новой игры
            }
        } else {
            JOptionPane.showMessageDialog(null, "Имя игрока не введено. Начните новую игру.");
            // Здесь можно добавить код для начала новой игры
        }
    }
    private String convertMapToString(GameMap gameMap) {
        // Реализация преобразования GameMap в строку
        return null;
    }

    private GameMap convertStringToMap(String mapData) {
        // Восстановление GameMap из строки
        if (mapData == null || mapData.isEmpty()) {
            return null;
        }

        GameMap restoredMap = new GameMap(mapWidth, mapHeight);

        return restoredMap; // Возвращаем восстановленную карту
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    private void handleHeroInCity() {
        Hero currentHero = players.get(0).getCurrentHero();
        Player player = players.get(0);

        //   if (playerCity != null && currentHero.getX() == playerCity.getX() && currentHero.getY() == playerCity.getY()) {
        if (playerCity != null && currentHero.getX() == playerCity.getY() && currentHero.getY() == playerCity.getX()) {
            cityView.displayCityMenu(playerCity, currentHero, player); // Player
            int choice = cityView.getChoice();
            switch (choice) {
                case 1:
                    cityView.viewBuildings(playerCity);
                    handleHeroInCity();
                    break;
                case 2:
                    cityView.buildBuilding(playerCity, currentHero, player);
                    handleHeroInCity();
                    break;
                case 3:
                    cityView.recruitUnits(playerCity, currentHero, player);
                    handleHeroInCity();
                    break;
                case 4:
                    cityView.viewHeroArmy(currentHero);
                    handleHeroInCity();
                    break;
                case 5:
                    exitCity(currentHero);
                    return;
                case 6:
                    TownHall townHall = null;
                    for (Building building : playerCity.getBuildings()) {
                        if (building instanceof TownHall) {
                            townHall = (TownHall) building;
                            break;
                        }
                    }
                    if (townHall != null) {
                        if (player.getGold().getAmount() >= 500) {
                            player.getGold().setAmount(player.getGold().getAmount() - 500);
                            townHall.upgrade(); // Изменено
                            System.out.println("Кремль был улучшен. Новый уровень " + townHall.getLevel());
                        } else {
                            System.out.println("У вас недостаточно золота");
                        }
                    }
                    handleHeroInCity();
                    break;
                case 7:
                    visitTavern(); // Убрали аргумент
                    handleHeroInCity();
                    break;
                default:
                    System.out.println("Неправильный выбор, попробуйте снова.");
                    handleHeroInCity();
            }
        } else {
            System.out.println("Герой не в городе.");
        }
    }
    private void exitCity(Hero currentHero) {
        System.out.println("Вы покинули город.");
        currentHero.setX(playerCity.getX() + 1);
        currentHero.setY(playerCity.getY() + 1);
    }

    @Test
    public void getAvailableExitCoordinates() {
        List<int[]> availableCoordinates = new ArrayList<>();
        int cityX = playerCity.getX();
        int cityY = playerCity.getY();
        int[][] potentialCoordinates = {
                {cityX - 1, cityY},
                {cityX + 1, cityY},
                {cityX, cityY - 1},
                {cityX, cityY + 1},
                {cityX - 1, cityY - 1},
                {cityX - 1, cityY + 1},
                {cityX + 1, cityY - 1},
                {cityX + 1, cityY + 1}
        };
        for (int[] coord : potentialCoordinates) {
            int x = coord[0];
            int y = coord[1];

            if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                String tile = gameMap.getMap()[x][y];
                if (!tile.equals("T") && !tile.equals("X") && !(x == aiHero.getX() && y == aiHero.getY())) {
                    availableCoordinates.add(new int[]{x, y});
                }
            }
        }
    }

    private void runGameLoop() {
        while (players.get(0).getCurrentHero() != null && players.get(1).getCurrentHero() != null) {
            Hero currentHero = players.get(0).getCurrentHero();
            Hero currentAIHero = players.get(1).getCurrentHero();
            if (currentHero == null) {
                System.out.println("ИИ победил! Герой уничтожен.");
                break;
            }
            if (currentAIHero == null) {
                System.out.println("Герой победил! ИИ уничтожен.");
                addBuildingScore();
                break;
            }
            mapView.displayMap(currentHero.getX(), currentHero.getY(), currentAIHero.getX(), currentAIHero.getY(), mapWidth, mapHeight, gameMap.getTerrain(), gameMap.getMap(), 0, 0, 9, 9, playerCity, players.get(0).getHeroes(), currentHero);
            displayHeroStats();
            processInput();
        }
        System.out.println("Игра окончена!");
        if (players.get(0).getCurrentHero() == null) {
            System.out.println("ИИ победил!");
        } else {
            System.out.println("Игрок победил!");
            addBuildingScore();
            handleWin();
        }
    }

    private void displayHeroStats() {
        Hero currentHero = players.get(0).getCurrentHero();
        movesLeft = currentHero.getMovesLeft();
        int gold = players.get(0).getGold().getAmount(); // Получаем золото ИГРОКА (правильно!)
        int gems = players.get(0).getGems().getAmount(); // Получаем гемы ИГРОКА (правильно!)
        movesLeft = currentHero.getMovesLeft();
        System.out.println("Золота в казне: " + gold);
        System.out.println("Гемов : " + gems);
        System.out.println("Осталось шагов до изнеможения: " + movesLeft);
        System.out.println("Enter command: (w, a, s, d, q, e, z, x, f, g, k, l, quit)");
    }
    private void displayAiArmy() {
        Hero aiHero = players.get(1).getCurrentHero();
        Map<String, Integer> unitCounts = new HashMap<>();

        aiHero.getArmy().getUnits().forEach(unit -> {
            String unitType = unit.getClass().getSimpleName();
            unitCounts.put(unitType, unitCounts.getOrDefault(unitType, 0) + 1);
        });

        System.out.println("Армия ИИ:");
        unitCounts.forEach((unitType, count) -> System.out.println("- " + unitType + ": " + count));
    }

    public void aiTurn() {
        currentPlayer = 2;
        System.out.println("AI turn started");
        if (players == null || players.size() < 2) {
            System.out.println("Ошибка");
            return;
        }
        Player aiPlayer = players.get(1);
        if (aiPlayer == null) {
            System.out.println("Ошибка: AI игрок не инициализирован.");
            return;
        }
        Hero aiHero = aiPlayer.getCurrentHero();
        if (aiHero == null) {
            System.out.println("Герой победил! ИИ уничтожен.");
            System.exit(0);
        }
        Hero playerHero = players.get(0).getCurrentHero();
        if (aiHero.getX() == playerHero.getX() && aiHero.getY() == playerHero.getY()) {
            battle(playerHero, aiHero);
            System.out.println("AI turn finished");
            currentPlayer = 1;
            Hero currentHero2 = players.get(0).getCurrentHero();
            List<Hero> heroes = players.get(0).getHeroes();
            displayHeroStats();
            return;
        }
        aiHero.setMovesLeft(5);
        Random random = new Random();
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;
        if (dx == 0 && dy == 0) {
            dx = 1;
        }
        moveAiHero(dx, dy, aiHero);
        aiHero.setMovesLeft(aiHero.getMovesLeft() - 1);
        System.out.println("AI turn finished");
        currentPlayer = 1;
        Hero currentHero = players.get(0).getCurrentHero();
        TownHall townHall = null;
        if (playerCity != null && playerCity.getBuildings() != null) {

            for (Building building : playerCity.getBuildings()) {
                if (building instanceof TownHall) {
                    townHall = (TownHall) building;
                    break;
                }
            }
            int goldIncome = 250;
            if (townHall != null) {
                goldIncome = townHall.getGoldIncome();
            }
            players.get(0).getGold().setAmount(players.get(0).getGold().getAmount() + goldIncome); // Начисляем золото игроку
            System.out.println("Собрано " + goldIncome + " золота налогов с граждан");
        }
        turnsCount++;
        System.out.println("Конец хода игрока. turnsCount = " + turnsCount);
        if (turnsCount % 5 == 0) {
            addUnitsToArmy();
        }
        startPlayerTurn();
        playerTurnStarted = false;
    }
    private boolean moveAiHero(int dx, int dy, Hero currentHero) {
        if (currentHero.getMovesLeft() > 0) {
            int newX = currentHero.getX() + dx;
            int newY = currentHero.getY() + dy;
            if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
                String tile = gameMap.getMap()[newY][newX];
                if (!tile.equals("T") && !tile.equals("X")) {
                    if (tile.equals("G")) {
                        players.get(1).getGold().setAmount(players.get(1).getGold().getAmount() + 500);
                        gameMap.getMap()[newY][newX] = ".";
                        System.out.println("ИИ собрал 500 золота!");
                    } else if (tile.equals("Z")) {
                        players.get(1).getGems().setAmount(players.get(1).getGems().getAmount() + 5);
                    }
                    currentHero.setX(newX);
                    currentHero.setY(newY);
                    System.out.println("ИИ передвинулся   (" + newX + ", " + newY + ")");
                    currentHero.setMovesLeft(currentHero.getMovesLeft() - 1);
                    return true;
                } else {
                    System.out.println("ИИ не может пройти.");
                    return false;
                }
            } else {
                System.out.println("ИИ не может пройти.");
                return false;
            }
        } else {
            System.out.println("ИИ устал.");
            return false;
        }
    }
    private void visitTavern() {
        System.out.println("\n--- Трактир ---");
        List<Building> buildings = playerCity.getBuildings();
        Tavern tavern = null;
        for (Building building : buildings) {
            if (building instanceof Tavern) {
                tavern = (Tavern) building;
                break;
            }
        }
        if (tavern == null) {
            System.out.println("В городе нет таверны.");
            return;
        }
        List<Hero> availableHeroes = tavern.getAvailableHeroes();
        if (availableHeroes.isEmpty()) {
            System.out.println("В таверне нет доступных героев.");
            return;
        }

        System.out.println("Доступные герои:");
        for (int i = 0; i < availableHeroes.size(); i++) {
            Hero hero = availableHeroes.get(i);
            String heroName = "";
            if(i == 0){
                heroName = "Соня";
            }
            else if(i == 1){
                heroName = "Вика";
            }

            System.out.println((i + 1) + ". " + heroName );
        }

        System.out.print("Выберите героя для покупки (или 0 для отмены): ");
        int choice = getChoice();

        if (choice > 0 && choice <= availableHeroes.size()) {
            Hero selectedHero = tavern.buyHero(choice - 1, players.get(0));
            if (selectedHero != null) {
                System.out.println("Вы купили героя!");
                if(choice - 1 == 0){
                    selectedHero.setName("Соня");
                }
                else if(choice - 1 == 1){
                    selectedHero.setName("Вика");
                }
                //   List<Hero> heroes = players.get(0).getHeroes();
                players.get(0).getHeroes().add(selectedHero);
                System.out.println("Теперь у вас " + players.get(0).getHeroes().size() + " героев");
            }
        }
    }

    private List<Stack> createUnitStacks(Army army) {
        List<Stack> stacks = new ArrayList<>();
        Map<Class<? extends Unit>, Integer> unitCounts = new HashMap<>();
        for (Unit unit : army.getUnits()) {
            Class<? extends Unit> unitClass = unit.getClass();
            unitCounts.put(unitClass, unitCounts.getOrDefault(unitClass, 0) + 1);
        }
        for (Map.Entry<Class<? extends Unit>, Integer> entry : unitCounts.entrySet()) {
            Class<? extends Unit> unitClass = entry.getKey();
            Integer count = entry.getValue();
            stacks.add(new Stack(unitClass, count));
        }
        return stacks;
    }

    public void battle(Hero hero1, Hero hero2) {
        BattleController battleController = new BattleController();
        System.out.println("Началась битва между " + hero1.getName() + " и " + hero2.getName() + "!");
        battleController.startBattle(hero1,hero2);
        if (!hero1.getArmy().getUnits().isEmpty() && hero2.getArmy().getUnits().isEmpty()) {
            players.get(1).setCurrentHero(null);
            System.out.println("Игрок проиграл");
        } else if (hero1.getArmy().getUnits().isEmpty() && !hero2.getArmy().getUnits().isEmpty()) {
            players.get(0).setCurrentHero(null);
            System.out.println("ИИ проиграл");
        }
    }
    public void processInput() {
        if (currentPlayer == 1) {
            Hero currentHero = null;
            Hero aiHero = null;
            try {
                currentHero = players.get(0).getCurrentHero();
                aiHero = players.get(1).getCurrentHero();
            } catch (Exception e) {
                System.exit(0);
            }
            if (!playerTurnStarted) {
                startPlayerTurn();
                playerTurnStarted = true;
            }
            if (currentHero == null) {
                System.out.println("ИИ победил");
                return;
            }
            if (aiHero == null) {
                System.out.println("Герой победил");
                return;
            }
            if (currentHero.getX() == playerCity.getX() && currentHero.getY() == playerCity.getY()) {
                handleHeroInCity();
            }
            if (currentHero.getMovesLeft() > 0) {
                try {
                    String command = scanner.nextLine();
                    boolean moved = false;

                    switch (command) {
                        case "a":
                            moved = moveHero(0, -1, currentHero);
                            break;
                        case "w":
                            moved = moveHero(-1, 0, currentHero);
                            break;
                        case "d":
                            moved = moveHero(0, 1, currentHero);
                            break;
                        case "s":
                            moved = moveHero(1, 0, currentHero);
                            break;
                        case "q":
                            moved = moveHero(-1, -1, currentHero);
                            break;
                        case "z":
                            moved = moveHero(1, -1, currentHero);
                            break;
                        case "e":
                            moved = moveHero(-1, 1, currentHero);
                            break;
                        case "x":
                            moved = moveHero(1, 1, currentHero);
                            break;
                        case "g":
                            switchHero();
                            break;
                        case "f":
                            currentPlayer = 2;
                            aiTurn();
                            break;
                        case "ai":
                            displayAiArmy();
                            break;
                        case "addmoney":
                            players.get(0).getGold().setAmount(players.get(0).getGold().getAmount() + 5000);
                            System.out.println((1) + ". " + players.get(0).getName() + " Здоровье: " + players.get(0).getGold().getAmount() + ", Гемы: " + players.get(0).getGems().getAmount());
                            break;
                        case "k":
                            System.out.print("Введите имя игрока: ");
                            String playerName = scanner.nextLine();
                            System.out.print("Введите имя сохранения: ");
                            String saveName = scanner.nextLine();
                            saveGame(playerName, saveName);
                            System.out.println("Игра сохранена успешно!");
                            break;
                        case "l":
                            System.out.print("Введите имя игрока: ");
                            String playerNameLoad = scanner.nextLine();
                            System.out.print("Введите имя сохранения: ");
                            String saveNameLoad = scanner.nextLine();
                            loadGame(playerNameLoad, saveNameLoad);
                            System.out.println("Игра загружена успешно!");
                            break;
                        case "quit":
                            System.out.println("Возврат в стартовое меню.");
                            startGame();
                        default:
                            System.out.println("Invalid command.");
                    }

                    if (moved) {
                        currentHero.setMovesLeft(currentHero.getMovesLeft() - 1);
                    }
                    displayHeroStats();
                } catch (Exception e) {
                    System.out.println("Error reading input: " + e.getMessage());
                }finally {
                   scanner = new Scanner(System.in);
                }
            } else {
                currentPlayer = 2;
                aiTurn();
            }
        }
    }
    private void addUnitsToArmy() {
        turnsSinceLastRecruit++;
        // Условие для найма юнитов только раз в 5 ходов
        if (turnsSinceLastRecruit >= 5) {
            System.out.println("addUnitsToArmy() вызывается!");
            aihero = players.get(1).getCurrentHero();
            for (int i = 0; i < 5; i++) {
                aihero.getArmy().addUnit(new Pikeman());
            }
            for (int i = 0; i < 3; i++) {
                aihero.getArmy().addUnit(new Archer());
            }
            System.out.println("В армию " + aihero.getName() + " добавлено подкрепление!");
            turnsSinceLastRecruit = 0;
        }
    }

    public int getChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    private void switchHero() {
        List<Hero> heroes = players.get(0).getHeroes();
        if (heroes.size() < 2) {
            System.out.println("У вас недостаточно героев для переключения.");
            return;
        }

        System.out.println("\nВыберите героя для переключения:");
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            String heroName = hero.getName() == null ? "Без имени" : hero.getName();
            if (i == 0) {
                heroName = "Соня";
            } else if (i == 1) {
                heroName = "Вика";
            }
            System.out.println((i + 1) + ". " + heroName + " Здоровье: " + players.get(0).getGold().getAmount() + ", Гемы: " +  players.get(0).getGems().getAmount());
        }

        System.out.print("Введите номер героя: ");
        int choice = getChoice();

        if (choice > 0 && choice <= heroes.size()) {
            Hero selectedHero = heroes.get(choice - 1);
            players.get(0).setCurrentHero(selectedHero);

            System.out.println("Вы переключились на героя.");
        } else {
            System.out.println("Неверный выбор.");
        }
    }
    public boolean moveHero(int dx, int dy, Hero currentHero) {
        if (currentHero == null) {
            System.out.println("Нет текущего героя!");
            return false;
        }
        if (currentHero.getMovesLeft() > 0) {
            int newX = currentHero.getX() + dx;
            int newY = currentHero.getY() + dy;

            if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
                String tile = gameMap.getMap()[newX][newY];
                if (!tile.equals("T") && !tile.equals("X")) {
                    if (tile.equals("G")) {
                        players.get(0).getGold().setAmount(players.get(0).getGold().getAmount()+ 500);
                        addGoldScore();
                        gameMap.getMap()[newX][newY] = ".";
                    } else if (tile.equals("Z")) {
                        if (turnsSinceLastCollection >= 5) {
                            players.get(0).getGems().setAmount(players.get(0).getGems().getAmount()+ 5); // Добавляем гемы игроку
                            turnsSinceLastCollection = 0;
                            addMineScore();
                            System.out.println("Собрано 5 кристаллов с шахты!");
                        } else {
                            System.out.println("Шахта пока пуста. Осталось " + (5 - turnsSinceLastCollection) + " ходов.");
                        }
                    }
                    Hero aiHero = players.get(1).getCurrentHero();
                    if (newX == aiHero.getX() && newY == aiHero.getY()) {
                        System.out.println("Битва начинается!");
                        battle(currentHero, aiHero);
                        return false;
                    }
                    currentHero.setX(newX);
                    currentHero.setY(newY);
                    System.out.println("Передвинулся на  (" + newX + ", " + newY + ")");
                    Building building = gameMap.getBuilding(newX, newY);
                    if (building instanceof WitcherSchool) {
                        witcherSchoolInterface.open(currentHero); // Открываем интерфейс Школы Ведьмаков
                    }
                    return true;
                } else {
                    System.out.println("Стой! Убьешься.");
                    return false;
                }
            } else {
                System.out.println("Стой! Убьешься.");
                return false;
            }
        } else {
            System.out.println("Герой устал и никуда не пойдет");
            return false;
        }
    }

    public void startPlayerTurn() {
        Hero currentHero = players.get(0).getCurrentHero();
        currentHero.setMovesLeft(5);
        //addUnitsToArmy();
    }
    private void endPlayerTurn() {
        Hero currentHero = players.get(0).getCurrentHero();
        applyCityIncome(playerCity, currentHero);

    }
    private void applyCityIncome(City city, Hero hero) {
        int income = city.getGoldIncome();
        players.get(0).getGold().setAmount(players.get(0).getGold().getAmount() + income); // Добавляем доход игроку
        System.out.println("Начислен доход от города: " + income + " золота.");
    }
    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {

    }
    public Hero getHero() {
        return hero;
    }
    public void handleHeroInCity(Hero hero, City playerCity, Scanner scanner) {
    }
    public void moveHeroTo(int x, int y) {
        hero.setX(x);
        hero.setY(y);
    }
    public City getPlayerCity() {
        return playerCity;
    }
    public void increaseTurnsSinceLastCollection() {
        turnsSinceLastCollection++;
    }
    public int getTurnsSinceLastCollection() {
        return turnsSinceLastCollection;
    }
    public void setTurnsSinceLastCollection(int turnsSinceLastCollection) {
        this.turnsSinceLastCollection = turnsSinceLastCollection;
    }
    public void processInputForTest(Hero hero) {
        if (hero.getX() >= 0 && hero.getX() < mapWidth && hero.getY() >= 0 && hero.getY() < mapHeight) {
            Hero aiHero = players.get(1).getCurrentHero(); // Получаем AI Hero
            if (aiHero != null && hero.getX() == aiHero.getX() && hero.getY() == aiHero.getY()) { // Проверяем, находятся ли герои в одной точке
                battle(hero, aiHero); // Вызываем битву, если герои встретились
            } else {
                String tile = gameMap.getMap()[hero.getX()][hero.getY()];
                if (tile != null) {
                    if (tile.equals("Z")) {
                        if (turnsSinceLastCollection >= 5) {
                            players.get(0).getGems().setAmount(players.get(0).getGems().getAmount() + 5);
                            turnsSinceLastCollection = 0;
                            System.out.println("Собрано 5 кристаллов с шахты!");
                        } else {
                            System.out.println("Шахта пока пуста. Осталось " + (5 - turnsSinceLastCollection) + " ходов.");
                        }
                    } else if (tile.equals("G")) {
                        players.get(0).getGold().setAmount(players.get(0).getGold().getAmount() + 500);
                        addGoldScore();
                        gameMap.getMap()[hero.getX()][hero.getY()] = ".";
                    }
                }
            }
        } else {
            System.out.println("Герой не на карте или на недопустимой клетке.");
        }
    }
    public void testBattle(Hero hero1, Hero hero2) {
        BattleController battleController = new BattleController();
        System.out.println("Началась битва между " + hero1.getName() + " и " + hero2.getName() + "!");
        battleController.startBattle(hero1,hero2);
        System.out.println("Координаты героя 1 после битвы: (" + hero1.getX() + ", " + hero1.getY() + ")");
        System.out.println("Координаты героя 2 после битвы: (" + hero2.getX() + ", " + hero2.getY() + ")");
        if (!hero1.getArmy().getUnits().isEmpty() && hero2.getArmy().getUnits().isEmpty()) {
            players.get(1).setCurrentHero(null);
        } else if (hero1.getArmy().getUnits().isEmpty() && !hero2.getArmy().getUnits().isEmpty()) {
            players.get(0).setCurrentHero(null);
        }
    }
    public void collectGold(Player player) {
        player.getGold().setAmount(player.getGold().getAmount() + 500);
    }

    // Метод для прямого сбора гемов
    public void collectGems(Player player) {
        player.getGems().setAmount(player.getGems().getAmount() + 5);
        turnsSinceLastCollection = 0;
    }
    public void setMap(GameMap gameMap) {}
    public void setHero(Hero initialHero) { this.hero = initialHero; }
    public void setPlayerCity(City playerCity) {
        this.playerCity = playerCity;
    }
    public void setAiHero(Hero aiHero) {
        this.aiHero = aiHero;
    }
    public void setAIController(AIController aiController) {
        this.aiController = aiController;
    }
    public void openMapEditor() {
        MapEditor mapEditor = new MapEditor(mapWidth, mapHeight);
        mapEditor.startEditor();
    }
    
}