package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Home;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HomeRepositoryImpl implements HomeRepository{

    @Override
    public Home findById(Long id) {
        for (Home home:
                this.homeSet) {
            if (home.getId()==id)
                return home;
        }
        return null;
    }

    @Override
    public Home save(Home home) {
        Home already_exists = findById(home.getId());
        remove(already_exists);
        this.homeSet.add(home);
        return this.findById(home.getId());
    }

    @Override
    public Set<Home> findAll() {
        return homeSet;
    }

    @Override
    public Home remove(Home Home) {
        Boolean removed = this.homeSet.remove(Home);
        if (removed)
            return Home;
        else
            return null;
    }

    @Override
    public Home deleteById(Long id) {
        Home user = this.findById(id);
        return remove(user);
    }

    @Override
    public Home findByName(String name) {
        for (Home home:
                this.homeSet) {
            if (home.getName()==name)
                return home;
        }
        return null;
    }

    @Override
    public Set<Home> findAllByUserId(String id) {
        return null;
    }
}