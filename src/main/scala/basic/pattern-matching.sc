case class BrandCategory(id: Int, name: String)
case class Brand(id: Int, brandCategory: Option[BrandCategory])
case class Item(id: Int, brand: Option[Brand])

val item1 = Some(Item(1, Some(Brand(1, Some(BrandCategory(3, "item1 category name"))))))
val item2 = Some(Item(2, Some(Brand(2, Some(BrandCategory(4, "item2 category name"))))))

def printOnlyBrand1CategoryWithIf(item: Option[Item]) = {
  if (item.isDefined
    && item.get.brand.isDefined
    && item.get.brand.get.id == 1
    && item.get.brand.get.brandCategory.isDefined)
    item.get.brand.get.brandCategory.get.name
  else
    "empty"
}

printOnlyBrand1CategoryWithIf(item1)
printOnlyBrand1CategoryWithIf(item2)
printOnlyBrand1CategoryWithIf(None)


def printOnlyBrand1Category(item: Option[Item]) = {
  item match {
    case Some(Item(_, Some(Brand(1, Some(BrandCategory(_, name)))))) => name
    case _ => "empty"
  }
}

printOnlyBrand1Category(item1)
printOnlyBrand1Category(item2)
printOnlyBrand1Category(None)


trait Shape
class Circle extends Shape
class Rectangle extends Shape
class Other extends Shape

def call(shape: Shape) = shape match {
  case sh : Shape if sh.isInstanceOf[Circle] || sh.isInstanceOf[Rectangle] =>
    println(sh.getClass.getName)
  case _ : Shape =>
    println("other")
}

call(new Circle)
call(new Rectangle)
call(new Other)