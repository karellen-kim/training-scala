package json
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.parser._

sealed trait Item {
  def name: String
}
case class ItemInt(name: String, key: Int) extends Item
case class ItemString(name: String, key: String) extends Item
case class ItemArray(name: String, key: List[Int]) extends Item

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

  implicit val decodeItem: Decoder[Item] =
    List[Decoder[Item]](
      Decoder[ItemInt].widen,
      Decoder[ItemString].widen,
      Decoder[ItemArray].widen,
    ).reduceLeft(_ or _)

  val parseInt = decode[Item](typeInt)
  println(parseInt)

  val parseArray = decode[Item](typeArray)
  println(parseArray)
}
