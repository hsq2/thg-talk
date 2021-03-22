package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.User;
import com.thg.accelerator.THGTalk.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserServiceTest {

  private final User user1 = new User(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "User 1",
      convertStringToDate("2017-03-31 9:30:20"));

  private final User user2 = new User(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      "User 2",
      convertStringToDate("2017-03-31 9:30:20"));

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  public UserServiceTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(userService.save(user1)).thenReturn(true);
    when(userRepository.save(user2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    userService.save(user1);
    verify(userRepository, times(1)).save(user1);
    assertThrows(ServiceException.class, () -> userService.save(user2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(userRepository.update(user1)).thenReturn(true);
    when(userRepository.update(user2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    userService.update(user1);
    verify(userRepository, times(1)).update(user1);
    assertThrows(ServiceException.class, () -> userService.update(user2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(userRepository.deleteByID(user1.getUserID())).thenReturn(true);
    when(userRepository.deleteByID(user2.getUserID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    userService.deleteByID(user1.getUserID());
    verify(userRepository, times(1)).deleteByID(user1.getUserID());
    assertThrows(ServiceException.class, () -> userService.deleteByID(user2.getUserID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(userRepository.getModelByID(user1.getUserID())).thenReturn(
        java.util.Optional.of(user1));
    when(userRepository.getModelByID(user2.getUserID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    userService.getModelByID(user1.getUserID());
    verify(userRepository, times(1)).getModelByID(user1.getUserID());
    assertThrows(ServiceException.class,
        () -> userService.getModelByID(user2.getUserID()));
  }

}
