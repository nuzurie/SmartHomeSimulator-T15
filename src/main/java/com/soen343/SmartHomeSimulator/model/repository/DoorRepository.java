package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Door;
import com.soen343.SmartHomeSimulator.model.Light;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Door repository Interface.
 */
public interface DoorRepository {

    /**
     * Find by id door.
     *
     * @param id the id
     * @return the door
     */
    public Door findById(Long id);

    /**
     * Save door.
     *
     * @param door the door
     * @return the door
     */
    public Door save(Door door);

    /**
     * Find all doors.
     *
     * @return the list
     */
    public List<Door> findAll();

    /**
     * Remove door.
     *
     * @param door the door
     * @return the door
     */
    public Door remove(Door door);

    /**
     * Delete by id door.
     *
     * @param id the id
     * @return the door
     */
    public Door deleteById(Long id);
}
