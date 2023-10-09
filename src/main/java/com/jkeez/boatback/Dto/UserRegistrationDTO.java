package com.jkeez.boatback.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Represents a new user account to add, when user registers its data
 */
public class UserRegistrationDTO {
    @NotBlank(message = "Firstname is required.")
    private String firstName;
    @NotBlank(message = "Lastname is required.")
    private String lastName;
    @NotBlank(message = "Email is required.")
    @Email(message = "Must be a valid email.")
    private String email;
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "Password needs to be at least six characters, one uppercase letter and one number")
    private String password;

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

