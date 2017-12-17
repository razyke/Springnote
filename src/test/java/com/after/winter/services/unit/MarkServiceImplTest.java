package com.after.winter.services.unit;

import com.after.winter.model.Mark;
import com.after.winter.repository.MarkRepository;
import com.after.winter.services.impl.MarkServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarkServiceImplTest {
    private static final Long MARK_ID = 1L;
    private static final String MARK_TYPE = "type";

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    private Mark mark;

    @Before
    public void setUp() throws Exception {
        mark = Mark.builder()
                .id(MARK_ID)
                .type(MARK_TYPE)
                .build();

        when(markRepository.exists(anyLong())).thenReturn(true);
    }

    @Test
    public void getMark_WhenIdNotNullAndMarkExists() throws Exception {
        when(markRepository.getOne(anyLong())).thenReturn(mark);
        Mark returnedMark = markService.getMark(MARK_ID);
        assertThat(returnedMark).isEqualTo(mark);
        verify(markRepository).getOne(anyLong());
    }

    @Test
    public void getMark_WhenIdIsNull() throws Exception {
        Mark returnedMark = markService.getMark(null);
        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
    }

    @Test
    public void getMark_WhenMarkDoesntExsists() throws Exception {
        when(markRepository.exists(anyLong())).thenReturn(false);
        Mark returnedMark = markService.getMark(MARK_ID);
        assertThat(returnedMark).isNull();
        verify(markRepository).exists(anyLong());
    }

    @Test
    public void getMarkByType_WhenTypeIsNotNull() throws Exception {
        when(markRepository.getByType(anyString())).thenReturn(mark);
        Mark returnedMark = markService.getMarkByType(MARK_TYPE);
        assertThat(returnedMark).isEqualTo(mark);
        verify(markRepository).getByType(anyString());
    }

    @Test
    public void getMarkByType_WhenTypeIsNull() throws Exception {
        Mark returnedMark = markService.getMarkByType(null);
        assertThat(returnedMark).isNull();
        verifyZeroInteractions(markRepository);
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

}