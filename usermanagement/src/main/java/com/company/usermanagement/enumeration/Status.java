package com.company.usermanagement.enumeration;

public enum Status {
    UNVERIFIED(""),
    VERIFIED(""),
    PENDING_VERIFICATION("PENDING VERIFICATION");

    private final String status;

    Status(String status){
        this.status= status;
    }

    public String getStatus() {
        return this.status;
    }
}
