package com.kflorence.springBootWeb.scala.application.converter

import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.core.ResolvableType
import org.springframework.core.convert.{ConversionService, TypeDescriptor}

import scala.reflect.runtime.universe._

/** Converts from type `A` to type `B` using the given ConversionService.
  *
  * @param from The object to convert from.
  * @param conversionService The ConversionService
  * @tparam A Type to convert from
  */
sealed class TypeConverter[A: TypeTag](from: A, conversionService: ConversionService) {
  protected[converter] val fromTypeDescriptor = TypeConverter.getTypeDescriptor[A]

  /** Performs conversion from type `A` to type `B`.
    *
    * @tparam B The type to convert to.
    * @return An instance of the class we are converting to.
    */
  def convertTo[B: TypeTag]: B =
    conversionService.convert(from, fromTypeDescriptor, TypeConverter.getTypeDescriptor[B]).asInstanceOf[B]
}

object TypeConverter {

  /** Provides an alternate constructor for [[TypeDescriptor]] that allows creation from a [[ResolvableType]].
    *
    * @param resolvableType The resolvable type.
    * @param classType The class type.
    * @param annotations Class annotations.
    */
  class ResolvableTypeDescriptor(
    resolvableType: ResolvableType,
    classType: Class[_],
    annotations: Array[java.lang.annotation.Annotation]
  ) extends TypeDescriptor(resolvableType, classType, annotations)

  /** Provides an alternate constructor to [[TypeDescriptor]] that converts the given [[TypeDescriptor]] into the
    * generic sub-type that it contains.
    *
    * @param typeDescriptor The type descriptor to extract the generic type from.
    */
  class GenericTypeDescriptor(typeDescriptor: TypeDescriptor) extends TypeDescriptor(
    typeDescriptor.getResolvableType.getGenerics.head,
    null,
    typeDescriptor.getAnnotations
  )

  /** Get a [[TypeDescriptor]] from a [[TypeTag]].
    *
    * @tparam A The underlying scala type.
    * @return A TypeDescriptor.
    */
  def getTypeDescriptor[A: TypeTag]: TypeDescriptor = {
    val tt = typeTag[A]
    val tpe = tt.tpe

    // Get the Java class object for the type
    val runtimeClass = tt.mirror.runtimeClass(tpe)

    // Get all of the type arguments for the class
    val typeArgs = tpe.typeArgs.map(tt.mirror.runtimeClass)

    // Construct a resolvable type given the java class and the type arguments
    val resolvableType = ResolvableType.forClassWithGenerics(runtimeClass, typeArgs: _*)

    // Construct a type descriptor which represents the resolvable type of the given class
    new ResolvableTypeDescriptor(resolvableType, runtimeClass, runtimeClass.getAnnotations)
  }
}

/** Extend this trait to provide automatic conversions inside your class. */
trait TypeConversionSupport {
  import scala.language.implicitConversions

  /** Provides a [[ConversionService]] for use inside REST controllers. This needs to be qualified specifically for
    * use by Spring MVC to prevent collisions with other `ConversionService` objects Spring boot supplies automatically.
    */
  @Autowired
  @Qualifier("mvcConversionService")
  var conversionService: ConversionService = _

  /** Implicitly provide conversions for the given type `A`.
    *
    * @param from The object to convert from.
    * @tparam A The type of the object.
    * @return The Conversion class for type `A`.
    */
  implicit def typeConverter[A: Manifest](from: A): TypeConverter[A] = new TypeConverter[A](from, conversionService)
}
