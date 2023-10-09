package com.jkeez.boatback.Service;


import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Entity.Boat;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.BoatException;
import com.jkeez.boatback.Repository.BoatRepository;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BoatService's interface implementation.
 * Manages user's boats CRUD operations services.
 */
@Service
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;

    private final UserAccountService userAccountService;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.boatRepository = boatRepository;
        this.userAccountService = userAccountService;
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Fetch a boat
     * @param boatId boat id
     * @return requested boat
     */
    @Override
    public Boat findById(Long boatId) {
        return boatRepository.findById(boatId)
                .orElseThrow(() -> new BoatException("Boat not found with id : " + boatId));
    }

    /**
     * Find user's boat
     * @param userId user id
     * @param boatId boat id
     * @return user's boat
     */
    @Override
    public Boat findBoatByUser(Long userId, Long boatId) {
        return boatRepository.findByBoatIdAndUserAccount_Id(boatId, userId)
                .orElseThrow(() -> new BoatException("Boat not found with id : " + boatId + " for user with id : " + userId));
    }

    /**
     * Fetch all user's boats
     * @param userId user id
     * @return user's boats
     */
    @Override
    public List<Boat> findAllByUser(Long userId) {
        return boatRepository.findAllByUserAccount_Id(userId);
    }

    /**
     * Add boat to a user
     * @param userId user id
     * @param boatDTO boat data
     * @return added boat
     */
    @Override
    public Boat addBoatToUser(Long userId, BoatDTO boatDTO) {
        UserAccount user = this.userAccountService.findById(userId);
        Boat newBoat = new Boat();
        newBoat.setName(boatDTO.getName());
        newBoat.setDescription(boatDTO.getDescription());
        newBoat.setUserAccount(user);

        Boat savedBoat = this.boatRepository.save(newBoat);

        // updated user's boats list
        user.getBoats().add(savedBoat);

        this.userAccountRepository.save(user);

        return savedBoat;
    }

    /**
     * Delete a user's boat
     * @param userId user id
     * @param boatId boat id
     * @return uer's updated boats list
     */
    @Override
    public List<Boat> deleteBoat(Long userId, Long boatId) {
        Boat boatToDelete = this.boatRepository.findByBoatIdAndUserAccount_Id(boatId, userId)
                .orElseThrow(() -> new BoatException("Can't delete boat with id " + boatId + " for user " + userId));
        UserAccount user = boatToDelete.getUserAccount();

        // update user's boats list
        user.getBoats().remove(boatToDelete);

        this.userAccountRepository.save(user);
        this.boatRepository.delete(boatToDelete);

        return user.getBoats();
    }

    /**
     * Update a user's boat
     * @param userId user id
     * @param boatId boat id
     * @param boatDTO new boat data
     * @return updated boat
     */
    @Override
    public Boat updateBoat(Long userId, Long boatId, BoatDTO boatDTO) {
        Boat boatToUpdate = this.boatRepository.findByBoatIdAndUserAccount_Id(boatId, userId)
                .orElseThrow(() -> new BoatException("Can't update boat with id " + boatId + " for user " + userId));

        boatToUpdate.setName(boatDTO.getName());
        boatToUpdate.setDescription(boatDTO.getDescription());

        return this.boatRepository.save(boatToUpdate);
    }
}
