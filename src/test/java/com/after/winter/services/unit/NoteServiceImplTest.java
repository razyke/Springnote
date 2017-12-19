package com.after.winter.services.unit;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.impl.NoteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceImplTest {

    private static final String NOTE_TITLE = "title";
    private static final String TITLE = "title";
    private static final Long ID = 1L;
    private static final Long NOTEBOOK_ID = 1L;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note note;
    private Notebook notebook;
    private Mark mark;
    private List<Mark> marks = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<Mark> emptyMarks = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        note = Note.builder()
                .id(1L)
                .title(NOTE_TITLE)
                .body("body")
                .marks(emptyMarks)
                .build();

        notebook = Notebook.builder()
                .id(NOTEBOOK_ID)
                .title(TITLE)
                .build();

        mark = Mark.builder()
                .id(ID)
                .type("text")
                .build();

        note.setNotebook(notebook);
        marks.add(mark);
        notes.add(note);

        notebook.setNotes(notes);

        when(noteRepository.existsById(anyLong())).thenReturn(true);
    }

    //TODO: change tests
 /*   @Test
    public void getNote_WhenNoteExists() throws Exception {
        when(noteRepository.getOne(anyLong())).thenReturn(note);
        Note returnedNote = noteService.getNote(ID);
        assertThat(returnedNote).isEqualTo(note);
        verify(noteRepository).getOne(anyLong());
    }

    @Test
    public void getNote_WhenNoteDoesntExists() throws Exception {
        when(noteRepository.exists(anyLong())).thenReturn(false);
        Note returnedNote = noteService.getNote(ID);
        assertThat(returnedNote).isNull();
        verify(noteRepository).exists(anyLong());
    }

    @Test
    public void getNote_WhenIdIsNull() throws Exception {
        Note returnedNote = noteService.getNote(null);
        assertThat(returnedNote).isNull();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void getNoteByTitleAndNotebookId_WhenBothExists() throws Exception {
        when(noteRepository.getByTitleAndAndNotebookId(anyString(), anyLong())).thenReturn(note);
        Note returnedNote = noteService.getNoteByTitleAndNotebookId(NOTE_TITLE, NOTEBOOK_ID);
        assertThat(returnedNote).isEqualTo(note);
        verify(noteRepository, only()).getByTitleAndAndNotebookId(anyString(), anyLong());
    }

    @Test
    public void getNoteByTitleAndNotebookId_WhenTitleIsNull() throws Exception {
        Note returnedNote = noteService.getNoteByTitleAndNotebookId(null, NOTEBOOK_ID);
        assertThat(returnedNote).isNull();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void getNoteByTitleAndNotebookId_WhenNotebookIdIsNull() throws Exception {
        Note returnedNote = noteService.getNoteByTitleAndNotebookId(NOTE_TITLE, null);
        assertThat(returnedNote).isNull();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void getNoteByTitleAndNotebookId_WhenBothAreNull() throws Exception {
        Note returnedNote = noteService.getNoteByTitleAndNotebookId(null, null);
        assertThat(returnedNote).isNull();
        verifyZeroInteractions(noteRepository);
    }*/

    @Test
    public void createNote_WhenNoteIsNotNull() throws Exception {
        when(noteRepository.saveAndFlush(any(Note.class))).thenReturn(note);
        Note returnedNote = noteService.createNote(note);
        assertThat(returnedNote).isEqualTo(note);
        verify(noteRepository, only()).saveAndFlush(any(Note.class));
    }

    @Test
    public void createNote_WhenNoteIsNull() throws Exception {
        Note returnedNote = noteService.createNote(null);
        assertThat(returnedNote).isNull();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void updateNote_WhenNoteExists() throws Exception {
        when(noteRepository.saveAndFlush(any(Note.class))).thenReturn(note);

        Note returnedNote = noteService.updateNote(note);

        assertThat(returnedNote).isEqualTo(note);

        verify(noteRepository).existsById(anyLong());
        verify(noteRepository).saveAndFlush(any(Note.class));
    }

    @Test
    public void updateNote_WhenNoteDoesntExists() throws Exception {
        when(noteRepository.existsById(anyLong())).thenReturn(false);;

        Note returnedNote = noteService.updateNote(note);

        assertThat(returnedNote).isNull();

        verify(noteRepository).existsById(anyLong());
    }

    @Test
    public void updateNote_WhenTransmittedNoteIsNull() throws Exception {
        Note returnedNote = noteService.updateNote(null);
        assertThat(returnedNote).isNull();

        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void deleteNote_WhenNoteExists() throws Exception {
        doNothing().when(noteRepository).deleteById(anyLong());
        boolean deleted = noteService.deleteNote(ID);

        assertThat(deleted).isTrue();
        verify(noteRepository).deleteById(anyLong());
    }

    @Test
    public void deleteNote_WhenNoteDoesntExists() throws Exception {
        when(noteRepository.existsById(anyLong())).thenReturn(false);
        boolean deleted = noteService.deleteNote(ID);

        assertThat(deleted).isFalse();
        verify(noteRepository).existsById(anyLong());
    }

    @Test
    public void deleteNote_WhenNoteIsNull() throws Exception {
        boolean deleted = noteService.deleteNote(null);

        assertThat(deleted).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    //TODO: and here too
/*    @Test
    public void getAllNotes() throws Exception {
        when(noteRepository.findAll()).thenReturn(notes);
        List<Note> returnedNotes = noteService.getAllNotes();
        assertThat(returnedNotes).isNotEmpty().contains(note).isEqualTo(notes);
        verify(noteRepository).findAll();
    }*/

    @Test
    public void getAllNotesByNotebook_WhenNotebookExists() throws Exception {
        when(noteRepository.getAllByNotebook(any(Notebook.class))).thenReturn(notes);
        when(notebookRepository.existsById(anyLong())).thenReturn(true);

        List<Note> returnedNotes = noteService.getAllNotesByNotebook(notebook);
        assertThat(returnedNotes).isNotEmpty().contains(note).isEqualTo(notes);

        verify(noteRepository).getAllByNotebook(any(Notebook.class));
    }

    @Test
    public void getAllNotesByNotebook_WhenNotebookIsNull() throws Exception {
        List<Note> returnedNotes = noteService.getAllNotesByNotebook(null);
        assertThat(returnedNotes).isNull();

        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void addMarkToNote_WhenBothAreNotNull() throws Exception {
        when(noteRepository.saveAndFlush(any(Note.class))).thenReturn(note);

        assertThat(note.getMarks()).isEmpty();

        boolean added = noteService.addMarkToNote(mark, note);

        assertThat(added).isTrue();
        assertThat(note.getMarks()).isNotEmpty().contains(mark);

        verify(noteRepository).saveAndFlush(any(Note.class));
    }

    @Test
    public void addMarkToNote_WhenMarkIsNull() throws Exception {
        boolean added = noteService.addMarkToNote(null, note);

        assertThat(added).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void addMarkToNote_WhenNoteIsNull() throws Exception {
        boolean added = noteService.addMarkToNote(mark, null);

        assertThat(added).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void addMarkToNote_WhenBothAreNull() throws Exception {
        boolean added = noteService.addMarkToNote(null, null);

        assertThat(added).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void removeMarkFromNote_WhenBothAreNotNull() throws Exception {
        when(noteRepository.saveAndFlush(any(Note.class))).thenReturn(note);
        note.setMarks(marks);
        assertThat(note.getMarks()).isNotEmpty().contains(mark);

        boolean removed = noteService.removeMarkFromNote(mark, note);

        assertThat(removed).isTrue();
        assertThat(note.getMarks()).isEmpty();
        verify(noteRepository).saveAndFlush(any(Note.class));
    }

    @Test
    public void removeMarkFromNote_WhenMarkIsNull() throws Exception {
        boolean removed = noteService.removeMarkFromNote(null, note);

        assertThat(removed).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void removeMarkFromNote_WhenNoteIsNull() throws Exception {
        boolean removed = noteService.removeMarkFromNote(mark, null);

        assertThat(removed).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void removeMarkFromNote_WhenBothAreNull() throws Exception {
        boolean removed = noteService.removeMarkFromNote(null, null);

        assertThat(removed).isFalse();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void removeAllMarksFromNote() throws Exception {
        when(noteRepository.saveAndFlush(any(Note.class))).thenReturn(note);
        note.setMarks(marks);
        assertThat(note.getMarks()).isNotEmpty().contains(mark);

        boolean removed = noteService.removeAllMarksFromNote(note);

        assertThat(removed).isTrue();
        assertThat(note.getMarks()).isEmpty();
        verify(noteRepository).saveAndFlush(any(Note.class));
    }
}