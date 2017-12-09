package com.after.winter.services;

import static org.junit.Assert.*;

import com.after.winter.config.AppConfig;
import com.after.winter.model.Mark;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MarkServiceTest {

  @Autowired
  MarkService markService;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  @Transactional
  public void createMark() throws Exception {
    Mark mark = new Mark();
    mark.setType("Super");
    boolean operation = markService.createMark(mark);
    System.out.println("******************");
    System.out.println(operation);

    List<Mark> marks = markService.getAllMarks();
    for (Mark m : marks){
      System.out.println(m);
    }

  }

  @After
  public void tearDown() throws Exception {
  }

}