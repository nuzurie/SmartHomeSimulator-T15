package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface HomeRepository{

    public Set<Home> homeSet = new HashSet<>();

    public Home findById(Long id);

    public Home save(Home home);

    public Set<Home> findAll();

    public Home remove(Home home);

    public Home deleteById(Long id);

    public Home findByName(String name);

    Set<Home> findAllByUserId(String id);

}
//
//public interface HomeRepository extends JpaRepository<Home, Long> {
//    Home findByName(String name);
//
//    List<Home> findAllByUserId(String id);
//}
