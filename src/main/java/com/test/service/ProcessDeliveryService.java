package com.test.service;

import com.test.domain.CardinalPoint;
import com.test.domain.Drone;
import com.test.domain.Location;
import com.test.util.PropertiesSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ProcessDeliveryService {

    private static final Logger log = LoggerFactory.getLogger(ProcessDeliveryService.class.getName());

    private int xIni;
    private int yIni;
    private int maxLimit;
    private static Properties applicationProperties;

    public ProcessDeliveryService() throws Exception {
        applicationProperties = PropertiesSingleton.getApplicationProperties();
        xIni = Integer.valueOf(applicationProperties.getProperty("initial.x"));
        yIni = Integer.valueOf(applicationProperties.getProperty("initial.y"));
        maxLimit = Integer.valueOf(applicationProperties.getProperty("max.limit"));
        log.info("application properties got successfully");
    }

    public void processOrders(Map<Integer, List<String>> ordersByDrone) {

        log.info(ordersByDrone.toString());
        List<Drone> drones = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> droneOrders : ordersByDrone.entrySet()) {
            Drone drone = new Drone(xIni, xIni, CardinalPoint.NORTH);
            droneOrders.getValue().stream().forEach(orderA -> {
                        if (validateFinalPoint(xIni, yIni, orderA, maxLimit)) {
                            drone.addOrderAdress(orderA);
                        }
                    }
            );
            drones.add(drone);
        }

        log.info("drones size" + drones.size());
        drones.stream().forEach(dron -> log.info(drones.toString()));


    }

    public Boolean validateFinalPoint(int initialX, int initialY, String deliveryAddress, int limit) {
        Drone drone = new Drone(initialX, initialY, CardinalPoint.NORTH);
        for (char command : deliveryAddress.toCharArray()) {
            if (validateCommand(command)) {
                move(command, drone);
            }
        }
        log.info(drone.getLocation().toString());

        return ((-limit <= drone.getLocation().getX() && (drone.getLocation().getX() <= limit))
                &&
                (-limit <= drone.getLocation().getY() && (drone.getLocation().getY() <= limit))
                ? true : false);
    }

    public void move(char command, Drone drone) {
        switch (command) {
            case 'A':
                //log.info("ahead moving");
                drone.moveA();
                break;
            case 'I':
                drone.moveLeft();
                break;
            case 'D':
                //log.info("right moving");
                drone.moveRigth();
                break;
        }
    }

    public Boolean validateCommand(char command) {
        //log.info("command " + command);
        boolean contain = Arrays.asList('A', 'I', 'D').contains(command);
        //log.info("contain " + contain);
        return contain;
    }


}
