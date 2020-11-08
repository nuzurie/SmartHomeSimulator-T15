package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Light;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LightRepositoryImpl implements LightRepository{

    List<Light> linkedList = new LinkedList<>();

    @Override
    public Light findById(Long id) {
        for (Light light:
                this.linkedList) {
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
            linkedList.add(light);
        }
        return this.findById(light.getId());
    }

    @Override
    public List<Light> findAll() {
        return linkedList;
    }

    @Override
    public Light remove(Light light) {
        Boolean removed = this.linkedList.remove(light);
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
