package com.kflorence.springBootWeb.scala.test

import com.kflorence.springBootWeb.scala.test.context.IntegrationTestActiveProfilesResolver
import org.specs2.specification.BeforeAll
import org.springframework.test.context.{ActiveProfiles, TestContextManager}

/** Integration test specification for testing anything that requires injection or access to an
  * [[org.springframework.context.ApplicationContext]].
  */
@ActiveProfiles(resolver = classOf[IntegrationTestActiveProfilesResolver])
class IntegrationTestSpecification extends TestSpecification with BeforeAll {

  /** Enable Spring application context, dependency injection, etc. */
  override def beforeAll(): Unit = new TestContextManager(this.getClass).prepareTestInstance(this)
}
