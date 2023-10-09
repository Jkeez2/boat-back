package com.jkeez.boatback.Service;


import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Entity.Boat;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.BoatException;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.BoatRepository;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Boat findById(Long boatId) {
        return boatRepository.findById(boatId)
                .orElseThrow(() -> new BoatException("Boat not found with id : " + boatId));
    }

    @Override
    public Boat findBoatByUser(Long userId, Long boatId) {
        return boatRepository.findByBoatIdAndUserAccount_Id(boatId, userId)
                .orElseThrow(() -> new BoatException("Boat not found with id : " + boatId + " for user with id : " + userId));
    }

    @Override
    public List<Boat> findAllByUser(Long userId) {
        return boatRepository.findAllByUserAccount_Id(userId);
    }

    @Override
    public Boat addBoatToUser(Long userId, BoatDTO boatDTO) {
        UserAccount user = this.userAccountService.findById(userId);
        Boat newBoat = new Boat();
        newBoat.setName(boatDTO.getName());
        newBoat.setDescription(boatDTO.getDescription());
        newBoat.setUserAccount(user);

        Boat savedBoat = this.boatRepository.save(newBoat);

        user.getBoats().add(savedBoat);

        this.userAccountRepository.save(user);

        return savedBoat;
    }

    @Override
    public List<Boat> deleteBoat(Long userId, Long boatId) {
        Boat boatToDelete = this.boatRepository.findById(boatId)
                .orElseThrow(() -> new BoatException("Can't delete boat with id " + boatId + " : boat not found"));
        UserAccount user = boatToDelete.getUserAccount();
        user.getBoats().remove(boatToDelete);

        this.userAccountRepository.save(user);
        this.boatRepository.delete(boatToDelete);

        return user.getBoats();
    }

    @Override
    public Boat updateBoat(Long userId, Long boatId, BoatDTO boatDTO) {
        Boat boatToUpdate = this.boatRepository.findByBoatIdAndUserAccount_Id(boatId, userId)
                .orElseThrow(() -> new BoatException("Can't update boat with id " + boatId + " for user " + boatId));

        boatToUpdate.setName(boatDTO.getName());
        boatToUpdate.setDescription(boatDTO.getDescription());

        return this.boatRepository.save(boatToUpdate);
    }
}
