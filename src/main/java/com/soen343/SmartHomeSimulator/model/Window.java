package com.soen343.SmartHomeSimulator.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Window implements setName{

    private static long classId = 0;
    @Builder.Default
    @EqualsAndHashCode.Include
    private long id = ++classId;
    private boolean open;
    private boolean blocked;
    private String name;

    public void blockWindow(){
        this.blocked = true;
    }
    public void unBlockWindow(){
        this.blocked = false;
    }

    public void openWindow(){
        this.open = true;
    }
    public void closeWindow(){
        this.open = false;
    }

}
