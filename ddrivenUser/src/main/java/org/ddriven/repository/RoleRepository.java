package org.ddriven.repository;
import org.ddriven.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO ROLE (name) VALUES (?1)")
    void save(String name);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM ROLE WHERE name =?1")
    void deleteRole(String role);
    @Query(nativeQuery = true,value="SELECT id FROM ROLE WHERE UPPER(name) = UPPER (?1)")
    Long findRoleId(String name);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM ROLE_PERMISSION WHERE role_id =?1")
    void deleteRolePermission(Long id);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM USER_ROLE WHERE role_id =?1")
    void deleteRoleUser(Long id);
}
