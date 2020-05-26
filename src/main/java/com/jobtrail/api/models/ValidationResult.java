package com.jobtrail.api.models;

import java.util.List;

public class ValidationResult {
    public ValidationResult() {}

    public ValidationResult(boolean isValid, List<String> errorMessages) {
        setValid(isValid);
        setErrorMessages(errorMessages);
    }

    private boolean valid;
    private List<String> errorMessages;

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public List<String> getErrorMessages() { return errorMessages; }
    public void setErrorMessages(List<String> errorMessages) { this.errorMessages = errorMessages; }
}
