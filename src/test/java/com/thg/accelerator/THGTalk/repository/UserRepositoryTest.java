package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");

  User user = new User(UUIDUser, "user1", convertStringToDate("2017-03-31 9:30:20"));

  UserRepositoryTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void getUserByID() throws RepositoryException {
    Optional<User> retrievedUser = repository.getModelByID(UUIDUser);
    assertEquals(retrievedUser.get().getUserID(), Optional.of(user).get().getUserID());
    assertEquals(retrievedUser.get().getUserName(), Optional.of(user).get().getUserName());
    assertEquals(retrievedUser.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deleteUserByID() throws RepositoryException, ParseException {
    User newUser = new User(UUIDToDelete, "user2", convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newUser));
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newUser));
  }

  @Test
  void getAllUsers() throws RepositoryException {
    List<User> users = repository.getAllModels();
    assertTrue(1 <= users.size());
    assertEquals(users.get(0).getUserID(), Optional.of(user).get().getUserID());
    assertEquals(users.get(0).getUserName(), Optional.of(user).get().getUserName());
    assertEquals(users.get(0).getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deleteUserByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void addUserAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(user));
  }

  @Test
  void addUser() throws RepositoryException, ParseException {
    User newUser = new User(UUIDNew, "user3", convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newUser));
    Optional<User> retrievedUser = repository.getModelByID(UUIDNew);
    assertEquals(retrievedUser.get().getUserID(), Optional.of(newUser).get().getUserID());
    assertEquals(retrievedUser.get().getUserName(), Optional.of(newUser).get().getUserName());
    assertEquals(retrievedUser.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void updateUser() throws RepositoryException, ParseException {
    User newUser = new User(UUIDToDelete, "user4", convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newUser));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getUserName(), "user4");
    User updatedUser =
        new User(UUIDToDelete, "updated user4", convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.update(updatedUser));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getUserName(), "updated user4");
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void updateUserDoesntExist() throws RepositoryException, ParseException {
    User newUser = new User(UUIDDoesntExist, "user5", convertStringToDate("2017-03-31 9:30:20"));
    assertFalse(repository.update(newUser));
  }

}