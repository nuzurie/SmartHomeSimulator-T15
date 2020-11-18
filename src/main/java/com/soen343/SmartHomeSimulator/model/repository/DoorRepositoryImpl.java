package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Door;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * The Implementation of the Door repository.
 */
@Repository
public class DoorRepositoryImpl implements DoorRepository{

    /**
     * The List of doors.
     */
    List<Door> doorList = new LinkedList<>();

    @Override
    public Door findById(Long id) {
        for (Door door:
                this.doorList) {
            if (door.getId()==id)
                return door;
        }
        return null;
    }

    @Override
    public Door save(Door door) {
        Door already_exists = findById(door.getId());
        if (already_exists!=null){
            already_exists.setLocked(door.isLocked());
            already_exists.setOpen(door.isOpen());
        }
        else{
            doorList.add(door);
        }
        return this.findById(door.getId());
    }

    @Override
    public List<Door> findAll() {
        return doorList;
    }

    @Override
    public Door remove(Door door) {
        Boolean removed = this.doorList.remove(door);
        if (removed)
            return door;
        else
            return null;
    }

    @Override
    public Door deleteById(Long id) {
        Door door = this.findById(id);
        return remove(door);
    }
}
