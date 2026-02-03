package com.app.ecom.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneno;
    private AddressDTO address;
}
