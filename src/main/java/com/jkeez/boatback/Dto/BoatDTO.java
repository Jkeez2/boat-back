package com.jkeez.boatback.Dto;

import com.jkeez.boatback.Entity.Boat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a boat DTO for HTTP communications
 */
public class BoatDTO {

    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Long userAccountId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    // constructors

    public BoatDTO(Boat boat) {
        this.id = boat.getBoatId();
        this.name = boat.getName();
        this.description = boat.getDescription();
        this.userAccountId = boat.getUserAccount().getId();
    }

    public BoatDTO() {

    }
}

