package com.health_donate.health.mapper;

import com.health_donate.health.dto.UserDTO;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setActif(user.isActif());
        dto.setVerified(user.isVerified());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getId());
            dto.setRoleName(user.getRole().getName().name());
        }

        return dto;
    }

    public static User toEntity(UserDTO dto, Role role) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setActif(dto.isActif());
        user.setVerified(dto.isVerified());
        user.setCreatedAt(dto.getCreatedAt());
        user.setRole(role);

        return user;
    }
}
