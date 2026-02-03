package com.app.ecom.service;


import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponce;
import com.app.ecom.entity.Address;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private List<User> userList= new ArrayList<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponce> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapUserToUserResponce)
                .toList();
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromUserRequest(user,userRequest);
        userRepository.save(user);
        return;
    }

    public Optional<UserResponce> fetchUserById(Long id){
        return userRepository.findById(id).map(this::mapUserToUserResponce);
    }

    public boolean updateUserById(Long id,User updatedUser){
        return userRepository.findById(id)
                .map((existingUser) -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void updateUserFromUserRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneno(userRequest.getPhoneno());
        if(userRequest.getAddress()!=null){
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }
    }

    private UserResponce mapUserToUserResponce(User user){
        UserResponce res=new UserResponce();
        res.setId(String.valueOf(user.getId()));
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setPhoneno(user.getPhoneno());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        if(user.getAddress()!=null){
            AddressDTO address = new AddressDTO();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZipCode(user.getAddress().getZipCode());
            res.setAddress(address);
        }

        return res;
    }
}
