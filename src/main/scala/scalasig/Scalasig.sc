import reflect.runtime.universe._

object Foo

val clazz = typeOf[Foo.type].typeSymbol.asClass
clazz.isModule
clazz.companionSymbol.isModule

