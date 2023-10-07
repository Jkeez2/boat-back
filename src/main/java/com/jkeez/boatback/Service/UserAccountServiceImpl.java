package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userEntity = userAccountRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles("USER") // Default role
                .build();
    }

    @Override
    public UserAccount registerNewUser(UserRegistrationDTO registrationDTO) {
        UserAccount existingUser = userAccountRepository.findByEmail(registrationDTO.getEmail());
        if (existingUser != null) {
            throw new UserAccountException("Email already registered");
        }

        UserAccount newUser = new UserAccount();
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userAccountRepository.save(newUser);
    }

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

    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount findById(Long userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow();
    }
}
