package zaim4s


import dispatch._
import dispatch.oauth._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import play.api.libs.json._
import zaim4s.Formats._

import scala.concurrent.ExecutionContext


case class Zaim(consumerKey: ConsumerKey, accessToken: RequestToken) {

  def userVerify(implicit ec: ExecutionContext): Future[UserVerifyResponse] =
    request[UserVerifyResponse](
      url("https://api.zaim.net/v2/home/user/verify").GET
    )

  def money(searchMoneyCondition: MoneyRequestCondition)(implicit ec: ExecutionContext): Future[MoneyResponse] =
    request[MoneyResponse](
      url("https://api.zaim.net/v2/home/money")
        .setQueryParameters(searchMoneyCondition.toQueryParameters)
        .addQueryParameter("mapping", "1")
        .GET
    )

  def moneyGroupByReceiptId(searchMoneyCondition: MoneyRequestCondition)(implicit ec: ExecutionContext): Future[MoneyGroupByReceiptResponse] =
    request[MoneyGroupByReceiptResponse](
      url("https://api.zaim.net/v2/home/money")
        .setQueryParameters(searchMoneyCondition.toQueryParameters)
        .addQueryParameter("mapping", "1")
        .addQueryParameter("group_by", "receipt_id")
        .GET
    )

  def account(implicit ec: ExecutionContext): Future[AccountResponse] =
    request[AccountResponse](
      url("https://api.zaim.net/v2/home/account").GET
    )

  def category(implicit ec: ExecutionContext): Future[CategoryResponse] =
    request[CategoryResponse](
      url("https://api.zaim.net/v2/home/category").GET
    )

  def genre(implicit ec: ExecutionContext): Future[GenreResponse] =
    request[GenreResponse](
      url("https://api.zaim.net/v2/home/genre").GET
    )

  private[this] def request[T](req: Req)(implicit ec: ExecutionContext, fjs: Reads[T]): Future[T] =
    Http.default(req <@ (consumerKey, accessToken) OK as.Bytes).map(Json.parse(_).as[T])
}



