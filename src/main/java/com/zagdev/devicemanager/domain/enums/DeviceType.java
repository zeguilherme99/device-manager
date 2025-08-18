package com.zagdev.devicemanager.domain.enums;

public enum DeviceType {
    SMARTPHONE, MODEM, ROUTER, IOT, TABLET, OTHER;

    public static DeviceType from(String v) {
        try {
            return DeviceType.valueOf(v.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }

    public String toValue() {
        return name().toLowerCase();
    }
}