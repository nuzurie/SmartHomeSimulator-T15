package com.soen343.SmartHomeSimulator.model.repository;


import com.soen343.SmartHomeSimulator.model.Light;
import com.soen343.SmartHomeSimulator.model.Window;

import java.util.List;

public interface WindowRepository {

    public Window findById(Long id);

    public Window save(Window window);

    public List<Window> findAll();

    public Window remove(Window window);

    public Window deleteById(Long id);

}
