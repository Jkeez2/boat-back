package com.jkeez.boatback.Service;

import com.jkeez.boatback.Entity.UserAccount;

public interface UserAccountService {
    UserAccount findByEmail(String email);

    UserAccount findById(Long userId);
}

