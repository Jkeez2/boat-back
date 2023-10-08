package com.jkeez.boatback.Dto;

import com.jkeez.boatback.Entity.Boat;

public class BoatDTO {
    private Long id;
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

    public BoatDTO(Boat boat) {
        this.id = boat.getBoatId();
        this.name = boat.getName();
        this.description = boat.getDescription();
        this.userAccountId = boat.getUserAccount().getId();
    }

    public BoatDTO() {

    }
}

