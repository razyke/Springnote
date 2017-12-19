package com.after.winter.controllers;

import com.after.winter.config.AppConfig;
import com.after.winter.config.WebConfig;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.services.NoteService;
import com.after.winter.services.NotebookService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class NoteControllerTest {
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private final static String USER_BY_ID_NOTE_BY_ID = "/user/{id}/notebook/{notebook_id}";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @Mock
    private NotebookService notebookService;

    @InjectMocks
    private NoteController noteController;

    private Note note;
    private Note note2;
    private User user;
    private Notebook notebook;
    private List<Note> noteList = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();

        notebook = Notebook.builder()
                .id(ID_1)
                .notes(noteList)
                .build();

        user = User.builder()
                .id(ID_1)
                .build();

        note = Note.builder()
                .id(ID_1)
                .notebook(notebook)
                .build();

        note2 = Note.builder()
                .id(ID_2)
                .notebook(notebook)
                .build();

        noteList.add(note);
        noteList.add(note2);

    }

    @Test
    public void getAllNotesByUserId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);
        when(noteService.getAllNotesByNotebook(any(Notebook.class))).thenReturn(noteList);

        RequestBuilder request = MockMvcRequestBuilders.get(USER_BY_ID_NOTE_BY_ID + "/notes", ID_1, ID_2)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        List<Note> returnedNotes = objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, Note.class));

        assertThat(returnedNotes).isNotEmpty().contains(note, note2).isEqualTo(noteList);

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(noteService).getAllNotesByNotebook(any(Notebook.class));

        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void getNoteByNotebookIdAndUserId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);
        when(noteService.getNoteByIdAndNotebookId(anyLong(), anyLong())).thenReturn(note);

        RequestBuilder request = MockMvcRequestBuilders.get(USER_BY_ID_NOTE_BY_ID + "/note/{note_id}", ID_1, ID_2, ID_1)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String json = result.getResponse().getContentAsString();

        Note returnedNote = objectMapper.readValue(json, typeFactory.constructType(Note.class));

        assertThat(returnedNote).isNotNull().isEqualTo(note);

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(noteService).getNoteByIdAndNotebookId(anyLong(), anyLong());

        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void createNoteByNotebookAndUserId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        RequestBuilder request = MockMvcRequestBuilders
                .post(USER_BY_ID_NOTE_BY_ID + "/note", ID_2, ID_1)
                .content(objectMapper.writeValueAsString(note))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.TEXT_PLAIN);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotNull().isNotEmpty().isEqualTo("Note has been created with ID - 1");

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(noteService).createNote(any(Note.class));

        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void updateNoteByNotebookAndUserId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);
        when(noteService.updateNote(any(Note.class))).thenReturn(note);

        RequestBuilder request = MockMvcRequestBuilders
                .put(USER_BY_ID_NOTE_BY_ID + "/note", ID_2, ID_1)
                .content(objectMapper.writeValueAsString(note))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.TEXT_PLAIN);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotNull().isNotEmpty().isEqualTo("Note updated");

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(noteService).updateNote(any(Note.class));

        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void deleteNoteByNotebookAndUserId() throws Exception {
        when(notebookService.getNotebookByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);
        when(noteService.deleteNote(anyLong())).thenReturn(true);
        when(noteService.getNoteByIdAndNotebookId(anyLong(), anyLong())).thenReturn(note);

        RequestBuilder request = MockMvcRequestBuilders.delete(USER_BY_ID_NOTE_BY_ID + "/note/{note_id}", ID_1, ID_2, ID_1)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response).isNotNull().isNotEmpty().isEqualTo("Note delete success");

        verify(notebookService).getNotebookByIdAndUserId(anyLong(), anyLong());
        verify(noteService).deleteNote(anyLong());
        verify(noteService).getNoteByIdAndNotebookId(anyLong(), anyLong());

        verifyNoMoreInteractions(notebookService);
        verifyNoMoreInteractions(noteService);
    }

}