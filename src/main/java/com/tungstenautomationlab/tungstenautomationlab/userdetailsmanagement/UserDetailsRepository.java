package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<Users, String> {
    List<Users> findByEmail(String email);

    List<Users> getByRole(String superadmin);
}
