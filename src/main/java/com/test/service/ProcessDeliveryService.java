package com.test.service;

import com.test.domain.CardinalPoint;
import com.test.domain.Drone;
import com.test.util.PropertiesSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ProcessDeliveryService {

    private static final Logger log = LoggerFactory.getLogger(ProcessDeliveryService.class.getName());

    private int xIni;
    private int yIni;
    private int maxLimit;
    private PerformDeliveryService performDeliveryService;

    public ProcessDeliveryService() throws Exception {
        Properties applicationProperties = PropertiesSingleton.getApplicationProperties();
        xIni = Integer.parseInt(applicationProperties.getProperty("initial.x"));
        yIni = Integer.valueOf(applicationProperties.getProperty("initial.y"));
        maxLimit = Integer.parseInt(applicationProperties.getProperty("max.limit"));
        log.info("application properties got successfully");
        performDeliveryService = new PerformDeliveryService();
    }

    public void processOrders(Map<Integer, List<String>> ordersByDrone) {

        log.info(ordersByDrone.toString());
        List<Drone> drones = new ArrayList<>();
        int x;
        int y;
        boolean band;
        for (Map.Entry<Integer, List<String>> droneOrders : ordersByDrone.entrySet()) {
            log.info("-------------Drone number: " + droneOrders.getKey());
            x = xIni;
            y = yIni;
            Drone drone = new Drone(x, y, CardinalPoint.NORTH);
            band = false;
            for (String orderA : droneOrders.getValue()) {
                log.info("X and Y {}{}", x, y);
                if (validateFinalPoint(drone, orderA, maxLimit)) {
                    x = drone.getLocation().getX();
                    y = drone.getLocation().getY();
                    drone.setId(droneOrders.getKey());
                    drone.addOrderAdress(orderA);
                    band = true;
                } else {
                    drone.setOrientation(CardinalPoint.NORTH);
                    drone.getLocation().setX(0);
                    drone.getLocation().setY(0);
                }
            }
            if (band) {
                drones.add(drone);
                drone.getLocation().setX(0);
                drone.getLocation().setY(0);
                drone.setOrientation(CardinalPoint.NORTH);
            }
        }
        log.info("---------------------------");
        log.info("drones size" + drones.size());
        drones.forEach(dron -> log.info(dron.toString()));
        performDeliveryService.performDelivery(drones);
    }

    public Boolean validateFinalPoint(Drone drone, String deliveryAddress, int limit) {
        log.info("deliverAddress " + deliveryAddress);
        log.info(drone.getLocation().toString());
        for (char command : deliveryAddress.toCharArray()) {
            if (validateCommand(command)) {
                drone.move(command);
            }
        }
        log.info(drone.getLocation().toString());
        boolean validate = ((-limit <= drone.getLocation().getX() && (drone.getLocation().getX() <= limit))
                &&
                (-limit <= drone.getLocation().getY() && (drone.getLocation().getY() <= limit)));
        log.info(String.valueOf(validate));
        return validate;
    }

    /*
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
    }*/

    public Boolean validateCommand(char command) {
        //log.info("command " + command);
        //log.info("contain " + contain);
        return Arrays.asList('A', 'I', 'D').contains(command);
    }


}
