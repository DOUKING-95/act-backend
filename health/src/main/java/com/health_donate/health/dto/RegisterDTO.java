package com.health_donate.health.dto;

import com.health_donate.health.entity.Actor;

public record RegisterDTO(String name, String firstname, String phone, String password, String confirmPassword, String email) {

    public static Actor toEntity(RegisterDTO dto) {
        if (dto == null) return null;

        Actor actor = new Actor();

        actor.setName(dto.name);
        actor.setFirstname(dto.firstname);
        actor.setPhoneNumber(dto.phone);
        actor.setPassword(dto.password());
        actor.setEmail(dto.email);


        return actor;
    }
}
