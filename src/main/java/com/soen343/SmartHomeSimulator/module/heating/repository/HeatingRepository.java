package com.soen343.SmartHomeSimulator.module.heating.repository;

import com.soen343.SmartHomeSimulator.module.heating.model.Heating;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@Data
@Repository
public class HeatingRepository {

    private Set<Heating> heating = new HashSet<>();

    public void save(Heating heating){
        this.heating.add(heating);
    }

    public Heating findById(int id){
        for (Heating heating: this.heating
             ) {
            if (heating.getId() == id)
                return heating;
        }
        return null;
    }
}
