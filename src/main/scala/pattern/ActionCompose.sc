trait Action[A, B] { self =>
  def apply(a: A): B = invoke(a)

  def invoke(a: A): B

  def andThen[C](other: Action[B, C]): Action[A, C] = new Action[A, C]{
    def invoke(a: A): C =
      other.invoke(self.invoke(a))
  }
}

case class BaseUser(id: Int)
case class SimpleUser(id: Int, name: String)
case class DetailUser(id: Int, name: String, tel: String)

val getBaseUserAction = new Action[Int, BaseUser] {
  override def invoke(id: Int) = BaseUser(id)
}

val getSimpleUser = new Action[BaseUser, SimpleUser] {
  override def invoke(user: BaseUser) = SimpleUser(user.id, "이름")
}

val getDetailUser = new Action[SimpleUser, DetailUser] {
  override def invoke(user: SimpleUser) = DetailUser(user.id, user.name, "010-0000-0000")
}


getBaseUserAction(1)
getBaseUserAction.andThen(getSimpleUser)(1)
getBaseUserAction.andThen(getSimpleUser).andThen(getDetailUser)(1)
