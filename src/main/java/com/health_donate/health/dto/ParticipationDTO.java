package com.health_donate.health.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationDTO {
    private Long id;
    private Long actorId;
    private Long activiteId;
    private boolean status;



    public boolean getSatus() {
        return status;
    }
}
