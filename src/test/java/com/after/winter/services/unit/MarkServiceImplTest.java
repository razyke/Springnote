package com.after.winter.services.unit;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.User;
import com.after.winter.repository.MarkRepository;
import com.after.winter.repository.NoteRepository;
import com.after.winter.services.impl.MarkServiceImpl;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarkServiceImplTest {
    private static final Long MARK_ID = 1L;
    private static final String MARK_TYPE = "type";

    @Mock
    private MarkRepository markRepository;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    private Mark mark;
    private User user;
    private Note note;
    private List<Mark> marks = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        user = User.builder()
                .id(1L)
                .build();

        mark = Mark.builder()
                .id(MARK_ID)
                .type(MARK_TYPE)
                .user(user)
                .build();


        marks.add(mark);

        note = Note.builder()
                .marks(marks)
                .build();

        notes.add(note);

        when(markRepository.exists(anyLong())).thenReturn(true);
    }

    @Test
    public void createMark_WhenMarkIsNotNull() throws Exception {
        when(markRepository.saveAndFlush(any(Mark.class))).thenReturn(mark);
        Mark returnedMark = markService.createMark(this.mark);

        assertThat(returnedMark).isEqualTo(mark);
        verify(markRepository).saveAndFlush(any(Mark.class));
    }

    @Test
    public void createMark_WhenMarkIsNull() throws Exception {
        Mark returnedMark = markService.createMark(null);

        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
    }

    @Test
    public void updateMark_WhenMarkIsNotNullAndExists() throws Exception {
        when(markRepository.saveAndFlush(any(Mark.class))).thenReturn(mark);
        Mark returnedMark = markService.updateMark(mark);

        assertThat(returnedMark).isEqualTo(mark);
        verify(markRepository).saveAndFlush(any(Mark.class));
    }

    @Test
    public void updateMark_WhenMarkIsNull() throws Exception {
        Mark returnedMark = markService.updateMark(null);

        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
    }

    //TODO: and here
    @Test
    public void updateMark_WhenMarkDoesntExists() throws Exception {
        when(markRepository.exists(anyLong())).thenReturn(false);
        Mark returnedMark = markService.updateMark(mark);

        assertThat(returnedMark).isNull();
        verify(markRepository).exists(anyLong());
    }

    @Test
    public void deleteMark_WhenIdIsNotNullAndExists() throws Exception {
        doNothing().when(markRepository).delete(anyLong());
        boolean deleted = markService.deleteMark(MARK_ID);
        assertThat(deleted).isTrue();

        verify(markRepository).delete(anyLong());
    }

    @Test
    public void deleteMark_WhenIdIsNull() throws Exception {

        boolean deleted = markService.deleteMark(null);
        assertThat(deleted).isFalse();

        verifyZeroInteractions(markRepository);
    }

    @Test
    public void deleteMark_WhenMarkDoesntExists() throws Exception {
        when(markRepository.exists(anyLong())).thenReturn(false);
        boolean deleted = markService.deleteMark(MARK_ID);
        assertThat(deleted).isFalse();
        verify(markRepository).exists(anyLong());
    }

    @Test
    public void getAllMarksByUserId_WhenIdIsNotNull() throws Exception {
        when(markRepository.getAllByUserId(anyLong())).thenReturn(marks);
        List<Mark> returnedMarks = markService.getAllMarksByUserId(anyLong());
        assertThat(returnedMarks).isNotEmpty().contains(mark).isEqualTo(marks);
        verify(markRepository).getAllByUserId(anyLong());
    }

    @Test
    public void getAllMarksByUserId_WhenIdIsNull() throws Exception {
        List<Mark> markList = markService.getAllMarksByUserId(null);
        assertThat(markList).isNull();
        verifyZeroInteractions(markRepository);
    }

    @Test
    public void getMarkByIdAndUserId_WhenBothAreNotNull() throws Exception {
        when(markRepository.getByIdAndUserId(anyLong(), anyLong())).thenReturn(mark);
        Mark returnedMark = markService.getMarkByIdAndUserId(anyLong(), anyLong());
        assertThat(returnedMark).isNotNull().isEqualTo(mark);
        verify(markRepository, only()).getByIdAndUserId(anyLong(), anyLong());
    }

    @Test
    public void getMarkByIdAndUserId_WhenMarkIdIsNull() throws Exception {
        Mark returnedMark = markService.getMarkByIdAndUserId(null, 1L);
        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
    }

    @Test
    public void getMarkByIdAndUserId_WhenUserIdIsNull() throws Exception {
        Mark returnedMark = markService.getMarkByIdAndUserId(1L, null);
        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
    }

    @Test
    public void getNotesWithMarks_WhenListNotNullAndFilled() throws Exception {
        when(noteRepository.getAllByMarks(anyList())).thenReturn(notes);
        List<Note> noteList = markService.getNotesWithMarks(marks);
        assertThat(noteList).isNotEmpty().contains(note).isEqualTo(notes);
        verify(noteRepository, only()).getAllByMarks(anyList());
    }

    @Test
    public void getNotesWithMarks_WhenListIsEmpty() throws Exception {
        List<Note> noteList = markService.getNotesWithMarks(Collections.emptyList());
        assertThat(noteList).isNull();
        verifyZeroInteractions(noteRepository);
    }

    @Test
    public void getNotesWithMarks_WhenListIsNull() throws Exception {
        List<Note> noteList = markService.getNotesWithMarks(null);
        assertThat(noteList).isNull();
        verifyZeroInteractions(noteRepository);
    }

}