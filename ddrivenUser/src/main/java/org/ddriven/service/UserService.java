package org.ddriven.service;
import org.ddriven.models.Users;

import java.util.Optional;

public interface UserService {
    Optional<Users> findByUsername(String username);
    Users getCurrentUser();
    void saveUser(String username,String password);
    Users findByUserName(String userName);
    void updateUser(String username,String password);
    void deleteUser(String username);
}
