package org.ddriven.service.impl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.ddriven.models.Users;
import org.ddriven.repository.UserRepository;
import org.ddriven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    @Override
    public Users getCurrentUser() {

        Subject currentUser = SecurityUtils.getSubject();
        Users user = null;
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            user = (Users) currentUser.getPrincipal();
        }

        return user;
    }
    @Override
    public void saveUser(String username,String password){
        userRepository.save(username,password);
    }
    @Override
    public void updateUser(String username,String password){
        userRepository.update(username,password);
    }
    @Override
    public void deleteUser(String username){
    	Users user=userRepository.findByUserName(username);
    	userRepository.deleteUserRole(user.getId());
        userRepository.deleteUser(username);
    }
}
