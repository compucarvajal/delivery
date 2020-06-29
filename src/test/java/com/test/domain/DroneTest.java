package com.test.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DroneTest {

    private static final Logger log = LoggerFactory.getLogger(DroneTest.class);

    @Test
    public void moveTest1(){
        //expected location
        Location location = new Location(3,3);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "AAAADADI";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
        }
        //expected values
        log.info(drone.getOrientation().toString());
        assertEquals(location,drone.getLocation());
    }

    @Test
    public void moveTest2(){
        //expected location
        Location location = new Location(-2,-1);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "IAAAIAIAI";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
        }
        //expected values
        assertEquals(location,drone.getLocation());
    }

    @Test
    public void moveTest3(){
        //expected location
        Location location = new Location(2,-2);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "DAAADAADAD";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
        }
        //expected values
        assertEquals(location.getX(),drone.getLocation().getX());
        assertEquals(location.getY(),drone.getLocation().getY());
    }

    @Test
    public void moveTest4(){
        //expected location
        Location location = new Location(2,-2);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "DAAADAADAD";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
        }
        //expected values
        assertEquals(location,drone.getLocation());
    }

    @Test
    public void moveTest5(){
        //expected location
        Location location = new Location(5,8);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "DDDADAIAADAAAAAADAAAAAAAA";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
            log.info(drone.getLocation().getLocationLikePoint());
        }
        //expected values
        assertEquals(location,drone.getLocation());
    }

    @Test
    public void moveTestError(){
        //expected location
        Location location = new Location(3,8);
        Drone drone = new Drone(0,0,CardinalPoint.NORTH);
        String delivertyAddress = "DDDADAIAADAAAAAADAAAAAAAA";
        //drone instructions
        for(char c : delivertyAddress.toCharArray()){
            drone.move(c);
            log.info(drone.getLocation().getLocationLikePoint());
        }
        //expected values

        assertNotEquals(location,drone.getLocation());
    }

    @Test
    public void runTest(){
        Drone drone = new Drone(0,0, CardinalPoint.NORTH);
        drone.addOrderAdress("DAAADAADAD");
        drone.addOrderAdress("AAAAADAIA");
        Location location = new Location(4,5);
        drone.run();
        assertEquals(location,drone.getLocation());
    }
}
