package com.soen343.SmartHomeSimulator.module.heating.model;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.module.heating.repository.HeatingRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Service
public class HVAC {
    private HeatingRepository heatingRepository;
    private SimulationRepository simulationRepository;
    private ArrayList<Thread> threads = new ArrayList<>();

    @Autowired
    public HVAC(HeatingRepository heatingRepository, SimulationRepository simulationRepository) {
        this.heatingRepository = heatingRepository;
        this.simulationRepository = simulationRepository;
    }

    public void operate() {
        Heating heating = heatingRepository.findById(1);
        Simulation simulation = simulationRepository.findById((long) 1);

        while (threads.size() > 0) {
            Thread thread = threads.get(0);
            threads.remove(0);
            thread.interrupt();
            System.out.println(thread.getState());
            System.out.println("interrupting " + threads.size());
            System.out.println("Is HVAC on:" + heating.isHVACon());
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                hvacON(simulation, heating);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        threads.add(thread);
    }

    private void hvacON(Simulation simulation, Heating heating) {

        List<Zone> zones = heating.getZones();

        zones.forEach(zone -> {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    controlTemp(zone);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            threads.add(thread);
        });
    }

    public void controlTemp(Zone zone) {
        double targetTemp = 25;
        for (IntervalTemp intervalTemp : zone.getIntervals()
        ) {
            if (checkInterval(intervalTemp))
                targetTemp = intervalTemp.getTemperature();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (Room room : zone.getRooms()
        ) {
            double finalTargetTemp = targetTemp;
            Runnable a = new Runnable() {
                @Override
                public void run() {
                    if (room.getTemperature() > finalTargetTemp + 0.25 || room.getTemperature() < finalTargetTemp - 0.25) {
                        System.out.println(room.getName() + room.getTemperature() + "" + finalTargetTemp);
                        if (!room.isHvacStatus())
                            changeTemp(room, finalTargetTemp, zone.getIntervals());
                    }
                }
            };
            Thread thread = new Thread(a);
            thread.start();
            threads.add(thread);

        }
    }

    private void changeTemp(Room room, double targetTemp, List<IntervalTemp> intervalTemps) {
        Simulation simulation = SpringContext.getBean(SimulationRepository.class).findById((long) 1);
        Heating heating = SpringContext.getBean(HeatingRepository.class).findById(1);
        while (true){
            double multiplier = simulation.getTimeMultiplier();
            for (IntervalTemp intervalTemp : intervalTemps
            ) {
                if (checkInterval(intervalTemp))
                    targetTemp = intervalTemp.getTemperature();
            }
            if (heating.isHVACon()) {
                while (Math.abs(room.getTemperature() - targetTemp) > 0.05) {
                    room.setHvacStatus(true);
                    if (targetTemp > room.getTemperature())
                        room.setTemperature(room.getTemperature() + 0.1);
                    else
                        room.setTemperature(room.getTemperature() - 0.1);
                    try {
                        Thread.sleep((long) (1000 / Math.ceil(multiplier)));
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                room.setHvacStatus(false);
            } else {
                while (Math.abs(room.getTemperature() - simulation.getTemperature()) > 0.05) {
                    if (simulation.getTemperature() > room.getTemperature())
                        room.setTemperature(room.getTemperature() + 0.05);
                    else
                        room.setTemperature(room.getTemperature() - 0.05);
                    try {
                        Thread.sleep((long) (1000 / Math.ceil(multiplier)));
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    public boolean checkInterval(IntervalTemp intervalTemp) {
        Simulation simulation = simulationRepository.findById((long) 1);
        LocalTime currentTime = simulation.getDateTime().toLocalTime();
        LocalTime startTime = intervalTemp.getStartTime();
        LocalTime endTime = intervalTemp.getEndTime();

        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime.minusMinutes(1)))
            return true;
        else
            return false;
    }

}
