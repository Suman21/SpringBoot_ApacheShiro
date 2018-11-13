package org.ddriven.controllers;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.ddriven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/user")
public class RoleController {
    @Autowired
    RoleService roleService;
    @PostMapping("/createRole")
    @RequiresRoles("admin")
    public ResponseEntity<String> create(@RequestParam String role){
        roleService.createRole(role);
        return new ResponseEntity<>("Role created", HttpStatus.OK);
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handleException(AuthorizationException e) {
        return new ResponseEntity<> ("user is not admin", HttpStatus.FORBIDDEN);
    }
    @PostMapping("/deleteRole")
    @RequiresRoles("admin")
    public ResponseEntity<String> delete(@RequestParam String role){
        roleService.deleteRole(role);
        return new ResponseEntity<>("Role deleted", HttpStatus.OK);
    }
}
