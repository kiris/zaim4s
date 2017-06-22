package zaim4s

import java.time.LocalDateTime

object CreateMoney {

  case class Response(
      stamps: Stamps,
      money: Money,
      user: User,
      requested: Long
  )

  final case class Stamps(
      kiriban: Stamp,
      repeat: Stamp,
      first: Stamp
  )

  final case class Stamp(
      imageUrl: String,
      name: String,
      description: String
  )

  final case class User(
      inputCount: Int,
      repeatCount: Int,
      dayCount: Int,
      data_modified: LocalDateTime
  )

  final case class Money(
      id: Long,
      modified: LocalDateTime
  )

}