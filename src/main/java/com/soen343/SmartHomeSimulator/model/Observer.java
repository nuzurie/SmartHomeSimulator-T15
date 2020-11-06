package com.soen343.SmartHomeSimulator.model;

import java.util.List;

public interface Observer {

    public int update(List<Room> rooms);
}
