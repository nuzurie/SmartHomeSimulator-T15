package com.soen343.SmartHomeSimulator.module.heating.controller;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.module.heating.model.*;
import com.soen343.SmartHomeSimulator.module.heating.repository.HeatingRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@RestController
@RequestMapping("/api")
public class HeatingController {
    private HeatingRepository heatingRepository;

    @Autowired
    public HeatingController(HeatingRepository heatingRepository) {
        this.heatingRepository = heatingRepository;
    }

    @PostMapping("heating/zones")
    public ResponseEntity<?> defineZones(@Valid @RequestBody Heating heating){
        return ResponseEntity.ok().build();
    }

    @PostMapping("heating/zones/timeIntervals")
    public ResponseEntity<?> zoneIntervals(@Valid @RequestBody ZoneIntervals zoneIntervals){
        zoneIntervals.parseIntervals();
        try {
            zoneIntervals.checkFullDuration();
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
        }
        Heating heating = heatingRepository.findById(1);
        List<IntervalTemp> intervalTempList = zoneIntervals.getListIntervalTimes();
        List<Zone> zoneSet = heating.getZones();

        for (Zone zone: zoneSet){
            List<IntervalTemp> intervalTempList1 = new LinkedList<>();
            for (IntervalTemp intervalTemp: intervalTempList
                 ) {
                intervalTempList1.add(intervalTemp.clone());
            }
            zone.setIntervals(intervalTempList1);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/heating/zone-interval-numbers")
    public ResponseEntity<?> zoneAndIntervalNumbers(@Valid @RequestBody ZoneAndTimeNumbers zoneAndTimeNumbers){
        Heating heating = heatingRepository.findById(1);
        heating.setZoneAndTimeNumbers(zoneAndTimeNumbers);
        List<Zone> zoneSet = new LinkedList<>();
        int numberOfZones = zoneAndTimeNumbers.getNumberZones();
        for (int i = 0; i < numberOfZones; i++) {
            zoneSet.add(Zone.builder().build());
        }
        heating.setZones(zoneSet);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/heating")
    public ResponseEntity<Heating> getHeating(){
        Heating heating = heatingRepository.findById(1);
        return ResponseEntity.ok().body(heating);
    }

    @PostMapping("/heating/zone-rooms")
    public ResponseEntity<?> setZoneRoomsAndTemp(@Valid @RequestBody ZonesRooms zonesRooms){
        try {
            zonesRooms.validate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(zonesRooms);
        List<Set<Room>> setOfRooms = zonesRooms.getRoomsForZones();
        System.out.println(setOfRooms);
        Heating heating = heatingRepository.findById(1);
        List<Zone> zones = heating.getZones();
        for (int i = 0; i <zones.size() ; i++) {
            zones.get(i).setRooms(setOfRooms.get(i));
        }
        zonesRooms.configureIntervalTemps(zones);
        HVAC hvac = SpringContext.getBean(HVAC.class);
        hvac.operate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/heating/months")
    public ResponseEntity<?> getMonths(){
        Heating heating = heatingRepository.findById(1);
        return ResponseEntity.ok(heating.getMonths());
    }

    @PostMapping("/heating/months")
    public ResponseEntity<?> setMonths(@RequestBody Heating heating){
        Heating currentHeating = heatingRepository.findById(1);
        currentHeating.setSummer(heating.getSummer());
        currentHeating.setWinter(heating.getWinter());
        currentHeating.setSummerTemperature(24);
        currentHeating.setWinterTemperature(19);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/heating/hvac")
    public ResponseEntity<?> toggleHVAC(){
        Heating heating = heatingRepository.findById(1);
        heating.setHVACon(!heating.isHVACon());
        return ResponseEntity.ok().build();
    }
}
