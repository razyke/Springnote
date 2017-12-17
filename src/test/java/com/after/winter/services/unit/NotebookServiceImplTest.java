package com.after.winter.services.unit;

import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.impl.NotebookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotebookServiceImplTest {

    private static final Long NOTEBOOK_ID = 1L;
    private static final String NOTEBOOK_TITLE = "title";

    private static final Long USER_ID = 1L;


    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NotebookServiceImpl notebookService;

    private User user;
    private Notebook notebook;
    private List<Notebook> notebooks = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        user = User.builder()
                .id(USER_ID)
                .build();

        notebook = Notebook.builder()
                .id(NOTEBOOK_ID)
                .title(NOTEBOOK_TITLE)
                .user(user)
                .build();

        notebooks.add(notebook);

        when(notebookRepository.exists(anyLong())).thenReturn(true);
    }

    @Test
    public void getNotebook() throws Exception {
        when(notebookRepository.findOne(anyLong())).thenReturn(notebook);
        Notebook returnedNotebook = notebookService.getNotebook(NOTEBOOK_ID);

        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).findOne(anyLong());
    }

    @Test
    public void getNotebookByTitleAndUserId() throws Exception {
        when(notebookRepository.getByTitleAndUserId(anyString(), anyLong())).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.getNotebookByTitleAndUserId(NOTEBOOK_TITLE, USER_ID);

        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).getByTitleAndUserId(anyString(), anyLong());
    }

    @Test
    public void createNotebook() throws Exception {
        when(notebookRepository.saveAndFlush(any(Notebook.class))).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.createNotebook(notebook);
        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).saveAndFlush(any(Notebook.class));
    }

    @Test
    public void updateNotebook() throws Exception {
        when(notebookRepository.saveAndFlush(any(Notebook.class))).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.updateNotebook(notebook);
        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).saveAndFlush(any(Notebook.class));
    }

    @Test
    public void deleteNotebook() throws Exception {
        doNothing().when(notebookRepository).delete(anyLong());

        notebookService.deleteNotebook(NOTEBOOK_ID);
        verify(notebookRepository).delete(anyLong());

    }

    @Test
    public void getAllNotebooksByUserId() throws Exception {
        when(notebookRepository.findAllByUserId(anyLong())).thenReturn(notebooks);

        List<Notebook> notebookList = notebookService.getAllNotebooksByUserId(USER_ID);
        assertThat(notebookList).isNotEmpty().contains(notebook).isEqualTo(notebooks);
        verify(notebookRepository).findAllByUserId(anyLong());
    }

}