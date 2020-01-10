package com.sktj;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sktj.controller.CaveAchievsController;
import com.sktj.service.CaveAchievementsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@WebMvcTest(CaveAchievsController.class)
class ParserApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CaveAchievementsService service;

//  @Before
//  public void setupMock() {
//    when(service.findById(2L).getNotificationTimestamp())
//        .thenReturn(LocalDateTime.parse("2010-11-21T21:51:50"));
//  }

  @Test
  void contextLoads() throws Exception {
    this.mockMvc.perform(get("/caves/id/2")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"notificationTimestamp\":\"2010-11-21T21:51:50\",\"country\":{\"name\":"
            + "\"Polska\"},\"notificationAuthor\":{\"name\":\"Jan\",\"surname\":\"Kowalski\","
            + "\"email\":\"j.kowalski@przykład.com\"},\"anotherAuthors\":\"\",\"comment\":\"\","
            + "\"authors\":[{\"name\":\"Jan\",\"surname\":\"Kowalski\",\"email\""
            + ":\"j.kowalski@przykład.com\"}],\"entryTimestamp\":\"2010-02-06T00:00:00\","
            + "\"exitTimestamp\":\"2010-02-06T01:00:00\",\"caveName\":{\"name\":\"Zimna\",\"region\""
            + ":\"Tatry\"},\"reachedParts\":\"Czarny Komin\",\"caveOvercomeStyle\":\"SPORTOWY\"}"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andReturn();
  }
}
