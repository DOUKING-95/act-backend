package com.health_donate.health.enumT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DonationStatus {
    UNAVAILABLE,
    EN_ATTENTE,
    DECLINE,
    AVAILABLE,
    PUBLIE,
    LIVRE,
    ANNULE;

    @JsonCreator
    public static DonationStatus fromValue(String value) {
        return DonationStatus.valueOf(value.toUpperCase());
    }
}