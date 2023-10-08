package com.jkeez.boatback.Controller;

import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Dto.UserAccountDTO;

import com.jkeez.boatback.Service.BoatService;
import com.jkeez.boatback.Service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new UserAccountDTO(userAccountService.findById(userId)));
    }

    @GetMapping("/{userId}/boats")
    public ResponseEntity<?> getUserBoats(@PathVariable Long userId) {
        List<BoatDTO> boats = boatService.findAllByUser(userId).stream()
                .map(BoatDTO::new)
                .toList();
        return ResponseEntity.ok(boats);
    }

    @GetMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> getUserBoat(@PathVariable Long userId, @PathVariable Long boatId) {
        return ResponseEntity.ok(new BoatDTO(boatService.findBoatByUser(userId, boatId)));
    }

    @PostMapping("/{userId}/boats")
    public ResponseEntity<?> addBoatToUser(@PathVariable Long userId, @RequestBody BoatDTO boatDto) {
        return new ResponseEntity<>(new BoatDTO(boatService.addBoatToUser(userId, boatDto)), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> updateBoat(@PathVariable Long userId, @PathVariable Long boatId, @RequestBody BoatDTO boatDto) {
        return ResponseEntity.ok(new BoatDTO(boatService.updateBoat(userId, boatId, boatDto)));
    }

    @DeleteMapping("/{userId}/boats/{boatId}")
    public ResponseEntity<?> deleteBoat(@PathVariable Long userId, @PathVariable Long boatId) {
        List<BoatDTO> refreshedBoatsList = boatService.deleteBoat(userId, boatId).stream()
                .map(BoatDTO::new)
                .toList();
        return ResponseEntity.ok(refreshedBoatsList);
    }
}

