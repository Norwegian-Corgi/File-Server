package com.norwegiancorgi.fileserver.internal.repositories;

import com.norwegiancorgi.fileserver.internal.entities.Userz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<Userz, UUID> {

    Optional<Userz> findByEmail(String email);
}
