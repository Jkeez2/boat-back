package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService {
    UserAccount registerNewUser(UserRegistrationDTO registrationDTO);

    UserAccount loginUser(UserLoginDTO userLoginDTO);
    UserAccount findByEmail(String email);

    UserAccount findById(Long userId);
}

