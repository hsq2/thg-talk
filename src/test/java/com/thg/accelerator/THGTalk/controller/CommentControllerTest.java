package com.thg.accelerator.THGTalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thg.accelerator.THGTalk.SpringDemoApplication;
import com.thg.accelerator.THGTalk.model.Comment;
import com.thg.accelerator.THGTalk.model.User;
import com.thg.accelerator.THGTalk.service.CommentService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentControllerTest {
  protected MockMvc mvc;

  @Autowired
  WebApplicationContext webApplicationContext;
  String uri = "/api/comment";
  Comment model1 = new Comment(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "Comment 1",
      convertStringToDate("2017-03-31 9:30:20"));
  Comment model2 = new Comment(
      UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "Updated Comment 1",
      convertStringToDate("2017-03-31 9:30:20"));

  @MockBean
  private CommentService service;

  public CommentControllerTest() throws ParseException {
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

    when(service.save(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
    status = mvcResult.getResponse().getStatus();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    assertEquals(201, status);
    assertNotEquals("", headerValue);

    when(service.save(isA(Comment.class))).thenReturn(false);
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

    when(service.save(isA(Comment.class))).thenReturn(true);
    when(service.update(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));

    mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/" + uuid.toString())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model2))).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.update(isA(Comment.class))).thenReturn(false);
    mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/" + uuid.toString())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model2))).andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }

  @Test
  public void testDelete() throws Exception {
    MvcResult mvcResult;
    int status;

    when(service.save(isA(Comment.class))).thenReturn(true);
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

    when(service.save(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getModelByID(uuid)).thenReturn(java.util.Optional.of(new Comment(
        uuid,
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
        "Comment 1",
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

    when(service.save(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getAllModels()).thenReturn(Collections.singletonList(new Comment(
        uuid,
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
        UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
        "Comment 1",
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

  @Test
  public void testGetCommentsByPostID() throws Exception {
    MvcResult mvcResult;
    int status;
    String response;

    when(service.save(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getCommentsByPostID(model1.getPostID()))
        .thenReturn(Collections.singletonList(new Comment(
            uuid,
            UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
            UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
            "Comment 1",
            convertStringToDate("2017-03-31 9:30:20"))));

    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/bypost/" + model1.getPostID())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    response = mvcResult.getResponse().getContentAsString();
    assertTrue(response.length() > 2);
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.getCommentsByPostID(isA(UUID.class))).thenReturn(Collections.emptyList());
    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/bypost/" + model1.getPostID())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    response = mvcResult.getResponse().getContentAsString();
    assertEquals(response.length(), 2);
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
  }

  @Test
  public void testGetCommentsByUserID() throws Exception {
    MvcResult mvcResult;
    int status;
    String response;

    when(service.save(isA(Comment.class))).thenReturn(true);
    mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(model1))).andReturn();
    String headerValue = mvcResult.getResponse().getHeader("Location");
    UUID uuid = UUID.fromString(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    when(service.getCommentsByUserID(model1.getUserID()))
        .thenReturn(Collections.singletonList(new Comment(
            uuid,
            UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
            UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
            "Comment 1",
            convertStringToDate("2017-03-31 9:30:20"))));

    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/byuser/" + model1.getUserID())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    response = mvcResult.getResponse().getContentAsString();
    assertTrue(response.length() > 2);
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    when(service.getCommentsByUserID(isA(UUID.class))).thenReturn(Collections.emptyList());
    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/byuser/" + model1.getUserID())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    response = mvcResult.getResponse().getContentAsString();
    assertEquals(response.length(), 2);
    status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
  }
}