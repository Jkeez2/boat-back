package com.jkeez.boatback.Repository;

import com.jkeez.boatback.Entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Boat's repository
 */
@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {

    /**
     * Query method that find user's boats
     * @param userAccountId user id
     * @return its boats
     */
    List<Boat> findAllByUserAccount_Id(Long userAccountId);

    /**
     * Query method that find a user's boat
     * @param boatId boat id
     * @param userId user id
     * @return user's boat
     */
    Optional<Boat> findByBoatIdAndUserAccount_Id(Long boatId, Long userId);
}

