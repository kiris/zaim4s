package com.github.kiris.zaim4s

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json._

object Formats extends
    VerifyUserFormats with
    GetMoneysFormats with
    CreateMoneyFormats with
    UpdateMoneyFormats with
    DeleteMoneyFormats with
    GetAccountsFormats with
    GetCategoriesFormats with
    GetGenresFormats



trait DateTimeFormats {
  import java.time.format.DateTimeFormatter
  import java.time.{LocalDate, LocalDateTime}

  implicit val dateReads: Reads[LocalDate] = Reads.localDateReads("yyyy-MM-dd")

  implicit val dateWrites: Writes[LocalDate] = Writes.DefaultLocalDateWrites

  implicit val localDateTimeReads: Reads[LocalDateTime] = Reads.localDateTimeReads("yyyy-MM-dd HH:mm:ss")

  implicit val localDateTimeWrites: Writes[LocalDateTime] =
    Writes.temporalWrites[LocalDateTime, DateTimeFormatter](DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

trait ModeFormats {
  implicit object ModeFormat extends Format[Mode] {
    override def reads(j: JsValue): JsResult[Mode] = j match {
      case JsString(str) if str == Payment.raw => JsSuccess(Payment)
      case JsString(str) if str == Income.raw => JsSuccess(Income)
      case JsString(str) if str == Transfer.raw => JsSuccess(Transfer)
      case str => JsError(s"'${str}' mode is unknown.")
    }

    override def writes(mode: Mode): JsValue =
      JsString(mode.raw)
  }
}

trait VerifyUserFormats extends DateTimeFormats {
  import VerifyUser._
  import com.github.tototoshi.play.json.JsonNaming
  import play.api.libs.json._

  implicit val verifyUserUserFormat: Format[User] = JsonNaming.snakecase(Json.format[User])

  implicit val verifyUserResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])
}

trait GetMoneysFormats extends DateTimeFormats with ModeFormats {
  import GetMoneys._

  implicit val getMoneysDataFormat: Format[Data] = JsonNaming.snakecase(Json.format[Data])

  implicit val getMoneysMoneyFormat: Format[Money] = JsonNaming.snakecase(Json.format[Money])

  implicit val getMoneysReceiptFormat: Format[Receipt] = JsonNaming.snakecase(Json.format[Receipt])

  implicit val getMoneysMoneyOrReceiptFormat: Format[MoneyOrReceipt] = new Format[MoneyOrReceipt] {
    override def writes(o: MoneyOrReceipt): JsValue = o match {
      case m: Money => getMoneysMoneyFormat.writes(m)
      case r: Receipt => getMoneysReceiptFormat.writes(r)
    }

    override def reads(json: JsValue): JsResult[MoneyOrReceipt] =
      (json \ "receipt_id").validate[Int].flatMap {
        case 0 => getMoneysMoneyFormat.reads(json)
        case _ => getMoneysReceiptFormat.reads(json)
      }
  }

  implicit val getMoneysResponseReads: Reads[Response] = JsonNaming.snakecase(Json.reads[Response])

  // FIXME: why compile failed.
  // implicit val getMoneysResponseWrites: Writes[Response] = Json.writes[Response]

  implicit val getMoneysGroupByReceiptResponseFormat: Format[GroupByReceiptIdResponse] = JsonNaming.snakecase(Json.format[GroupByReceiptIdResponse])
}


trait CreateMoneyFormats extends DateTimeFormats {
  import CreateMoney._

  implicit val createMoneyMoneyFormat: Format[Money] = JsonNaming.snakecase(Json.format[Money])

  implicit val createMoneyUserFormat: Format[User] = JsonNaming.snakecase(Json.format[User])

  implicit val createMoneyStampFormat: Format[Stamp] = JsonNaming.snakecase(Json.format[Stamp])

  implicit val createMoneyStampsFormat: Format[Stamps] = JsonNaming.snakecase(Json.format[Stamps])

  implicit val createMoneyResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])

}

trait UpdateMoneyFormats extends DateTimeFormats {
  import UpdateMoney._

  implicit val updateMoneyMoneyFormat: Format[Money] = JsonNaming.snakecase(Json.format[Money])

  implicit val updateMoneyUserFormat: Format[User] = JsonNaming.snakecase(Json.format[User])

  implicit val updateMoneyStampFormat: Format[Stamp] = JsonNaming.snakecase(Json.format[Stamp])

  implicit val updateMoneyStampsFormat: Format[Stamps] = JsonNaming.snakecase(Json.format[Stamps])

  implicit val updateMoneyResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])

}

trait DeleteMoneyFormats extends DateTimeFormats {
  import DeleteMoney._

  implicit val deleteMoneyMoneyFormat: Format[Money] = JsonNaming.snakecase(Json.format[Money])

  implicit val deleteMoneyUserFormat: Format[User] = JsonNaming.snakecase(Json.format[User])

  implicit val deleteMoneyStampFormat: Format[Stamp] = JsonNaming.snakecase(Json.format[Stamp])

  implicit val deleteMoneyStampsFormat: Format[Stamps] = JsonNaming.snakecase(Json.format[Stamps])

  implicit val deleteMoneyResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])

}
trait GetAccountsFormats extends DateTimeFormats {
  import GetAccounts._

  implicit val getAccountsAccountFormat: Format[Account] = JsonNaming.snakecase(Json.format[Account])

  implicit val getAccountsAccountResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])
}

trait GetCategoriesFormats extends ModeFormats {
  import GetCategories._

  implicit val getCategoriesCategoryFormat: Format[Category] = JsonNaming.snakecase(Json.format[Category])

  implicit val getCategoriesCategoryResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])

}

trait GetGenresFormats {
  import GetGenres._

  implicit val genreFormat: Format[Genre] = JsonNaming.snakecase(Json.format[Genre])

  implicit val genreResponseFormat: Format[Response] = JsonNaming.snakecase(Json.format[Response])

}
