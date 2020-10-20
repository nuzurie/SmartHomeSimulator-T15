package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SimulationControllerTest {

    @MockBean
    private SimulationRepository simulationRepository;

    @Test
    public void setDateTest(){

        when(simulationRepository.findById((long) 0)).thenReturn(Simulation.builder().build());
        Simulation sim = simulationRepository.findById((long) 0);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = dateFormat.format(date);

        sim.setDate(stringDate);
        assertEquals(stringDate, sim.getDate());

    }
}
