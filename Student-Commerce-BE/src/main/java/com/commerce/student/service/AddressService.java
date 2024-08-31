package com.commerce.student.service;

import com.commerce.student.model.entity.Address;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.AddressRequest;
import com.commerce.student.repositiory.AddressRepository;
import com.commerce.student.repositiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address saveUserAddress(String userId, AddressRequest addressRequest) {

        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) throw new NullPointerException();

            Address address = new Address();

            address.setAddress1(addressRequest.getAddress1());
            address.setAddress2(addressRequest.getAddress2());
            address.setCountry(addressRequest.getCountry());
            address.setState(addressRequest.getState());
            address.setZip(addressRequest.getZip());
            address.setUser(user);

            addressRepository.save(address);
            return address;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Address> fetchAddressesByUserId(String userId) {
        List<Address> addressList = addressRepository.findByUserId(userId);

        if (addressList.isEmpty()) {
            return null;
        }
        return addressList;
    }

}
