package com.soen343.SmartHomeSimulator.module.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Away Mode Lights lights.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AwayModeLights {

    private long[] checked;
}
