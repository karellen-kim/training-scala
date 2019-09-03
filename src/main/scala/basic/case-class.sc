case class User(id: Int, name: String)

val user = new User(1, "name")
user.id
user.eq(user)

new User(1, "name") == new User(1, "name")
val copied = user.copy()

user == copied
