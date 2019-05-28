case class Iso[A, B](get: A => B)(reverseGet: B => A)



case class User(id: Long, firstName: String, lastName: String)
case class NameDto(first: String, last: String)
case class UserDto(id: Long, name: NameDto)



/**
  *
  */
User(1, "Kare", "Kim")
