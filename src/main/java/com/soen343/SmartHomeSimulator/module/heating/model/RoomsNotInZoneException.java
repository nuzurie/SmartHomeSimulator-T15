package com.soen343.SmartHomeSimulator.module.heating.model;

public class RoomsNotInZoneException extends Exception {
    public RoomsNotInZoneException(String message) {
        super(message);
    }
}
