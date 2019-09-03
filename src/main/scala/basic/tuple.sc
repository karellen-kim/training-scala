case class Item(id: Int, name: String, price: Int, tpe: String)

val items: Seq[Item] = List(
  Item(1, "아메리카노", 5000, "COUPON"),
  Item(2, "아이스크림 케이크", 20000, "COUPON"),
  Item(3, "교촌치킨", 15000, "COUPON"),
  Item(4, "맥 립스틱", 20000, "DELIVERY"),
  Item(5, "라이언인형", 10000, "DELIVERY")
)

val sorted: Seq[(Item, Int)] = items
  .sortBy(_.price)
  .zipWithIndex

sorted.map {
  data => {
    val item = data._1
    val index = data._2

    (index, item.name)
  }
}

sorted.map { case (item, index) => (index, item.name) }

val myTuple = ("a" -> 1)
