package com.github.kiris.zaim4s.cmd

import com.github.kiris.zaim4s.OAuthExchange
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, blocking}

object GenAccessToken extends App {
  val exchange = createOAuthExchange()
  val future = for {
    r <- exchange.fetchRequestToken
    accessToken <- r match {
      case Right(requestToken) => getAccessToken(requestToken)
      case Left(m) => Future.successful(Left(m))
    }
  } yield accessToken

  Await.result(future, Duration.Inf) match {
    case Right(accessToken) =>
      println(accessToken.toString)

    case Left(m) =>
      System.err.println(m)
      System.exit(-1)
  }

  private[this] def createOAuthExchange(): OAuthExchange = {
    print("consumer key:")
    val consumerKey = scala.io.StdIn.readLine()
    print("consumer secret:" )
    val consumerSecret = scala.io.StdIn.readLine()
    println("callback url:")
    val callbackUrl = scala.io.StdIn.readLine()

    new OAuthExchange(
      new ConsumerKey(
        consumerKey,
        consumerSecret
      ),
      callbackUrl
    )
  }

  private[this] def getAccessToken(reqToken: RequestToken): Future[Either[String, RequestToken]] = {
    val verifier = blocking {
      val url = exchange.signedAuthorize(reqToken)
      println(s"access to: $url\n")
      print("please input oauth verifier:" )
      scala.io.StdIn.readLine()
    }

    exchange.fetchAccessToken(reqToken, verifier)
  }
}
