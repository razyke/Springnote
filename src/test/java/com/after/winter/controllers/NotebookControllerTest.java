package com.after.winter.controllers;

import com.after.winter.config.AppConfig;
import com.after.winter.config.WebConfig;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.services.NotebookService;
import com.after.winter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class NotebookControllerTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private final static String USER_BY_ID = "/user/{id}";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    private MockMvc mockMvc;

    @Mock
    private NotebookService notebookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private NotebookController notebookController;

    private User user;
    private Notebook notebook;
    private List<Notebook> notebooks = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        notebook = Notebook.builder()
                .id(ID_1)
                .build();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(notebookController).build();

        notebooks.add(notebook);
    }

    @Test
    public void getAllNotebooksByUserId() throws Exception {
        when(notebookService.getAllNotebooksByUserId(anyLong())).thenReturn(notebooks);

        RequestBuilder request = MockMvcRequestBuilders.get(USER_BY_ID + "/notebooks", ID_1)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<Notebook> returnedNotebooks = objectMapper.readValue(json, typeFactory
                .constructCollectionType(List.class, Notebook.class));

        assertThat(returnedNotebooks).isNotEmpty().contains(notebook).isEqualTo(notebooks);

        verify(notebookService).getAllNotebooksByUserId(anyLong());
        verifyNoMoreInteractions(notebookService);
    }

    @Test
    public void getNotebookByUserIdAndNotebookId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);

        RequestBuilder request = MockMvcRequestBuilders
                .get(USER_BY_ID + "/notebook/{notebook_id}", ID_1, ID_1)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result.getResponse().getContentAsString();

        Notebook returnedNotebook = objectMapper.readValue(json, typeFactory.constructType(Notebook.class));

        assertThat(returnedNotebook).isNotNull().isEqualTo(notebook);

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(notebookService);
    }

    @Test
    public void createNotebookForUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(notebookService.createNotebook(any(Notebook.class))).thenReturn(notebook);

        RequestBuilder request = MockMvcRequestBuilders
                .post(USER_BY_ID + "/notebook", ID_1)
                .content(objectMapper.writeValueAsString(notebook))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Notebook has been created with ID - 1");

        verify(notebookService).createNotebook(any(Notebook.class));
        verify(userService).getUser(anyLong());
        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateNotebookForUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(notebookService.updateNotebook(any(Notebook.class))).thenReturn(notebook);
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);

        RequestBuilder request = MockMvcRequestBuilders
                .put(USER_BY_ID + "/notebook", ID_1)
                .content(objectMapper.writeValueAsString(notebook))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Update success");

        verify(notebookService).updateNotebook(any(Notebook.class));
        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(userService).getUser(anyLong());
        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void deleteNotebook() throws Exception {
        when(notebookService.deleteNotebook(anyLong())).thenReturn(true);
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(USER_BY_ID + "/notebook/{notebook_id}", ID_1, ID_1)
                .content(objectMapper.writeValueAsString(notebook))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Notebook has been deleted");

        verify(notebookService).deleteNotebook(anyLong());
        verify(notebookService).getNotebookByIdAndUserId(anyLong(),anyLong());
        verifyNoMoreInteractions(notebookService);

    }

}