package com.commerce.student.repositiory;

import com.commerce.student.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Query("select u from User u WHERE id =:id ")
    public User findByUserId(String id);

    @Query("Select u from User u WHERE username =:username")
    User findByUsername(String username);
    @Query(value = "Select * from User WHERE role_id !=:roleId",nativeQuery = true)
    List<User> findUsersWithRoleNotUser(String roleId);
}
