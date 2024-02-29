package org.example.medicalcontrol.users.dtos;

import jakarta.annotation.Nonnull;
import org.example.medicalcontrol.users.enums.UserRole;

public record RegisterDto (
        @Nonnull String name,
        @Nonnull String email,
        @Nonnull String password,
        @Nonnull UserRole role
){}
