package zaim4s

object GetCategories {

  final case class Response(
      categories: List[Category]
  ) {
    def toMap: Map[Int, Category] = categories.map { category: Category => category.id -> category }.toMap
  }

  final case class Category(
      id: Int,
      mode: Mode,
      name: String
  )
}