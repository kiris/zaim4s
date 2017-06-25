package zaim4s

import java.time.LocalDateTime

object VerifyUser {

  final case class Response(
      me: User,
      requested: Long
  )

  final case class User(
      id: Long,
      login: String,
      name: String,
      inputCount: Int,
      dayCount: Int,
      repeatCount: Int,
      day: Int,
      week: Int,
      month: Int,
      currencyCode: String,
      profileModified: LocalDateTime,
      profileImageUrl: String,
      coverImageUrl: String
  )
}