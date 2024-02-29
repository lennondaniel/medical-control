package org.example.medicalcontrol.users.repositories;

import org.example.medicalcontrol.users.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersModel, UUID> {
    UsersModel findByEmail(String email);
}
