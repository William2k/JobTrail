package com.jobtrail.api.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntity {
    private UUID id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean isActive;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public LocalDateTime getDateCreated() {return  dateCreated;}
    public void  setDateCreated(LocalDateTime value) {dateCreated = value;}

    public LocalDateTime getDateModified() {return  dateModified;}
    public void  setDateModified(LocalDateTime value) {dateModified = value;}

    public boolean getIsActive() {return  isActive;}
    public void  setIsActive(boolean value) {isActive = value;}
}
