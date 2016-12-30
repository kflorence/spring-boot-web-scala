package com.kflorence.springBootWeb.scala.application.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.convert.converter.{Converter, GenericConverter}
import org.springframework.core.convert.support.GenericConversionService
import org.springframework.stereotype.Component

import scala.collection.convert.WrapAsScala

/** Provides late registration of [[Converter]] and [[GenericConverter]] so that they will be available to Spring MVC
  * and have access to the Spring MVC [[org.springframework.core.convert.ConversionService]].
  *
  * @see https://jira.spring.io/browse/SPR-6415
  */
@Component
class ConverterRegistry extends ApplicationListener[ContextRefreshedEvent] with WrapAsScala {
  @Autowired private[converter] var conversionService: GenericConversionService = _

  @Autowired(required = false) private[converter] var converters: java.util.Set[Converter[_, _]] = _
  @Autowired(required = false) private[converter] var genericConverters: java.util.Set[GenericConverter] = _

  def onApplicationEvent(event: ContextRefreshedEvent): Unit = {
    if (Option(converters).nonEmpty) converters.foreach(conversionService.addConverter)
    if (Option(genericConverters).nonEmpty) genericConverters.foreach(conversionService.addConverter)
  }
}
