package zaim4s

import java.time.LocalDateTime

final case class VerifyUserResponse(
  me: User
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
  coverImageUrl: String,
  dataModified: Option[LocalDateTime]
)
