package com.kflorence.springBootWeb.scala.test.context

import org.springframework.test.context.ActiveProfilesResolver

import scala.collection.convert.WrapAsJava

/** This class should be applied using the [[org.springframework.test.context.ActiveProfiles]] annotation.
  *
  * @example \@ActiveProfiles(resolver = classOf[IntegrationTestActiveProfilesResolver])
  */
class IntegrationTestActiveProfilesResolver extends ActiveProfilesResolver with WrapAsJava {

  /** Adds the "it" profile to integration tests by default.
    *
    * @param testClass the test class for which the profiles should be resolved.
    * @return An array of profiles.
    */
  def resolve(testClass: Class[_]): Array[String] = {
    val key = "spring.profiles.active"
    val existing = sys.props.get(key).orElse(sys.env.get(key)).toList.flatMap(_.split(","))
    (Set("it") ++ existing).toArray
  }
}
