package com.jkeez.boatback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkeez.boatback.Controller.AuthController;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Service.AuthService;
import com.jkeez.boatback.Service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = AuthController.class, includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class))
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserAccountService userAccountService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegistrationDTO userRegistrationDTO;

    private UserAccount userAccount;

    @BeforeEach
    void setup() {
        userRegistrationDTO = new UserRegistrationDTO();
        userAccount = new UserAccount();
    }

    @Test
    @DisplayName("Register user - Ok")
    public void registerUserOk() throws Exception{

        // given
        userRegistrationDTO.setFirstName("Marie");
        userRegistrationDTO.setLastName("Plume");
        userRegistrationDTO.setEmail("marieplume@test.com");
        userRegistrationDTO.setPassword("secretPassword1");

        userAccount.setFirstName("Marie");
        userAccount.setLastName("Plume");
        userAccount.setEmail("marieplume@test.com");
        userAccount.setPassword("secretPassword1");

        doReturn(userAccount).when(authService).registerNewUser(any(UserRegistrationDTO.class));

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)));

        // then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(userRegistrationDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(userRegistrationDTO.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(userRegistrationDTO.getEmail())));

    }

    @Test
    @DisplayName("Register user - Invalid email")
    public void registerUserInvalidEmail() throws Exception{

        // given
        userRegistrationDTO.setFirstName("Marie");
        userRegistrationDTO.setLastName("Plume");
        userRegistrationDTO.setEmail("marieplume");
        userRegistrationDTO.setPassword("secretPassword1");

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)));

        // then
        response.andDo(print()).
                andExpect(status().isBadRequest());

        String bodyContent = response.andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(bodyContent.contains("Must be a valid email"));
    }

    @Test
    @DisplayName("Register user - Invalid password")
    public void registerUserInvalidPassword() throws Exception{

        // given
        userRegistrationDTO.setFirstName("Marie");
        userRegistrationDTO.setLastName("Plume");
        userRegistrationDTO.setEmail("marieplume@test.com");
        userRegistrationDTO.setPassword("secret");

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)));

        // then
        response.andDo(print()).
                andExpect(status().isBadRequest());

        String bodyContent = response.andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(bodyContent.contains("Password needs to be at least six characters, one uppercase letter and one number"));
    }

    @Test
    void loginUser() {
        // TODO : Write auth tests using Spring security
    }
}