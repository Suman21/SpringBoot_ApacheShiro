package org.ddriven.repository;
import org.ddriven.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM users WHERE UPPER (username)=UPPER (?1)")
    Optional<Users> findByUsername(String username);
    @Query(nativeQuery = true,value = "SELECT * FROM users WHERE UPPER (username)=UPPER (?1)")
    Users findByUserName(String userName);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO users (username,password) VALUES (?1,?2)")
    void save(String username,String password);
    
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE users SET password=(?2) where username=(?1)")
    void update(String username,String password);
    
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE from users where username=(?1)")
    void deleteUser(String username);
    
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE from user_role where user_id=(?1)")
    void deleteUserRole(Long id);
    
}
