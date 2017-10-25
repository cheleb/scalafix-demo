package fix

import cats.data.EitherK

object Reponame_v1_0_Test {
  val x = 43

  def myMethod[A, F[_], G[_]](implicit c: EitherK[F,G,A]) = ???
}
