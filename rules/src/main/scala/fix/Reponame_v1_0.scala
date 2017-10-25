package fix

import scalafix._
import scala.meta._

final case class Reponame_v1_0(index: SemanticdbIndex)
    extends SemanticRule(index, "Reponame_v1_0") {

  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case t @ Lit.Int(value) =>
        //ctx.removeTokens(t.tokens) + ctx.addRight(t, (value+1).toString)
        ctx.replaceTree(t, Lit.Int(value+1).syntax)
    }.asPatch

  }

}
