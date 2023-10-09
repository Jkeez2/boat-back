package com.jkeez.boatback.Entity;

import jakarta.persistence.*;


/**
 * Entity representing a boat.
 */
@Entity
@Table(name = "BOAT")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boatId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ACCOUNT_ID")
    private UserAccount userAccount;

    // Getters and setters

    public Long getBoatId() {
        return boatId;
    }

    public void setBoatId(Long boatId) {
        this.boatId = boatId;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    // Constructors

    public Boat(String name, String description, UserAccount userAccount) {
        this.name = name;
        this.description = description;
        this.userAccount = userAccount;
    }

    public Boat() {

    }
}

