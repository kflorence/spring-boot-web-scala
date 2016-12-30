package com.kflorence.springBootWeb.scala.application.json

import java.util.TimeZone

import com.fasterxml.jackson.databind.{DeserializationFeature, Module, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.kflorence.springBootWeb.scala.application.json.Json.JacksonJsonMapper
import org.springframework.context.annotation.{Bean, Configuration, Primary}

@Configuration
class JsonConfiguration {

  @Bean
  def scalaModule: DefaultScalaModule = new DefaultScalaModule

  @Bean
  @Primary
  def objectMapper(modules: java.util.List[Module]): JacksonJsonMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    // Use ISO-8601 compliant notation instead of timestamps
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
    mapper.registerModules(modules)
    // UTC time
    mapper.setTimeZone(TimeZone.getTimeZone("GMT"))
    mapper
  }
}
