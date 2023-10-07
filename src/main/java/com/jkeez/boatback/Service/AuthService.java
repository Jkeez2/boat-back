package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;

public interface AuthService {
    UserAccount registerNewUser(UserRegistrationDTO registrationDTO);

    UserAccount loginUser(UserLoginDTO userLoginDTO);
}

