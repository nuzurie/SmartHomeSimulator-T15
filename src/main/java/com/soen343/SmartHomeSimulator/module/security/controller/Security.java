package com.soen343.SmartHomeSimulator.module.security.controller;

import com.soen343.SmartHomeSimulator.model.Door;
import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.Observer;
import com.soen343.SmartHomeSimulator.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The Security object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
public class Security implements Observer {

    private String name;

    @Override
    public int update(List<Room> rooms) {
        for (Room room: rooms
             ) {
            if (!room.getSimulationUsers().isEmpty())
                return -1;
        }
        return 0;
    }

    /**
     * Away mode protocol array list.
     *
     * @param home the home
     * @return the array list
     */
    public ArrayList<Long> awayModeProtocol(Home home){
        home.getBackyardDoor().setOpen(false);
        home.getBackyardDoor().setLocked(true);

        home.getEntranceDoor().setOpen(false);
        home.getEntranceDoor().setLocked(true);

        ArrayList<Long> blockedWindows = new ArrayList<>();

        home.getRooms().forEach(room -> {
            room.getDoors().forEach(door -> {
                door.lockDoor();
                door.closeDoor();
            });

            room.getWindows().forEach(window -> {
                if (!window.isBlocked())
                    window.closeWindow();
                else
                    blockedWindows.add(window.getId());
            });
        });

        return blockedWindows;
    }

}
