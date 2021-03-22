package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.SubTalkJSON;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SubTalkTransformerTest {

  @Autowired
  SubTalkTransformer transformer;

  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDSub = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  SubTalk subTalk = new SubTalk(UUIDSub, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com", date);
  SubTalkJSON subTalkJSON =
      new SubTalkJSON(UUIDSub, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com", date);

  SubTalkTransformerTest() throws ParseException {
  }


  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void toJson() {
    SubTalkJSON newSubTalkJson = transformer.toJson(subTalk);
    assertEquals(newSubTalkJson, subTalkJSON);
  }

  @Test
  void toJsonList() {
    SubTalk newSubTalk =
        new SubTalk(UUIDNew, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com", date);
    SubTalkJSON newSubTalkJSON =
        new SubTalkJSON(UUIDNew, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com", date);
    List<SubTalk> subTalkList = Arrays.asList(subTalk, newSubTalk);
    List<SubTalkJSON> expectedJSONList = Arrays.asList(subTalkJSON, newSubTalkJSON);

    List<SubTalkJSON> actualJSONList = transformer.toJsonList(subTalkList);

    assertEquals(actualJSONList, expectedJSONList);
  }

  @Test
  void fromJson() {
    SubTalk newSubTalk = transformer.fromJson(subTalkJSON);
    assertEquals(newSubTalk, subTalk);
  }

  @Test
  void fromJSONWithUUID() {
    SubTalk newSubTalk = transformer.fromJSONWithUUID(UUIDNew, subTalkJSON);
    SubTalk expectedSubTalk =
        new SubTalk(UUIDNew, UUIDUser, "THG SubTalk", "A SubTalk", "thg.com", date);

    assertEquals(newSubTalk, expectedSubTalk);
  }
}