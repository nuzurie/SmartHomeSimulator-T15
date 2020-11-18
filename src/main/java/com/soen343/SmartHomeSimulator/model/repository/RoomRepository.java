package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The Room repository Interface.
 */
public interface RoomRepository{

    /**
     * The constant roomSet.
     */
    public List<Room> roomSet = new LinkedList<>();

    /**
     * Find by Room by ID.
     *
     * @param id the id
     * @return the room
     */
    public Room findById(Long id);

    /**
     * Save Room.
     *
     * @param room the room
     * @return the room
     */
    public Room save(Room room);

    /**
     * Get list of all Rooms.
     *
     * @return the list
     */
    public List<Room> findAll();

    /**
     * Remove room.
     *
     * @param room the room
     * @return the room
     */
    public Room remove(Room room);

    /**
     * Delete by Room by ID.
     *
     * @param id the id
     * @return the room
     */
    public Room deleteById(Long id);

}