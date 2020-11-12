package json
import cats.syntax.functor._
import io.circe
import io.circe.{Decoder, Json}
import io.circe.generic.auto._
import io.circe.parser._

sealed trait Item {
  def name: String
}
case class ItemInt(name: String, key: Int) extends Item
case class ItemString(name: String, key: String) extends Item
case class ItemArray(name: String, key: List[Int]) extends Item
case class ItemJson(name: String, key: Json) extends Item

object ParseAny extends App {

  val typeInt =
    """{
      |	"name" : "int",
      |	"key" : 1
      |}""".stripMargin

  val typeString =
    """{
      |	"name" : "string",
      |	"key" : "str"
      |}""".stripMargin

  val typeArray =
    """{
      |	"name" : "array",
      |	"key" : [1, 2, 3]
      |}""".stripMargin

  val typeJson =
    """{
      |	"name" : "json",
      |	"key" : {
      |   "a" : {
      |     "b" : 2
      |   }
      | }
      |}""".stripMargin

  implicit val decodeItem: Decoder[Item] =
    List[Decoder[Item]](
      Decoder[ItemInt].widen,
      Decoder[ItemString].widen,
      Decoder[ItemArray].widen,
      Decoder[ItemJson].widen,
    ).reduceLeft(_ or _)

  val parseInt = decode[Item](typeInt)
  printValue(parseInt)

  val parseArray = decode[Item](typeArray)
  printValue(parseArray)

  val parseJson = decode[Item](typeJson)
  printValue(parseJson)

  def printValue(item: Either[circe.Error, Item]) = {
    item.foreach { json =>
      json match {
        case ItemInt(key, value) => println(value)
        case ItemString(key, value) => println(value)
        case ItemArray(key, value) => println(value)
        case ItemJson(key, value) => println(value \\ "a")
      }
    }
  }

}
