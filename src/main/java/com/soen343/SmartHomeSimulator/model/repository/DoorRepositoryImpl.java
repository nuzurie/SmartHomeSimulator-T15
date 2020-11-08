package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Door;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Repository
public class DoorRepositoryImpl implements DoorRepository{

    List<Door> linkedList = new LinkedList<>();

    @Override
    public Door findById(Long id) {
        for (Door door:
                this.linkedList) {
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
            linkedList.add(door);
        }
        return this.findById(door.getId());
    }

    @Override
    public List<Door> findAll() {
        return linkedList;
    }

    @Override
    public Door remove(Door door) {
        Boolean removed = this.linkedList.remove(door);
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
