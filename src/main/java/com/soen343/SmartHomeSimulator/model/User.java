package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String id;
    private String type;
    @NonNull
    private String name;
    @NonNull
    private String username;
    private String time;
    private String weather;
    private String location;
}