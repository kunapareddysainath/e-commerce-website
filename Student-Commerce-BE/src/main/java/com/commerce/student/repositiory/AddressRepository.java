package com.commerce.student.repositiory;

import com.commerce.student.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,String> {
    List<Address> findByUserId(String userId);
}
