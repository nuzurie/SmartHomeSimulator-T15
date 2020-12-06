package com.soen343.SmartHomeSimulator.module.heating.model;

import com.soen343.SmartHomeSimulator.model.Room;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Builder
@Slf4j
@Data
@Getter
@Setter
public class Heating {
    int id;
    @Builder.Default
    private ZoneAndTimeNumbers zoneAndTimeNumbers = new ZoneAndTimeNumbers(1, 1);
    @Builder.Default
    private List<Zone> zones = new LinkedList<>();
    @Builder.Default
    private double defaultTemperature = 22;
    @Builder.Default
    private Set<Room> overriddenRooms = new HashSet<>();
    private MONTHS[] summer;
    private MONTHS[] winter;
    private double summerTemperature;
    private double winterTemperature;
    private boolean HVACon;

    private enum MONTHS {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public MONTHS[] getMonths(){
        return MONTHS.values();
    }

    public void validateMonth() throws MonthsNotInSeasonException, SameMonthInBothSeasonException {
        if (summer.length + winter.length != 12)
            throw new MonthsNotInSeasonException("Some months are not in winter and summer");
        Set<MONTHS> summerSet = Set.of(summer);
        Set<MONTHS> winterSet = Set.of(winter);
        if (!Collections.disjoint(summerSet, winterSet))
            throw new SameMonthInBothSeasonException("Some month(s) in both seasons.");
    }
}
