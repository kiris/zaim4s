package com.github.kiris.zaim4s

import java.time.LocalDateTime

object GetAccounts {

  final case class Response(
      accounts: List[Account]
  ) {
    def toMap: Map[Int, Account] = accounts.map { account => account.id -> account }.toMap
  }

  final case class Account(
      id: Int,
      name: String,
      sort: Int,
      parentAccountId: Int,
      active: Int,
      modified: LocalDateTime
  )
}