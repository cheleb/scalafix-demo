/*
rule = "class:fix.Reponame_v1_0"
*/
package fix

import cats.data.Coproduct

object Reponame_v1_0_Test {
  val x = 42
  "test"

  def myMethod[A, F[_], G[_]](implicit c: Coproduct[F,G,A]) = ???
}
