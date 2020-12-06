package com.soen343.SmartHomeSimulator.module.heating.model;

import com.soen343.SmartHomeSimulator.model.Room;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class Zone {
    private static int classID = 0;
    @Builder.Default
    private int id = ++ classID;
    @Builder.Default
    private Set<Room> rooms = new HashSet<>();
    @Builder.Default
    private List<IntervalTemp> intervals = new LinkedList<>();
}
