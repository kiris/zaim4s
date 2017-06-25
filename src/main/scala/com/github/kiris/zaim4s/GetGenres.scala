package com.github.kiris.zaim4s

object GetGenres {

  final case class Response(
      genres: List[Genre]
  ) {
    def toMap: Map[Int, Genre] = genres.map { genre => genre.id -> genre }.toMap
  }

  final case class Genre(
      id: Int,
      categoryId: Int,
      name: String
  )
}