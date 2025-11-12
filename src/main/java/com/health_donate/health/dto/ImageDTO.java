package com.health_donate.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;
    private LocalDateTime uploadAt;
    private Long donationId;
}
