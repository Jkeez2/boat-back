package com.jkeez.boatback.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "BOAT")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boat_id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ACCOUNT_ID")
    private UserAccount userAccount;

    // Getters and setters

    public Long getBoat_id() {
        return boat_id;
    }

    public void setBoat_id(Long boat_id) {
        this.boat_id = boat_id;
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
}

