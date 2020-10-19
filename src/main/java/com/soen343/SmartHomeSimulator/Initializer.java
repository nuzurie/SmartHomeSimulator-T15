package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepositoryImpl;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final HomeRepository repository;
    @Autowired
    private final SimulationUserRepository simulationUserRepository;
    private final SimulationRepository simulationRepository;

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

        Set<Room.DoorWindow> windows = new HashSet<>();
        windows.add(Room.DoorWindow.builder().open(false).blocked(false).build());
        windows.add(Room.DoorWindow.builder().open(true).blocked(false).build());

        Set<Room.DoorWindow> door = new HashSet<>();
        door.add(Room.DoorWindow.builder().open(false).blocked(false).build());

        Set<Room.Lights> lights = new HashSet<>();
        lights.add(Room.Lights.builder().turnedOn(true).build());

        Home mainHome = Home.builder().name("Main Home").build();
        mainHome.setId();

        Room livingRoom = Room.builder().name("Living Room")
                .size("12x24")
                .window(windows)
                .door(door)
                .lights(lights)
                .build();


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

        Set<SimulationUser> simulationUsers = new HashSet<>();
        simulationUsers.add(user1);
        simulationUsers.add(user2);

        Set<Room.DoorWindow> windows2 = new HashSet<>();
        windows2.add(Room.DoorWindow.builder().open(false).blocked(false).build());

        Set<Room.DoorWindow> door2 = new HashSet<>();
        door2.add(Room.DoorWindow.builder().open(true).blocked(false).build());

        Room livingRoom2 = Room.builder().name("Bed Room")
                .size("12x18")
                .window(windows2)
                .door(door2)
                .simulationUsers(simulationUsers)
//                .lights(lights)
                .build();

        Set<Room> rooms = new HashSet<>();
        rooms.add(livingRoom);
        rooms.add(livingRoom2);
        mainHome.setRooms(rooms);

        repository.save(mainHome);

        repository.findAll().forEach(System.out::println);
        simulationUserRepository.findAll().forEach(System.out::println);


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
