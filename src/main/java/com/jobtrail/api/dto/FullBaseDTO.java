package com.jobtrail.api.dto;

import java.time.LocalDateTime;

public class FullBaseDTO {
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean active;

    public LocalDateTime getDateCreated() {return dateCreated;}
    public void  setDateCreated(LocalDateTime value) {dateCreated = value;}

    public LocalDateTime getDateModified() {return dateModified;}
    public void  setDateModified(LocalDateTime value) {dateModified = value;}

    public boolean isActive() {return active;}
    public void  setActive(boolean value) {active = value;}
}
