package com.example.tracker.api.register.role;

import com.example.tracker.api.register.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   // Optional<Role> findByName(UserRole name);

}
