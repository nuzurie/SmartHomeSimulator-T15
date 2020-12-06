package com.soen343.SmartHomeSimulator.module.heating.model;

import com.nimbusds.jose.util.ArrayUtils;
import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.model.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The zones room numbers
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZonesRooms {

    private Long[] zone1 = {};
    private Long[] zone2 = {};
    private Long[] zone3 = {};
    private double zone1TempInterval1;
    private double zone1TempInterval2;
    private double zone1TempInterval3;
    private double zone2TempInterval1;
    private double zone2TempInterval2;
    private double zone2TempInterval3;
    private double zone3TempInterval1;
    private double zone3TempInterval2;
    private double zone3TempInterval3;

    private void initiate() {
        if (zone1 == null)
            zone1 = new Long[0];
        if (zone2 == null)
            zone2 = new Long[0];
        if (zone3 == null)
            zone3 = new Long[0];
    }

    public void validate() throws RoomsNotInZoneException, RoomInMultipleZonesException{
        initiate();

        Long[] allRoomsInZones = ArrayUtils.concat(zone1, zone2, zone3);
        Set<Long> allRoomsSet = Arrays.stream(allRoomsInZones)
                                    .collect(Collectors.toSet());

        RoomRepository roomRepository = SpringContext.getBean(RoomRepository.class);
        List<Room> allRooms = roomRepository.findAll();
        if (allRoomsInZones.length != allRoomsSet.size())
            throw new RoomInMultipleZonesException("Same room in different zones");
        else if (allRooms.size() != allRoomsSet.size())
            throw new RoomsNotInZoneException("Not all rooms in zones");
    }

    public List<Set<Room>> getRoomsForZones(){
        RoomRepository roomRepository = SpringContext.getBean(RoomRepository.class);

        Set<Room> zone1Rooms = new HashSet<>();
        Set<Room> zone2Rooms = new HashSet<>();
        Set<Room> zone3Rooms = new HashSet<>();

        RoomsFromID(roomRepository, zone1Rooms, this.zone1);
        RoomsFromID(roomRepository, zone2Rooms, this.zone2);
        RoomsFromID(roomRepository, zone3Rooms, this.zone3);

        List<Set<Room>> setsOfRooms = new LinkedList<>();
        if (zone1Rooms.size()>0)
            setsOfRooms.add(zone1Rooms);
        if (zone2Rooms.size()>0)
            setsOfRooms.add(zone2Rooms);
        if (zone3Rooms.size()>0)
            setsOfRooms.add(zone3Rooms);

        return setsOfRooms;
    }

    private void RoomsFromID(RoomRepository roomRepository, Set<Room> zoneRooms, Long[] zone) {
        for (Long id: zone
             ) {
            zoneRooms.add(roomRepository.findById(id));
        }
    }

    public void configureIntervalTemps(List<Zone> zones){
        int numberOfZones = zones.size();
        int numberOfIntervals = zones.get(0).getIntervals().size();

        Zone zone;
        if (numberOfZones>0){
            zone = zones.get(0);
            zone.getIntervals().get(0).setTemperature(zone1TempInterval1);
            if (numberOfIntervals>1)
                zone.getIntervals().get(1).setTemperature(zone1TempInterval2);
            if (numberOfIntervals>2)
                zone.getIntervals().get(2).setTemperature(zone1TempInterval3);
        }
        if (numberOfZones > 1){
            zone = zones.get(1);
            zone.getIntervals().get(0).setTemperature(zone2TempInterval1);
            if (numberOfIntervals>1)
                zone.getIntervals().get(1).setTemperature(zone2TempInterval2);
            if (numberOfIntervals>2)
                zone.getIntervals().get(2).setTemperature(zone2TempInterval3);
        }
        if (numberOfZones > 2){
            zone = zones.get(2);
            zone.getIntervals().get(0).setTemperature(zone3TempInterval1);
            if (numberOfIntervals>1)
                zone.getIntervals().get(1).setTemperature(zone3TempInterval2);
            if (numberOfIntervals>2)
                zone.getIntervals().get(2).setTemperature(zone3TempInterval3);
        }
    }
}