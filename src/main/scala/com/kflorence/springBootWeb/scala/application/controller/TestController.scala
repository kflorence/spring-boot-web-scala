package com.kflorence.springBootWeb.scala.application.controller

import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

@RestController
class TestController {

  @GetMapping(Array("/optionalParameter"))
  def optionalParameter(@RequestParam optional: Option[String]): Map[String, String] =
    Map("result" -> optional.getOrElse("None"))
}
