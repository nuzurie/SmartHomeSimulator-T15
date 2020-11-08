package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface RoomRepository{

    public List<Room> roomSet = new LinkedList<>();

    public Room findById(Long id);

    public Room save(Room room);

    public List<Room> findAll();

    public Room remove(Room room);

    public Room deleteById(Long id);

}