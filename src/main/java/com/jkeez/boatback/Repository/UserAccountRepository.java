package com.jkeez.boatback.Repository;

import com.jkeez.boatback.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * UserAccount's repository
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Query method to find a user by its email
     * @param email email
     * @return corresponding user
     */
    UserAccount findByEmail(String email);
}

