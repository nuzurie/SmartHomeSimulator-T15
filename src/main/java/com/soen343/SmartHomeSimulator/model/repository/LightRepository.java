package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Light;

import java.util.List;

/**
 * The Light repository Interface.
 */
public interface LightRepository {

    /**
     * Find light by ID.
     *
     * @param id the id
     * @return the light
     */
    public Light findById(Long id);

    /**
     * Save light.
     *
     * @param light the light
     * @return the light
     */
    public Light save(Light light);

    /**
     * Find all lights.
     *
     * @return the list
     */
    public List<Light> findAll();

    /**
     * Remove light.
     *
     * @param light the light
     * @return the light
     */
    public Light remove(Light light);

    /**
     * Delete light by ID.
     *
     * @param id the id
     * @return the light
     */
    public Light deleteById(Long id);

}
