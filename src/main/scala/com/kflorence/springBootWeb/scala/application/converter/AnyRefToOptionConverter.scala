package com.kflorence.springBootWeb.scala.application.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.{ConversionService, TypeDescriptor}
import org.springframework.core.convert.converter.ConditionalGenericConverter
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair
import org.springframework.stereotype.Component

import scala.collection.convert.WrapAsJava

/** Handles conversion of any value to [[Option]] containing any value.
  *
  * @param conversionService Used for conversion of the inner value.
  */
@Component
class AnyRefToOptionConverter @Autowired()(
  conversionService: ConversionService
) extends ConditionalGenericConverter with WrapAsJava {

  def convert(source: AnyRef, sourceType: TypeDescriptor, targetType: TypeDescriptor): AnyRef =
    Option(conversionService.convert(source, sourceType, new TypeConverter.GenericTypeDescriptor(targetType)))

  def getConvertibleTypes: java.util.Set[ConvertiblePair] =
    Set(new ConvertiblePair(classOf[AnyRef], classOf[Option[_]]))

  def matches(sourceType: TypeDescriptor, targetType: TypeDescriptor): Boolean =
    conversionService.canConvert(sourceType, new TypeConverter.GenericTypeDescriptor(targetType))
}
