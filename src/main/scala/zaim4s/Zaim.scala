package zaim4s


import java.time.LocalDate

import dispatch._
import dispatch.oauth._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import play.api.libs.json._
import zaim4s.Formats._

import scala.concurrent.ExecutionContext


case class Zaim(consumerKey: ConsumerKey, accessToken: RequestToken) {
  import Zaim.OptionValueMapOps

  private[this] def baseUrl = url("https://api.zaim.net")

  def verifyUser(implicit ec: ExecutionContext): Future[VerifyUserResponse] =
    request[VerifyUserResponse](
      (baseUrl / "v2/home/user/verify").GET
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
  )(implicit ec: ExecutionContext): Future[GetMoneysResponse] =
    request[GetMoneysResponse](
      (baseUrl / "/v2/home/money").GET <<? Map(
        "mapping" -> Some("1"),
        "group_by" -> Some("receipt_id"),
        "category_id" -> categoryId.map(_.toString),
        "genre_id" -> genreId.map(_.toString),
        "mode" -> mode.map(_.raw),
        "order" -> order,
        "start_date" -> startDate.map(_.toString),
        "end_date" -> endDate.map(_.toString),
        "page" -> Some(page.toString),
        "limit" -> Some(limit.toString)
      ).clean
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
  )(implicit ec: ExecutionContext): Future[GetMoneysGroupByReceiptIdResponse] =
    request[GetMoneysGroupByReceiptIdResponse](
      (baseUrl / "v2/home/money").GET <<? Map(
        "mapping" -> Some("1"),
        "group_by" -> Some("receipt_id"),
        "category_id" -> categoryId.map(_.toString),
        "genre_id" -> genreId.map(_.toString),
        "mode" -> mode.map(_.raw),
        "order" -> order,
        "start_date" -> startDate.map(_.toString),
        "end_date" -> endDate.map(_.toString),
        "page" -> Some(page.toString),
        "limit" -> Some(limit.toString)
      ).clean
    )

  def createPayment(
      categoryId: Int,
      genreId: Int,
      amount: Long,
      date: LocalDate,
      toAccountId: Option[Int] = None,
      memo: Option[String] = None
  )(implicit ec: ExecutionContext): Future[JsValue] =
    request[JsValue](
      (baseUrl / "v2/home/money").POST <<? Map(
        "mapping" -> Some("1"),
        "category_id" -> Some(categoryId.toString),
        "genre_id" -> Some(genreId.toString),
        "amount" -> Some(amount.toString),
        "date" -> Some(date.toString),
        "to_account_id" -> toAccountId.map(_.toString)
      ).clean
    )



  def createIncome()(implicit ec: ExecutionContext): Future[Any] = ???

  def createTransfer()(implicit ec: ExecutionContext): Future[Any] = ???

  def updatePayment()(implicit ec: ExecutionContext): Future[Any] = ???

  def updateIncome()(implicit ec: ExecutionContext): Future[Any] = ???

  def updateTransfer()(implicit ec: ExecutionContext): Future[Any] = ???

  def deletePayment()(implicit ec: ExecutionContext): Future[Any] = ???

  def deleteIncome()(implicit ec: ExecutionContext): Future[Any] = ???

  def deleteTransfer()(implicit ec: ExecutionContext): Future[Any] = ???

  def getCategory()(implicit ec: ExecutionContext): Future[Any] = ???

  def getGenre()(implicit ec: ExecutionContext): Future[Any] = ???

  def getAccount()(implicit ec: ExecutionContext): Future[Any] = ???


  def getAccounts(implicit ec: ExecutionContext): Future[GetAccountsResponse] =
    request[GetAccountsResponse](
      (baseUrl / "v2/home/account").GET
    )

  def getCategories(implicit ec: ExecutionContext): Future[GetCategoriesResponse] =
    request[GetCategoriesResponse](
      (baseUrl / "v2/home/category").GET
    )

  def getGenres(implicit ec: ExecutionContext): Future[GetGenresResponse] =
    request[GetGenresResponse](
      (baseUrl / "v2/home/genre").GET
    )

  def getCurrency: Future[Any] = ???
  
  private[this] def request[T](req: Req)(implicit ec: ExecutionContext, fjs: Reads[T]): Future[T] =
    Http.default(req <@ (consumerKey, accessToken) OK as.Bytes).map(Json.parse(_).as[T])
}

object Zaim {
  implicit class OptionValueMapOps[K, V](val map: Map[K, Option[V]]) extends AnyVal {
    def clean: Map[K, V] = map.collect { case (key, Some(value)) => (key, value) }
  }

}