package com.tungstenautomationlab.tungstenautomationlab.supports.authentication;

import com.tungstenautomationlab.tungstenautomationlab.supports.security.Roles;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("fake")
public class AuthenticationLoginUserDao implements LoginUserDao {
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public AuthenticationLoginUserDao(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Optional<LoginUser> selectLoginUserByUsername(String username) {

        return getAllUsers().stream().filter((user) -> user.getUsername().equals(username)).findFirst();
    }

    public List<LoginUser> getAllUsers() {
        return userDetailsRepository.findAll().stream().map((user) ->
                new LoginUser(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        Roles.valueOf(user.getRole()).getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true)
        ).collect(Collectors.toList());
    }
}
