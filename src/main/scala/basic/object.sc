object Item {
  val ERROR_CODE = "500"

  private val TYPE_1 = "TYPE_1"
  private val TYPE_2 = "TYPE_2"
}

case class Item(id: Int, tpe: String) {
  def getType(): Int = {
    tpe match {
      case Item.TYPE_1 =>
      case Item.TYPE_2 =>
    }
  }
}

Item.ERROR_CODE
//Item.TYPE_1 // ERROR
