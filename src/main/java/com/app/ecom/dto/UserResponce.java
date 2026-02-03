package com.app.ecom.dto;

import com.app.ecom.entity.UserRole;
import lombok.Data;

@Data
public class UserResponce {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneno;
    private UserRole role;
    private AddressDTO address;
}
