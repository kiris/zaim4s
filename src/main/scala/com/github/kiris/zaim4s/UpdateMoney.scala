package com.github.kiris.zaim4s

import java.time.LocalDateTime

object UpdateMoney {

  case class Response(
      stamps: Option[Stamps],
      money: Money,
      user: User,
      requested: Long
  )

  final case class Stamps(
      kiriban: Option[Stamp],
      repeat: Option[Stamp],
      first: Option[Stamp]
  )

  final case class Stamp(
      imageUrl: String,
      name: String,
      description: String
  )

  final case class User(
      inputCount: Int,
      repeatCount: Option[Int],
      dayCount: Option[Int]
  )

  final case class Money(
      id: Long,
      modified: LocalDateTime
  )

}