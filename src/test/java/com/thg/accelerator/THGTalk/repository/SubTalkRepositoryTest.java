package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.SubTalk;
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
class SubTalkRepositoryTest {

  @Autowired
  SubTalkRepository repository;

  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDSub = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");

  SubTalk subTalk = new SubTalk(UUIDSub, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com",
      convertStringToDate("2017-03-31 9:30:20"));

  SubTalkRepositoryTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void getSubByID() throws RepositoryException {
    Optional<SubTalk> retrievedSub = repository.getModelByID(UUIDSub);
    assertEquals(retrievedSub.get().getSubTalkID(), Optional.of(subTalk).get().getSubTalkID());
    assertEquals(retrievedSub.get().getImageURL(), Optional.of(subTalk).get().getImageURL());
    assertEquals(retrievedSub.get().getUserID(), Optional.of(subTalk).get().getUserID());
    assertEquals(retrievedSub.get().getSubTalkName(), Optional.of(subTalk).get().getSubTalkName());
    assertEquals(retrievedSub.get().getSubTalkDescription(),
        Optional.of(subTalk).get().getSubTalkDescription());
    assertEquals(retrievedSub.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deleteSubByID() throws RepositoryException, ParseException {
    SubTalk newSubTalk = new SubTalk(UUIDToDelete, UUIDUser, "Second sub", "sub", "thg.com",
        convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newSubTalk));
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newSubTalk));
  }

  @Test
  void getAllSubs() throws RepositoryException {
    List<SubTalk> subs = repository.getAllModels();
    assertTrue(1 <= subs.size());
    assertEquals(subs.get(0).getSubTalkID(), Optional.of(subTalk).get().getSubTalkID());
    assertEquals(subs.get(0).getUserID(), Optional.of(subTalk).get().getUserID());
    assertEquals(subs.get(0).getSubTalkName(), Optional.of(subTalk).get().getSubTalkName());
    assertEquals(subs.get(0).getSubTalkDescription(),
        Optional.of(subTalk).get().getSubTalkDescription());
    assertEquals(subs.get(0).getImageURL(), Optional.of(subTalk).get().getImageURL());
    assertEquals(subs.get(0).getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deleteSubByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void addSubAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(subTalk));
  }

  @Test
  void addSub() throws RepositoryException, ParseException {
    SubTalk newSub = new SubTalk(UUIDNew, UUIDUser, "Third Sub", "Sub", "thg.com",
        convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newSub));
    Optional<SubTalk> retrievedSub = repository.getModelByID(UUIDNew);
    assertEquals(retrievedSub.get().getSubTalkID(), Optional.of(newSub).get().getSubTalkID());
    assertEquals(retrievedSub.get().getImageURL(), Optional.of(newSub).get().getImageURL());
    assertEquals(retrievedSub.get().getUserID(), Optional.of(newSub).get().getUserID());
    assertEquals(retrievedSub.get().getSubTalkName(), Optional.of(newSub).get().getSubTalkName());
    assertEquals(retrievedSub.get().getSubTalkDescription(),
        Optional.of(newSub).get().getSubTalkDescription());
    assertEquals(retrievedSub.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void updateSub() throws RepositoryException, ParseException {
    SubTalk newSub = new SubTalk(UUIDToDelete, UUIDUser, "Third Sub", "sub", "thg.com",
        convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.save(newSub));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getSubTalkDescription(), "sub");
    SubTalk updatedSub = new SubTalk(UUIDToDelete, UUIDUser, "Third Sub", "updated sub", "thg.com",
        convertStringToDate("2017-03-31 9:30:20"));
    assertTrue(repository.update(updatedSub));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getSubTalkDescription(),
        "updated sub");
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void updateSubDoesntExist() throws RepositoryException, ParseException {
    SubTalk newSub = new SubTalk(UUIDDoesntExist, UUIDUser, "Third Sub", "sub", "thg.com",
        convertStringToDate("2017-03-31 9:30:20"));
    assertFalse(repository.update(newSub));
  }

}