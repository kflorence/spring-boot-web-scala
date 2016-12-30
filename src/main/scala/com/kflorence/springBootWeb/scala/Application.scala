package com.kflorence.springBootWeb.scala

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/** Spring boot application configuration. */
@ComponentScan(Array("com.kflorence.springBootWeb.scala.application"))
@SpringBootApplication
class Application

object Application {

  /** Spring boot application runner.
    *
    * @param args Command-line arguments.
    */
  def main(args: Array[String]): Unit = {
    val application = new SpringApplication(classOf[Application])
    application.run(args: _*)
  }
}
