package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Home repository Interface.
 */
public interface HomeRepository{

    /**
     * The set of homes.
     */
    public Set<Home> homeSet = new HashSet<>();

    /**
     * Find a home by ID.
     *
     * @param id the id
     * @return the home
     */
    public Home findById(Long id);

    /**
     * Save home.
     *
     * @param home the home
     * @return the home
     */
    public Home save(Home home);

    /**
     * Find all homes.
     *
     * @return the set
     */
    public Set<Home> findAll();

    /**
     * Remove home.
     *
     * @param home the home
     * @return the home
     */
    public Home remove(Home home);

    /**
     * Delete home by id.
     *
     * @param id the id
     * @return the home
     */
    public Home deleteById(Long id);

    /**
     * Find home by name.
     *
     * @param name the name
     * @return the home
     */
    public Home findByName(String name);

    /**
     * Find all homes using the user ID.
     *
     * @param id the id
     * @return the set
     */
    Set<Home> findAllByUserId(String id);

}
//
//public interface HomeRepository extends JpaRepository<Home, Long> {
//    Home findByName(String name);
//
//    List<Home> findAllByUserId(String id);
//}
