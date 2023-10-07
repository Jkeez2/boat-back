package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    @Spy
    private AuthServiceImpl authService;

    @Mock
    private UserAccountRepository userAccountRepository;

    private UserRegistrationDTO userRegistrationDTO;

    private UserLoginDTO userLoginDTO;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        userRegistrationDTO = new UserRegistrationDTO();
        userLoginDTO = new UserLoginDTO();
    }

    @Test
    @DisplayName("Register a new user - Ok")
    void registerNewUserOk() {
        // given
        userRegistrationDTO.setFirstName("Jean");
        userRegistrationDTO.setLastName("Doux");
        userRegistrationDTO.setEmail("jeandoux@test.com");
        userRegistrationDTO.setPassword("Jeandoux1234");

        // when
        this.authService.registerNewUser(userRegistrationDTO);

        // verify
        verify(userAccountRepository).findByEmail(userRegistrationDTO.getEmail());
        verify(bCryptPasswordEncoder).encode(any());
        verify(userAccountRepository).save(any());
    }

    @Test
    @DisplayName("Register a new user - email already exists")
    void registerNewUserEmailAlreadyExists() {
        // given
        userRegistrationDTO.setFirstName("Jean");
        userRegistrationDTO.setLastName("Doux");
        userRegistrationDTO.setEmail("jeandoux@test.com");
        userRegistrationDTO.setPassword("Jeandoux1234");

        doReturn(new UserAccount()).when(userAccountRepository).findByEmail(userRegistrationDTO.getEmail());

        // when
        Exception exception = assertThrows(UserAccountException.class, () -> {
            this.authService.registerNewUser(userRegistrationDTO);
        });

        String expectedMessage = "Email already registered";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Login user - Ok")
    void loginUserOk() {
        // given
        userLoginDTO.setEmail("abc@test.com");
        userLoginDTO.setPassword("123456Abc");

        doReturn(new UserAccount()).when(userAccountRepository).findByEmail(userLoginDTO.getEmail());
        doReturn(true).when(bCryptPasswordEncoder).matches(eq(userLoginDTO.getPassword()), any());

        // when

        this.authService.loginUser(userLoginDTO);

        // verify

        verify(userAccountRepository).findByEmail(userLoginDTO.getEmail());
        verify(bCryptPasswordEncoder).matches(eq(userLoginDTO.getPassword()), any());
    }

    @Test
    @DisplayName("Login user - invalid email")
    void loginUserInvalidEmail() {
        // given
        userLoginDTO.setEmail("abc@test.com");
        userLoginDTO.setPassword("123456Abc");

        doReturn(null).when(userAccountRepository).findByEmail(userLoginDTO.getEmail());

        // when
        Exception exception = assertThrows(UserAccountException.class, () -> {
            this.authService.loginUser(userLoginDTO);
        });

        String expectedMessage = "Account with given email not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Login user - invalid password")
    void loginUserInvalidPassword() {
        // given
        userLoginDTO.setEmail("abc@test.com");
        userLoginDTO.setPassword("123456Abc");

        doReturn(new UserAccount()).when(userAccountRepository).findByEmail(userLoginDTO.getEmail());
        doReturn(false).when(bCryptPasswordEncoder).matches(eq(userLoginDTO.getPassword()), any());

        // when
        Exception exception = assertThrows(UserAccountException.class, () -> {
            this.authService.loginUser(userLoginDTO);
        });

        String expectedMessage = "Incorrect password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}