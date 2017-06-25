package com.github.kiris.zaim4s

import java.time.LocalDate

import dispatch._
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}
import org.scalatest.Ignore
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global

@Ignore
class ZaimSpec extends org.scalatest.FreeSpec {

  val logger = LoggerFactory.getLogger(getClass)

  val client = Zaim(
    new ConsumerKey(
      Option(System.getenv("ZAIM4S_CONSUMER_KEY_KEY")).getOrElse(""),
      Option(System.getenv("ZAIM4S_CONSUMER_KEY_SECRET")).getOrElse("")
    ),
    new RequestToken(
      Option(System.getenv("ZAIM4S_ACCESS_TOKEN_KEY")).getOrElse(""),
      Option(System.getenv("ZAIM4S_ACCESS_TOKEN_TOKEN")).getOrElse("")
    )
  )

  "#verifyUser" in {
    val r = client.verifyUser().either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#getMoneys" in {
    val r = client.getMoneys(limit = 5).either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#getMoneysGroupByReceiptId" in {
    val r = client.getMoneysGroupByReceiptId(limit = 5).either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createPayment" in {
    val r = client.createPayment(
      amount = 100L,
      date = LocalDate.now(),
      categoryId = 101,
      genreId = 10101,
      fromAccountId = Some(1),
      comment = Some("Test Comment"),
      place = Some("Test Place"),
      name = Some("Test Name")
    ).either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createPayment & #updatePayment" in {
    val r = for {
      r1 <- client.createPayment(
        amount = 100L,
        date = LocalDate.now(),
        categoryId = 101,
        genreId = 10101
      ).either()

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
      ).either()
    } yield (r1, r2)
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createPayment & #deletePayment" in {
    val r = for {
      r1 <- client.createPayment(
        amount = 999L,
        date = LocalDate.now(),
        categoryId = 101,
        genreId = 10101
      ).either()

      r2 <- client.deletePayment(
        id = r1.money.id
      ).either()
    } yield (r1, r2)
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createIncome" in {
    val r = client.createIncome(
      amount = 100L,
      date = LocalDate.now(),
      categoryId = 11,
      toAccountId = Some(1),
      place = Some("Test Place"),
      comment = Some("Test Comment")
    ).either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createIncome & #updateIncome" in {
    val r = for {
      r1 <- client.createIncome(
        amount = 100L,
        date = LocalDate.now(),
        categoryId = 11
      ).either()

      r2 <- client.updateIncome(
        id = r1.money.id,
        amount = 200L,
        date = LocalDate.now(),
        categoryId = Some(12),
        toAccountId = Some(2),
        place = Some("Test Place2"),
        comment = Some("Test Comment2")
      ).either()
    } yield (r1, r2)

    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createIncome & #deleteIncome" in {
    val r = for {
      r1 <- client.createIncome(
        amount = 999L,
        date = LocalDate.now(),
        categoryId = 11
      ).either()

      r2 <- client.deleteIncome(
        id = r1.money.id
      ).either()
    } yield (r1, r2)

    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createTransfer" in {
    val r = client.createTransfer(
      amount = 100L,
      date = LocalDate.now(),
      toAccountId = 1,
      fromAccountId = 1,
      comment = Some("Test Comment")
    ).either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#createTransfer & #updateTransfer" in {
    val r = for {
      r1 <- client.createTransfer(
        amount = 100L,
        date = LocalDate.now(),
        toAccountId = 1,
        fromAccountId = 1
      ).either()

      r2 <- client.updateTransfer(
        id = r1.money.id,
        amount = 200L,
        date = LocalDate.now(),
        toAccountId = Some(2),
        fromAccountId = Some(2),
        comment = Some("Test Comment2")
      ).either()
    } yield (r1, r2)

    logger.debug(r.toString)
    assert(r.isRight)
  }


  "#createTransfer & #deleteTransfer" in {
    val r = for {
      r1 <- client.createTransfer(
        amount = 999L,
        date = LocalDate.now(),
        toAccountId = 1,
        fromAccountId = 1
      ).either()

      r2 <- client.deleteTransfer(
        id = r1.money.id
      ).either()
    } yield (r1, r2)

    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#getAccounts" in {
    val r = client.getAccounts().either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

  "#getCategories" in {
    val r = client.getCategories().either()
    logger.debug(r.toString)
    assert(r.isRight)

    val r2 = client.getCategories(mode = Some(Payment)).either()
    logger.debug(r2.toString)
    assert(r2.isRight)


    val r3 = client.getCategories(mode = Some(Income)).either()
    logger.debug(r3.toString)
    assert(r3.isRight)
  }

  "#getGenres" in {
    val r = client.getGenres().either()
    logger.debug(r.toString)
    assert(r.isRight)
  }

}
