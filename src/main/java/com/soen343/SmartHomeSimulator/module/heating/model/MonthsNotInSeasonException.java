package com.soen343.SmartHomeSimulator.module.heating.model;

public class MonthsNotInSeasonException extends Exception {
    public MonthsNotInSeasonException(String message) {
        super(message);
    }
}
