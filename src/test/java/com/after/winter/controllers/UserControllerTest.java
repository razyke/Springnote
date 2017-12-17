package com.after.winter.controllers;

import com.after.winter.config.WebConfig;
import com.after.winter.config.WebConfigForTests;
import com.after.winter.model.User;
import com.after.winter.config.AppConfigForTest;
import com.after.winter.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigForTest.class, WebConfigForTests.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private UserService userService;

    private List<User> list = new ArrayList<>();


    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user1 = User.builder()
                .id(1L)
                .firstname("Pok")
                .lastname("PuK")
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstname("bot")
                .lastname("quake")
                .build();

        list.add(user1);
        list.add(user2);
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

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(list.toString());
        ModelAndView mv = new ModelAndView();
        mv.addObject(list).getModel().values();
        Collection<Object> testMv = mv.getModel().values();
        assertThat(testMv).isEqualTo(list);
    }

    @Test
    public void getUserById() throws Exception {
    }

    @Test
    public void createUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

}