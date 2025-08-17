package com.zagdev.devicemanager.domain.entities;

import com.zagdev.devicemanager.domain.enums.DeviceType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Device {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeviceType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    @Column(nullable = false)
    private long version;
}
