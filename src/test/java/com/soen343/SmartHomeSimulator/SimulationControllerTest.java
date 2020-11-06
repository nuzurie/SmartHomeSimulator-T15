//package com.soen343.SmartHomeSimulator;
//
//import com.soen343.SmartHomeSimulator.model.Home;
//import com.soen343.SmartHomeSimulator.model.Room;
//import com.soen343.SmartHomeSimulator.model.SimulationUser;
//import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
//import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
//import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
//import org.assertj.core.api.Assert;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.net.http.HttpResponse;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static org.mockito.Mockito.when;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//public class SimulationControllerTest {
//
//    @MockBean
//    private SimulationRepository simulationRepository;
//    @MockBean
//    private HomeRepository homeRepository;
//
//    private MockMvc mockMvc;
//
//
//    /**
//     * @useCaseId 2.5
//     */
//    @Test
//    public void setDateAndTimeTest(){
//
//        when(simulationRepository.findById((long) 0)).thenReturn(Simulation.builder().build());
//
//        Simulation sim = simulationRepository.findById((long) 0);
//
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String stringDate = dateFormat.format(date);
//
//        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//        String stringTime = timeFormat.format(date);
//
//        sim.setDate(stringDate);
//        sim.setTime(stringTime);
//
//        assertEquals(stringDate, sim.getDate());
//        assertEquals(stringTime, sim.getTime());
//
//    }
//
//    /**
//     * @useCaseId 2.9
//     */
//    @Test
//    public void modifyDateAndTimeTest(){
//
//        when(simulationRepository.findById((long) 0)).thenReturn(Simulation.builder().date("01-02-2020").time("13:00").build());
//
//        Simulation sim = simulationRepository.findById((long) 0);
//
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String stringDate = dateFormat.format(date);
//
//        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//        String stringTime = timeFormat.format(date);
//
//        sim.setDate(stringDate);
//        sim.setTime(stringTime);
//
//        assertEquals(stringDate, sim.getDate());
//        assertEquals(stringTime, sim.getTime());
//
//    }
//
//    /**
//     * @useCaseId 10
//     */
//    @Test
//    public void moveLoggedUser(){
//        Set<SimulationUser> simulationUsers = new HashSet<SimulationUser>();
//
//        SimulationUser user1 = SimulationUser.builder().id((long)10).name( "user1").privilege("parent").build();
//
//        simulationUsers.add(user1);
//        Room livingRoom = Room.builder().name("Living room").build();
//        Home home = Home.builder().build();
//
//        home.getRooms().add(livingRoom);
//
//
//        when(simulationRepository.findById((long) 0)).thenReturn(Simulation.builder().build());
//
//        Simulation sim = simulationRepository.findById((long) 0);
//        sim.setSimulationUsers(simulationUsers);
//        sim.setHome(home);
//        sim.setLoggedInUser(user1);
//
//        //Moving the user from to a specific room
//        sim.getSimulationUsers().remove(user1);
//
//        Room selectedRoom = sim.getHome().getRooms().stream()
//                .filter(room -> room.getName().equals("Living room"))
//                .findFirst().get();
//
//        selectedRoom.getSimulationUsers().add(user1);
//
//        assertEquals(sim.getSimulationUsers().size(), 0);
//        assertEquals(selectedRoom.getSimulationUsers().size(), 1);
//
//    }
//
//    /**
//     * @useCaseId 2.11
//     */
//    @Test
//    public void placePeopleInOtherRoom(){
//        Set<SimulationUser> simulationUsers = new HashSet<SimulationUser>();
//
//        SimulationUser user1 = SimulationUser.builder().id((long)10).name( "user1").privilege("parent").build();
//
//        simulationUsers.add(user1);
//        Room livingRoom = Room.builder().name("Living room").build();
//        Home home = Home.builder().build();
//
//        home.getRooms().add(livingRoom);
//
//
//        when(simulationRepository.findById((long) 0)).thenReturn(Simulation.builder().build());
//
//        Simulation sim = simulationRepository.findById((long) 0);
//        sim.setSimulationUsers(simulationUsers);
//        sim.setHome(home);
//
//
//        //Moving the user from to a specific room
//        sim.getSimulationUsers().remove(user1);
//
//        Room selectedRoom = sim.getHome().getRooms().stream()
//                .filter(room -> room.getName().equals("Living room"))
//                .findFirst().get();
//
//        selectedRoom.getSimulationUsers().add(user1);
//
//        assertEquals(sim.getSimulationUsers().size(), 0);
//        assertEquals(selectedRoom.getSimulationUsers().size(), 1);
//
//    }
//
//    /**
//     * @useCaseId 2.13
//     */
////    @Test
////    public void blockWindowMovement(){
////
////        when(homeRepository.findById((long) 0)).thenReturn(Home.builder().build());
////
////        Home home = homeRepository.findById((long) 0);
////        Room bedRoom = Room.builder().name("bedroom").build();
////        Room.DoorWindow window = Room.DoorWindow.builder().open(true).blocked(false).build();
////
////        bedRoom.getWindow().add(window);
////        home.getRooms().add(bedRoom);
////
////        //Blocking the window
////        window.setBlocked(true);
////        Room selectedRoom = home.getRooms().stream()
////                .filter(room -> room.getName().equals("bedroom"))
////                .findFirst().get();
////
////        Room.DoorWindow selectedWindow = selectedRoom.getWindow().stream()
////                .filter(w -> w.getId() == window.getId()).findFirst().get();
////
////        assertEquals(selectedWindow.getBlocked(), true);
////
////    }
//}
