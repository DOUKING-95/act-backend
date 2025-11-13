package com.health_donate.health.enumT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DonationStatus {

    EN_ATTENTE,
    DECLINE,

    PUBLIE,
    LIVRE,
    ANNULE;

    @JsonCreator
    public static DonationStatus fromValue(String value) {
        return DonationStatus.valueOf(value.toUpperCase());
    }
}