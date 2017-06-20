package zaim4s

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json._

object Formats {
  implicit val dateReads: Reads[LocalDate] = Reads.localDateReads("yyyy-MM-dd")

  implicit val dateWrites: Writes[LocalDate] = Writes.DefaultLocalDateWrites

  implicit val localDateTimeReads: Reads[LocalDateTime] = Reads.localDateTimeReads("yyyy-MM-dd HH:mm:ss")

  implicit val localDateTimeWrites: Writes[LocalDateTime] =
    Writes.temporalWrites[LocalDateTime, DateTimeFormatter](DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

  implicit val modeFormat: Format[Mode] = Format[Mode](
    {
      case JsString(str) if str == Payment.raw => JsSuccess(Payment)
      case JsString(str) if str == Income.raw => JsSuccess(Income)
      case str => JsError(s"'${str}' mode is unknown.")
    }, {
      mode => JsString(mode.raw)
    })


  implicit val userFormat: Format[User] = JsonNaming.snakecase(Json.format[User])

  implicit val userVerifyResponseFormat: Format[VerifyUserResponse] = JsonNaming.snakecase(Json.format[VerifyUserResponse])


  implicit val moneyFormat: Format[Money] = JsonNaming.snakecase(Json.format[Money])

  implicit val dataFormat: Format[Data] = JsonNaming.snakecase(Json.format[Data])

  implicit val receiptFormat: Format[Receipt] = JsonNaming.snakecase(Json.format[Receipt])

  implicit val moneyOrReceiptFormat: Format[MoneyOrReceipt] = new Format[MoneyOrReceipt] {
    override def writes(o: MoneyOrReceipt): JsValue = o match {
      case m: Money => moneyFormat.writes(m)
      case r: Receipt => receiptFormat.writes(r)
    }

    override def reads(json: JsValue): JsResult[MoneyOrReceipt] =
      (json \ "receipt_id").validate[Int].flatMap {
        case 0 => moneyFormat.reads(json)
        case _ => receiptFormat.reads(json)
      }
  }

  implicit val moneyResponseReads: Reads[GetMoneysResponse] = JsonNaming.snakecase(Json.reads[GetMoneysResponse])

  // FIXME: why compile failed.
  // implicit val moneyResponseWrites: Writes[MoneyResponse] = Json.writes[MoneyResponse]

  implicit val moneyGroupByReceiptResponseFormat: Format[GetMoneysGroupByReceiptIdResponse] = JsonNaming.snakecase(Json.format[GetMoneysGroupByReceiptIdResponse])


  implicit val accountFormat: Format[Account] = JsonNaming.snakecase(Json.format[Account])

  implicit val accountResponseFormat: Format[GetAccountsResponse] = JsonNaming.snakecase(Json.format[GetAccountsResponse])


  implicit val categoryFormat: Format[Category] = JsonNaming.snakecase(Json.format[Category])

  implicit val categoryResponseFormat: Format[GetCategoriesResponse] = JsonNaming.snakecase(Json.format[GetCategoriesResponse])


  implicit val genreFormat: Format[Genre] = JsonNaming.snakecase(Json.format[Genre])

  implicit val genreResponseFormat: Format[GetGenresResponse] = JsonNaming.snakecase(Json.format[GetGenresResponse])


}

