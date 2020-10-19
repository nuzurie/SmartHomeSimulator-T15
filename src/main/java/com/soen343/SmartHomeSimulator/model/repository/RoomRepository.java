package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Room;

import java.util.HashSet;
import java.util.Set;

public interface RoomRepository{

    public Set<Room> roomSet = new HashSet<>();

    public Room findById(Long id);

    public Room save(Room room);

    public Set<Room> findAll();

    public Room remove(Room room);

    public Room deleteById(Long id);

}