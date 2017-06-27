package com.github.kiris.zaim4s

import java.time.LocalDate

import dispatch._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global

class ZaimSpec extends org.scalatest.FreeSpec {

  val logger = LoggerFactory.getLogger(getClass)

  val consumerKey = Option(System.getenv("ZAIM4S_CONSUMER_KEY"))
  val consumerSecret = Option(System.getenv("ZAIM4S_CONSUMER_SECRET"))
  val accessTokenKey = Option(System.getenv("ZAIM4S_ACCESS_TOKEN_KEY"))
  val accessTokenSecret = Option(System.getenv("ZAIM4S_ACCESS_TOKEN_SECRET"))
  val testAvailable = List(consumerKey, consumerSecret, accessTokenKey, accessTokenSecret).forall(_.isDefined)

  val client = Zaim(
    new ConsumerKey(
      consumerKey.getOrElse(""),
      consumerSecret.getOrElse("")
    ),
    new RequestToken(
      accessTokenKey.getOrElse(""),
      accessTokenSecret.getOrElse("")
    )
  )

  "#verifyUser" in {
    assume(testAvailable)
    val r = client.verifyUser()
    logger.debug(r().toString)
  }

  "#getMoneys" in {
    assume(testAvailable)
    val r = client.getMoneys(limit = 5)
    logger.debug(r().toString)
  }

  "#getMoneysGroupByReceiptId" in {
    assume(testAvailable)
    val r = client.getMoneysGroupByReceiptId(limit = 5)
    logger.debug(r().toString)
  }

  "#createPayment" in {
    assume(testAvailable)
    val r = client.createPayment(
      amount = 100L,
      date = LocalDate.now(),
      categoryId = 101,
      genreId = 10101,
      fromAccountId = Some(1),
      comment = Some("Test Comment"),
      place = Some("Test Place"),
      name = Some("Test Name")
    )
    logger.debug(r().toString)
  }

  "#createPayment & #updatePayment" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createPayment(
        amount = 100L,
        date = LocalDate.now(),
        categoryId = 101,
        genreId = 10101
      )

      r2 <- client.updatePayment(
        id = r1.money.id,
        amount = 200L,
        date = LocalDate.now(),
        categoryId = Some(102),
        genreId = Some(10102),
        fromAccountId = Some(1),
        comment = Some("Test Comment2"),
        place = Some("Test Place2"),
        name = Some("Test Name2")
      )
    } yield (r1, r2)
    logger.debug(r().toString)
  }

  "#createPayment & #deletePayment" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createPayment(
        amount = 999L,
        date = LocalDate.now(),
        categoryId = 101,
        genreId = 10101
      )

      r2 <- client.deletePayment(
        id = r1.money.id
      )
    } yield (r1, r2)
    logger.debug(r().toString)

  }

  "#createIncome" in {
    assume(testAvailable)
    val r = client.createIncome(
      amount = 100L,
      date = LocalDate.now(),
      categoryId = 11,
      toAccountId = Some(1),
      place = Some("Test Place"),
      comment = Some("Test Comment")
    )
    logger.debug(r().toString)
  }

  "#createIncome & #updateIncome" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createIncome(
        amount = 100L,
        date = LocalDate.now(),
        categoryId = 11
      )

      r2 <- client.updateIncome(
        id = r1.money.id,
        amount = 200L,
        date = LocalDate.now(),
        categoryId = Some(12),
        toAccountId = Some(2),
        place = Some("Test Place2"),
        comment = Some("Test Comment2")
      )
    } yield (r1, r2)

    logger.debug(r().toString)
  }

  "#createIncome & #deleteIncome" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createIncome(
        amount = 999L,
        date = LocalDate.now(),
        categoryId = 11
      )

      r2 <- client.deleteIncome(
        id = r1.money.id
      )
    } yield (r1, r2)

    logger.debug(r().toString)
  }

  "#createTransfer" in {
    assume(testAvailable)
    val r = client.createTransfer(
      amount = 100L,
      date = LocalDate.now(),
      toAccountId = 1,
      fromAccountId = 1,
      comment = Some("Test Comment")
    )
    logger.debug(r().toString)
  }

  "#createTransfer & #updateTransfer" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createTransfer(
        amount = 100L,
        date = LocalDate.now(),
        toAccountId = 1,
        fromAccountId = 1
      )

      r2 <- client.updateTransfer(
        id = r1.money.id,
        amount = 200L,
        date = LocalDate.now(),
        toAccountId = Some(2),
        fromAccountId = Some(2),
        comment = Some("Test Comment2")
      )
    } yield (r1, r2)

    logger.debug(r().toString)
  }


  "#createTransfer & #deleteTransfer" in {
    assume(testAvailable)
    val r = for {
      r1 <- client.createTransfer(
        amount = 999L,
        date = LocalDate.now(),
        toAccountId = 1,
        fromAccountId = 1
      )

      r2 <- client.deleteTransfer(
        id = r1.money.id
      )
    } yield (r1, r2)

    logger.debug(r().toString)
  }

  "#getAccounts" in {
    assume(testAvailable)
    val r = client.getAccounts()
    logger.debug(r().toString)
  }

  "#getCategories" in {
    assume(testAvailable)
    val r = client.getCategories()
    logger.debug(r().toString)

    val r2 = client.getCategories(mode = Some(Payment))
    logger.debug(r2().toString)

    val r3 = client.getCategories(mode = Some(Income))
    logger.debug(r3().toString)
  }

  "#getGenres" in {
    assume(testAvailable)
    val r = client.getGenres()
    logger.debug(r().toString)
  }

}
