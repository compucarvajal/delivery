package com.test.domain;

import com.test.service.ProcessDeliveryService;
import com.test.util.PropertiesSingleton;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Drone implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Drone.class.getName());

    private int id;
    private Location location;
    private CardinalPoint orientation;
    private List<String> orderAddresses;

    public Drone(int iniX, int iniY, CardinalPoint iniOrientation) {
        location = new Location(0, 0);
        orientation = iniOrientation;
        orderAddresses = new ArrayList<>();
    }

    public void moveA() {
        //log.info("move A");
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
        //log.info("move right");
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
        //log.info("move left");
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

    public Location getLocation() {
        return location;
    }

    public void addOrderAdress(String orderAddress) {
        orderAddresses.add(orderAddress);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrientation(CardinalPoint c) {
        this.orientation = c;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "location=" + location +
                ", orientation=" + orientation +
                ", orderAddresses=" + orderAddresses +
                '}';
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("begin run");
        Properties properties = PropertiesSingleton.getApplicationProperties();
        String outputPath = properties.getProperty("output.files.path");
        String fileName = "out" + StringUtils.leftPad(String.valueOf(id), 2, "0") + ".txt";
        String fullOutputPath = outputPath + fileName;
        Path filePath = Paths.get(fullOutputPath);

        String header = "== Reporte de entregas ==";
        StringBuilder outputText = new StringBuilder();
        outputText.append(header);
        log.info(getLocation().toString());
        orderAddresses.stream().forEach(order -> {
                    log.info(order);
                    for (char c : order.toCharArray()) {
                        move(c);
                    }
                    outputText.append(System.getProperty("line.separator"));
                    outputText.append(getLocation().getLocationLikePoint() + " dirección " + orientation.toString());
                }
        );
        log.info(outputText.toString());
        Files.writeString(filePath, outputText.toString());
        log.info("run finished");
    }

    public void move(char command) {
        switch (command) {
            case 'A':
                moveA();
                break;
            case 'I':
                moveLeft();
                break;
            case 'D':
                moveRigth();
                break;
        }
    }

    @SneakyThrows
    public void runTest() {
        log.info("begin run");
        Properties properties = PropertiesSingleton.getApplicationProperties();
        String outputPath = properties.getProperty("output.files.path");
        String fileName = "out" + StringUtils.leftPad(String.valueOf(id), 2, "0") + ".txt";
        String fullOutputPath = outputPath + fileName;

        String header = "== Reporte de entregas ==";
        StringBuilder outputText = new StringBuilder();
        outputText.append(header);
        log.info(getLocation().toString());
        log.info(orientation.toString());
        orderAddresses.stream().forEach(order -> {
                    log.info(order);
                    for (char c : order.toCharArray()) {
                        move(c);
                    }
                    outputText.append("/n");
                    outputText.append(getLocation().getLocationLikePoint() + " dirección " + CardinalPoint.SOUTH.toString());
                }
        );
        log.info(getLocation().toString());
        log.info(outputText.toString());
        log.info("run finished");
    }
}
