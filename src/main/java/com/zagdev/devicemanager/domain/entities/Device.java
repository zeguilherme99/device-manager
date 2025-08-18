package com.zagdev.devicemanager.domain.entities;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.enums.DeviceType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "devices", indexes = {
    @Index(name = "idx_devices_created_at", columnList = "created_at"),
    @Index(name = "idx_devices_type", columnList = "type")
})
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
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

    protected Device() {
    }

    public Device(UUID id, String name, DeviceType type, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static Device fromDto(DeviceDto dto) {
        return new Device(
                dto.id(),
                dto.name(),
                DeviceType.from(dto.type()),
                dto.createdAt()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public long getVersion() {
        return version;
    }
}
