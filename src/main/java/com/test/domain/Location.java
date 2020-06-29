package com.test.domain;

import lombok.Data;

@Data
public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void decreaseX() {
        x = x - 1;
    }

    public void increaseX() {
        x = x + 1;
    }

    public void decreaseY() {
        y = y - 1;
    }

    public void increaseY() {
        y = y + 1;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    public String getLocationLikePoint() {
        return "(" + x + "," + y + ')';
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Location)obj).getX() && y == ((Location)obj).getY();
    }
}
