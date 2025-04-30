package main;

import model.units.Unit;
import model.units.UnitData;

import java.io.Serializable;
import java.util.List;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mapData;
    private HeroData hero1Data;
    private HeroData hero2Data;
    private int currentTurn;
    private int player1Gold;
    private int player2Gold;
    private String currentMapName;
    private int mapWidth;
    private int mapHeight;
    private List<UnitData> player1Units;
    private List<UnitData> player2Units;
    private int player1Gems;
    private int player2Gems;
    private int townHallLevel;
    private boolean crossbowTowerBuilt;
    private boolean ArenaBuilt;
    private boolean ArmoryBuilt;
    private boolean CathedralBuilt;
    private boolean GatesBuilt;
    private boolean GuardPostBuilt;
    private List<Unit> units;
    private int gold;
    private boolean wonMiniBattle;

    public SaveData(String mapData, HeroData hero1Data, HeroData hero2Data, int currentTurn, int player1Gold, int player2Gold, String currentMapName, int mapWidth, int mapHeight, List<UnitData> player1Units, List<UnitData> player2Units, int player1Gems, int player2Gems, int townHallLevel, boolean crossbowTowerBuilt, boolean arenaBuilt, boolean armoryBuilt, boolean cathedralBuilt, boolean gatesBuilt, boolean guardPostBuilt, List<Unit> units, int gold) {
        this.mapData = mapData;
        this.hero1Data = hero1Data;
        this.hero2Data = hero2Data;
        this.currentTurn = currentTurn;
        this.player1Gold = player1Gold;
        this.player2Gold = player2Gold;
        this.currentMapName = currentMapName;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.player1Units = player1Units;
        this.player2Units = player2Units;
        this.player1Gems = player1Gems;
        this.player2Gems = player2Gems;
        this.townHallLevel = townHallLevel;
        this.crossbowTowerBuilt = crossbowTowerBuilt;
        ArenaBuilt = arenaBuilt;
        ArmoryBuilt = armoryBuilt;
        CathedralBuilt = cathedralBuilt;
        GatesBuilt = gatesBuilt;
        GuardPostBuilt = guardPostBuilt;
        this.units = units;
        this.gold = gold;
        this.wonMiniBattle = false;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public String getMapData() {
        return mapData;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public HeroData getHero1Data() {
        return hero1Data;
    }

    public void setHero1Data(HeroData hero1Data) {
        this.hero1Data = hero1Data;
    }

    public HeroData getHero2Data() {
        return hero2Data;
    }

    public void setHero2Data(HeroData hero2Data) {
        this.hero2Data = hero2Data;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public List<UnitData> getPlayer1Units() {
        return player1Units;
    }

    public void setPlayer1Units(List<UnitData> player1Units) {
        this.player1Units = player1Units;
    }

    public List<UnitData> getPlayer2Units() {
        return player2Units;
    }

    public void setPlayer2Units(List<UnitData> player2Units) {
        this.player2Units = player2Units;
    }

    public int getPlayer1Gems() {
        return player1Gems;
    }

    public void setPlayer1Gems(int player1Gems) {
        this.player1Gems = player1Gems;
    }

    public int getPlayer2Gems() {
        return player2Gems;
    }

    public void setPlayer2Gems(int player2Gems) {
        this.player2Gems = player2Gems;
    }

    public int getPlayer1Gold() {
        return player1Gold;
    }

    public void setPlayer1Gold(int player1Gold) {
        this.player1Gold = player1Gold;
    }

    public int getPlayer2Gold() {
        return player2Gold;
    }

    public void setPlayer2Gold(int player2Gold) {
        this.player2Gold = player2Gold;
    }
    public int getTownHallLevel() {
        return townHallLevel;
    }

    public void setTownHallLevel(int townHallLevel) {
        this.townHallLevel = townHallLevel;
    }

    public boolean isCrossbowTowerBuilt() {
        return crossbowTowerBuilt;
    }

    public void setCrossbowTowerBuilt(boolean crossbowTowerBuilt) {
        this.crossbowTowerBuilt = crossbowTowerBuilt;
    }
    public boolean isArmoryBuilt() {
        return ArmoryBuilt;
    }

    public void setArmoryBuilt(boolean ArmoryBuilt) {
        this.ArmoryBuilt = ArmoryBuilt;
    }
    public boolean isArenaBuilt() {
        return ArenaBuilt;
    }

    public void setArenaBuilt(boolean ArenaBuilt) {
        this.ArenaBuilt = ArenaBuilt;
    }
    public boolean isCathedralBuilt() {
        return CathedralBuilt;
    }

    public void setCathedralBuilt(boolean CathedralBuilt) {
        this.CathedralBuilt = CathedralBuilt;
    }
    public boolean isGatesBuilt() {
        return GatesBuilt;
    }

    public void setGatesBuilt(boolean GatesBuilt) {
        this.GatesBuilt = GatesBuilt;
    }
    public boolean isGuardPostBuilt() {
        return GuardPostBuilt;
    }

    public void setGuardPostBuilt(boolean GuardPostBuilt) {
        this.GuardPostBuilt = GuardPostBuilt;
    }
    public List<Unit> getUnits() {
        return units;
    }

    public int getGold() {
        return gold;
    }

    public boolean getWonMiniBattle() { return wonMiniBattle; }

    public void setWonMiniBattle(boolean wonMiniBattle) {this.wonMiniBattle = wonMiniBattle;}
    // Геттеры и сеттеры для других данных
}