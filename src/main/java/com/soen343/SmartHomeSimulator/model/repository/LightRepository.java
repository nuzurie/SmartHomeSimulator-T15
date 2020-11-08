package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Light;

import java.util.List;

public interface LightRepository {

    public Light findById(Long id);

    public Light save(Light light);

    public List<Light> findAll();

    public Light remove(Light light);

    public Light deleteById(Long id);

}
