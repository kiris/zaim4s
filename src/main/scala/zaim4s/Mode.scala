package zaim4s

sealed trait Mode {
  def raw: String
  override def toString: String = raw
}

case object Payment extends Mode {
  override def raw: String = "payment"
}
case object Income extends Mode {
  override def raw: String = "income"
}