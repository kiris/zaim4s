package zaim4s

import java.time.{LocalDate, LocalDateTime}

case class MoneyRequestCondition(
  categoryId: Option[Int] = None,
  genreId: Option[Int] = None,
  mode: Option[Mode] = None,
  order: Option[String] = None,
  startDate: Option[LocalDate] = None,
  endDate: Option[LocalDate] = None,
  page: Int = 1,
  limit: Int = 20
) {
  def toQueryParameters: Map[String, List[String]] =
    List(
      categoryId.map { c => "category_id" -> List(c.toString) },
      genreId.map { g => "genre_id" -> List(g.toString) },
      mode.map { m => "mode" -> List(m.toString) },
      order.map { o => "order" -> List(o) },
      startDate.map { sd => "start_date" -> List(sd.toString) },
      endDate.map { ed => "end_date" -> List(ed.toString) }
    ).collect {
      case Some(v) => v
    }.toMap ++ Map(
      "page" -> List(page.toString),
      "limit" -> List(limit.toString)
    )
}

case class MoneyResponse(
  money: List[Money]
)

case class MoneyGroupByReceiptResponse(
  money: List[MoneyOrReceipt]
)

sealed trait MoneyOrReceipt
final case class Money(
  id: Long,
  amount: Int,
  name: String,
  userId: Int,
  date: LocalDate,
  mode: Mode,
  categoryId: Int,
  genreId: Int,
  fromAccountId: Int,
  toAccountId: Int,
  comment: String,
  active: Int,
  created: LocalDateTime,
  currencyCode: String,
  receiptId: Int,
  place: String,
  placeUid: Option[String]
) extends MoneyOrReceipt

final case class Receipt(
  amount: Int,
  toAccountId: Int,
  fromAccountId: Int,
  date: LocalDate,
  receiptId: Int,
  mode: Mode,
  place: String,
  placeUid: Option[String],
  categoryId: Int,
  genreId: Int,
  currencyCode: String,
  data: List[Data]
) extends MoneyOrReceipt

final case class Data(
  id: Long,
  receiptId: Int,
  amount: Int,
  name: String,
  categoryId: Int,
  genreId: Int,
  comment: String,
  active: Int,
  created: LocalDateTime
)

