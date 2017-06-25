package com.github.kiris.zaim4s

import java.time.LocalDate

import dispatch._
import dispatch.oauth._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import org.slf4j.LoggerFactory
import play.api.libs.json._
import Formats._

import scala.concurrent.ExecutionContext

case class Zaim(consumerKey: ConsumerKey, accessToken: RequestToken) {
  import Zaim.OptionValueMapOps

  private[this] def baseUrl = "https://api.zaim.net"

  private[this] val logger = LoggerFactory.getLogger(getClass)

  def verifyUser()(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/user/verify").GET
    )

  def getMoneys(
      categoryId: Option[Int] = None,
      genreId: Option[Int] = None,
      mode: Option[Mode] = None,
      order: Option[String] = None,
      startDate: Option[LocalDate] = None,
      endDate: Option[LocalDate] = None,
      page: Int = 1,
      limit: Int = 20
  )(implicit ec: ExecutionContext): Future[GetMoneys.Response] =
    request[GetMoneys.Response](
      url(s"$baseUrl/v2/home/money").GET <<? Map(
        "mapping" -> Some("1"),
        "category_id" -> categoryId,
        "genre_id" -> genreId,
        "mode" -> mode.map(_.raw),
        "order" -> order,
        "start_date" -> startDate,
        "end_date" -> endDate,
        "page" -> Some(page),
        "limit" -> Some(limit)
      ).clean.mapValues(_.toString)
    )

  def getMoneysGroupByReceiptId(
      categoryId: Option[Int] = None,
      genreId: Option[Int] = None,
      mode: Option[Mode] = None,
      order: Option[String] = None,
      startDate: Option[LocalDate] = None,
      endDate: Option[LocalDate] = None,
      page: Int = 1,
      limit: Int = 20
  )(implicit ec: ExecutionContext): Future[GetMoneys.GroupByReceiptIdResponse] =
    request[GetMoneys.GroupByReceiptIdResponse](
      url(s"$baseUrl/v2/home/money").GET <<? Map(
        "mapping" -> Some("1"),
        "group_by" -> Some("receipt_id"),
        "category_id" -> categoryId,
        "genre_id" -> genreId,
        "mode" -> mode.map(_.raw),
        "order" -> order,
        "start_date" -> startDate,
        "end_date" -> endDate,
        "page" -> Some(page),
        "limit" -> Some(limit)
      ).clean.mapValues(_.toString)
    )

  def createPayment(
      amount: Long,
      date: LocalDate,
      categoryId: Int,
      genreId: Int,
      fromAccountId: Option[Int] = None,
      name: Option[String] = None,
      place: Option[String] = None,
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[CreateMoney.Response] =
    request[CreateMoney.Response](
      url(s"$baseUrl/v2/home/money/payment").POST << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "category_id" -> Some(categoryId),
        "genre_id" -> Some(genreId),
        "from_account_id" -> fromAccountId,
        "name" -> name,
        "place" -> place,
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )

  def createIncome(
      amount: Long,
      date: LocalDate,
      categoryId: Int,
      toAccountId: Option[Int] = None,
      place: Option[String] = None,
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[CreateMoney.Response] =
    request[CreateMoney.Response](
      url(s"$baseUrl/v2/home/money/income").POST << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "category_id" -> Some(categoryId),
        "to_account_id" -> toAccountId,
        "place" -> place,
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )


  def createTransfer(
      amount: Long,
      date: LocalDate,
      fromAccountId: Int,
      toAccountId: Int,
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[CreateMoney.Response] =
    request[CreateMoney.Response ](
      url(s"$baseUrl/v2/home/money/transfer").POST << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "from_account_id" -> Some(fromAccountId),
        "to_account_id" -> Some(toAccountId),
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )

  def updatePayment(
      id: Long,
      amount: Long,
      date: LocalDate,
      categoryId: Option[Int]= None,
      genreId: Option[Int] = None,
      fromAccountId: Option[Int] = None,
      name: Option[String] = None,
      place: Option[String] = None,
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/payment/$id").PUT << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "category_id" -> categoryId,
        "genre_id" -> genreId,
        "from_account_id" -> fromAccountId,
        "name" -> name,
        "place" -> place,
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )

  def updateIncome(
      id: Long,
      amount: Long,
      date: LocalDate,
      categoryId: Option[Int] = None,
      toAccountId: Option[Int] = None,
      place: Option[String] = None,
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/income/$id").PUT << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "category_id" -> categoryId,
        "to_account_id" -> toAccountId,
        "place" -> place,
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )

  def updateTransfer(
      id: Long,
      amount: Long,
      date: LocalDate,
      fromAccountId: Option[Int] = None, // check
      toAccountId: Option[Int] = None, // check
      comment: Option[String] = None
  )(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/transfer/$id").PUT << Map(
        "mapping" -> Some("1"),
        "amount" -> Some(amount),
        "date" -> Some(date),
        "from_account_id" -> fromAccountId,
        "to_account_id" -> toAccountId,
        "comment" -> comment
      ).clean.mapValues(_.toString)
    )


  def deletePayment(id: Long)(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/payment/$id").DELETE
    )

  def deleteIncome(id: Long)(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/income/$id").DELETE
    )

  def deleteTransfer(id: Long)(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      url(s"$baseUrl/v2/home/money/transfer/$id").DELETE
    )


  def getAccounts()(implicit ec: ExecutionContext): Future[GetAccounts.Response] =
  request[GetAccounts.Response](
    url(s"$baseUrl/v2/home/account").GET <<? Map(
      "mapping" -> Some("1")
    ).clean
  )

  def getCategories(mode: Option[Mode] = None)(implicit ec: ExecutionContext): Future[GetCategories.Response] =
    request[GetCategories.Response](
      url(s"$baseUrl/v2/home/category").GET <<? Map(
        "mapping" -> Some("1"),
        "mode" -> mode.map(_.raw)
      ).clean
    )

  def getGenres()(implicit ec: ExecutionContext): Future[GetGenres.Response] =
    request[GetGenres.Response](
      url(s"$baseUrl/v2/home/genre").GET <<? Map(
        "mapping" -> Some("1")
      ).clean
    )

  private[this] def request[T](req: Req)(implicit ec: ExecutionContext, fjs: Reads[T]): Future[T] = {
    Http.default(req <@ (consumerKey, accessToken) OK as.String).map { json =>
      if (logger.isDebugEnabled) {
        logger.debug(req.url)
        logger.debug(json)
      }
      Json.parse(json).as[T]
    }
  }
}

object Zaim {
  implicit class OptionValueMapOps[K, V](val map: Map[K, Option[V]]) extends AnyVal {
    def clean: Map[K, V] = map.collect { case (key, Some(value)) => (key, value) }
  }
}