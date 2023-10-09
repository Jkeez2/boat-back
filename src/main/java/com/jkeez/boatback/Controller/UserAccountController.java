package com.jkeez.boatback.Controller;

import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Dto.UserAccountDTO;

import com.jkeez.boatback.Service.BoatService;
import com.jkeez.boatback.Service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserAccountController manages CRUD requests for user's boats.
 */

@RestController
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;

    private final BoatService boatService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, BoatService boatService) {
        this.userAccountService = userAccountService;
        this.boatService = boatService;
    }

    /**
     * Get a user with given user id
     * @param userId user id
     * @return requested user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new UserAccountDTO(userAccountService.findById(userId)));
    }

    /**
     * Fetch boats for given user id
     * @param userId user id
     * @return user's boats
     */
    @GetMapping("/{userId}/boats")
    public ResponseEntity<?> getUserBoats(@PathVariable Long userId) {
        List<BoatDTO> boats = boatService.findAllByUser(userId).stream()
                .map(BoatDTO::new)
                .toList();
        return ResponseEntity.ok(boats);
    }

    /**
     * Get a specific user's boat
     * @param userId user id
     * @param boatId boat id to retrieve
     * @return requested boat
     */
    @GetMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> getUserBoat(@PathVariable Long userId, @PathVariable Long boatId) {
        return ResponseEntity.ok(new BoatDTO(boatService.findBoatByUser(userId, boatId)));
    }

    /**
     * Add boat to a user
     * @param userId user id
     * @param boatDto new boat
     * @return added boat
     */
    @PostMapping("/{userId}/boats")
    public ResponseEntity<?> addBoatToUser(@PathVariable Long userId, @Valid @RequestBody BoatDTO boatDto) {
        return new ResponseEntity<>(new BoatDTO(boatService.addBoatToUser(userId, boatDto)), HttpStatus.CREATED);
    }

    /**
     * Update a specific user's boat
     * @param userId user id
     * @param boatId boat's id
     * @param boatDto new boat data
     * @return updated boat
     */
    @PutMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> updateBoat(@PathVariable Long userId, @PathVariable Long boatId, @RequestBody BoatDTO boatDto) {
        return ResponseEntity.ok(new BoatDTO(boatService.updateBoat(userId, boatId, boatDto)));
    }

    /**
     * Delete request to remove a user's boat.
     * We return the user's boats list so the front-end won't request it to refresh its list.
     * @param userId user id
     * @param boatId boat id to remove
     * @return updated user's boats list
     */
    @DeleteMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> deleteBoat(@PathVariable Long userId, @PathVariable Long boatId) {
        List<BoatDTO> refreshedBoatsList = boatService.deleteBoat(userId, boatId).stream()
                .map(BoatDTO::new)
                .toList();
        return ResponseEntity.ok(refreshedBoatsList);
    }
}

