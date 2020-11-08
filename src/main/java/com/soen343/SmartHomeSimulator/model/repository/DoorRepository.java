package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Door;
import com.soen343.SmartHomeSimulator.model.Light;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DoorRepository {

    public Door findById(Long id);

    public Door save(Door door);

    public List<Door> findAll();

    public Door remove(Door door);

    public Door deleteById(Long id);
}
