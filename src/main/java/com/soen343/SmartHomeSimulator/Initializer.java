package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.model.*;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepositoryImpl;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class Initializer implements CommandLineRunner {

    private final HomeRepository repository;
    private final SimulationUserRepository simulationUserRepository;
    private final SimulationRepository simulationRepository;

    @Autowired
    public Initializer(HomeRepository repository, SimulationUserRepositoryImpl simulationUserRepository, SimulationRepository simulationRepository) {
        this.repository = repository;
        this.simulationUserRepository = simulationUserRepository;
        this.simulationRepository = simulationRepository;
    }

    @Override
    public void run(String... strings) {
//        Stream.of("Main Home", "Summer House").forEach(name ->
//                repository.save(new Home(name))
//        );

        List<Window> windows = new LinkedList<>();
        windows.add(new Window());
        Window w = Window.builder().build();
        w.setBlocked(true);
        windows.add(w);
//        windows.add(new Door());

//        Set<DoorWindow> door = new HashSet<>();
//        door.

        //Set<Room.Lights> lights = new HashSet<>();


        Home mainHome = Home.builder().name("Main Home").build();
        mainHome.setId();

        Room livingRoom = Room.builder().name("Living Room")
                .size("12x24")
                .build();

        livingRoom.add(w);
        livingRoom.add(Light.builder().turnedOn(true).build());
        livingRoom.add(Door.builder().open(false).locked(false).build());
        livingRoom.add(Door.builder().open(false).locked(false).build());



        SimulationUser user1 = SimulationUser.builder().name("User1").privilege("Parent").id(++SimulationUser.classId).build();
        SimulationUser user2 = SimulationUser.builder().name("User2").privilege("Child").id(++SimulationUser.classId).build();
        SimulationUser user3 = SimulationUser.builder().name("User3").privilege("Child").id(++SimulationUser.classId).build();
        SimulationUser user4 = SimulationUser.builder().name("Squatter").privilege("Stranger").id(++SimulationUser.classId).build();
        SimulationUser user5 = SimulationUser.builder().name("Squatter2").privilege("Stranger").id(user4.getId()).build();
        simulationUserRepository.save(user1);
        simulationUserRepository.save(user2);
        simulationUserRepository.save(user3);
        simulationUserRepository.save(user4);
        simulationUserRepository.save(user5);

        List<SimulationUser> simulationUsers = new LinkedList<>();


        List<Window> windows2 = new LinkedList<>();
        windows2.add(Window.builder().open(false).blocked(false).build());

        List<Door> door2 = new LinkedList<>();
        door2.add(Door.builder().open(true).locked(false).build());

        Room livingRoom2 = Room.builder().name("Bed Room")
                .size("12x18")
                .windows(windows2)
                .doors(door2)
                .simulationUsers(simulationUsers)
                .build();

        livingRoom2.add(Light.builder().turnedOn(false).build());

        //Third room

        List<Door> door3 = new LinkedList<>();
        door3.add(Door.builder().open(false).locked(false).build());

        Room room3 = Room.builder().name("Bed Room")
                .size("12x18")
                .doors(door3)
                .build();

        room3.add(Light.builder().turnedOn(true).build());
        room3.add(Window.builder().open(false).blocked(false).build());
        room3.add(Window.builder().open(true).blocked(false).build());

        //Fourth Room
        Room room4 = Room.builder().name("Garage")
                .size("12x18")
                .build();

        room4.add(Door.builder().open(false).locked(false).build());
        room4.add(Window.builder().open(false).blocked(false).build());
        room4.add(Light.builder().turnedOn(false).build());
        room4.add(Door.builder().open(false).locked(false).build());

        List<Room> rooms = new LinkedList<>();
        rooms.add(livingRoom);
        rooms.add(livingRoom2);
        rooms.add(room3);
        rooms.add(room4);

        mainHome.setRooms(rooms);

        repository.save(mainHome);

        Set<SimulationUser> simulationUsers2 = new HashSet<>(simulationUserRepository.findAll());

        System.out.println(simulationUsers2);
        Simulation simulation = Simulation.builder()
                .name(1)
                .date("2020-01-01")
                .home(mainHome)
                .time("03:00")
                .temperature(22.5)
                .simulationUsers(simulationUsers2)
                .build();

        System.out.println(simulationRepository.save(simulation));
    }
}
