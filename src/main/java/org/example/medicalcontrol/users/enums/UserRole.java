package org.example.medicalcontrol.users.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    PATIENT("patient"),
    DOCTOR("doctor"),
    ADMIN("admin");
    private final String role;

    UserRole(String role){
        this.role = role;
    }

}
