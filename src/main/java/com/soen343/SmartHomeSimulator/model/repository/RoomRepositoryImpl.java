package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.model.Room;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomRepositoryImpl implements RoomRepository {
    @Override
    public Room findById(Long id) {
        for (Room user:
                this.roomSet) {
            if (user.getId()==id)
                return user;
        }
        return null;
    }

    @Override
    public Room save(Room room) {
        Mapper mapper = new DozerBeanMapper();
        Room already_exists = findById(room.getId());
        if(already_exists!=null){
            mapper.map(room, already_exists);
        }
        else{
            roomSet.add(room);
        }
        return this.findById(room.getId());
    }

    @Override
    public List<Room> findAll() {
        return roomSet;
    }

    @Override
    public Room remove(Room room) {
        Boolean removed = this.roomSet.remove(room);
        if (removed)
            return room;
        else
            return null;
    }

    @Override
    public Room deleteById(Long id) {
        Room user = this.findById(id);
        return remove(user);
    }
}
