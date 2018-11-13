package org.ddriven.controllers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.ddriven.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @PostMapping("/sign-up")
    @RequiresRoles("admin")
    public ResponseEntity<String> signup(@RequestParam String userName,@RequestParam String password){
        if (userServiceImpl.findByUserName(userName)==null){
            userServiceImpl.saveUser(userName,password);
            return new ResponseEntity<>("User successfully registered",HttpStatus.OK);
        }else {
            return new ResponseEntity<> ("User already registered", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/editUser")
    @RequiresRoles("admin_role")
    
    public ResponseEntity<String> editUser(@RequestParam String userName,String password) throws AuthorizationException{
        
    	if (userServiceImpl.findByUserName(userName)!=null){
            userServiceImpl.updateUser(userName,password);
            return new ResponseEntity<>("successfully updated",HttpStatus.OK);
        }else {
            return new ResponseEntity<> ("enter valid username", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/deleteUser")
    @RequiresRoles("admin_role")
    
    public ResponseEntity<String> deleteUser(@RequestParam String userName) throws AuthorizationException{
        
    	if (userServiceImpl.findByUserName(userName)!=null){
            userServiceImpl.deleteUser(userName);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<> ("enter valid username", HttpStatus.FORBIDDEN);
        }
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handleException(AuthorizationException e) {
        return new ResponseEntity<> ("You need to be an admin to register a user", HttpStatus.FORBIDDEN);
    }
    @PostMapping("/login")
    public ResponseEntity<String> signin(@RequestParam String userName, @RequestParam String password, HttpSession session) {
        if(session.getAttribute("currUser")==null) {
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token
                        = new UsernamePasswordToken(userName, password);
                token.setRememberMe(true);
                try {
                    currentUser.login(token);
                    session.setAttribute("token", token);
                    session.setAttribute("currUser", currentUser);
                } catch (IncorrectCredentialsException ice) {
                    return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
                } catch (UnknownAccountException uae) {
                    return new ResponseEntity<>("Unknown Account", HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>("You have logged in successfully!", HttpStatus.OK);
        }
            return new ResponseEntity<>("You are already logged in!",HttpStatus.FORBIDDEN);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if(session.getAttribute("token")!=null) {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
            return new ResponseEntity<>("logged out successfully!", HttpStatus.OK);
        }
            return new ResponseEntity<>("log in first",HttpStatus.UNAUTHORIZED);
    }
}
