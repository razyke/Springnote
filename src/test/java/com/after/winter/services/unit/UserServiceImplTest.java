package com.after.winter.services.unit;

import com.after.winter.model.User;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.UserService;
import com.after.winter.services.impl.UserServiceImpl;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private final long USER_ID = 1L;
    private List<User> userList = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        user = User.builder()
                .id(USER_ID)
                .email("pork@epam.com")
                .firstname("Pig")
                .lastname("Puk")
                .password("rap")
                .build();
        userList.add(user);
    }

    @Test
    public void getUserByEmailWhenUserExists() throws Exception {
        when(userRepository.getUserByEmail(anyString())).thenReturn(user);
        User returnedUser = userService.getUserByEmail("pork");
        assertThat(returnedUser).isEqualTo(user);
        verify(userRepository, times(1)).getUserByEmail(anyString());

    }

    @Test
    public void getUserByEmailWhenUserDoesntExists() throws Exception {
        when(userRepository.getUserByEmail(anyString())).thenReturn(null);
        User returnedUser = userService.getUserByEmail("pork");
        assertThat(returnedUser).isNull();
        verify(userRepository, times(1)).getUserByEmail(anyString());

    }

    @Test
    public void getUserByEmailWhenEmailIsNull() throws Exception {
        when(userRepository.getUserByEmail(null)).thenReturn(null);
        User returnedUser = userService.getUserByEmail(null);
        assertThat(returnedUser).isNull();
        verifyZeroInteractions(userRepository);

    }

   @Test
    public void getUserWhenUserExists() throws Exception {
       when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
       User returnedUser = userService.getUser(USER_ID);
       assertThat(returnedUser).isEqualTo(user);
       verify(userRepository, times(1)).findById(anyLong());
   }

    @Test
    public void getUserWhenIdIsNull() throws Exception {
        User returnedUser = userService.getUser(null);
        assertThat(returnedUser).isNull();
        verifyZeroInteractions(userRepository);
    }

    @Test
    public void createUserWhenTransmittedUserNotNull() throws Exception {
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        User returnedUser = userService.createUser(user);
        assertThat(returnedUser).isEqualTo(user);
        verify(userRepository, atLeastOnce()).saveAndFlush(user);
    }

    @Test
    public void createUserWhenTransmittedUserIsNull() throws Exception {
        User returnedUser = userService.createUser(null);
        assertThat(returnedUser).isNull();
        verifyZeroInteractions(userRepository);
    }

    @Test
    public void updateUserWhenUserExists() throws Exception {
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        User returnedUser = userService.updateUser(user);
        assertThat(returnedUser).isEqualTo(user);

        verify(userRepository).saveAndFlush(user);
        verify(userRepository).existsById(USER_ID);
    }

    @Test
    public void updateUserWhenUserDoesntExists() throws Exception {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        User returnedUser = userService.updateUser(user);
        assertThat(returnedUser).isNull();
        verify(userRepository, atLeastOnce()).existsById(USER_ID);
    }

    @Test
    public void updateUserWhenTransmittedUserIsNull() throws Exception {
        User returnedUser = userService.updateUser(null);
        assertThat(returnedUser).isNull();
        verifyZeroInteractions(userRepository);
    }

    @Test
    public void deleteUserWhenUserExists() throws Exception {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        doNothing().when(userRepository).deleteById(USER_ID);
        boolean deleted = userService.deleteUser(USER_ID);
        assertThat(deleted).isTrue();
        verify(userRepository).deleteById(USER_ID);
        verify(userRepository).existsById(USER_ID);
    }

    @Test
    public void deleteUserWhenUserDoesntExists() throws Exception {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        boolean deleted = userService.deleteUser(USER_ID);
        assertThat(deleted).isFalse();
        verify(userRepository).existsById(USER_ID);
    }

    @Test
    public void deleteUserWhenTransmittedIdIsNull() throws Exception {
        boolean deleted = userService.deleteUser(null);
        assertThat(deleted).isFalse();
        verifyZeroInteractions(userRepository);

    }

    @Test
    public void getAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(userList);
        List<User> returnedUserList = userService.getAllUsers();
        assertThat(returnedUserList).isNotNull().contains(user).isEqualTo(userList);
        verify(userRepository, atLeastOnce()).findAll();
    }

}