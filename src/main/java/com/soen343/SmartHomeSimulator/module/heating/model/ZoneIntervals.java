package com.soen343.SmartHomeSimulator.module.heating.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Data
@Getter
@Setter
public class ZoneIntervals {
    private String time1Start;
    private String time2Start;
    private String time3Start;
    private String time1End;
    private String time2End;
    private String time3End;

    private LocalTime interval1Start;
    private LocalTime interval2Start;
    private LocalTime interval3Start;
    private LocalTime interval1End;
    private LocalTime interval2End;
    private LocalTime interval3End;

    public void parseIntervals(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (!time1Start.contains("-") && !time1End.contains("-")){
            this.interval1Start = LocalTime.parse(time1Start, formatter);
            this.interval1End = LocalTime.parse(time1End, formatter);
        }
        else {
            this.interval1Start = LocalTime.parse("00:00", formatter);
            this.interval1End = LocalTime.parse("00:00", formatter);
        }
        if (!time2Start.contains("-") && !time2End.contains("-")){
            this.interval2Start = LocalTime.parse(time2Start, formatter);
            this.interval2End = LocalTime.parse(time2End, formatter);
        }
        else {
            this.interval2Start = LocalTime.parse("00:00", formatter);
            this.interval2End = LocalTime.parse("00:00", formatter);
        }
        if (!time3Start.contains("-") && !time3End.contains("-")){
            this.interval3Start = LocalTime.parse(time3Start, formatter);
            this.interval3End = LocalTime.parse(time3End, formatter);
        }
        else {
            this.interval3Start = LocalTime.parse("00:00", formatter);
            this.interval3End = LocalTime.parse("00:00", formatter);
        }
    }

    public void checkFullDuration() throws Exception{
        if (interval3End != null){
            Duration zone3 = Duration.between(interval3Start, interval3End);
            Duration zone2 = Duration.between(interval2Start, interval2End);
            Duration zone1 = Duration.between(interval1Start, interval1End);
            Duration total = zone1.plus(zone2).plus(zone3);
            if (total.toMinutes() != 0)
                throw new Exception("Not full 24 hours");
        }
        else if (interval2End != null) {
            Duration zone1 = Duration.between(interval1Start, interval1End);
            Duration zone2 = Duration.between(interval2Start, interval2End);
            Duration total = zone1.plus(zone2);
            if (total.toMinutes() != 0)
                throw new Exception("Not full 24 hours");
        }
        else {
            Duration zone1 = Duration.between(interval1Start, interval1End);
            if (zone1.toMinutes() != 0)
                throw new Exception("Not full 24 hours");
        }
    }

    public List<IntervalTemp> getListIntervalTimes(){
        List<IntervalTemp> intervalTempList = new LinkedList<>();
        if (!time3End.contains("-")) {
            IntervalTemp intervalTemp1 = IntervalTemp.builder().startTime(interval1Start).endTime(interval1End).build();
            IntervalTemp intervalTemp2 = IntervalTemp.builder().startTime(interval2Start).endTime(interval2End).build();
            IntervalTemp intervalTemp3 = IntervalTemp.builder().startTime(interval3Start).endTime(interval3End).build();

            intervalTempList.add(intervalTemp1);
            intervalTempList.add(intervalTemp2);
            intervalTempList.add(intervalTemp3);
            return intervalTempList;
        }
        else if (!time2End.contains("-")) {
            IntervalTemp intervalTemp1 = IntervalTemp.builder().startTime(interval1Start).endTime(interval1End).build();
            IntervalTemp intervalTemp2 = IntervalTemp.builder().startTime(interval2Start).endTime(interval2End).build();

            intervalTempList.add(intervalTemp1);
            intervalTempList.add(intervalTemp2);
            return intervalTempList;
        }
        else if (!time1End.contains("-")) {
            IntervalTemp intervalTemp1 = IntervalTemp.builder().startTime(interval1Start).endTime(interval1End).build();
            intervalTempList.add(intervalTemp1);
            return intervalTempList;
        }
        else
            return intervalTempList;
    }

}
