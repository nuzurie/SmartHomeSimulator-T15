package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The User object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class User {

//    @Id
    /**
     * The Id.
     */
    private String id;
    /**
     * The user's name.
     */
    private String name;
}