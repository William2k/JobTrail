package com.jobtrail.api.models.entities;

import java.util.UUID;

public class ClientEntity extends BaseEntity {
    private String name;
    private String description;
    private long zoneId;
    private UUID clientManagerId;

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public UUID getClientManagerId() {return clientManagerId;}
    public void setClientManagerId(UUID value) {clientManagerId = value;}

    public long getZoneId() { return zoneId; }
    public void setZone(long value) {zoneId = value;}
}
