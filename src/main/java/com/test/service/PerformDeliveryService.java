package com.test.service;

import com.test.domain.CardinalPoint;
import com.test.domain.Drone;

import java.util.List;

public class PerformDeliveryService {


    public void performDelivery(List<Drone> drones){
        drones.forEach(drone->{
            new Thread(drone).start();
        } );
    }

}
