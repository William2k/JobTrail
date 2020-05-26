package com.jobtrail.api.models.entities;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntity {
    @NotNull(message = "Id cannot be null")
    private UUID id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean active;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public LocalDateTime getDateCreated() {return dateCreated;}
    public void  setDateCreated(LocalDateTime value) {dateCreated = value;}

    public LocalDateTime getDateModified() {return dateModified;}
    public void  setDateModified(LocalDateTime value) {dateModified = value;}

    public boolean isActive() {return active;}
    public void  setActive(boolean value) {active = value;}
}
