package com.github.kiris.zaim4s

import java.time.{LocalDate, LocalDateTime}

object GetMoneys {

  case class RequestCondition(
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

  case class Response(
      money: List[Money],
      requested: Long
  )
  case class GroupByReceiptIdResponse(
      money: List[MoneyOrReceipt],
      requested: Long
  )


  sealed trait MoneyOrReceipt

  final case class Money(
      id: Long,
      amount: Long,
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
      receiptId: Long,
      place: String,
      placeUid: Option[String]
  ) extends MoneyOrReceipt

  final case class Receipt(
      amount: Long,
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
      receiptId: Long,
      amount: Long,
      name: String,
      categoryId: Int,
      genreId: Int,
      comment: String,
      active: Int,
      created: LocalDateTime
  )
}