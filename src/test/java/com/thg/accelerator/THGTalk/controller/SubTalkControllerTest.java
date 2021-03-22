package com.thg.accelerator.THGTalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thg.accelerator.THGTalk.SpringDemoApplication;
import com.thg.accelerator.THGTalk.model.SubTalk;
import com.thg.accelerator.THGTalk.model.User;
import com.thg.accelerator.THGTalk.service.SubTalkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubTalkControllerTest {
  protected MockMvc mvc;

  @Autowired
  WebApplicationContext webApplicationContext;
  String uri = "/api/subtalk";
  SubTalk model1 = new SubTalk(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "SubTalk 1",
      "A subtalk 1 wiki",
      "thg.com",
      convertStringToDate("2017-03-31 9:30:20"));
  SubTalk model2 = new SubTalk(
      UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "Updated Subtalk 2",
      "Updated Subtalk 2 wiki",
      "Updated thg.com",
      convertStringToDate("2017-03-31 9:30:20"));

  @MockBean
  private SubTalkService service;

  public SubTalkControllerTest() throws ParseException {
  }


  @Before
  public void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  public String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  public void testSave() throws Exception {
    MvcResult mvcResult;
    int status;

    String inputJson = mapToJson(model1);

    when(service.save(isA(SubTalk.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
    status = mvcResult.getResponse().getStatus();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    assertEquals(201, status);
    assertNotEquals("", headerValue);

    when(service.save(isA(SubTalk.class))).thenReturn(false);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
    status = mvcResult.getResponse().getStatus();
    headerValue = mvcResult.getResponse().getHeader("Location");
    assertEquals(202, status);
    assertEquals(null, headerValue);

    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapToJson(new User(null, null, null)))).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(400, status);
  }

  @Test
  public void testUpdate() throws Exception {
    MvcResult mvcResult;
    int status;

    when(service.save(isA(SubTalk.class))).thenReturn(true);
    when(service.update(isA(SubTalk.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));

    mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/" + uuid.toString())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model2))).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.update(isA(SubTalk.class))).thenReturn(false);
    mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/" + uuid.toString())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model2))).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }

  @Test
  public void testDelete() throws Exception {
    MvcResult mvcResult;
    int status;

    when(service.save(isA(SubTalk.class))).thenReturn(true);
    when(service.deleteByID(isA(UUID.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));

    mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.deleteByID(isA(UUID.class))).thenReturn(false);
    mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }

  @Test
  public void testGetModelByID() throws Exception {
    MvcResult mvcResult;
    int status;

    when(service.save(isA(SubTalk.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getModelByID(uuid)).thenReturn(java.util.Optional.of(new SubTalk(
        uuid,
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
        "SubTalk 1",
        "A subtalk 1 wiki",
        "thg.com",
        convertStringToDate("2017-03-31 9:30:20"))));

    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.getModelByID(uuid)).thenReturn(Optional.empty());
    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }

  @Test
  public void testGetAllModels() throws Exception {
    MvcResult mvcResult;
    int status;

    when(service.save(isA(SubTalk.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getAllModels()).thenReturn(Collections.singletonList(new SubTalk(
        uuid,
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
        "SubTalk 1",
        "A subtalk 1 wiki",
        "thg.com",
        convertStringToDate("2017-03-31 9:30:20"))));

    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.getAllModels()).thenReturn(Collections.emptyList());
    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }
}