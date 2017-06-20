package zaim4s

final case class GetGenresResponse(
  genres: List[Genre]
) {
  def toMap: Map[Int, Genre] = genres.map { genre => genre.id -> genre }.toMap
}

final case class Genre(
  id: Int,
  categoryId: Int,
  name: String
)