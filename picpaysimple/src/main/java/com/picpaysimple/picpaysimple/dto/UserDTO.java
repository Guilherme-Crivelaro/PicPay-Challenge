package com.picpaysimple.picpaysimple.dto;

import com.picpaysimple.picpaysimple.domain.user.UserType;

import java.math.BigDecimal;


public record UserDTO(String firstName,
                      String lastName,
                      String document,
                      String email,
                      String password,
                      BigDecimal balance,
                      UserType userType) {
}
