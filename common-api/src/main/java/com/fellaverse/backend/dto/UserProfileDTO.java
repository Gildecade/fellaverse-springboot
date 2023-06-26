package com.fellaverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fellaverse.backend.bean.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Float wallet;
}