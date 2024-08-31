package com.commerce.student.controller;

import com.commerce.student.model.entity.Address;
import com.commerce.student.model.request.AddressRequest;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/getUserAddress")
    public ResponseEntity<?> getUserAddresses() {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");
        }

        List<Address> addressList = addressService.fetchAddressesByUserId(userPrincipal.getId());
        if (addressList == null) return ResponseEntity.ok("Addresses is not found for the user");

        return ResponseEntity.ok(addressList);
    }

    @PostMapping("/saveAddress")
    public ResponseEntity<?> saveAddress(@RequestBody @Valid AddressRequest addressRequest){

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null)  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");

        if (addressRequest==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Provide Address Object");

        return ResponseEntity.ok(addressService.saveUserAddress(userPrincipal.getId(),addressRequest));
    }

}
