package zaim4s

object GetAccounts {

  final case class Response(
      accounts: List[Account]
  ) {
    def toMap: Map[Int, Account] = accounts.map { account => account.id -> account }.toMap
  }

  final case class Account(
      id: Int,
      name: String
  )
}