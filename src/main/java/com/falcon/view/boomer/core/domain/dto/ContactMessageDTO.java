package com.falcon.view.boomer.core.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageDTO {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @Digits(integer=10, fraction=0)
    @Size(min = 5, max = 20)
    private String phoneNumber;
    @NotEmpty
    @Size(max = 100)
    private String subject;
    @NotEmpty
    @Size(max = 1000)
    private String message;
}
