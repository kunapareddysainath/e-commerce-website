package com.commerce.student.service;

import com.commerce.student.model.entity.Address;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.UpdateUserRequest;
import com.commerce.student.repositiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean updateUserFields(User user, UpdateUserRequest updateUserRequest) {
        boolean updated = setIfChanged(user::getName, user::setName, updateUserRequest.getName());

        if (setIfChanged(user::getUsername, user::setUsername, updateUserRequest.getUsername(),
                userRepository::findByUsername)) updated = true;
        if (setIfChanged(user::getPhoneNumber, user::setPhoneNumber, updateUserRequest.getPhoneNumber())) updated = true;
        if (setIfChanged(user::getEmailAddress, user::setEmailAddress, updateUserRequest.getEmailAddress())) updated = true;

        return updated;
    }

    public boolean updateAddressFields(Address address, UpdateUserRequest updateUserRequest) {
        boolean updated = setIfChanged(address::getAddress1, address::setAddress1, updateUserRequest.getAddress1());

        if (setIfChanged(address::getAddress2, address::setAddress2, updateUserRequest.getAddress2())) updated = true;
        if (setIfChanged(address::getCountry, address::setCountry, updateUserRequest.getCountry())) updated = true;
        if (setIfChanged(address::getState, address::setState, updateUserRequest.getState())) updated = true;
        if (setIfChanged(address::getZip, address::setZip, updateUserRequest.getZip())) updated = true;

        return updated;
    }

    private <T> boolean setIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue) {
        T currentValue = getter.get();
        if (!Objects.equals(currentValue, newValue)) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }

    private <T> boolean setIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue, Function<T, User> findExisting) {
        T currentValue = getter.get();
        if (!Objects.equals(currentValue, newValue) && findExisting.apply(newValue) == null) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }


}
