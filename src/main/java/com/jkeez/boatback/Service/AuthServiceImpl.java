package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService interface implementation.
 * Manages register and login services.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new.
     * @param registrationDTO new user
     * @return created user
     */
    @Override
    public UserAccount registerNewUser(UserRegistrationDTO registrationDTO) {
        // Check if given email is already taken
        UserAccount existingUser = userAccountRepository.findByEmail(registrationDTO.getEmail());
        if (existingUser != null) {
            throw new UserAccountException("Email already registered");
        }

        UserAccount newUser = new UserAccount();
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setEmail(registrationDTO.getEmail());
        // encode password
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userAccountRepository.save(newUser);
    }

    /**
     * Authenticate user
     * @param userCredentials credentials
     * @return Authenticated user
     */
    @Override
    public UserAccount loginUser(UserLoginDTO userCredentials) {
        // TODO Replace with spring security auth
        UserAccount existingUser = userAccountRepository.findByEmail(userCredentials.getEmail());
        if (existingUser == null) {
            throw new UserAccountException("Account with given email not found");
        }

        boolean passwordMatches = passwordEncoder.matches(userCredentials.getPassword(), existingUser.getPassword());

        if (!passwordMatches) {
            throw new UserAccountException("Incorrect password");
        }

        return existingUser;
    }
}
