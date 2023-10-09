package com.jkeez.boatback.Dto;

import com.jkeez.boatback.Entity.UserAccount;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a UserAccount DTO for HTTP communication
 */
public class UserAccountDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<BoatDTO> boats;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(List<BoatDTO> boats) {
        this.boats = boats;
    }

    // constructors
    public UserAccountDTO(){}

    public UserAccountDTO(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
        this.boats = userAccount.getBoats().stream()
                .map(BoatDTO::new)
                .collect(Collectors.toList());
    }
}

