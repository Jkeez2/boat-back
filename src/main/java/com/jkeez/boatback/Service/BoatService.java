package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Entity.Boat;

import java.util.List;

public interface BoatService {

    Boat findById(Long boatId);

    Boat findBoatByUser(Long userId, Long boatId);

    List<Boat> findAllByUser(Long userId);

    Boat addBoatToUser(Long userId, BoatDTO boatDTO);

    List<Boat> deleteBoat(Long userId, Long boatId);

    Boat updateBoat(Long userId, Long boatId, BoatDTO boatDTO);
}

