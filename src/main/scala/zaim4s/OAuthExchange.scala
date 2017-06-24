package zaim4s

import dispatch.{Http, HttpExecutor}
import dispatch.oauth.{Exchange, SomeCallback, SomeConsumer, SomeEndpoints, SomeHttp}
import org.asynchttpclient.oauth.ConsumerKey

import scala.concurrent.{Await, ExecutionContext, Future, blocking}
import scala.concurrent.duration.Duration

class OAuthExchange(
    override val consumer: ConsumerKey,
    override val callback: String
) extends Exchange with SomeHttp with SomeConsumer with SomeEndpoints with SomeCallback {

  override def http: HttpExecutor = Http.default

  override def requestToken: String = "https://api.zaim.net/v2/auth/request"

  override def accessToken: String = "https://api.zaim.net/v2/auth/access"

  override def authorize: String = "https://auth.zaim.net/users/auth"
}

object OAuthExchange extends App {
  val consumerKey = new ConsumerKey(
    Option(System.getenv("ZAIM4S_CONSUMER_KEY_KEY")).getOrElse(""),
    Option(System.getenv("ZAIM4S_CONSUMER_KEY_SECRET")).getOrElse("")
  )
  val callbackUrl = Option(System.getenv("ZAIM4S_OAUTH_CALLBACK_URL")).getOrElse("")
  val exchange = new OAuthExchange(consumerKey, callbackUrl)
  println(accessToken(exchange))

  private[this] def accessToken(exchange: OAuthExchange) = Await.result(getAccessToken(exchange)(ExecutionContext.global), Duration.Inf)

  private[this] def getAccessToken(exchange: OAuthExchange)(implicit executionContext: ExecutionContext) = {
    for {
      t <- exchange.fetchRequestToken
      accessToken <- t match {
        case Right(reqToken) =>
          val verifier = blocking {
            val url = exchange.signedAuthorize(reqToken)
            println(url)
            print("please input oauth verifier:")
            scala.io.StdIn.readLine()
          }

          exchange.fetchAccessToken(reqToken, verifier)
        case Left(m) =>
          Future.failed(new Exception(m))
      }
    } yield accessToken
  }
}
