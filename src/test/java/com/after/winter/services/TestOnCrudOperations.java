package com.after.winter.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import java.util.Collections;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = AppConfigForTest.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)

/**
 * This pack of tests work only in sequential mode.
 * Check correctly work with local memory h2 db.
 * Integration TEST
 */
public class TestOnCrudOperations {

  @Autowired
  MarkService markService;

  @Autowired
  NoteService noteService;

  @Autowired
  NotebookService notebookService;

  @Autowired
  UserService userService;

  //If need check only one test, change @Test on @Before. (A6, A7 and A8 wired)
  @Test
  public void A1_prepareTestDB() throws Exception {

    User user_1 = User.builder()
        .firstname("Fred")
        .lastname("Broxon")
        .email("fbrox@epam.com")
        .password("fred123")
        .build();

    User user_2 = User.builder()
        .firstname("Bruce")
        .lastname("Wayne")
        .email("jockerdead@epam.com")
        .password("batman777")
        .build();

    User createdUser_1 = userService.createUser(user_1);
    User createdUser_2 = userService.createUser(user_2);

    Notebook notebook_1 = Notebook.builder()
        .user(createdUser_1)
        .title("How to get Happy")
        .description("Here is my plan, what should I do")
        .build();

    Notebook notebook_2 = Notebook.builder()
        .user(createdUser_2)
        .title("How to kill Joker")
        .description("And don't get in jail")
        .build();

    Notebook createdNotebook_1 = notebookService.createNotebook(notebook_1);
    Notebook createdNotebook_2 = notebookService.createNotebook(notebook_2);

    Note note_1 = Note.builder()
        .notebook(createdNotebook_1)
        .title("I need to forgive my self")
        .body("I know all we make mistakes, but i murdered human"
            + " how can i live with this burden ...")
        .build();

    Note note_2 = Note.builder()
        .notebook(createdNotebook_2)
        .title("I know that i'm hero")
        .body("Good and Evil always fight in infinity wars, my"
            + " foe a man, that killed my family, how can i forgive him ...")
        .build();

    Note createdNote_1 = noteService.createNote(note_1);
    Note createdNote_2 = noteService.createNote(note_2);

    Mark mark_1 = Mark.builder()
        .type("MAIN")
        .user(createdUser_1)
        .build();

    Mark mark_2 = Mark.builder()
        .type("BAT")
        .user(createdUser_2)
        .build();

    Mark createdMark1 = markService.createMark(mark_1);
    Mark createdMark2 = markService.createMark(mark_2);

    Note note_mark_1 = noteService.getNoteByIdAndNotebookId(createdNote_1.getId(),
        createdNotebook_1.getId());

    Note note_mark_2 = noteService.getNoteByIdAndNotebookId(createdNote_2.getId(),
        createdNotebook_2.getId());

    noteService.addMarkToNote(
        createdMark1,
        note_mark_1);

    noteService.addMarkToNote(
        createdMark2,
        note_mark_2);

  }

  @Test
  public void A2_getUsers() throws Exception {

    List<User> allUsers = userService.getAllUsers();
    assertEquals(2, allUsers.size());
  }

  @Test
  public void A3_getNotebookFromUser1() throws Exception {
    User user = userService.getUserByEmail("fbrox@epam.com");
    assertEquals("How to get Happy",user.getNotebooks().get(0).getTitle());
  }

  @Test
  public void A4_getNoteFromNotebook1FromUser2() throws Exception {
    User user = userService.getUserByEmail("jockerdead@epam.com");
    assertEquals("I know that i'm hero", user.getNotebooks().get(0).getNotes().get(0).getTitle());
  }


  @Test
  public void A5_getAllNotesWithMark() throws Exception {
    User user = userService.getUserByEmail("jockerdead@epam.com");
    Mark mark = markService.getMarkByIdAndUserId(user.getMarks().get(0).getId(), user.getId());
    assertEquals("I know that i'm hero", mark.getNotes().get(0).getTitle());
  }

  @Test
  public void A6_createNewUserWith2NewNotebookAndUpdateNotebook() throws Exception {
    User user = User.builder()
        .firstname("Morgan")
        .lastname("Freeman")
        .password("freckles")
        .email("wormholeking@epam.com")
        .build();

    User createdUser = userService.createUser(user);

    Notebook notebook = Notebook.builder()
        .user(createdUser)
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
    Notebook changedNotebook = userService.getUserByEmail("wormholeking@epam.com").getNotebooks()
        .get(1);
    changedNotebook.setDescription("Shopping list from my clever wife");
    notebookService.updateNotebook(changedNotebook);
    assertEquals("Shopping list from my clever wife", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getDescription());
  }

  @Test
  public void A7_add2NotesChange1NoteDelete2DeleteAndCheckUser() {
    User user = userService.getUserByEmail("wormholeking@epam.com");

    Notebook notebook777 = notebookService.getNotebookByIdAndUserId(
        user.getNotebooks().get(1).getId(),user.getId());

    Note note = Note.builder()
        .notebook(notebook777)
        .title("First magazine")
        .body("Buy chicken and sugar")
        .build();

    Note createdNote1 = noteService.createNote(note);

    Note note2 = Note.builder()
        .notebook(notebook777)
        .title("Second magazine")
        .body("Buy paint for door")
        .build();
    noteService.createNote(note2);

    assertEquals("Buy chicken and sugar", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getNotes().get(0)
        .getBody());

    Note changedNote = noteService.getNoteByIdAndNotebookId(
        createdNote1.getId(),createdNote1.getNotebook().getId());

    changedNote.setBody("Buy a gun");

    noteService.updateNote(changedNote);

    assertEquals("Buy a gun", userService
        .getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getNotes().get(0)
        .getBody());

  }

  @Test
  public void A8_setMarksOn2NoteUnmarkFromNoteDeleteMarkNotesNotebooksAndUser() {

    User user = userService.getUserByEmail("wormholeking@epam.com");

    Mark mark = Mark.builder()
        .type("STAR")
        .user(user)
        .build();

    markService.createMark(mark);

    Note note = userService.getUserByEmail("wormholeking@epam.com")
        .getNotebooks().get(1).getNotes().get(0);


    User forMark = userService.getUserByEmail("wormholeking@epam.com");
    Mark star = markService.getMarkByIdAndUserId(forMark.getMarks().get(0).getId(),forMark.getId());

    noteService.addMarkToNote(star, note);

    assertEquals("STAR",
        userService.getUserByEmail("wormholeking@epam.com").getNotebooks().get(1).getNotes().
            get(0).getMarks().get(0).getType());


    User userForUnmark = userService.getUserByEmail("wormholeking@epam.com");

    noteService.removeMarkFromNote(
        userForUnmark.getNotebooks().get(1).getNotes().get(0).getMarks().get(0),
        userForUnmark.getNotebooks().get(1).getNotes().get(0)
    );

    User testUser = userService.getUser(userForUnmark.getId());
    System.out.println(testUser.getId());

    assertEquals(Collections.emptyList(),
        userService.getUserByEmail("wormholeking@epam.com").getNotebooks().get(1)
        .getNotes().get(0).getMarks());

    userService.deleteUser(userService.getUserByEmail("wormholeking@epam.com").getId());
    if (userService.getUserByEmail("wormholeking@epam.com") == null) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }

  }

}