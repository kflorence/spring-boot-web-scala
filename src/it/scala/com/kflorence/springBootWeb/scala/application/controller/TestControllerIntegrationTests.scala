package com.kflorence.springBootWeb.scala.application.controller

import com.kflorence.springBootWeb.scala.Application
import com.kflorence.springBootWeb.scala.test.IntegrationTestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.{MockMvcResultHandlers, MockMvcResultMatchers}

@AutoConfigureMockMvc
@SpringBootTest(classes = Array(classOf[Application]), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerIntegrationTests extends IntegrationTestSpecification {

  @Autowired
  var mvc: MockMvc = _

  "mockMvc" should {
    "be provided" in {
      Option(mvc) must beSome[MockMvc]
    }
  }

  "application" should {
    "be healthy" in {
      val result = mvc.perform(MockMvcRequestBuilders.get("/health"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andReturn()

      result.getResponse.getContentAsString must contain("UP")
    }
  }
}
