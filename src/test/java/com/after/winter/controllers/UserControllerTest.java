package com.after.winter.controllers;

import com.after.winter.config.AppConfigForTest;
import com.after.winter.config.WebConfigForTests;
import com.after.winter.model.User;
import com.after.winter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigForTest.class, WebConfigForTests.class})
@WebAppConfiguration
public class UserControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private List<User> list = new ArrayList<>();
    private User user;
    private User user1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = User.builder()
                .id(1L)
                .firstname("Pok")
                .lastname("PuK")
                .notebooks(Collections.emptyList())
                .marks(Collections.emptyList())
                .build();

        user1 = User.builder()
                .id(2L)
                .firstname("bot")
                .lastname("quake")
                .notebooks(Collections.emptyList())
                .marks(Collections.emptyList())
                .build();

        list.add(user);
        list.add(user1);
    }

    @Test
    public void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        List<User> returnedList = objectMapper.readValue(json, typeFactory
                .constructCollectionType(List.class, User.class));

        assertThat(returnedList).isNotEmpty().isEqualTo(list);
        verify(userService, only()).getAllUsers();
    }

    @Test
    public void getUserById() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        User returnedUser = objectMapper.readValue(json, typeFactory.constructType(User.class));

        assertThat(returnedUser).isNotNull().isEqualTo(user);
        verify(userService, only()).getUser(anyLong());
    }

    @Test
    public void getUserById_WhenIdDoesntExists() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/3")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        assertThat(json).isNullOrEmpty();
        verify(userService, only()).getUser(anyLong());
    }

    @Test
    public void createUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user")
                .content(objectMapper.writeValueAsBytes(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        User returnedUser = objectMapper.readValue(json, typeFactory.constructType(User.class));

        assertThat(returnedUser).isNotNull().isEqualTo(user);
        verify(userService, only()).createUser(any(User.class));

    }

    @Test
    public void updateUser() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(user);

        RequestBuilder request = put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(user))
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        User returnedUser = objectMapper.readValue(json, typeFactory.constructType(User.class));

        assertThat(returnedUser).isNotNull().isEqualTo(user);
        verify(userService, only()).updateUser(any(User.class));
    }

    @Test
    public void deleteUser() throws Exception {
        when(userService.deleteUser(anyLong())).thenReturn(true);

        RequestBuilder request = delete("/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
    }
}