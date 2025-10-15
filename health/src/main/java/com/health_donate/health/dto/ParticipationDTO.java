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
    private Long acteurId;
   private  boolean status;
    private Long activiteId;

    public boolean getStatus() {
        return  status;
    }
}
