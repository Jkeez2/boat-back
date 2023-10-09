package com.jkeez.boatback.Service;


import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserAccountService's interface implementation.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Find a user by given email
     * @param email email
     * @return user
     */
    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    /**
     * Find user by its id
     * @param userId user id
     * @return user
     */
    @Override
    public UserAccount findById(Long userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new UserAccountException("User not found with id : " + userId));
    }
}
