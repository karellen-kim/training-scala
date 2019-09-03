{
  class User(id: Int, name: String) {
    def sayHello(): Unit = println("hello")
  }

  val user = new User(1, "name")
  // user.id // ERROR
}

{
  class User(var id: Int, var name: String) {
    def sayHello(): Unit = println("hello")
  }

  val user = new User(1, "name")
  println(user.id) // OK

  println(new User(1, "name").equals() == new User(1, "name"))
}
