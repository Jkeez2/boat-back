package com.jkeez.boatback.Service;

import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Exception.BoatException;
import com.jkeez.boatback.Exception.UserAccountException;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks
    @Spy
    private UserAccountServiceImpl userAccountService;

    @Mock
    private UserAccountRepository userAccountRepository;

    private UserAccount userAccount;
    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
    }

    @Test
    @DisplayName("Should get a user with valid id")
    void findByIdOk() {
        // given
        Long userId = 1L;

        doReturn(Optional.of(userAccount)).when(this.userAccountRepository).findById(userId);

        // when
        this.userAccountService.findById(userId);

        // verify
        verify(this.userAccountRepository).findById(userId);
    }
}