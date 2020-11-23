package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Window;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * The Implementation of the Window Repository.
 */
@Repository
public class WindowRepositoryImpl implements WindowRepository{

    /**
     * The List of Windows.
     */
    List<Window> windowList = new LinkedList<>();

    @Override
    public Window findById(Long id) {
        for (Window window:
                this.windowList) {
            if (window.getId()==id)
                return window;
        }
        return null;
    }

    @Override
    public Window save(Window window) {
        Window already_exists = findById(window.getId());
        if (already_exists!=null){
            already_exists.setBlocked(window.isBlocked());
            already_exists.setOpen(window.isOpen());
        }
        else{
            windowList.add(window);
        }
        return this.findById(window.getId());
    }

    @Override
    public List<Window> findAll() {
        return windowList;
    }

    @Override
    public Window remove(Window window) {
        Boolean removed = this.windowList.remove(window);
        if (removed)
            return window;
        else
            return null;
    }

    @Override
    public Window deleteById(Long id) {
        Window window = this.findById(id);
        return remove(window);
    }
}
