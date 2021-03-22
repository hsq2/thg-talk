package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.SubTalk;
import com.thg.accelerator.THGTalk.repository.SubTalkRepository;
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
class SubTalkServiceTest {
  private final SubTalk subTalk1 = new SubTalk(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "SubTalk 1",
      "A subtalk 1 wiki",
      "thg.com",
      convertStringToDate("2017-03-31 9:30:20"));

  private final SubTalk subTalk2 = new SubTalk(
      UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "Subtalk 2",
      "Subtalk 2 wiki",
      "thg.com",
      convertStringToDate("2017-03-31 9:30:20"));

  @Autowired
  private SubTalkService subTalkService;

  @MockBean
  private SubTalkRepository subTalkRepository;

  public SubTalkServiceTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(subTalkService.save(subTalk1)).thenReturn(true);
    when(subTalkRepository.save(subTalk2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    subTalkService.save(subTalk1);
    verify(subTalkRepository, times(1)).save(subTalk1);
    assertThrows(ServiceException.class, () -> subTalkService.save(subTalk2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(subTalkRepository.update(subTalk1)).thenReturn(true);
    when(subTalkRepository.update(subTalk2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    subTalkService.update(subTalk1);
    verify(subTalkRepository, times(1)).update(subTalk1);
    assertThrows(ServiceException.class, () -> subTalkService.update(subTalk2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(subTalkRepository.deleteByID(subTalk1.getSubTalkID())).thenReturn(true);
    when(subTalkRepository.deleteByID(subTalk2.getSubTalkID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    subTalkService.deleteByID(subTalk1.getSubTalkID());
    verify(subTalkRepository, times(1)).deleteByID(subTalk1.getSubTalkID());
    assertThrows(ServiceException.class, () -> subTalkService.deleteByID(subTalk2.getSubTalkID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(subTalkRepository.getModelByID(subTalk1.getSubTalkID())).thenReturn(
        java.util.Optional.of(subTalk1));
    when(subTalkRepository.getModelByID(subTalk2.getSubTalkID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    subTalkService.getModelByID(subTalk1.getSubTalkID());
    verify(subTalkRepository, times(1)).getModelByID(subTalk1.getSubTalkID());
    assertThrows(ServiceException.class,
        () -> subTalkService.getModelByID(subTalk2.getSubTalkID()));
  }

}