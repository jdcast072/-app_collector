package com.collectorwheels.app.model;

import com.collectorwheels.app.Role;

public final class UserAccount {
    public final long id;
    public final String fullName;
    public final String email;
    public final Role role;
    public final boolean active;

    public UserAccount(long id, String fullName, String email, Role role, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.active = active;
    }
}
