package com.soen343.SmartHomeSimulator.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeRepository extends JpaRepository<Home, Long> {
    Home findByName(String name);
}
