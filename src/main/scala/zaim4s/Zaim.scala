package zaim4s


import dispatch._
import dispatch.oauth._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import play.api.libs.json._
import zaim4s.Formats._

import scala.concurrent.ExecutionContext


case class Zaim(consumerKey: ConsumerKey, accessToken: RequestToken) {

  private val baseUrl:String = "https://api.zaim.net"

  def verifyUser(implicit ec: ExecutionContext): Future[VerifyUserResponse] =
    request[VerifyUserResponse](
      url(s"$baseUrl/v2/home/user/verify").GET
    )

  def getMoneys(condition: GetMoneysRequestCondition)(implicit ec: ExecutionContext): Future[GetMoneysResponse] =
    request[GetMoneysResponse](
      url(s"$baseUrl/v2/home/money")
        .setQueryParameters(condition.toQueryParameters)
        .addQueryParameter("mapping", "1")
        .GET
    )

  def getMoneysGroupByReceiptId(condition: GetMoneysRequestCondition)(implicit ec: ExecutionContext): Future[GetMoneysGroupByReceiptIdResponse] =
    request[GetMoneysGroupByReceiptIdResponse](
      url(s"$baseUrl/v2/home/money")
        .setQueryParameters(condition.toQueryParameters)
        .addQueryParameter("mapping", "1")
        .addQueryParameter("group_by", "receipt_id")
        .GET
    )

  // 
  def createPayment()(implicit ec: ExecutionContext): Future[Any] = ???

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
      url(s"$baseUrl/v2/home/account").GET
    )

  def getCategories(implicit ec: ExecutionContext): Future[GetCategoriesResponse] =
    request[GetCategoriesResponse](
      url(s"$baseUrl/v2/home/category").GET
    )

  def getGenres(implicit ec: ExecutionContext): Future[GetGenresResponse] =
    request[GetGenresResponse](
      url(s"$baseUrl/v2/home/genre").GET
    )

  def getCurrency: Future[Any] = ???
  
  private[this] def request[T](req: Req)(implicit ec: ExecutionContext, fjs: Reads[T]): Future[T] =
    Http.default(req <@ (consumerKey, accessToken) OK as.Bytes).map(Json.parse(_).as[T])
}



