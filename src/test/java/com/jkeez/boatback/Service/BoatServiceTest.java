package com.jkeez.boatback.Service;

import com.jkeez.boatback.Dto.BoatDTO;
import com.jkeez.boatback.Entity.Boat;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Repository.BoatRepository;
import com.jkeez.boatback.Repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoatServiceTest {

    @InjectMocks
    @Spy
    private BoatServiceImpl boatService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private BoatRepository boatRepository;

    private Boat boat;

    private BoatDTO boatDTO;

    private UserAccount userAccount;
    @BeforeEach
    void setUp() {
        boat = new Boat();
        boatDTO = new BoatDTO();
        userAccount = new UserAccount();
        userAccount.setBoats(new ArrayList<>());
    }

    @Test
    @DisplayName("Should get a boat with valid id")
    void findByIdOk() {
        // given
        Long boatId = 1L;
        boat.setName("Test");
        boat.setBoatId(boatId);

        doReturn(Optional.of(boat)).when(this.boatRepository).findById(boatId);

        // when
        Boat boatResult = this.boatService.findById(boatId);

        // verify
        verify(boatRepository).findById(boatId);
        assertEquals(boatResult.getBoatId(), boat.getBoatId());
        assertEquals(boatResult.getName(), boat.getName());
    }

    @Test
    @DisplayName("Should get a user's boat")
    void findBoatByUserOk() {
        // given
        Long userId = 1L;
        Long boatId = 1L;
        boat.setName("Test");
        boat.setBoatId(boatId);
        boat.setBoatId(userId);


        doReturn(Optional.of(boat)).when(this.boatRepository).findByBoatIdAndUserAccount_Id(boatId, userId);

        // when
        Boat boatResult = this.boatService.findBoatByUser(userId, boatId);

        // verify
        verify(boatRepository).findByBoatIdAndUserAccount_Id(boatId, userId);
        assertEquals(boatResult.getBoatId(), boat.getBoatId());
        assertEquals(boatResult.getName(), boat.getName());
    }

    @Test
    @DisplayName("Should add boat to a user")
    void addBoatToUserOk() {
        // given
        userAccount.setId(1L);
        boatDTO.setName("Test");
        boat.setName("test");
        boat.setUserAccount(userAccount);

        doReturn(userAccount).when(this.userAccountService).findById(userAccount.getId());
        doReturn(boat).when(this.boatRepository).save(any());

        // when
        this.boatService.addBoatToUser(userAccount.getId(), boatDTO);

        // verify
        verify(boatRepository).save(any());
        assertEquals(userAccount.getBoats().size(), 1);
    }

    @Test
    @DisplayName("Should delete user's boat")
    void deleteBoatOk() {
        // given
        userAccount.setId(1L);
        boat.setBoatId(1L);
        boat.setName("Test");
        boat.setUserAccount(userAccount);
        userAccount.getBoats().add(boat);


        doReturn(Optional.of(boat)).when(this.boatRepository).findByBoatIdAndUserAccount_Id(boat.getBoatId(), userAccount.getId());

        // when
        List<Boat> boatResult = this.boatService.deleteBoat(userAccount.getId(), boat.getBoatId());

        // verify
        verify(userAccountRepository).save(any());
        verify(boatRepository).delete(any());
        assertEquals(boatResult.size(), 0);
    }

    @Test
    @DisplayName("Should update user's boat")
    void updateBoatOk() {
        // given
        userAccount.setId(1L);
        boatDTO.setId(1L);
        boatDTO.setName("new name");
        boatDTO.setUserAccountId(1L);
        boat.setBoatId(1L);
        boat.setName("Test");
        boat.setUserAccount(userAccount);

        doReturn(Optional.of(boat)).when(this.boatRepository).findByBoatIdAndUserAccount_Id(boat.getBoatId(), userAccount.getId());

        // when
        this.boatService.updateBoat(userAccount.getId(), boat.getBoatId(), boatDTO);

        // verify
        verify(boatRepository).save(any());
    }
}