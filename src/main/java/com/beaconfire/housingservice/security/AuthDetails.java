package com.beaconfire.housingservice.security;

public class AuthDetails {
    private final Long userId;
    private final String role;
    private final String username;
    private final Object webDetails;

    public AuthDetails(Long userId, String role, String username, Object webDetails) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.webDetails = webDetails;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public Object getWebDetails() {
        return webDetails;
    }
}

