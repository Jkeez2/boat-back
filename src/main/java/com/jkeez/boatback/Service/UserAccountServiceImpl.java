package com.jkeez.boatback.Service;


import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.BoatException;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount findById(Long userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new UserAccountException("User not found with id : " + userId));
    }
}
