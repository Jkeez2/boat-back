package com.jkeez.boatback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkeez.boatback.Controller.UserAccountController;
import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Entity.Boat;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Service.BoatService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserAccountController.class, includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class))
class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private BoatService boatService;

    @Autowired
    private ObjectMapper objectMapper;

    private BoatDTO boatDTO;

    private Boat boat;

    private UserAccount userAccount;

    @BeforeEach
    void setup() {
        boat = new Boat();
        boatDTO = new BoatDTO();
        userAccount = new UserAccount();
        userAccount.setBoats(new ArrayList<>());
    }

    @Test
    @DisplayName("should find user")
    public void getUserOk() throws Exception{

        // given
        Long userId = 1L;

        userAccount.setFirstName("Marie");
        userAccount.setLastName("Plume");
        userAccount.setEmail("marieplume@test.com");

        doReturn(userAccount).when(userAccountService).findById(userId);

        // when
        ResultActions response = mockMvc.perform(get("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(userAccount.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(userAccount.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(userAccount.getEmail())));

    }

    @Test
    @DisplayName("should find user's boats")
    public void getUserBoats() throws Exception{

        // given
        Long userId = 1L;
        List<Boat> boats = new ArrayList<>();
        boat.setName("test");

        doReturn(boats).when(boatService).findAllByUser(userId);

        // when
        ResultActions response = mockMvc.perform(get("/api/users/" + userId + "/boats")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print()).
                andExpect(status().isOk());

    }

    @Test
    @DisplayName("should find user's boat")
    public void getUserBoat() throws Exception{

        // given
        Long userId = 1L;
        userAccount.setId(userId);
        Long boatId = 1L;
        boat.setName("test");
        boat.setUserAccount(userAccount);

        doReturn(boat).when(boatService).findBoatByUser(userId, boatId);

        // when
        ResultActions response = mockMvc.perform(get("/api/users/" + userId + "/boats/" + boatId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(boat.getName())));

    }

    @Test
    @DisplayName("should add boat to user")
    public void addBoatToUser() throws Exception{

        // given
        Long userId = 1L;
        userAccount.setId(userId);
        boatDTO.setName("test");
        boat.setName("test");
        boat.setUserAccount(userAccount);

        doReturn(boat).when(boatService).addBoatToUser(any(), any());

        // when
        ResultActions response = mockMvc.perform(post("/api/users/" + userId + "/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boatDTO)));

        // then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(boat.getName())));

    }

    @Test
    @DisplayName("should update boat")
    public void updateBoat() throws Exception{

        // given
        Long userId = 1L;
        Long boatId = 1L;
        userAccount.setId(userId);
        boatDTO.setName("test");
        boat.setName("test");
        boat.setUserAccount(userAccount);

        doReturn(boat).when(boatService).updateBoat(any(), any(), any());

        // when
        ResultActions response = mockMvc.perform(put("/api/users/" + userId + "/boats/" + boatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boatDTO)));

        // then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(boatDTO.getName())));

    }

    @Test
    @DisplayName("should delete boat")
    public void deleteBoat() throws Exception{

        // given
        Long userId = 1L;
        Long boatId = 1L;
        userAccount.setId(userId);
        boatDTO.setName("test");
        boat.setName("test");
        boat.setUserAccount(userAccount);
        List<Boat> boats = new ArrayList<>();
        boats.add(boat);

        doReturn(boats).when(boatService).deleteBoat(userId, boatId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/users/" + userId + "/boats/" + boatId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",
                        is(boatDTO.getName())));

    }
}