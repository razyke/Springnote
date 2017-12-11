package com.after.winter.services;

import static org.junit.Assert.*;

import com.after.winter.config.AppConfig;
import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
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
public class TestOnCrudOperations {

  @Autowired
  MarkService markService;

  @Autowired
  NoteService noteService;

  @Autowired
  NotebookService notebookService;

  @Autowired
  UserService userService;


  @Before
  @Transactional
  public void prepareTestDB() throws Exception {
    User user_1 = User.builder()
        .firstName("Fred")
        .lastName("Broxon")
        .email("fbrox@epam.com")
        .password("fred123")
        .build();

    User user_2 = User.builder()
        .firstName("Bruce")
        .lastName("Wayne")
        .email("jockerdead@epam.com")
        .password("batman777")
        .build();

    userService.createUser(user_1);
    userService.createUser(user_2);

    Notebook notebook_1 = Notebook.builder()
        .user(userService.getUserByEmail("fbrox@epam.com"))
        .title("How to get Happy")
        .description("Here is my plan, what should I do")
        .build();

    Notebook notebook_2 = Notebook.builder()
        .user(userService.getUserByEmail("jockerdead@epam.com"))
        .title("How to kill Joker")
        .description("And don't get in jail")
        .build();

    notebookService.createNotebook(notebook_1);
    notebookService.createNotebook(notebook_2);

    Note note_1 = Note.builder()
        .notebook(notebookService.getNotebookByTitle("How to get Happy"))
        .title("I need to forgive my self")
        .body("I know all we make mistakes, but i murdered human"
            + " how can i live with this burden ...")
        .build();

    Note note_2 = Note.builder()
        .notebook(notebookService.getNotebookByTitle("How to kill Joker"))
        .title("I know that i'm hero")
        .body("Good and Evil always fight in infinity wars, my"
            + " foe a man, that killed my family, how can i forgive him ...")
        .build();

    noteService.createNote(note_1);
    noteService.createNote(note_2);

    Mark mark_1 = Mark.builder()
        .type("MAIN")
        .build();

    Mark mark_2 = Mark.builder()
        .type("BAT")
        .build();

    markService.createMark(mark_1);
    markService.createMark(mark_2);

    Note note_mark_1 = noteService.getNoteByTitle("I need to forgive my self");
    Note note_mark_2 = noteService.getNoteByTitle("I know that i'm hero");


    noteService.addMarkToNote(markService.getMarkByType("MAIN"), note_mark_1);
    noteService.addMarkToNote(markService.getMarkByType("BAT"), note_mark_2);

  }

  @Test
  @Transactional
  public void getUsers() throws Exception {
    List<User> allUsers = userService.getAllUsers();
    assertEquals(2, allUsers.size());
  }

  @Test
  @Transactional
  public void getNotebookFromUser1() throws Exception {
    User user = userService.getUserByEmail("fbrox@epam.com");
    assertEquals("How to get Happy",user.getNotebooks().get(0).getTitle());
  }

  @Test
  @Transactional
  public void getNoteFromNotebook1FromUser2() throws Exception {
    User user = userService.getUserByEmail("jockerdead@epam.com");
    assertEquals("I know that i'm hero", user.getNotebooks().get(0).getNotes().get(0).getTitle());
  }

  @Test
  @Transactional
  public void getAllNotesWithMark() throws Exception {
    User user = userService.getUserByEmail("jockerdead@epam.com");
    Mark mark = markService.getMarkByType("BAT");
    List<Note> notesWithMark = noteService.getAllNotesByTag(mark, user);
    assertEquals("I know that i'm hero", notesWithMark.get(0).getTitle());
  }

  @Test
  @Transactional
  public void createNewUserWith2NewNotebookAndUpdateNotebook() throws Exception {
    User user = User.builder()
        .firstName("Morgan")
        .lastName("Freeman")
        .password("freckles")
        .email("wormholeking@epam.com")
        .build();
    userService.createUser(user);
    Notebook notebook = Notebook.builder()
        .user(userService.getUserByEmail("wormholeking@epam.com"))
        .title("What in galaxy?")
        .description("New day become, new mystery born")
        .build();
    notebookService.createNotebook(notebook);
    Notebook notebook2 = Notebook.builder()
        .user(userService.getUserByEmail("wormholeking@epam.com"))
        .title("Boring things")
        .description("Shopping list from stupid wife")
        .build();
    notebookService.createNotebook(notebook2);

    assertEquals("Boring things", userService.getUserByEmail("wormholeking@epam.com")
        .getNotebooks().get(1).getTitle());
    Notebook changedNotebook = userService.getUserByEmail("wormholeking@epam.com").getNotebooks().get(1);
    changedNotebook.setDescription("Shopping list from my clever wife");
    notebookService.updateNotebook(changedNotebook);
    assertEquals("Shopping list from my clever wife", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getDescription());

    //part 2
    //add2NotesChange1NoteDelete2checkAndDeleteUser
    Notebook notebook777 = notebookService.getNotebookByTitle("Boring things");
    Note note = Note.builder()
        .notebook(notebook777)
        .title("First magazine")
        .body("Buy chicken and sugar")
        .build();
    noteService.createNote(note);
    Note note2 = Note.builder()
        .notebook(notebook777)
        .title("Second magazine")
        .body("Buy paint for door")
        .build();
    noteService.createNote(note2);
    assertEquals("Buy chicken and sugar", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getNotes().get(0)
        .getBody());
    Note changedNote = noteService.getNoteByTitle("First magazine");
    changedNote.setBody("Buy a gun");
    noteService.updateNote(changedNote);
    assertEquals("Buy a gun", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getNotes().get(0)
        .getBody());
    userService.deleteUser(userService.getUserByEmail("wormholeking@epam.com"));
    if (userService.getUserByEmail("wormholeking@epam.com") == null) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
  }

  @After
  public void tearDown() throws Exception {
    System.out.println("Well done B-)");
  }

}