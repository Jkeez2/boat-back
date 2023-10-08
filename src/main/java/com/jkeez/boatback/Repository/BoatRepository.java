package com.jkeez.boatback.Repository;

import com.jkeez.boatback.Entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {
    List<Boat> findAllByUserAccount_Id(Long userAccountId);

    Optional<Boat> findByBoatIdAndUserAccount_Id(Long boatId, Long userId);
}

