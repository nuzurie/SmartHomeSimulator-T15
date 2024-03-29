package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.*;
import com.soen343.SmartHomeSimulator.model.repository.*;
import com.soen343.SmartHomeSimulator.module.heating.model.Heating;
import com.soen343.SmartHomeSimulator.module.heating.model.Zone;
import com.soen343.SmartHomeSimulator.module.heating.repository.HeatingRepository;
import com.soen343.SmartHomeSimulator.module.security.controller.Security;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Initializes the entire Application.
 */
@Component
class Initializer implements CommandLineRunner {

    private final HomeRepository repository;
    private final SimulationUserRepository simulationUserRepository;
    private final SimulationRepository simulationRepository;
    private final RepositoryService repositoryService;
    private final RoomRepository roomRepository;

    /**
     * Instantiates a new Initializer.
     *
     * @param repository               the repository
     * @param simulationUserRepository the simulation user repository
     * @param simulationRepository     the simulation repository
     * @param repositoryService        the repository service
     * @param roomRepository           the room repository
     */
    @Autowired
    public Initializer(HomeRepository repository, SimulationUserRepositoryImpl simulationUserRepository, SimulationRepository simulationRepository, RepositoryService repositoryService, RoomRepository roomRepository) {
        this.repository = repository;
        this.simulationUserRepository = simulationUserRepository;
        this.simulationRepository = simulationRepository;
        this.repositoryService = repositoryService;
        this.roomRepository = roomRepository;
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
        SimulationUser user4 = SimulationUser.builder().name("User4").privilege("Guest").id(++SimulationUser.classId).build();
        simulationUserRepository.save(user1);
        simulationUserRepository.save(user2);
        simulationUserRepository.save(user3);
        simulationUserRepository.save(user4);

        List<SimulationUser> simulationUsers = new LinkedList<>();


        List<Window> windows2 = new LinkedList<>();
        windows2.add(Window.builder().open(false).blocked(false).build());

        List<Door> door2 = new LinkedList<>();
        door2.add(Door.builder().open(true).locked(false).build());

        Room livingRoom2 = Room.builder().name("Bed Room 1")
                .size("12x18")
                .simulationUsers(simulationUsers)
                .build();

        livingRoom2.add(Light.builder().turnedOn(false).build());
        windows2.forEach(livingRoom2::add);
        door2.forEach(livingRoom2::add);

        //Third room

        List<Door> door3 = new LinkedList<>();
        door3.add(Door.builder().open(false).locked(false).build());

        Room room3 = Room.builder().name("Bed Room 2")
                .size("12x18")
                .build();

        room3.add(Light.builder().turnedOn(true).build());
        room3.add(Window.builder().open(false).blocked(false).build());
        room3.add(Window.builder().open(true).blocked(false).build());
        door3.forEach(room3::add);

        //Fourth Room
        Room room4 = Room.builder().name("Garage")
                .size("12x18")
                .build();

        room4.add(Door.builder().open(false).locked(false).build());
        room4.add(Window.builder().open(false).blocked(false).build());
        room4.add(Light.builder().turnedOn(false).build());
        room4.add(Door.builder().open(false).locked(false).build());

        Room room5 = Room.builder().name("Guest Room")
                .size("12x18")
                .build();

        room5.add(Door.builder().open(false).locked(false).build());
        room5.add(Window.builder().open(false).blocked(false).build());
        room5.add(Light.builder().turnedOn(false).build());
        room5.add(Door.builder().open(false).locked(false).build());

        Room room6 = Room.builder().name("Study Room")
                .size("12x18")
                .build();

        room6.add(Door.builder().open(false).locked(false).build());
        room6.add(Window.builder().open(false).blocked(false).build());
        room6.add(Light.builder().turnedOn(false).build());
        room6.add(Door.builder().open(false).locked(false).build());

        List<Room> rooms = new LinkedList<>();
        rooms.add(livingRoom);
        rooms.add(livingRoom2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);

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
                .lightsAutoMode(true)
                .observer(Security.builder().name("1").build())
                .timeMultiplier(10)
                .build();

        System.out.println(simulationRepository.save(simulation));
        this.repositoryService.saveLights();
        this.repositoryService.saveWindows();
        this.repositoryService.saveDoors();

        rooms.forEach(roomRepository::save);

        Heating heating = Heating.builder().id(1).build();
        Zone zone = Zone.builder().rooms(Set.copyOf(rooms)).build();
        heating.setZones(List.of(zone));
        HeatingRepository heatingRepository = SpringContext.getBean(HeatingRepository.class);
        heatingRepository.save(heating);

        simulation.setTimeMultiplier(100);
        simulation.increaseTime();
    }
}
