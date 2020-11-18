package com.soen343.SmartHomeSimulator.model.repository;


import com.soen343.SmartHomeSimulator.model.Light;
import com.soen343.SmartHomeSimulator.model.Window;

import java.util.List;

/**
 * The Window Repository interface.
 */
public interface WindowRepository {

    /**
     * Find Window by ID.
     *
     * @param id the id
     * @return the window
     */
    public Window findById(Long id);

    /**
     * Save window.
     *
     * @param window the window
     * @return the window
     */
    public Window save(Window window);

    /**
     * Get List of all Windows.
     *
     * @return the list
     */
    public List<Window> findAll();

    /**
     * Remove window.
     *
     * @param window the window
     * @return the window
     */
    public Window remove(Window window);

    /**
     * Delete Window by ID.
     *
     * @param id the id
     * @return the window
     */
    public Window deleteById(Long id);

}
