trait A {
  def func: String
}

trait A1 extends A {
  override def func: String = "A1"
}

trait A2 extends A {
  override def func: String = "A2"
}

class A1A2 extends A1 with A2
class A2A1 extends A2 with A1

val a1a2 = new A1A2
val a2a1 = new A2A1

a1a2.func // A2
a2a1.func // A1


