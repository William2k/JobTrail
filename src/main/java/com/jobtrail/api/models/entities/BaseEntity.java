package com.jobtrail.api.models.entities;

import java.util.UUID;

public class BaseEntity {
    private UUID id;
    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}
}
