package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<Users, String> {
    List<Users> findByEmail(String email);

    List<Users> getByRole(String superadmin);
}
