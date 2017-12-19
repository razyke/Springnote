package com.after.winter.controllers;

import com.after.winter.config.AppConfig;
import com.after.winter.config.WebConfig;
import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.services.MarkService;
import com.after.winter.services.NoteService;
import com.after.winter.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class MarkControllerTest {

    private static final Long ID = 1L;
    private static final String USER_BY_ID = "/user/{id}";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    private MockMvc mockMvc;

    @Mock
    private MarkService markService;
    @Mock
    private UserService userService;
    @Mock
    private NoteService noteService;

    @InjectMocks
    private MarkController markController;

    private User user;
    private Mark mark;
    private Note note;
    private List<Mark> marks = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(markController).build();

        user = User.builder()
                .id(ID)
                .build();

        mark = Mark.builder()
                .id(ID)
                .type("puk")
                .notes(Collections.emptyList())
                .user(user)
                .build();

        note = Note.builder()
                .id(ID)
                .build();

        marks.add(mark);
    }

    @Test
    public void getAllMarksByUserId() throws Exception {
        when(markService.getAllMarksByUserId(anyLong())).thenReturn(marks);

        RequestBuilder request = MockMvcRequestBuilders.get(USER_BY_ID + "/marks", ID)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        List<Mark> returnedMarks = objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, Mark.class));

        assertThat(returnedMarks).isNotEmpty().contains(mark).isEqualTo(marks);

        verify(markService).getAllMarksByUserId(anyLong());

        verifyNoMoreInteractions(markService);
    }

    @Test
    public void getMarkByIdAndUserId() throws Exception {
        when(markService.getMarkByIdAndUserId(anyLong(), anyLong())).thenReturn(mark);

        RequestBuilder request = MockMvcRequestBuilders.get(USER_BY_ID + "/mark/{mark_id}", ID, ID)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        Mark returnedMark = objectMapper.readValue(json, typeFactory.constructType(Mark.class));

        assertThat(returnedMark).isEqualTo(mark);

        verify(markService).getMarkByIdAndUserId(anyLong(), anyLong());

        verifyNoMoreInteractions(markService);
    }

    @Test
    public void createMark() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(markService.createMark(any(Mark.class))).thenReturn(mark);

        RequestBuilder request = MockMvcRequestBuilders.post(USER_BY_ID + "/mark", ID)
                .content(objectMapper.writeValueAsString(mark))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result
                .getResponse()
                .getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Mark has been created with ID -1");

        verify(markService).createMark(any(Mark.class));
        verify(userService).getUser(anyLong());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(markService);
    }

    @Test
    public void updateMark() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(markService.updateMark(any(Mark.class))).thenReturn(mark);

        RequestBuilder request = MockMvcRequestBuilders.put(USER_BY_ID + "/mark", ID)
                .content(objectMapper.writeValueAsString(mark))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result
                .getResponse()
                .getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Mark has been updated");

        verify(markService).updateMark(any(Mark.class));
        verify(userService).getUser(anyLong());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(markService);
    }

    @Test
    public void deleteMark() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(markService.getMarkByIdAndUserId(anyLong(), anyLong()));
        when(markService.deleteMark(anyLong())).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders.delete(USER_BY_ID + "/mark/{mark_id}", ID, ID)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result
                .getResponse()
                .getContentAsString();

        assertThat(response).isNotEmpty().isEqualTo("Mark has been deleted");

        verify(markService).deleteMark(anyLong());
        verify(markService).getMarkByIdAndUserId(anyLong(), anyLong());
        verify(userService).getUser(anyLong());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(markService);
    }

    @Test
    public void markNote() throws Exception {

    }

    @Test
    public void removeMarkFromNote() throws Exception {
    }

    @Test
    public void getAllNotesByMark() throws Exception {
    }

}