package com.soen343.SmartHomeSimulator.model;

import java.util.List;

/**
 * The Observer interface.
 */
public interface Observer {

    /**
     * Update the rooms.
     *
     * @param rooms the rooms
     * @return the int
     */
    public int update(List<Room> rooms);
}
