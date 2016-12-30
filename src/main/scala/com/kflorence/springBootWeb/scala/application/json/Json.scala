package com.kflorence.springBootWeb.scala.application.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.kflorence.springBootWeb.scala.application.json.Json.JacksonJsonMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/** Simple interface for serializing and deserializing Json using Jackson.
  *
  * @param mapper Jackson [[ObjectMapper]] with [[ScalaObjectMapper]] support.
  */
@Component
class Json @Autowired()(mapper: JacksonJsonMapper) {

  /** Deserialize a JSON string into an Object of the specified type.
    *
    * @param content Json string.
    * @tparam A Type to deserialize to.
    * @return Object representation of the provided JSON string.
    */
  def deserialize[A: Manifest](content: String): A = mapper.readValue[A](content)

  /** Serialize an Object of the specified type into a JSON string.
    *
    * @param value Object to serialize.
    * @tparam A Type of the Object to be serialized.
    * @return JSON string.
    */
  def serialize[A](value: A): String = mapper.writeValueAsString(value)
}

object Json {
  type JacksonJsonMapper = ObjectMapper with ScalaObjectMapper
}
