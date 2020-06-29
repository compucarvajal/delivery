package com.test.domain;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    private Location location;
    private CardinalPoint orientation;
    private List<String> orderAddresses;

    public Drone(int iniX, int iniY,CardinalPoint iniOrientation){
        location = new Location(0,0);
        orientation = iniOrientation;
        orderAddresses = new ArrayList<>();
    }

    public void moveA() {
        switch (orientation) {
            case EAST:
                location.increaseX();
                break;
            case WEST:
                location.decreaseX();
                break;
            case NORTH:
                location.increaseY();
                break;
            case SOUTH:
                location.decreaseY();
                break;
        }
    }

    public void moveRigth() {
        switch (orientation) {
            case EAST:
                location.decreaseY();
                orientation = CardinalPoint.SOUTH;
                break;
            case WEST:
                location.increaseY();
                orientation = CardinalPoint.NORTH;
                break;
            case NORTH:
                location.increaseX();
                orientation = CardinalPoint.EAST;
                break;
            case SOUTH:
                location.decreaseX();
                orientation = CardinalPoint.WEST;
                break;
        }
    }

    public void moveLeft() {
        switch (orientation) {
            case EAST:
                location.increaseY();
                orientation = CardinalPoint.NORTH;
                break;
            case WEST:
                location.decreaseY();
                orientation = CardinalPoint.SOUTH;
                break;
            case NORTH:
                location.decreaseX();
                orientation = CardinalPoint.WEST;
                break;
            case SOUTH:
                location.increaseX();
                orientation = CardinalPoint.EAST;
                break;
        }
    }

    public Location getLocation(){
        return location;
    }

    public void addOrderAdress(String orderAddress){
        orderAddresses.add(orderAddress);
    }

    @Override
    public String toString() {
        return "Drone{" +
                "location=" + location +
                ", orientation=" + orientation +
                ", orderAddresses=" + orderAddresses +
                '}';
    }
}
