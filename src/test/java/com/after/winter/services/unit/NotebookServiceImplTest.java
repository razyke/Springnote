package com.after.winter.services.unit;

import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.impl.NotebookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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

        when(notebookRepository.existsById(anyLong())).thenReturn(true);
    }

    @Test
    public void getNotebookByTitleAndUserId_WhenBothAreNotNull() throws Exception {
        when(notebookRepository.getByIdAndUserId(anyLong(), anyLong())).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.getNotebookByIdAndUserId(NOTEBOOK_ID, USER_ID);

        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).getByIdAndUserId(anyLong(), anyLong());
    }

    @Test
    public void getNotebookByTitleAndUserId_WhenNotebooTitleIsNull() throws Exception {
        Notebook returnedNotebook = notebookService.getNotebookByIdAndUserId(null, USER_ID);

        assertThat(returnedNotebook).isNull();
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void getNotebookByTitleAndUserId_WhenUserIdIsNull() throws Exception {
        Notebook returnedNotebook = notebookService.getNotebookByIdAndUserId(NOTEBOOK_ID, null);

        assertThat(returnedNotebook).isNull();
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void getNotebookByTitleAndUserId_WhenBothAreNull() throws Exception {
        Notebook returnedNotebook = notebookService.getNotebookByIdAndUserId(null, null);

        assertThat(returnedNotebook).isNull();
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void createNotebook_WhenNotebookIsNotNull() throws Exception {
        when(notebookRepository.saveAndFlush(any(Notebook.class))).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.createNotebook(notebook);
        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).saveAndFlush(any(Notebook.class));
    }

    @Test
    public void createNotebook_WhenNotebookIsNull() throws Exception {
        Notebook returnedNotebook = notebookService.createNotebook(null);
        assertThat(returnedNotebook).isNull();
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void updateNotebook_WhenNotebookExistsAndNotNull() throws Exception {
        when(notebookRepository.saveAndFlush(any(Notebook.class))).thenReturn(notebook);

        Notebook returnedNotebook = notebookService.updateNotebook(notebook);
        assertThat(returnedNotebook).isEqualTo(notebook);
        verify(notebookRepository).saveAndFlush(any(Notebook.class));
    }

    @Test
    public void updateNotebook_WhenNotebookDoesntExists() throws Exception {
        when(notebookRepository.existsById(anyLong())).thenReturn(false);

        Notebook returnedNotebook = notebookService.updateNotebook(notebook);
        assertThat(returnedNotebook).isNull();
        verify(notebookRepository).existsById(anyLong());
    }

    @Test
    public void updateNotebook_WhenNotebookIsNull() throws Exception {
        Notebook returnedNotebook = notebookService.updateNotebook(null);
        assertThat(returnedNotebook).isNull();
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void deleteNotebook_WhenIdIsNotNullAndNotebookExists() throws Exception {
        doNothing().when(notebookRepository).deleteById(anyLong());

        notebookService.deleteNotebook(NOTEBOOK_ID);
        verify(notebookRepository).deleteById(anyLong());
    }

    @Test
    public void deleteNotebook_WhenIdIsNull() throws Exception {
        notebookService.deleteNotebook(null);
        verifyZeroInteractions(notebookRepository);
    }

    @Test
    public void deleteNotebook_WhenNotebookDoesntExists() throws Exception {
        when(notebookRepository.existsById(anyLong())).thenReturn(false);

        notebookService.deleteNotebook(NOTEBOOK_ID);
        verify(notebookRepository).existsById(anyLong());
    }

    @Test
    public void getAllNotebooksByUserId_WhenUserIdIsNotNull() throws Exception {
        when(notebookRepository.findAllByUserId(anyLong())).thenReturn(notebooks);

        List<Notebook> notebookList = notebookService.getAllNotebooksByUserId(USER_ID);
        assertThat(notebookList).isNotEmpty().contains(notebook).isEqualTo(notebooks);
        verify(notebookRepository).findAllByUserId(anyLong());
    }

    @Test
    public void getAllNotebooksByUserId_WhenUserIdIsNull() throws Exception {

        List<Notebook> notebookList = notebookService.getAllNotebooksByUserId(null);
        assertThat(notebookList).isEmpty();
        verifyZeroInteractions(notebookRepository);
    }

}