package com.health_donate.health.dto;

public record ApiResponse<T>(
        String code ,
        String message ,
        T data) {

}
