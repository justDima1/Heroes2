package model;

import java.util.ArrayList;
import java.util.List;

import model.buildings.*;

public class City {
    private int x;
    private int y;
    private String name;
    private List<Building> buildings;

    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.buildings = new ArrayList<>();
        this.buildings.add(new TownHall()); // Add only TownHall at the beginning
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public int getGoldIncome() {
        int income = 0;
        for (Building building : buildings) {
            if (building instanceof TownHall) {
                income += ((TownHall) building).getGoldIncome();
            }
        }
        return income;
    }
    public TownHall getTownHall() {
        for (Building building : buildings) {
            if (building instanceof TownHall) {
                return (TownHall) building;
            }
        }
        return null; // Or throw an exception if a TownHall should always exist
    }
    public boolean hasTownHall() {
        return getTownHall() != null;
    }
    public List<RecruitBuilding> getRecruitBuildings() {
        List<RecruitBuilding> recruitBuildings = new ArrayList<>();
        for (Building building : buildings) {
            if (building instanceof RecruitBuilding) {
                recruitBuildings.add((RecruitBuilding) building);
            }
        }
        return recruitBuildings;
    }
    
}