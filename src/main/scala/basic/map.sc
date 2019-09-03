val map = Map((1, "a"), (2, "b"), (3, "c"))
val another = Map((1 -> "a"), (2 -> "b"), (3 -> "c"))

map.get(1)
map.getOrElse(4, "not found")
map.contains(4)
map.map { case (key, value) =>
  s"키=${key}/값=${value}"
}
