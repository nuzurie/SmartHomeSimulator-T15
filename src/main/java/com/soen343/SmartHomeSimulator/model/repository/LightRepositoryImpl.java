package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Light;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * The Implementation of the Light repository.
 */
@Service
public class LightRepositoryImpl implements LightRepository{

    /**
     * The Lights list.
     */
    List<Light> lightsList = new LinkedList<>();

    @Override
    public Light findById(Long id) {
        for (Light light:
                this.lightsList) {
            if (light.getId()==id)
                return light;
        }
        return null;
    }

    @Override
    public Light save(Light light) {
        Light already_exists = findById(light.getId());
        if (already_exists!=null){
            already_exists.setTurnedOn(light.isTurnedOn());
        }
        else{
            lightsList.add(light);
        }
        return this.findById(light.getId());
    }

    @Override
    public List<Light> findAll() {
        return lightsList;
    }

    @Override
    public Light remove(Light light) {
        Boolean removed = this.lightsList.remove(light);
        if (removed)
            return light;
        else
            return null;
    }

    @Override
    public Light deleteById(Long id) {
        Light light = this.findById(id);
        return remove(light);
    }
}
