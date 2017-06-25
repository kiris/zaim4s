package zaim4s

import dispatch.{Http, HttpExecutor}
import dispatch.oauth.{Exchange, SomeCallback, SomeConsumer, SomeEndpoints, SomeHttp}
import org.asynchttpclient.oauth.ConsumerKey

class OAuthExchange(
    override val consumer: ConsumerKey,
    override val callback: String
) extends Exchange with SomeHttp with SomeConsumer with SomeEndpoints with SomeCallback {

  override def http: HttpExecutor = Http.default

  override def requestToken: String = "https://api.zaim.net/v2/auth/request"

  override def accessToken: String = "https://api.zaim.net/v2/auth/access"

  override def authorize: String = "https://auth.zaim.net/users/auth"
}

